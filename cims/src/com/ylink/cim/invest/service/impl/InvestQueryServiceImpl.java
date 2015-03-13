package com.ylink.cim.invest.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.common.msg.handle.MsgA2032;
import com.ylink.cim.common.msg.handle.MsgA2033;
import com.ylink.cim.common.msg.handle.MsgA2034;
import com.ylink.cim.common.msg.handle.MsgA2035;
import com.ylink.cim.common.msg.handle.MsgA2036;
import com.ylink.cim.common.msg.handle.MsgA2038;
import com.ylink.cim.common.msg.handle.MsgA2039;
import com.ylink.cim.common.msg.handle.MsgA2044;
import com.ylink.cim.common.msg.handle.MsgA2048;
import com.ylink.cim.common.msg.handle.MsgA2049;
import com.ylink.cim.common.msg.handle.MsgA2052;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.invest.service.InvestQueryService;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;
@Component("investQueryService")
public class InvestQueryServiceImpl implements InvestQueryService {


	/**
	 * 定投计划查询
	 * @param map
	 * @return
	 */
	public Paginater queryPlan(Map<String, String> map, Pager pager)throws BizException{
		map.put(MsgField.h_exch_code.getFieldCode(), "A2032");
		return MsgUtil.getResBodyPager(map, MsgA2032.A2032REQ, MsgA2032.A2032RES, pager);
	}

	
	

	/**
	 * 黄金账户查询(汇总部分)
	 * @param map
	 * @return
	 */
	public Map queryGoldAcctTatol(
			Map<String, String> map) throws BizException {
		Map resultMap=new HashMap();
		Map<String,String> mapA2039=new HashMap<String,String>();
		mapA2039.put(MsgField.h_bank_no.getFieldCode(), map.get(MsgField.h_bank_no.getFieldCode()));
		mapA2039.put(MsgField.h_exch_code.getFieldCode(), "A2039");
		mapA2039.put(MsgField.aip_no.getFieldCode(), map.get(MsgField.aip_no.getFieldCode()));
		Map<String, MsgField> resMapA2039=MsgUtil.getResBody(mapA2039, MsgA2039.A2039REQ, MsgA2039.A2039RES);
		resultMap.put("totalDeposit",resMapA2039.get(MsgField.curr_plan_weight.getFieldCode()).getValue());
		double physical=Double.parseDouble(StringUtils.isEmpty(resMapA2039.get(MsgField.curr_take_weight.getFieldCode()).getValue())?"0":resMapA2039.get(MsgField.curr_take_weight.getFieldCode()).getValue());
		double money=Double.parseDouble(StringUtils.isEmpty(resMapA2039.get(MsgField.curr_sell_weight.getFieldCode()).getValue())?"0":resMapA2039.get(MsgField.curr_sell_weight.getFieldCode()).getValue());
		double total=physical+money;
		resultMap.put("totalDeposit",total);
		
		Map<String,String> mapA2044=new HashMap<String,String>();
		mapA2044.put(MsgField.h_bank_no.getFieldCode(), map.get(MsgField.h_bank_no.getFieldCode()));
		mapA2044.put(MsgField.h_exch_code.getFieldCode(), "A2044");
		mapA2044.put(MsgField.aip_no.getFieldCode(), map.get(MsgField.aip_no.getFieldCode()));
		Map<String, MsgField> resMapA2044=MsgUtil.getResBody(mapA2044, MsgA2044.A2044REQ, MsgA2044.A2044RES);
		resultMap.put("currentMoney", resMapA2044.get(MsgField.curr_can_use.getFieldCode()).getValue());
		
		Map<String,String> mapA2036=new HashMap<String,String>();
		mapA2036.put(MsgField.h_bank_no.getFieldCode(), map.get(MsgField.h_bank_no.getFieldCode()));
		mapA2036.put(MsgField.h_exch_code.getFieldCode(), "A2036");
		mapA2036.put(MsgField.aip_no.getFieldCode(), map.get(MsgField.aip_no.getFieldCode()));
		Map<String, MsgField> resMapA2036=MsgUtil.getResBody(mapA2036, MsgA2036.A2036REQ, MsgA2036.A2036RES);
		resultMap.put("goldRealBal", resMapA2036.get(MsgField.gold_real_bal.getFieldCode()).getValue());
		resultMap.put("goldVirtualBal", resMapA2036.get(MsgField.gold_virtual_bal.getFieldCode()).getValue());
		resultMap.put("accountSurplus", resMapA2036.get(MsgField.account_surplus.getFieldCode()).getValue());
		resultMap.put("avgPrice", resMapA2036.get(MsgField.avg_price.getFieldCode()).getValue());
		
		Map<String,String> mapA2052=new HashMap<String,String>();
		mapA2052.put(MsgField.h_bank_no.getFieldCode(), map.get(MsgField.h_bank_no.getFieldCode()));
		mapA2052.put(MsgField.h_exch_code.getFieldCode(), "A2052");
		mapA2052.put(MsgField.aip_type.getFieldCode(), "Au");
		String endDate=map.get(MsgField.end_date.getFieldCode());
		if(StringUtils.isEmpty(endDate)){
			Date date=new Date();
			DateFormat df=new SimpleDateFormat("yyyyMMdd");
			mapA2052.put(MsgField.end_date.getFieldCode(), df.format(date));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			mapA2052.put(MsgField.start_date.getFieldCode(), df.format(calendar.getTime()));
		}else{
			Date date=new Date(endDate);
			DateFormat df=new SimpleDateFormat("yyyyMMdd");
			mapA2052.put(MsgField.end_date.getFieldCode(),endDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			mapA2052.put(MsgField.start_date.getFieldCode(), df.format(calendar.getTime()));
		}
		Map<String, MsgField> resMapA2052=MsgUtil.getResBody(mapA2052, MsgA2052.A2052REQ, MsgA2052.A2052RES);
		resultMap.put("lastDayPrice", resMapA2052.get(MsgField.settle_price.getFieldCode()).getValue());
		
		
		
		return resultMap;
	}



	/**
	 * 黄金账户查询
	 * @param map
	 * @return
	 */
	public List<Map<String, MsgField>>  queryGoldAcct(Map<String, String> map)throws BizException{
		map.put(MsgField.h_exch_code.getFieldCode(), "A2033");
		List<Map<String, MsgField>> bodyMapList = MsgUtil.getResBodyList(map, MsgA2033.A2033REQ, MsgA2033.A2033RES);
		return bodyMapList;
	}


	/**
	 * 资金账户查询
	 * @param map
	 * @return
	 */
	public Paginater queryCashAcct(Map<String,String> map, Pager pager)throws BizException{
		map.put(MsgField.h_exch_code.getFieldCode(), "A2049");
		return MsgUtil.getResBodyPager(map, MsgA2049.A2049REQ, MsgA2049.A2049RES, pager);
	}


	/**
	 * 积存情况查询
	 * @param map
	 * @return
	 * @throws BizException 
	 */
	public Paginater queryDepositRecord(Map<String, String> map, Pager pager) throws BizException{
		map.put(MsgField.h_exch_code.getFieldCode(), "A2034");
		return MsgUtil.getResBodyPager(map, MsgA2034.A2034REQ, MsgA2034.A2034RES, pager);
	}
	

	/**
	 * 赎回记录查询
	 * @param map
	 * @return
	 */
	public Paginater queryRedeemRecord(Map<String, String> map, Pager pager)throws BizException{
		map.put(MsgField.h_exch_code.getFieldCode(), "A2038");
		return MsgUtil.getResBodyPager(map, MsgA2038.A2038REQ, MsgA2038.A2038RES, pager);
	}

	/**
	 * 账户管理费查询
	 * @param map
	 * @return
	 */
	public Paginater queryMngFee(Map<String, String> map, Pager pager)throws BizException{
		map.put(MsgField.h_exch_code.getFieldCode(), "A2048");
		return MsgUtil.getResBodyPager(map, MsgA2048.A2048REQ, MsgA2048.A2048RES, pager);
	}

	/**
	 * 现金赎回查询
	 */
	public Paginater querySoldRecord(Map<String, String> map,
			Pager pager) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A2034");
		return MsgUtil.getResBodyPager(map, MsgA2034.A2034REQ, MsgA2034.A2034RES, pager);
	}

	public List<Map<String, MsgField>> querySumGoldAcct(Map<String, String> map, String[] acctNos) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A2039");
		List<Map<String, MsgField>> list= new ArrayList<Map<String, MsgField>>();
		
		if (!StringUtils.isEmpty(map.get(MsgField.aip_no.getFieldCode()))) {
			list = MsgUtil.getResBodyList(map, MsgA2039.A2039REQ, MsgA2039.A2039RES);
		}else {
			for (int i = 0; i < acctNos.length; i++) {
				map.put(MsgField.aip_no.getFieldCode(), acctNos[i]);
				List<Map<String, MsgField>> bodyMapList = MsgUtil.getResBodyList(map, MsgA2039.A2039REQ, MsgA2039.A2039RES);
				list.addAll(bodyMapList);
			}
		}
		return list;
	}

	public List<Map<String, MsgField>> queryPlanOperList(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A2035");
		List<Map<String, MsgField>> bodyMapList = MsgUtil.getResBodyList(map, MsgA2035.A2035REQ, MsgA2035.A2035RES);
		return bodyMapList;
	}

}
