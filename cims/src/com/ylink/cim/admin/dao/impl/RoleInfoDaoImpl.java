package com.ylink.cim.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.RoleInfoDao;
import com.ylink.cim.admin.domain.LimitGroupInfo;
import com.ylink.cim.admin.domain.RoleInfo;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("roleInfoDao")
public class RoleInfoDaoImpl extends BaseDaoHibernateImpl implements RoleInfoDao {

	public Paginater getRoleInfoPageList(RoleInfo roleInfo, Pager pager) {
		
		QueryHelper helper=new QueryHelper();
		helper.append("from RoleInfo ri,LimitGroupInfo lgi");
		helper.append("where 1=1");
		helper.append("and ri.limitGroupId=lgi.limitGroupId");
		helper.append("and ri.roleName like ? ",roleInfo.getRoleName(),MatchMode.ANYWHERE);
		helper.append("and lgi.limitGroupName like ? ",roleInfo.getLimitGroupName(),MatchMode.ANYWHERE);
		
		Paginater paginater = super.getPageData(helper, pager);
		List<RoleInfo> ls=convert((List)paginater.getData());
		paginater.setData(ls);
		
		return paginater;
	}

	private List<RoleInfo> convert(List data) {
		
		List<RoleInfo> ls=new ArrayList<RoleInfo>();
		if(null==data){
			return ls;
		}
		
		for(int i=0;i<data.size();i++){
			Object[] object =(Object[]) data.get(i);
			
			RoleInfo roleInfo=(RoleInfo)object[0];
			LimitGroupInfo limitGroupInfo=(LimitGroupInfo)object[1];
			
			roleInfo.setLimitGroupId(limitGroupInfo.getLimitGroupId());
			roleInfo.setLimitGroupName(limitGroupInfo.getLimitGroupName());
			
			ls.add(roleInfo);
		}
		
		return ls;
	}

	public void deleteRoleLimits(RoleInfo roleInfo) {
		
		QueryHelper helper=new QueryHelper();
		helper.append("delete from RolePrivilege rp ");
		helper.append("where 1=1");
		helper.append("and rp.id.roleId=?",roleInfo.getRoleId());
		super.execute(helper);
	}


	protected Class getModelClass() {
		 
		return RoleInfo.class;
	}

	public RoleInfo getRoleInfo(RoleInfo roleInfo) {
		
		QueryHelper helper=new QueryHelper();
		
		helper.append("from RoleInfo ri,LimitGroupInfo lgi");
		helper.append("where 1=1");
		helper.append("and ri.limitGroupId=lgi.limitGroupId");
		helper.append("and ri.roleId=?",roleInfo.getRoleId());
		
		List ls = super.getList(helper);
		
		return this.convert(ls).get(0);
	}

	public List<RoleInfo> queryRoleInfoByLimitGroupId(String limitGroupId) {
		
		QueryHelper helper=new QueryHelper();
		helper.append("from RoleInfo ri");
		helper.append("where 1=1");
		helper.append("and ri.limitGroupId=?",limitGroupId);
		return getList(helper);
	}
	
	
	
}
