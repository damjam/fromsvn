package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.CommonServiceBillDao;
import com.ylink.cim.manage.dao.DecorateServiceBillDao;
import com.ylink.cim.manage.dao.DepositBillDao;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.DecorateServiceBill;
import com.ylink.cim.manage.domain.DepositBill;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.service.ImportService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
@Component("importService")
public class ImportServiceImpl implements ImportService {

	@Autowired
	private CommonServiceBillDao commonServiceBillDao;
	@Autowired
	private DepositBillDao depositBillDao;
	@Autowired
	private DecorateServiceBillDao decorateServiceBillDao;
	@Autowired
	private HouseInfoDao houseInfoDao;
	@Override
	public Integer addCSBFromExcel(List<List<Map<String, Object>>> list, UserInfo userInfo) throws BizException{
		Integer totalCnt = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> rows = list.get(i);
			for (int j = 0; j < rows.size(); j++) {
				Map<String, Object> map = rows.get(j);
				String id = MapUtils.getString(map, "id");
				Assert.isNull(commonServiceBillDao.findById(id), "�˵�"+id+"�Ѵ���");
				Map<String, Object> params = new HashMap<String, Object>();
				String houseSn = MapUtils.getString(map, "houseSn");
				params.put("houseSn", houseSn);
				params.put("startDate", MapUtils.getString(map, "startDate"));
				params.put("endDate", MapUtils.getString(map, "endDate"));
				params.put("branchNo", userInfo.getBranchNo());
				//��ѯ��¼�Ƿ��Ѵ���
				List<CommonServiceBill> exitBills = commonServiceBillDao.findList(params);
				Assert.isEmpty(exitBills, "���ݱ��Ϊ"+houseSn+"�ļ�¼�Ѵ���");
				CommonServiceBill bill = new CommonServiceBill();
				try {
					BeanUtils.populate(bill, map);
				} catch (Exception e) {
					e.printStackTrace();
					throw new BizException("��������쳣");
				}
				
				String stateName = MapUtils.getString(map, "state");
				String state = getStateByName(stateName);
				bill.setState(state);
				bill.setCreateType("�����");
				//bill.setId(IdFactoryHelper.getId(Constants.BILL_ID));
				bill.setCreateDate(DateUtil.getCurrent());
				commonServiceBillDao.save(bill);
				totalCnt++;
			}
		}
		return totalCnt;
	}
	@Override
	public Integer addDepositBillFromExcel(List<List<Map<String, Object>>> list, UserInfo userInfo) throws BizException {
		Integer totalCnt = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> rows = list.get(i);
			for (int j = 0; j < rows.size(); j++) {
				Map<String, Object> map = rows.get(j);
				String id = MapUtils.getString(map, "id");
				Assert.isNull(depositBillDao.findById(id), "�˵�"+id+"�Ѵ���");
				Map<String, Object> params = new HashMap<String, Object>();
				String houseSn = MapUtils.getString(map, "houseSn");
				params.put("houseSn", houseSn);
				params.put("branchNo", userInfo.getBranchNo());
				params.put("amount", MapUtils.getDouble(map, "amount"));
				String state = getStateByName(MapUtils.getString(map, "state"));
				params.put("state", state);
				//��ѯ��¼�Ƿ��Ѵ���
				List<DepositBill> exitBills = depositBillDao.findList(params);
				Assert.isEmpty(exitBills, "���ݱ��Ϊ"+houseSn+"�ļ�¼�Ѵ���");
				DepositBill bill = new DepositBill();
				try {
					BeanUtils.populate(bill, map);
				} catch (Exception e) {
					e.printStackTrace();
					throw new BizException("��������쳣");
				}
				bill.setState(state);
				bill.setCreateType("�����");
				//�˵�����ɱ���ṩ
				//bill.setId(IdFactoryHelper.getId(Constants.BILL_ID));
				bill.setCreateDate(DateUtil.getCurrent());
				commonServiceBillDao.save(bill);
				totalCnt++;
			}
		}
		return totalCnt;
	}
	
	private String getStateByName(String stateName){
		String state = "";
		if (BillState.PAID.getName().equals(stateName)) {
			state = BillState.PAID.getValue();
		}else if (BillState.PART_PAID.getName().equals(stateName)) {
			state = BillState.PART_PAID.getValue();
		}else if (BillState.REFUND.getName().equals(stateName)) {
			state = BillState.REFUND.getValue();
		}else if (BillState.REVERSE.getName().equals(stateName)) {
			state = BillState.REVERSE.getValue();
		}else if (BillState.SETTLED.getValue().equals(stateName)) {
			state = BillState.SETTLED.getValue();
		}else if (BillState.UNPAY.getValue().equals(stateName)) {
			state = BillState.UNPAY.getValue();
		}else if (BillState.VERIFIED.getValue().equals(stateName)) {
			state = BillState.VERIFIED.getValue();
		}
		return state;
	}
	/**
	 * ����װ�޷�����˵�
	 */
	@Override
	public Integer addDSBFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws BizException {
		Integer totalCnt = 0;
		String branchNo = sessionUser.getBranchNo();
		String cleanPriceStr = ParaManager.getCleanPrice(branchNo);
		Double clenaPrice = Double.parseDouble(cleanPriceStr);
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> rows = list.get(i);
			for (int j = 0; j < rows.size(); j++) {
				Map<String, Object> map = rows.get(j);
				String id = MapUtils.getString(map, "id");
				Assert.isNull(decorateServiceBillDao.findById(id), "�˵�"+id+"�Ѵ���");
				String state = getStateByName(MapUtils.getString(map, "state"));
				String houseSn = MapUtils.getString(map, "houseSn");
				/*Map<String, Object> params = new HashMap<String, Object>();
				
				params.put("houseSn", houseSn);
				params.put("branchNo", sessionUser.getBranchNo());
				params.put("amount", MapUtils.getDouble(map, "amount"));
				params.put("state", state);
				//��ѯ��¼�Ƿ��Ѵ���
				List<DecorateServiceBill> exitBills = decorateServiceBillDao.findList(params);
				Assert.isEmpty(exitBills, "���ݱ��Ϊ"+houseSn+"�ļ�¼�Ѵ���");*/
				DecorateServiceBill bill = new DecorateServiceBill();
				try {
					BeanUtils.populate(bill, map);
				} catch (Exception e) {
					e.printStackTrace();
					throw new BizException("��������쳣");
				}
				HouseInfo houseInfo = houseInfoDao.findById(houseSn);
				bill.setArea(houseInfo.getArea());
				bill.setCleanPrice(clenaPrice);
				bill.setState(state);
				bill.setCreateType("�����");
				//bill.setAmount(amount);
				//�˵�����ɱ���ṩ
				//bill.setId(IdFactoryHelper.getId(Constants.BILL_ID));
				bill.setCreateDate(DateUtil.getCurrent());
				decorateServiceBillDao.save(bill);
				totalCnt++;
			}
		}
		return totalCnt;
	}
}
