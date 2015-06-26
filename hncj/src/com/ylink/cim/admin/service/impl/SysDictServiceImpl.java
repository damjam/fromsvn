package com.ylink.cim.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.SysDictDao;
import com.ylink.cim.admin.domain.SysDict;
import com.ylink.cim.admin.domain.SysDictId;
import com.ylink.cim.admin.service.SysDictService;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;
@Component("sysDictService")
public class SysDictServiceImpl implements SysDictService{

	@Autowired
	private SysDictDao sysDictDao;
	
	public void deleteSysDict(SysDict sysDict) throws BizException {

		this.sysDictDao.deleteById(sysDict.getId());
		
	}

	public SysDict getSysDict(SysDictId id) throws BizException {
		
		return this.sysDictDao.findById(id);
	}

	public List<SysDict> getSysDictByDictType(String dictType)
			throws BizException {
		 
		return this.sysDictDao.getSysDictByDictType(dictType);
	}

	public Paginater getSysDictPageList(SysDict sysDict,Pager pager) throws BizException {
		
		return this.sysDictDao.getSysDictPageList(sysDict, pager);
	}

	public boolean isExist(SysDictId id) throws BizException {
	
		return null!=this.sysDictDao.findById(id);
	}

	public void saveSysDict(SysDict sysDict) throws BizException {
		
		this.sysDictDao.save(sysDict);
	}

	public void setSysDictDao(SysDictDao sysDictDao) {
		this.sysDictDao = sysDictDao;
	}

}
