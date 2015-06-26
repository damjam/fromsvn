package com.ylink.cim.admin.service;

import java.util.List;

import com.ylink.cim.admin.domain.LimitGroupInfo;
import com.ylink.cim.admin.domain.SysDict;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface LimitGroupInfoService {

	public Paginater getLimitGroupInfoPageList(LimitGroupInfo limitGroupInfo,
			Pager pager)throws BizException;
	
	
	public void deleteLimitGroupInfo(String limitGroupId) throws BizException;
	
	public void saveLimitGroup(LimitGroupInfo limitGroupInfo) throws BizException; 
	
	
	public List<SysDict> getSysDictNoLimitGroup() throws BizException;
	
	
	public void updateLimitGroupInfo(LimitGroupInfo limitGroupInfo) throws BizException;
	
	public LimitGroupInfo getLimitGroupInfoById(String limitGroupId) throws BizException;
	
	public List<LimitGroupInfo> getAll() throws BizException;
	
}
