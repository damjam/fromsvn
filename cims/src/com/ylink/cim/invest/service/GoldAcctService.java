package com.ylink.cim.invest.service;

import java.util.List;
import java.util.Map;

import com.ylink.cim.common.msg.handle.MsgField;

import flink.etc.BizException;

public interface GoldAcctService {

	String addRedeemCash(Map<String, String> map) throws BizException;

	String addRedeemGold(Map<String, String> map) throws BizException;

	// 查询提货网点
	List<Map<String, String>> findStoreBranch(Map<String, String> map) throws BizException;

	// 查询库存
	Map<String, String> findGoldStock(Map<String, String> map, String[] investAcctNos) throws BizException;

	// 提货日查询
	List<Map<String, MsgField>> findTakeDate(Map<String, String> map, String[] takeDate) throws BizException;

	// 提货城市查询
	Map<String, String> findStoreCity() throws BizException;

	// 查询提货品种
	List<Map<String, MsgField>> findGoldVariety(Map<String, String> map) throws BizException;

}
