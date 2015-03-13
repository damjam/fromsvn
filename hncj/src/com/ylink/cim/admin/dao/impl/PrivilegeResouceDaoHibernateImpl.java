package com.ylink.cim.admin.dao.impl;

import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.PrivilegeResourceDao;
import com.ylink.cim.admin.domain.PrivilegeResource;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("privilegeResourceDao")
public class PrivilegeResouceDaoHibernateImpl extends BaseDaoHibernateImpl 
	implements PrivilegeResourceDao {

	
	protected Class getModelClass() {
		
		return PrivilegeResource.class;
	}

	public Paginater getPrivilegeResourceList(
			PrivilegeResource privilegeResource,Pager pager) {
		
		QueryHelper helper=new QueryHelper();
		helper.append("from PrivilegeResource p ");
		helper.append("where 1=1");
		helper.append("and p.id=?",privilegeResource.getId());
		helper.append("and p.limitId=?",privilegeResource.getLimitId());
		helper.append("and p.url=?",privilegeResource.getUrl());
		helper.append("and p.param=?",privilegeResource.getParam());
		helper.append("and p.isEntry=?",privilegeResource.getIsEntry());
		
		return super.getPageData(helper, pager);
	}

}
