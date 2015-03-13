package com.ylink.cim.invest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ylink.cim.common.msg.handle.MsgA1014;
import com.ylink.cim.common.msg.handle.MsgA1016;
import com.ylink.cim.common.msg.handle.MsgA2034;
import com.ylink.cim.common.msg.handle.MsgA2040;
import com.ylink.cim.common.msg.handle.MsgA2042;
import com.ylink.cim.common.msg.handle.MsgA2043;
import com.ylink.cim.common.msg.handle.MsgA2044;
import com.ylink.cim.common.msg.handle.MsgA2046;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.invest.service.GoldAcctService;

import flink.etc.BizException;
@Component("goldAcctService")
public class GoldAcctServiceImpl implements GoldAcctService {
	//现金赎回A1014
	public String addRedeemCash(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A1014");
		Map<String, MsgField> resMap = MsgUtil.getResBody(map, MsgA1014.A1014REQ, MsgA1014.A1014RES);
		return resMap.get(MsgField.order_no.getFieldCode()).getValue();
	}
	//实物赎回(A1016)
	public String addRedeemGold(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A1016");
		Map<String, MsgField> resMap = MsgUtil.getResBody(map, MsgA1016.A1016REQ, MsgA1016.A1016RES);
		return resMap.get(MsgField.local_serial_no.getFieldCode()).getValue();
	}
	/**
	 * 查询提货网点(A2034)
	 */
	public List<Map<String, MsgField>> findStore(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A2034");
		List<Map<String, MsgField>> resMapList = MsgUtil.getResBodyList(map, MsgA2034.A2034REQ, MsgA2034.A2034RES);
		return resMapList;
	}
	//库存查询(A2044)提供或现金赎回时应查询存库存，而非积存
	public Map<String, String> findGoldStock(Map<String, String> map, String[] investAcctNos) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A2044");
		Map<String, String> stockMap = new HashMap<String, String>();
		for (int i = 0; i < investAcctNos.length; i++) {
			map.put(MsgField.aip_no.getFieldCode(), investAcctNos[i]);
			Map<String, MsgField> resMap = MsgUtil.getResBody(map, MsgA2044.A2044REQ, MsgA2044.A2044RES);
			stockMap.put(investAcctNos[i], resMap.get(MsgField.curr_can_use.getFieldCode()).getValue());
		}
		return stockMap;
	}
	//提货网点查询(A2013)
	public List<Map<String, String>> findStoreBranch(Map<String, String> paraMap) throws BizException {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		paraMap.put(MsgField.h_exch_code.getFieldCode(), "A2043");
		List<Map<String, MsgField>> resMapList = MsgUtil.getResBodyList(paraMap, MsgA2043.A2043REQ, MsgA2043.A2043RES);
		for (int i = 0; i < resMapList.size(); i++) {
			Map<String, MsgField> map = resMapList.get(i);
			Map<String, String> resMap = new HashMap<String, String>();
			resMap.put(MsgField.branch_id.getFieldCode(), map.get(MsgField.branch_id.getFieldCode()).getValue());
			resMap.put(MsgField.branch_name.getFieldCode(), map.get(MsgField.branch_name.getFieldCode()).getValue());
			resMap.put(MsgField.stor_id.getFieldCode(), map.get(MsgField.stor_id.getFieldCode()).getValue());
			resMap.put(MsgField.contacter.getFieldCode(), map.get(MsgField.contacter.getFieldCode()).getValue());
			resMap.put(MsgField.addr.getFieldCode(), map.get(MsgField.addr.getFieldCode()).getValue());
			resMap.put(MsgField.b_contacter.getFieldCode(), map.get(MsgField.b_contacter.getFieldCode()).getValue());
			resMap.put(MsgField.b_addr.getFieldCode(), map.get(MsgField.b_addr.getFieldCode()).getValue());
			resMap.put(MsgField.tel.getFieldCode(), map.get(MsgField.tel.getFieldCode()).getValue());
			mapList.add(resMap);
		}
		return mapList;
	}
	//
	public List<Map<String, MsgField>> findTakeDate(Map<String, String> map, String[] takeDates) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A2046");
		List<Map<String, MsgField>> list = new ArrayList<Map<String,MsgField>>();
		for (int i = 0; i < takeDates.length; i++) {
			map.put(MsgField.take_month.getFieldCode(), takeDates[i]);
			list.addAll(MsgUtil.getResBodyList(map, MsgA2046.A2046REQ, MsgA2046.A2046RES));
		}
		return list;
	}
	public Map<String, String> findStoreCity() throws BizException {
		Map<String, String> reqMap = new HashMap<String, String>();
		Map<String, String> resMap = new HashMap<String, String>();
		reqMap.put(MsgField.h_exch_code.getFieldCode(), "A2042");
		List<Map<String, MsgField>> resMapList = MsgUtil.getResBodyList(reqMap, MsgA2042.A2042REQ, MsgA2042.A2042RES);
		for (int i = 0; i < resMapList.size(); i++) {
			Map<String, MsgField> map = resMapList.get(i);
			resMap.put(map.get(MsgField.city_code.getFieldCode()).getValue(), map.get(MsgField.city_name.getFieldCode()).getValue());
		}
		return resMap;
	}
	public List<Map<String, MsgField>> findGoldVariety(Map<String, String> map) throws BizException {
		if (map == null) {
			map = new HashMap<String, String>();
		}
		map.put(MsgField.h_exch_code.getFieldCode(), "A2040");
		List<Map<String, MsgField>> resMapList = MsgUtil.getResBodyList(map, MsgA2040.A2040REQ, MsgA2040.A2040RES);
		
		return resMapList;
	}
	
}
