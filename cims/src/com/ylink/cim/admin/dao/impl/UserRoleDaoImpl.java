package com.ylink.cim.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.UserRoleDao;
import com.ylink.cim.admin.domain.RoleInfo;
import com.ylink.cim.admin.domain.UserRole;
import com.ylink.cim.admin.domain.UserRoleId;
import com.ylink.cim.user.domain.UserInfo;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.HqlHelper;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("userRoleDao")
public class UserRoleDaoImpl extends BaseDaoHibernateImpl implements UserRoleDao {
	
	public Paginater getUserRolePageList(UserRole userRole, Pager pager) {
		
		QueryHelper helper=new QueryHelper();
		helper.append("select");
		helper.append("ui.userId as userId,ui.userName as userName,ui.loginId as loginId,ui.userType as userType,");
		helper.append("ur.id.roleId as roleId,");
		helper.append("ri.roleName as roleName");
		helper.append("from UserInfo ui,UserRole ur,RoleInfo ri");
		helper.append("where 1=1");
		helper.append("and ui.userId=ur.id.userId");
		helper.append("and ur.id.roleId=ri.roleId");
		helper.append("and ui.userId=?",userRole.getId().getUserId());
		helper.append("and ri.roleName like ?",userRole.getRoleName(),MatchMode.ANYWHERE);
		
		Paginater pageData = super.getPageData(helper, pager);
		List<UserRole> lsRoleInfo=convertToUserRole((List)pageData.getData());
		
		pageData.setData(lsRoleInfo);
		
		return pageData;
	}

	public List<UserRole> getUserRoleByUser(final String id) {
		String hql = "from UserRole u where u.id.userId='" + id + "'";
		return getList(hql);
	}
	private List<UserRole> convertToUserRole(List data) {
		
		List<UserRole> ls=new ArrayList<UserRole>();
		if(null==data){
			return ls;
		}
		
		for(int i=0;i<data.size();i++){
			
			UserRoleId id=new UserRoleId();
			UserRole userRole=new UserRole();
			userRole.setId(id);
			
			Object[] objects =(Object[])data.get(i);
			assingUserRoleValue(userRole,objects);
			
			ls.add(userRole);
		}
		
		return ls;
		
	}

	
	private void assingUserRoleValue(UserRole userRole, Object[] objects) {
		
		String[] columns=new String[]{"userId","userName","loginId","userType"
				,"roleId","roleName"};
		
		for(int i=0;i<columns.length;i++){
			
			Object object = objects[i];

			String value="";
			if(null!=object);{
				value=object.toString();
			}
			
			switch (i) {
				case 0:
					userRole.getId().setUserId(value);
					break;
				case 1:
					userRole.setUserName(value);
					break;
				case 2:
					userRole.setLoginId(value);
					break;
				case 3:
					userRole.setUserType(value);
					break;
				case 4:
					userRole.getId().setRoleId(value);	
					break;
				case 5:
					userRole.setRoleName(value);
					break;
				default:
					break;
			}
		}
	}

	protected Class getModelClass() {
		
		return UserRole.class;
	}

	public Paginater getWaitAssignRolePageList(String userId,Pager pager) {
		
		QueryHelper helper=new QueryHelper();
		helper.append("select ");
		helper.append(" ri.role_id as roleId,");
		helper.append(" ri.role_name as roleName,");
		helper.append(" lgi.limit_group_id as limitGroupId,");
		helper.append(" lgi.limit_group_name as limitGroupName");
		helper.append("from role_info  ri");
		helper.append("inner join limit_group_info lgi on lgi.limit_group_id=ri.limit_group_id");
		helper.append("inner join user_info ui on ui.user_type=lgi.user_type");
		helper.append("where  1=1");
		helper.append("and ui.user_id=?",userId);
		helper.append("and ri.role_id not in (select ur.role_id from user_role ur where ui.user_id= ? )",userId);
		
		Paginater paginater = super.getPageDataBySql(helper, pager);

		List<RoleInfo> lsRoleInfo=convertToRoleInfo((List)paginater.getData());
		paginater.setData(lsRoleInfo);
		
		return paginater;
	}

	
	private List<RoleInfo> convertToRoleInfo(List data) {
		
		List<RoleInfo> ls=new ArrayList<RoleInfo>();
		if(null==data){
			return ls;
		}
		
		for(int i=0;i<data.size();i++){
			RoleInfo roleInfo = new RoleInfo();
			Object[] objects = (Object[])data.get(i);
			
			assignRoleInfoValue(roleInfo,objects);
			
			ls.add(roleInfo);
		}
		return ls;
	}

	
	private void assignRoleInfoValue(RoleInfo roleInfo, Object[] objects) {
		
		String[] columns=new String[]{"roleId","roleName","limitGroupId","limitGroupName"};
		for(int i=0;i<columns.length;i++){
			Object object=objects[i];
			String value="";
			if(null!=object){
				value=object.toString();
			}
			
			switch (i) {
			case 0:
				roleInfo.setRoleId(value);
				break;
			case 1:
				roleInfo.setRoleName(value);
				break;
			case 2:
				roleInfo.setLimitGroupId(value);
				break;
			case 3:
				roleInfo.setLimitGroupName(value);
				break;

			default:
				break;
			}
		}
	}

	public List<RoleInfo> getRoleByUser(String userId) {
		QueryHelper helper = new QueryHelper();
		helper.append("from RoleInfo t where t.roleId in (select u.id.roleId from UserRole u where u.id.userId = ?)", userId);
		return super.getList(helper);
	}

	public Paginater getAvailableRoles(String userId, Pager pager) {
		UserInfo userInfo = super.findById(UserInfo.class, userId);
		QueryHelper helper = new QueryHelper();
		helper.append("from RoleInfo t where t.limitGroupId in (select g.limitGroupId from LimitGroupInfo g where g.userType = ?)", userInfo.getUserType());
		return super.getPageData(helper, pager);
	}

	public void delRoleByUser(String userId) {
		HqlHelper helper = new HqlHelper();
		helper.append("delete from UserRole t where t.id.userId = ?", userId);
		super.execute(helper);
	}

	public List<UserRole> getUserRoleByRole(String roleId) {
		QueryHelper helper = new QueryHelper();
		helper.append("from UserRole t where t.id.roleId = ?", roleId);
		return super.getList(helper);
	}


}