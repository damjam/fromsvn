package com.ylink.cim.invest.service;

import java.util.List;
import java.util.Map;

import com.ylink.cim.common.msg.handle.MsgField;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface InvestQueryService {

	/**
	 * ��Ͷ�ƻ���ѯ
	 */
	public Paginater queryPlan(Map<String, String> map, Pager pager) throws BizException;

	/**
	 * �ƽ��˻���ѯ(���ܲ���)
	 */
	public Map queryGoldAcctTatol(Map<String, String> map) throws BizException;

	/**
	 * �ƽ��˻���ѯ(���ܲ���)
	 */
	public List<Map<String, MsgField>> queryGoldAcct(Map<String, String> map) throws BizException;

	/**
	 * �ʽ��˻���ѯ
	 */
	public Paginater queryCashAcct(Map<String, String> map, Pager pager) throws BizException;

	/**
	 * ���������ѯ
	 */
	public Paginater queryDepositRecord(Map<String, String> map, Pager pager) throws BizException;

	/**
	 * ��ؼ�¼��ѯ
	 */
	public Paginater queryRedeemRecord(Map<String, String> map, Pager pager) throws BizException;

	/**
	 * �˻�����Ѳ�ѯ
	 */
	public Paginater queryMngFee(Map<String, String> map, Pager pager) throws BizException;

	/**
	 * �ֽ���ز�ѯ
	 */
	public Paginater querySoldRecord(Map<String, String> map, Pager pager) throws BizException;

	/**
	 * �ƽ��˻�ͳ�ƽ����ѯ
	 * 
	 * @param map
	 * @param acctNos
	 * @return
	 * @throws BizException
	 */
	public List<Map<String, MsgField>> querySumGoldAcct(Map<String, String> map, String[] acctNos) throws BizException;

	public List<Map<String, MsgField>> queryPlanOperList(Map<String, String> map) throws BizException;
}
