package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.OrderDetailDao;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.OrderRecord;
import com.ylink.cim.manage.service.OrderRecordService;

import flink.util.DateUtil;
@Component("orderRecordService")
public class OrderRecordServiceImpl implements OrderRecordService {

	@Autowired
	private OrderRecordDao orderRecordDao;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Override
	public void save(OrderRecord model) {
		model.setCreateDate(DateUtil.getCurrent());
		orderRecordDao.save(model);
	}

	@Override
	public void delete(String id) {
		orderRecordDao.deleteById(id);
		orderDetailDao.deleteByOrderId(id);
	}
}
