package com.ylink.cim.invest.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.invest.domain.SignContract;

import flink.etc.BizException;

public interface SignContractService {

	String addSignContract(SignContract signContract, HttpServletRequest request, HttpServletResponse response) throws BizException;

	String updateSignContract(String acctNo, String payChnlNo, HttpServletRequest request, HttpServletResponse response) throws BizException;
	
	public String signSuccess(Map<String, String> map) throws BizException;

	public List<Map<String, String>> queryInvestAcctList(Map<String, String> msgMap, String[] acctNos)
			throws BizException;

	public String modifyInvestAcct(Map<String, String> msgMap) throws BizException;

	public Map<String, MsgField> queryInvestAcctStock(Map<String, String> msgMap) throws BizException;

	String cancelSuccess(String accreditId) throws BizException;

	boolean checkSign(String accreditId) throws BizException;
	
	boolean checkCancel(String accreditId) throws BizException;
	/**
	 * 解约
	 * @param request
	 * @param response
	 * @param acctNo
	 * @throws BizException
	 */
	void cancelSign(HttpServletRequest request, HttpServletResponse response, String acctNo) throws BizException;
	/**
	 * 销户
	 * @param request
	 * @param response
	 * @param acctNo
	 * @throws BizException
	 */
	void cancelAcct(HttpServletRequest request, HttpServletResponse response, String acctNo) throws BizException;
}
