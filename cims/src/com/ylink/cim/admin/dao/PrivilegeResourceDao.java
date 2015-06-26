package com.ylink.cim.admin.dao;

import com.ylink.cim.admin.domain.PrivilegeResource;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface PrivilegeResourceDao extends BaseDao{
	
	public Paginater getPrivilegeResourceList(PrivilegeResource privilegeResource,Pager pager); 
	
	
}
