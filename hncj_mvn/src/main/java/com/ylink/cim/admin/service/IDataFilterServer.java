package com.ylink.cim.admin.service;

import java.util.List;

import com.ylink.cim.admin.domain.UserInfo;

/**
 * 
 */
public interface IDataFilterServer {
	
	public List<String> getAllAvailableMrcht(UserInfo userInfo);

	
	public List<String> getAvailableBranch(UserInfo userInfo);

	
	public List<String> getAvailableBranchForBus(UserInfo userInfo);

	
	public List<String> getAvailableChnl(UserInfo userInfo);

}
