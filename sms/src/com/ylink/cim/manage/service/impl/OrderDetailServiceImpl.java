package com.ylink.cim.manage.service.impl;

import java.util.List;

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
			throw new BizException("�˿���������0");
		}
		if (refundAmt > amount) {
			throw new BizException("�˿���ܴ�����Ʒ���");
		}
		orderDetail.setDeliState(DeliveryState.RETURNED.getValue());//�˿�
		orderDetail.setRefundAmt(refundAmt);
		orderDetailDao.update(orderDetail);
		
		String orderId = orderDetail.getOrderId();
		OrderRecord orderRecord = orderRecordDao.findById(orderId);
		Double totalAmt = orderRecord.getAmount();
		Double payAmt = orderRecord.getPayAmt();
		String payState = orderRecord.getPayState();
		if(PayState.UNPAY.getValue().equals(payState)){//δ����
			//ֱ���޸��ܽ��
			orderRecord.setAmount(totalAmt-amount);
			orderRecord.setPayAmt(payAmt-refundAmt);
		}else if (PayState.PAID.getValue().equals(payState)) {
			//����˿��
			
		}
		orderRecordDao.update(orderRecord);
		//TODO ������
	}
	//����
	@Override
	public void sendOut(String id) throws BizException {
		OrderDetail orderDetail = orderDetailDao.findByIdWithLock(id);
		Assert.equals(orderDetail.getDeliState(), DeliveryState.INIT.getValue(), "״̬�ѷ���������޷����з�������");
		orderDetail.setDeliState(DeliveryState.SENT.getValue());
		orderDetailDao.update(orderDetail);//����
		//������
		//TODO
		OrderRecord record = orderRecordDao.findById(orderDetail.getOrderId());
		if (OrderState.INIT.getValue().equals(record.getState())) {
			record.setState(OrderState.DEALING.getValue());//������
			orderRecordDao.update(record);
		}
	}

	@Override
	public void cancel(String id, Double refundAmt) throws BizException {
		OrderDetail orderDetail = orderDetailDao.findByIdWithLock(id);
		Assert.equals(DeliveryState.INIT.getValue(), orderDetail.getDeliState(), "��Ʒ����״̬�ѱ�����޷�ȡ��");
		orderDetailDao.deleteById(id);//ɾ��
		OrderRecord orderRecord = orderRecordDao.findById(orderDetail.getOrderId());
		String payState = orderRecord.getPayState();
		Double totalAmt = orderRecord.getAmount();
		Double payAmt = orderRecord.getPayAmt();
		Double cancelAmt = orderDetail.getAmount();
		if(PayState.UNPAY.getValue().equals(payState)){//δ����
			//ֱ���޸��ܽ��
			orderRecord.setAmount(totalAmt-cancelAmt);
			orderRecord.setPayAmt(payAmt-refundAmt);
		}else if (PayState.PAID.getValue().equals(payState)) {
			//����˿��
			
		}
		orderRecordDao.update(orderRecord);
		checkRecordState(orderDetail.getOrderId());
	}

	@Override
	public void receive(String id) throws BizException {
		OrderDetail orderDetail = orderDetailDao.findByIdWithLock(id);
		Assert.equals(DeliveryState.SENT.getValue(), orderDetail.getDeliState(), "״̬�ѱ�������ɲ���");
		orderDetail.setDeliState(DeliveryState.RECEIVED.getValue());
		orderDetailDao.update(orderDetail);
		checkRecordState(orderDetail.getOrderId());
	}

	private void checkRecordState(String orderId) {
		OrderRecord record = orderRecordDao.findByIdWithLock(orderId);
		if (OrderState.FINISHED.getValue().equals(record.getState())) {
			return;
		}
		List<OrderDetail> list = orderDetailDao.findUndelis(orderId);
		if (list.size() == 0) {
			record.setState(OrderState.FINISHED.getValue());
			orderRecordDao.update(record);
		}
	}

}
