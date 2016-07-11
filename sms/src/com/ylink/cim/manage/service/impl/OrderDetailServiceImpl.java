package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.java_cup.internal.runtime.Symbol;
import com.ylink.cim.common.state.DeliveryState;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.manage.dao.OrderDetailDao;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.OrderDetail;
import com.ylink.cim.manage.domain.OrderRecord;
import com.ylink.cim.manage.service.OrderDetailService;

import flink.etc.Assert;
import flink.etc.BizException;
@Component("orderDetailService")
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailDao orderDetailDao;
	@Autowired
	private OrderRecordDao orderRecordDao;
	@Override
	public void save(OrderDetail model) {
		orderDetailDao.save(model);
	}

	@Override
	public void returnGoods(String id, Double refundAmt) throws BizException {
		OrderDetail orderDetail = orderDetailDao.findById(id);
		OrderRecord orderRecord = orderRecordDao.findByIdWithLock(orderDetail.getOrderId());
		String payState = orderRecord.getPayState();
		if (YesNoType.YES.getValue().equals(payState)) {
			//���һ���˿��
		}else {
			//δ����,�޸Ľ��
			Double totalAmt = orderRecord.getAmount();
			Double payAmt = orderRecord.getPayAmt();
			Assert.isTrue(refundAmt <= payAmt, "�˿���ܴ��ڶ������");
			orderRecord.setAmount(totalAmt - orderDetail.getAmount());
			orderRecord.setPayAmt(payAmt - refundAmt);
			orderRecordDao.update(orderRecord);
			//
		}
		orderDetail.setDeliState(DeliveryState.RETURNED.getValue());//�˻�
		orderDetailDao.update(orderDetail);
	}
}
