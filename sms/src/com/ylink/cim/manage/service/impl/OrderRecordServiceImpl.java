package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.state.DeliveryState;
import com.ylink.cim.common.state.OrderState;
import com.ylink.cim.common.state.PayState;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.manage.dao.MerchantInfoDao;
import com.ylink.cim.manage.dao.OrderDetailDao;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.MerchantInfo;
import com.ylink.cim.manage.domain.OrderDetail;
import com.ylink.cim.manage.domain.OrderRecord;
import com.ylink.cim.manage.service.AccountJournalService;
import com.ylink.cim.manage.service.CarModelService;
import com.ylink.cim.manage.service.OrderRecordService;
import com.ylink.cim.util.CopyPropertyUtil;

import flink.IdFactoryHelper;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("orderRecordService")
public class OrderRecordServiceImpl implements OrderRecordService {

	@Autowired
	private OrderRecordDao orderRecordDao;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Autowired
	private MerchantInfoDao merchantInfoDao;
	@Autowired
	private CarModelService carModelService;
	@Autowired
	private AccountJournalService accountJournalService;
	@Override
	public void save(OrderRecord model, UserInfo userInfo) throws BizException{
		String orderId = IdFactoryHelper.getId(OrderRecord.class);
		model.setId(orderId);
		model.setCreateDate(DateUtil.getCurrent());
		model.setCreateUser(userInfo.getUserName());
		model.setState(OrderState.INIT.getValue());
		model.setPayState(PayState.UNPAY.getValue());
		model.setBranchNo(userInfo.getBranchNo());
		orderRecordDao.save(model);
		saveOrderDetails(model);
		//
		saveClientInfo(model.getClientName(), model.getClientTel(), model.getAddress(), userInfo.getBranchNo());
	}

	private void saveClientInfo(String clientName, String clientTel, String address, String branchNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("mrname", clientName);
		map.put("mobile", clientTel);
		List<MerchantInfo> list = merchantInfoDao.findList(map);
		if(list.size() == 0) {
			MerchantInfo merchantInfo = new MerchantInfo();
			merchantInfo.setMrname(clientName);
			merchantInfo.setMobile(clientTel);
			merchantInfo.setAddr(address);
			merchantInfo.setCreateDate(DateUtil.getCurrent());
			merchantInfo.setId(IdFactoryHelper.getId(MerchantInfo.class));
			merchantInfo.setBranchNo(branchNo);
			merchantInfoDao.save(merchantInfo);
		}
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
			String carModel = model.getCarModels()[i];
			detail.setCarModel(model.getCarModels()[i]);
			detail.setMaterial(model.getMaterials()[i]);
			detail.setColor(model.getColors()[i]);
			detail.setPrice(model.getPrices()[i]);
			detail.setNum(model.getNums()[i]);
			detail.setAmount(model.getAmounts()[i]);
			//detail.setDeliType(model.getDeliTypes()[i]);
			detail.setId(IdFactoryHelper.getId(OrderDetail.class));
			detail.setDeliState(DeliveryState.INIT.getValue());
			orderDetailDao.save(detail);
			//checkCarModel(carModel);
			carModelService.addIfNotExist(carModel);
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

	@Override
	public void pay(String id, UserInfo sessionUser) throws BizException {
		OrderRecord orderRecord = orderRecordDao.findByIdWithLock(id);
		orderRecord.setPayState(PayState.PAID.getValue());
		orderRecordDao.update(orderRecord);
		//��������
		accountJournalService.add(TradeType.ORDER_INCOME.getValue(), orderRecord.getPayAmt(), id, "", sessionUser);
	}

	@Override
	public void cancel(String id) throws BizException {
		OrderRecord orderRecord = orderRecordDao.findByIdWithLock(id);
		Assert.equals(OrderState.INIT.getValue(), orderRecord.getState(), "����״̬�ѱ��������ȡ��");
		orderRecord.setState(OrderState.CANCELED.getValue());
		orderRecordDao.update(orderRecord);
	}
}
