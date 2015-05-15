package com.ylink.cim.admin.dao.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.UserInfoDao;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.type.UserType;
import com.ylink.cim.common.util.ParaManager;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.IPrivilege;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.WebResource;

/**
 * <p>
 * Title: 用户管理
 * </p>
 * <p>
 * Description:查询、新增、修改
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 雁联
 * </p>
 * 
 * @version 1.0
 */
@Component("userInfoDao")
public class UserInfoDaoImpl extends BaseDaoHibernateImpl implements UserInfoDao {

	public UserInfo getUserInfoByLoginId(String loginId) {

		QueryHelper queryHelper = new QueryHelper();
		if (ParaManager.isCustServer()) {
			queryHelper
					.append("from UserInfo ui where 1=1 and exists (select userId from CustInfo ci where ui.userId = ci.userId ");
			queryHelper.append("and (ci.mobile = ?", loginId);
			queryHelper.append("or ci.idCard = ?))", loginId);
		} else {
			queryHelper.append("from UserInfo ui where ui.loginId =?", loginId);
		}
		return (UserInfo) this.getUniqueResult(queryHelper);
	}

	public Date getDBTime() {
		String hql = "SELECT CURRENT_TIME() from UserInfo u";
		List<Date> list = this.getList(hql);
		return list.get(0);
	}

	public void changePwd(String userId, String newPwd) {
		String hql = "update UserInfo u set u.loginPwd='" + newPwd + "' where u.userId='" + userId + "'";
		execute(hql);
	}

	public List<IPrivilege> getAllPriv(String userId) {
		QueryHelper helper = new QueryHelper();

		helper.append("select p.limit_id, p.limit_name, p.parent, p.is_menu");
		helper.append("from privilege p where 1=1");
		userPrivFilter(userId, helper);
		helper.append("order by p.parent, p.menu_order");

		List<Object[]> list = getListBySql(helper);
		List<IPrivilege> menus = new ArrayList<IPrivilege>();

		for (Object[] element : list) {
			Privilege p = new Privilege();
			p.setLimitId((String) element[0]);
			p.setLimitName((String) element[1]);
			p.setParent((String) element[2]);
			p.setIsMenu(String.valueOf(element[3]));

			menus.add(p);
		}

		return menus;
	}

	/**
	 * 获取子权限列表
	 * 
	 * @param parentPrivId
	 * @return
	 */
	private List<String> getSubPriv(String parentPrivId) {
		String hql = "select p.LIMIT_ID from privilege p" + " start with p.LIMIT_ID='" + parentPrivId + "'"
				+ " connect by  prior p.LIMIT_ID = p.parent";
		return getListBySql(hql);
	}

	/**
	 * 用户权限过滤
	 * 
	 * @param userId
	 * @param helper
	 */
	private void userPrivFilter(final String userId, QueryHelper helper) {
		String condtion = this.getSqlStrForUserPriv(userId);
		if (condtion.length() > 0) {
			helper.append(" and p.limit_id in(" + condtion + ")");
		}
	}

	/**
	 * 获取用户所有权限点的sql语句
	 * 
	 * @param userId
	 * @return
	 */
	private String getSqlStrForUserPriv(String userId) {
		String condtion = "";
		UserInfo userInfo = (UserInfo) findById(userId);
		if (!userInfo.getUserType().equals(UserType.SUPER_ADMIN.getValue())) {// 不是测试
			condtion = "select rp.LIMIT_ID from ROLE_PRIVILEGE rp left join USER_ROLE ur on rp.ROLE_ID = ur.ROLE_ID "
					+ "where ur.USER_ID='" + userId + "'";

		}
		return condtion;
	}

	public List<String> getPrivilege(String userId) {
		String sql = this.getSqlStrForUserPriv(userId);
		String finalString = "select p.limit_id from PRIVILEGE p where p.limit_id in(" + sql + ")";
		List<String> list = this.getListBySql(finalString);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<WebResource> getPrivilegeResources(List<IPrivilege> listOfPriv) {
		if (listOfPriv == null || listOfPriv.size() == 0) {// 没有权限的，则返回空的列表
			return new ArrayList<WebResource>();
		}
		StringBuilder sb = new StringBuilder();
		for (IPrivilege p : listOfPriv) {
			sb.append(",'" + p.getCode() + "'");
		}
		String limits = sb.toString().substring(1);

		QueryHelper helper = new QueryHelper();
		helper.append("select r");
		helper.append("from PrivilegeResource r where r.limitId in (" + limits + ")");
		return getList(helper);
	}

	/**
	 * 序号格式化:两位数字
	 */
	private static DecimalFormat formatOfSeq = new DecimalFormat("00");

	public UserInfo getUserByLoginId(String loginId, String passwordAfterCrypt) {
		QueryHelper sql = new QueryHelper();
		sql.append(" from UserInfo u where u.loginId=? ", loginId);
		sql.append(" and u.userPwd=?", passwordAfterCrypt);
		List<UserInfo> list = this.getList(sql);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	public UserInfo getUserInfoById(String string) {
		return (UserInfo) findById(UserInfo.class, string);
	}

	protected Class<UserInfo> getModelClass() {
		return UserInfo.class;
	}

	public Paginater getUserInfoPageList(UserInfo userInfo, Pager pager) {
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.append("from UserInfo ui");
		queryHelper.append("where 1=1");
		queryHelper.append("and ui.userId <> ? ", userInfo.getUserId());
		queryHelper.append("and ui.userName like ? ", userInfo.getUserName(), MatchMode.ANYWHERE);
		queryHelper.append("and ui.userType = ? ", userInfo.getUserType());
		// queryHelper.append("and ui.userType <> ?",
		// UserType.CUSTOM.getValue());
		queryHelper.append("and ui.loginId = ?", userInfo.getLoginId());
		/*
		 * if (!BranchType.HQ_0000.getValue().equals(userInfo.getBranchNo())) {
		 * queryHelper.append("and ui.branchNo = ?", userInfo.getBranchNo());
		 * queryHelper
		 * .append("and ui.userType <> ?",UserType.BRANCH_ADMIN_USER.getValue
		 * ()); }
		 */
		return super.getPageData(queryHelper, pager);
	}

	public Paginater getPopUpUserInfoPageList(UserInfo userInfo, Pager pager) {
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.append("from UserInfo ui");
		queryHelper.append("where 1=1");
		queryHelper.append("and ui.userId like ? ", userInfo.getUserId(), MatchMode.ANYWHERE);
		queryHelper.append("and ui.userName like ? ", userInfo.getUserName(), MatchMode.ANYWHERE);
		queryHelper.append("and ui.userType =? ", userInfo.getUserType());

		return super.getPageData(queryHelper, pager);
	}

	public boolean isExistLoginIdExpellUserId(String loginId, String userId) {

		QueryHelper helper = new QueryHelper();
		helper.append("from UserInfo ui");
		helper.append("where 1=1");
		helper.append("and ui.loginId=?", loginId);
		helper.append("and ui.userId<>?", userId);

		List list = super.getList(helper);

		return null != list && list.size() > 0;
	}

}
