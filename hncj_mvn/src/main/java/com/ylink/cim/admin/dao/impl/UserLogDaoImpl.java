package com.ylink.cim.admin.dao.impl;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.UserLogDao;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserLog;
import com.ylink.cim.common.type.BranchType;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("userLogDao")
public class UserLogDaoImpl extends BaseDaoImpl implements UserLogDao {
	@Override
	protected Class getModelClass() {
		return UserLog.class;
	}

	/**
	 * 查询结果转化成hashMap
	 * 
	 * @param pageDataBySql
	 * @return
	 */
	@Override
	public Paginater getUserLogPageList(UserLog userLog, Pager pager,
			UserInfo userInfo) {
		QueryHelper helper = new QueryHelper();
		helper.append("from UserLog where 1=1");
		helper.append("and limitId = ?", userLog.getLimitId());
		helper.append("and content like ?", userLog.getContent(),
				MatchMode.ANYWHERE);
		helper.append("and logType = ?", userLog.getLogType());
		helper.append("and loginIp = ?", userLog.getLoginIp());
		helper.append("and limitId = ?", userLog.getLimitId());
		helper.append("and userId = ?", userLog.getUserId());
		if (!BranchType.HQ_0000.getValue().equals(userInfo.getBranchNo())) {
			helper.append("and branchNo = ?", userInfo.getBranchNo());
		}
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	@Override
	public void saveUserLog(UserLog userLog) throws Exception {
		this.save(userLog);

	}

}
