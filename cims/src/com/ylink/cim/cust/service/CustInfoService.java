package com.ylink.cim.cust.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ylink.cim.cust.domain.CustInfo;

import flink.etc.BizException;

public interface CustInfoService {
	/**
	 * ͨ��userId��ȡ���ͻ�
	 * 
	 */
	public CustInfo getCustInfoByUserId(String userId);
	/**
	 * ͨ��CustInfo������ȡ��ʵ��
	 * @param custId
	 * @return
	 */
	public CustInfo getCustInfoById(String custId);
	/**
	 * ��ӿͻ�
	 * @param custInfo
	 */
	public String addCustBasicInfo(CustInfo custInfo) throws BizException;
	/**
	 * ��ǩԼ���
	 * @param custId
	 * @param result
	 */
	public void updateSignResult(String custId, boolean result);
	/**
	 * ��ӳ�Ϊ��Ա������Ϣ
	 * @param custInfo
	 */
	public String addMemberInfo(CustInfo custInfo, String branchInfo, HttpServletRequest request, HttpServletResponse response) throws BizException;
	/**
	 * �޸Ŀͻ���Ϣ
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
