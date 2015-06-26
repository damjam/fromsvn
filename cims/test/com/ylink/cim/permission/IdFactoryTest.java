package com.ylink.cim.permission;

import com.ylink.cim.admin.service.IdFactoryService;

import flink.consant.IdFactoryConstant;
import flink.etc.BizException;
import flink.util.LoggerCacheUtils;
import flink.util.SpringContextUtil;
import junit.framework.TestCase;

public class IdFactoryTest extends TestCase{
	
	public void testGetId() throws BizException{
		
		IdFactoryService idFactoryService=(IdFactoryService)SpringContextUtil.getBean("idFactoryService");
		String id = idFactoryService.generateId(IdFactoryConstant.PRIVILEG_ERESOURCE_ID.getValue());
		LoggerCacheUtils.getLogger(IdFactoryTest.class).info(id);
	}
	
	 
}
