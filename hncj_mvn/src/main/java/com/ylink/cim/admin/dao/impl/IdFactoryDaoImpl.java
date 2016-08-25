package com.ylink.cim.admin.dao.impl;

import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.IdFactoryDao;
import com.ylink.cim.admin.domain.IdFactory;

import flink.hibernate.BaseDaoImpl;

@Component("idFactoryDao")
public class IdFactoryDaoImpl extends BaseDaoImpl implements IdFactoryDao {

	@Override
	public void deleteIdFactory(String seqIdName) {
		super.deleteById(seqIdName);

	}

	@Override
	public IdFactory getIdFactory(String seqIdName) {

		return (IdFactory) super.findById(seqIdName);
	}

	@Override
	public void saveIdFactory(IdFactory idFactory) {
		super.save(idFactory);
	}

	@Override
	protected Class<?> getModelClass() {

		return IdFactory.class;
	}

}
