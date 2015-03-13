package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.IdFactory;

import flink.etc.BizException;

public interface IdFactoryService {

	public void saveIdFactory(IdFactory idFactory) throws BizException;
	
	public void deleteIdFactory(String seqIdName) throws BizException;
	
	public String generateId(String seqIdName) throws BizException;
	
	
}
