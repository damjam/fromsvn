package com.ylink.cim.admin.dao;

import com.ylink.cim.admin.domain.IdFactory;

import flink.hibernate.BaseDao;

public interface IdFactoryDao extends BaseDao {
	
	public void saveIdFactory(IdFactory idFactory);
	
	public IdFactory getIdFactory(String seqIdName);
	
	public void deleteIdFactory(String seqIdName);
}
