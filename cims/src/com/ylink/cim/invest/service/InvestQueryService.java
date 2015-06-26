package com.ylink.cim.invest.service;

import java.util.List;
import java.util.Map;

import com.ylink.cim.common.msg.handle.MsgField;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface InvestQueryService {

	/**
	 * 定投计划查询
	 */
	public Paginater queryPlan(Map<String, String> map,
			Pager pager) throws BizException;

	/**
	 * 黄金账户查询(汇总部分)
	 */
	public Map queryGoldAcctTatol(Map<String, String> map) throws BizException;

	/**
	 * 黄金账户查询(汇总部分)
	 */
	public List<Map<String, MsgField>> queryGoldAcct(Map<String, String> map)
			throws BizException;

	/**
	 * 资金账户查询
	 */
	public Paginater queryCashAcct(Map<String, String> map,
			Pager pager) throws BizException;

	/**
	 * 积存情况查询
	 */
	public Paginater queryDepositRecord(
			Map<String, String> map, Pager pager) throws BizException;

	/**
	 * 赎回记录查询
	 */
	public Paginater queryRedeemRecord(
			Map<String, String> map, Pager pager) throws BizException;

	/**
	 * 账户管理费查询
	 */
	public Paginater queryMngFee(Map<String, String> map,
			Pager pager) throws BizException;

	/**
	 * 现金赎回查询
	 */
	public Paginater querySoldRecord(Map<String, String> map,
			Pager pager) throws BizException;

	/**
	 * 黄金账户统计结果查询
	 * 
	 * @param map
	 * @param acctNos
	 * @return
	 * @throws BizException
	 */
	public List<Map<String, MsgField>> querySumGoldAcct(
			Map<String, String> map, String[] acctNos) throws BizException;

	public List<Map<String, MsgField>> queryPlanOperList(Map<String, String> map)
			throws BizException;
}
