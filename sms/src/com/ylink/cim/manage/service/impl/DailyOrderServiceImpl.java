package com.ylink.cim.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.common.state.PayState;
import com.ylink.cim.manage.dao.DailyOrderDao;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.DailyOrder;
import com.ylink.cim.manage.service.DailyOrderService;

import flink.IdFactoryHelper;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
@Component("dailyOrderService")
public class DailyOrderServiceImpl implements DailyOrderService {

	@Autowired
	private DailyOrderDao dailyOrderDao;
	@Autowired
	private OrderRecordDao orderRecordDao;
	@Override
	public void exeDailySumTask(String id) throws BizException {
		Date date = DateUtil.addMonths(DateUtil.getCurrent(), -1);
		
		String beginDate = DateUtil.getDateYYYYMMDD(date);
		String endDate = DateUtil.addDays(DateUtil.getCurrentDate(), -1, "yyyyMMdd");
		List<Map<String, Object>> list = orderRecordDao.findDailySum(beginDate, endDate);
		Map<String, Map<String, Object>> tmpMap = new HashMap<>();
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String orderDate = (String)map.get("orderDate");
			//String productType = (String)map.get("productType");
			String payState = (String)map.get("payState");
			Map<String, Object> dailyData = tmpMap.get(orderDate);
			if(dailyData == null) {
				dailyData = new HashMap<String, Object>();
				dailyData.put("paidCnt", 0);
				dailyData.put("paidAmt", 0d);
				dailyData.put("unpaidCnt", 0);
				dailyData.put("unpaidAmt", 0d);
				/*
				dailyData.put("footpadCnt", 0);
				dailyData.put("footpadAmt", 0);
				dailyData.put("seatpadCnt", 0);
				dailyData.put("seatpadAmt", 0);
				dailyData.put("wrapTrunkCnt", 0);
				dailyData.put("wrapTurnkAmt", 0);
				dailyData.put("flatTrunkCnt", 0);
				dailyData.put("flatTurnkAmt", 0);
				dailyData.put("silkFootpadCnt", 0);
				dailyData.put("silkFootpadAmt", 0);
				*/
				tmpMap.put(orderDate, dailyData);
				//dailyData.put("productType", productType);
				//dailyData.put("payState", payState);
			}
			Integer paidCnt = 0;
			Double paidAmt = 0d;
			Integer unpaidCnt = 0;
			Double unpaidAmt = 0d;
			if(PayState.PAID.getValue().equals(payState)){
				paidCnt = ((Long)map.get("cnt")).intValue();
				paidAmt = (Double)map.get("amt");
			}else if (PayState.UNPAY.getValue().equals(payState)) {
				unpaidCnt = ((Long)map.get("cnt")).intValue();
				unpaidAmt = (Double)map.get("amt");
			}
			/*
			if("½Åµæ".equals(productType)){
				dailyData.put("footpadCnt", (Integer)dailyData.get("footpadCnt")+paidCnt);
				dailyData.put("footpadAmt", (Double)dailyData.get("footpadAmt")+paidAmt);
			}else if ("×ùµæ".equals(productType)) {
				dailyData.put("seatpadCnt", (Integer)dailyData.get("seatpadCnt")+paidCnt);
				dailyData.put("seatpadAmt", (Double)dailyData.get("seatpadAmt")+paidAmt);
			}else if ("È«°üÎ§ºó±¸Ïäµæ".equals(productType)) {
				dailyData.put("wrapTrunkCnt", (Integer)dailyData.get("wrapTrunkCnt")+paidCnt);
				dailyData.put("wrapTurnkAmt", (Double)dailyData.get("wrapTurnkAmt")+paidAmt);
			}else if ("Æ½°åºó±¸Ïäµæ".equals(productType)) {
				dailyData.put("flatTrunkCnt", (Integer)dailyData.get("flatTrunkCnt")+paidCnt);
				dailyData.put("flatTurnkAmt", (Double)dailyData.get("flatTurnkAmt")+paidAmt);
			}else if ("½Åµæ+Ë¿È¦".equals(productType)) {
				dailyData.put("silkFootpadCnt", (Integer)dailyData.get("silkFootpadCnt")+paidCnt);
				dailyData.put("silkFootpadAmt", (Double)dailyData.get("silkFootpadAmt")+paidAmt);
			}*/
			if(PayState.UNPAY.getValue().equals(payState)){
				dailyData.put("unpaidCnt", (Integer)dailyData.get("unpaidCnt")+unpaidCnt);
				dailyData.put("unpaidAmt", (Double)dailyData.get("unpaidAmt")+unpaidAmt);
			}else if (PayState.PAID.getValue().equals(payState)) {
				dailyData.put("paidCnt", (Integer)dailyData.get("paidCnt")+paidCnt);
				dailyData.put("paidAmt", (Double)dailyData.get("paidAmt")+paidAmt);
			}
		}
		Map<String, Object> params = new HashMap<>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		Paginater paginater = dailyOrderDao.findPaginater(params, null); 
		List<DailyOrder> dailyOrders = paginater.getList();
		List<String> dailyOrderDates = new ArrayList<String>();
		for(int i=0; i<dailyOrders.size(); i++){
			DailyOrder dailyOrder = dailyOrders.get(i);
			String orderDate = dailyOrder.getOrderDate();
			Map<String, Object> map = tmpMap.get(orderDate);
			dailyOrder.setFlatTrunkAmt(MapUtils.getDouble(map, "flatTrunkAmt"));
			dailyOrder.setFlatTrunkCnt(MapUtils.getInteger(map, "flatTrunkCnt"));
			dailyOrder.setFootpadAmt(MapUtils.getDouble(map, "footpadAmt"));
			dailyOrder.setFootpadCnt(MapUtils.getInteger(map, "footpadCnt"));
			dailyOrder.setPaidAmt(MapUtils.getDouble(map, "paidAmt"));
			dailyOrder.setPaidCnt(MapUtils.getInteger(map, "paidCnt"));
			dailyOrder.setUnpaidAmt(MapUtils.getDouble(map, "unpaidAmt"));
			dailyOrder.setUnpaidCnt(MapUtils.getInteger(map, "unpaidCnt"));
			dailyOrder.setWrapTrunkAmt(MapUtils.getDouble(map, "wrapTrunkAmt"));
			dailyOrder.setWrapTrunkCnt(MapUtils.getInteger(map, "wrapTrunkCnt"));
			dailyOrder.setSeatpadAmt(MapUtils.getDouble(map, "seatpadAmt"));
			dailyOrder.setSeatpadCnt(MapUtils.getInteger(map, "seatpadCnt"));
			dailyOrder.setSilkFootpadAmt(MapUtils.getDouble(map, "silkFootpadAmt"));
			dailyOrder.setSilkFootpadCnt(MapUtils.getInteger(map, "silkFootpadCnt"));
			dailyOrder.setTotalAmt(MapUtils.getDouble(map, "paidAmt")+MapUtils.getDouble(map, "unpaidAmt"));
			dailyOrder.setTotalCnt(MapUtils.getInteger(map, "paidCnt")+MapUtils.getInteger(map, "unpaidCnt"));
			dailyOrderDao.update(dailyOrder);
			dailyOrderDates.add(orderDate);
		}
		for (String key : tmpMap.keySet()) {
			Map<String, Object> map = tmpMap.get(key);
			if(!dailyOrderDates.contains(key)){
				DailyOrder dailyOrder = new DailyOrder();
				dailyOrder.setOrderDate(key);
				dailyOrder.setFlatTrunkAmt(MapUtils.getDouble(map, "flatTrunkAmt"));
				dailyOrder.setFlatTrunkCnt(MapUtils.getInteger(map, "flatTrunkCnt"));
				dailyOrder.setFootpadAmt(MapUtils.getDouble(map, "footpadAmt"));
				dailyOrder.setFootpadCnt(MapUtils.getInteger(map, "footpadCnt"));
				dailyOrder.setPaidAmt(MapUtils.getDouble(map, "paidAmt"));
				dailyOrder.setPaidCnt(MapUtils.getInteger(map, "paidCnt"));
				dailyOrder.setUnpaidAmt(MapUtils.getDouble(map, "unpaidAmt"));
				dailyOrder.setUnpaidCnt(MapUtils.getInteger(map, "unpaidCnt"));
				dailyOrder.setWrapTrunkAmt(MapUtils.getDouble(map, "wrapTrunkAmt"));
				dailyOrder.setWrapTrunkCnt(MapUtils.getInteger(map, "wrapTrunkCnt"));
				dailyOrder.setSeatpadAmt(MapUtils.getDouble(map, "seatpadAmt"));
				dailyOrder.setSeatpadCnt(MapUtils.getInteger(map, "seatpadCnt"));
				dailyOrder.setSilkFootpadAmt(MapUtils.getDouble(map, "silkFootpadAmt"));
				dailyOrder.setSilkFootpadCnt(MapUtils.getInteger(map, "silkFootpadCnt"));
				dailyOrder.setTotalAmt(MapUtils.getDouble(map, "paidAmt")+MapUtils.getDouble(map, "unpaidAmt"));
				dailyOrder.setTotalCnt(MapUtils.getInteger(map, "paidCnt")+MapUtils.getInteger(map, "unpaidCnt"));
				dailyOrder.setId(IdFactoryHelper.getId(DailyOrder.class));
				dailyOrderDao.save(dailyOrder);
			}
		}
	}
}
