package com.ylink.cim.manage.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.manage.dao.DailyOrderDao;
import com.ylink.cim.manage.domain.DailyOrder;
import com.ylink.cim.manage.service.DailyOrderService;

import flink.util.Paginater;
import flink.web.BaseAction;
@Scope("prototype")
@Component
public class DailyOrderAction extends BaseAction implements ModelDriven<DailyOrder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private DailyOrderDao dailyOrderDao;
	@Autowired
	private DailyOrderService dailyOrderService;
	
	@Override
	public DailyOrder getModel() {
		return model;
	}

	private DailyOrder model = new DailyOrder();
	
	public String dailySum() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("beginDate", model.getBeginDate());
		map.put("endDate", model.getEndDate());
		Paginater paginater = dailyOrderDao.findPaginater(map, getPager(request));
		saveQueryResult(request, paginater);
		return "dailySum";
	}
	public String test() throws Exception {
		dailyOrderService.exeDailySumTask(null);
		return dailySum();
	}
}
