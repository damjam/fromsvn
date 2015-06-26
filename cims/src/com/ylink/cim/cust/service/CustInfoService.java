package com.ylink.cim.cust.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ylink.cim.cust.domain.CustInfo;

import flink.etc.BizException;

public interface CustInfoService {
	/**
	 * 通过userId获取到客户
	 * 
	 */
	public CustInfo getCustInfoByUserId(String userId);
	/**
	 * 通过CustInfo主键获取到实体
	 * @param custId
	 * @return
	 */
	public CustInfo getCustInfoById(String custId);
	/**
	 * 添加客户
	 * @param custInfo
	 */
	public String addCustBasicInfo(CustInfo custInfo) throws BizException;
	/**
	 * 置签约结果
	 * @param custId
	 * @param result
	 */
	public void updateSignResult(String custId, boolean result);
	/**
	 * 添加成为会员必填信息
	 * @param custInfo
	 */
	public String addMemberInfo(CustInfo custInfo, String branchInfo, HttpServletRequest request, HttpServletResponse response) throws BizException;
	/**
	 * 修改客户信息
	 * @param custInfo
	 */
	public void updateCustInfo(CustInfo custInfo) throws BizException;
	/**
	 * 
	 * @param custId
	 * @param loginPwd
	 */
	public void resetCustPwd(String custId, String loginPwd) throws BizException;
	
	public void addMemberRole(String custId) throws BizException; 
}
