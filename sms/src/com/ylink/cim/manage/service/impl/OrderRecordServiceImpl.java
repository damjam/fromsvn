package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.OrderDetailDao;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.OrderDetail;
import com.ylink.cim.manage.domain.OrderRecord;
import com.ylink.cim.manage.service.OrderRecordService;

import flink.IdFactoryHelper;
import flink.util.DateUtil;

@Component("orderRecordService")
public class OrderRecordServiceImpl implements OrderRecordService {

	@Autowired
	private OrderRecordDao orderRecordDao;
	@Autowired
	private OrderDetailDao orderDetailDao;

	@Override
	public void save(OrderRecord model) {
		String orderId = IdFactoryHelper.getId(OrderRecord.class);
		model.setId(orderId);
		model.setCreateDate(DateUtil.getCurrent());
		orderRecordDao.save(model);
		saveOrderDetails(model);
	}

	public void update(OrderRecord model) {
		orderRecordDao.update(model);
		orderDetailDao.deleteByOrderId(model.getId());
		saveOrderDetails(model);
	}

	private void saveOrderDetails(OrderRecord model) {
		for (int i = 0, length = model.getProductNames().length; i < length; i++) {
			OrderDetail detail = new OrderDetail();
			detail.setOrderId(model.getId());
			detail.setProductName(model.getProductNames()[i]);
			detail.setCarModel(model.getCarModels()[i]);
			detail.setMaterial(model.getMaterials()[i]);
			detail.setColor(model.getColors()[i]);
			detail.setPrice(model.getPrices()[i]);
			detail.setNum(model.getNums()[i]);
			detail.setAmount(model.getAmounts()[i]);
			detail.setDeliType(model.getDeliTypes()[i]);
			orderRecordDao.save(detail);
		}
	}

	@Override
	public void delete(String id) {
		orderRecordDao.deleteById(id);
		orderDetailDao.deleteByOrderId(id);
	}
}
