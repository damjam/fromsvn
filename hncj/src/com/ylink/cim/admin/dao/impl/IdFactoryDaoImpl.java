package com.ylink.cim.admin.dao.impl;

import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.IdFactoryDao;
import com.ylink.cim.admin.domain.IdFactory;

import flink.hibernate.BaseDaoImpl;

@Component("idFactoryDao")
public class IdFactoryDaoImpl extends BaseDaoImpl implements IdFactoryDao {

	public void deleteIdFactory(String seqIdName) {
		super.deleteById(seqIdName);

	}

	public IdFactory getIdFactory(String seqIdName) {

		return (IdFactory) super.findById(seqIdName);
	}

	public void saveIdFactory(IdFactory idFactory) {
		super.save(idFactory);
	}

	protected Class getModelClass() {

		return IdFactory.class;
	}

}
