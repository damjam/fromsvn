package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mchange.v1.identicator.IdHashMap;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.state.DeliveryState;
import com.ylink.cim.common.state.OrderState;
import com.ylink.cim.manage.dao.OrderDetailDao;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.OrderDetail;
import com.ylink.cim.manage.domain.OrderRecord;
import com.ylink.cim.manage.service.OrderRecordService;
import com.ylink.cim.util.CopyPropertyUtil;

import flink.IdFactoryHelper;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("orderRecordService")
public class OrderRecordServiceImpl implements OrderRecordService {

	@Autowired
	private OrderRecordDao orderRecordDao;
	@Autowired
	private OrderDetailDao orderDetailDao;

	@Override
	public void save(OrderRecord model, UserInfo userInfo) throws BizException{
		String orderId = IdFactoryHelper.getId(OrderRecord.class);
		model.setId(orderId);
		model.setCreateDate(DateUtil.getCurrent());
		model.setCreateUser(userInfo.getUserName());
		model.setState(OrderState.INIT.getValue());
		orderRecordDao.save(model);
		saveOrderDetails(model);
	}

	public void update(OrderRecord model) throws BizException{
		orderRecordDao.update(model);
		orderDetailDao.deleteByOrderId(model.getId());
		saveOrderDetails(model);
	}

	private void saveOrderDetails(OrderRecord model) throws BizException{
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
			//detail.setDeliType(model.getDeliTypes()[i]);
			detail.setId(IdFactoryHelper.getId(OrderDetail.class));
			orderRecordDao.save(detail);
		}
	}

	@Override
	public void delete(String id) {
		orderRecordDao.deleteById(id);
		orderDetailDao.deleteByOrderId(id);
	}

	@Override
	public void update(OrderRecord model, UserInfo userInfo) throws BizException {
		OrderRecord record = orderRecordDao.findById(model.getId());
		CopyPropertyUtil.copyPropertiesIgnoreNull(model, record);
		orderRecordDao.update(record);
		orderDetailDao.deleteByOrderId(model.getId());
		saveOrderDetails(model);
		
	}

	@Override
	public void updateState(String id, String state) throws BizException {
		OrderRecord record = orderRecordDao.findByIdWithLock(id);
		record.setState(state);
		orderRecordDao.update(record);
	}

	@Override
	public boolean canEdit(String id) throws BizException {
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", id);
		map.put("excludeState", DeliveryState.INIT.getValue());
		List<OrderDetail> list = orderDetailDao.findList(map);
		if (list.size() > 0) {
			return false;
		}
		return true;
	}
}
