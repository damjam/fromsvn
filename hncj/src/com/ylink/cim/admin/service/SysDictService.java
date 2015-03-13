package com.ylink.cim.admin.service;

import java.util.List;

import com.ylink.cim.admin.domain.SysDict;
import com.ylink.cim.admin.domain.SysDictId;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface SysDictService {

	public Paginater getSysDictPageList(SysDict sysDict,Pager pager) throws BizException;
	
	public void saveSysDict(SysDict sysDict) throws BizException;
	
	public void deleteSysDict(SysDict sysDict) throws BizException;
	
	public boolean isExist(SysDictId id) throws BizException;
	
	public List<SysDict> getSysDictByDictType(String dictType) throws BizException;
	
	public SysDict getSysDict(SysDictId id) throws BizException;
	
}
