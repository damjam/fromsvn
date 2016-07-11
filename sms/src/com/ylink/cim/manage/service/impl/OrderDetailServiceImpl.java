package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.common.state.DeliveryState;
import com.ylink.cim.common.state.OrderState;
import com.ylink.cim.common.state.PayState;
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
		model.setDeliState(DeliveryState.INIT.getValue());
		orderDetailDao.save(model);
	}

	@Override
	public void returnGoods(String id, Double refundAmt) throws BizException {
		OrderDetail orderDetail = orderDetailDao.findById(id);
		Double amount = orderDetail.getAmount();
		if (refundAmt == null || refundAmt == 0) {
			throw new BizException("退款金额必须大于0");
		}
		if (refundAmt > amount) {
			throw new BizException("退款金额不能大于商品金额");
		}
		orderDetail.setDeliState(DeliveryState.RETURNED.getValue());//退款
		orderDetail.setRefundAmt(refundAmt);
		orderDetailDao.update(orderDetail);
		
		String orderId = orderDetail.getOrderId();
		OrderRecord orderRecord = orderRecordDao.findById(orderId);
		Double totalAmt = orderRecord.getAmount();
		Double payAmt = orderRecord.getPayAmt();
		String payState = orderRecord.getPayState();
		if(PayState.UNPAY.getValue().equals(payState)){//未付款
			//直接修改总金额
			orderRecord.setAmount(totalAmt-amount);
			orderRecord.setPayAmt(payAmt-refundAmt);
		}else if (PayState.PAID.getValue().equals(payState)) {
			//添加退款交易
			
		}
		orderRecordDao.update(orderRecord);
		//TODO 变更库存
	}
	//发货
	@Override
	public void sendOut(String id) throws BizException {
		OrderDetail orderDetail = orderDetailDao.findByIdWithLock(id);
		Assert.equals(orderDetail.getDeliState(), DeliveryState.INIT.getValue(), "状态已发生变更，无法进行发货操作");
		orderDetail.setDeliState(DeliveryState.SENT.getValue());
		orderDetailDao.update(orderDetail);//发货
		//变更库存
		//TODO
		OrderRecord record = orderRecordDao.findById(orderDetail.getOrderId());
		if (OrderState.INIT.getValue().equals(record.getState())) {
			record.setState(OrderState.DEALING.getValue());//处理中
			orderRecordDao.update(record);
		}
	}

	@Override
	public void cancel(String id, Double refundAmt) throws BizException {
		OrderDetail orderDetail = orderDetailDao.findByIdWithLock(id);
		Assert.equals(DeliveryState.INIT.getValue(), orderDetail.getDeliState(), "商品物流状态已变更，无法取消");
		orderDetail.setDeliState(DeliveryState.CANCELED.getValue());
		orderDetailDao.update(orderDetail);
		OrderRecord orderRecord = orderRecordDao.findById(orderDetail.getOrderId());
		String payState = orderRecord.getPayState();
		Double totalAmt = orderRecord.getAmount();
		Double payAmt = orderRecord.getPayAmt();
		Double amount = orderRecord.getAmount();
		if(PayState.UNPAY.getValue().equals(payState)){//未付款
			//直接修改总金额
			orderRecord.setAmount(totalAmt-amount);
			orderRecord.setPayAmt(payAmt-refundAmt);
		}else if (PayState.PAID.getValue().equals(payState)) {
			//添加退款交易
			
		}
		orderRecordDao.update(orderRecord);
	}

}
