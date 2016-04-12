package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.common.state.OwnerState;
import com.ylink.cim.common.type.OwnerGrade;
import com.ylink.cim.common.type.SexType;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.service.OwnerInfoService;
import com.ylink.cim.util.OrderSnGenerator;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;

@Component("ownerInfoService")
public class OwnerInfoServiceImpl implements OwnerInfoService {
	@Autowired
	private OwnerInfoDao ownerInfoDao;
	@Autowired
	private IdFactoryService idFactoryService;
	@Autowired
	private HouseInfoDao houseInfoDao;
	@Override
	public void delete(String id, UserInfo userInfo) throws BizException {
		ownerInfoDao.deleteById(id);
		Account account = ownerInfoDao.findById(Account.class, id);
		if (account != null) {
			ownerInfoDao.delete(account);
		}
	}

	@Override
	public void add(OwnerInfo ownerInfo, UserInfo userInfo) throws BizException {
		String houseSn = ownerInfo.getHouseSn();
		HouseInfo houseInfo = ownerInfoDao.findById(HouseInfo.class, houseSn);
		Assert.notNull(houseInfo, "�����ڷ��ݱ��" + houseSn);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("houseSn", houseInfo.getHouseSn());
		Assert.isNull(ownerInfoDao.getNormalOwner(houseSn), "����" + houseSn
				+ "�ѹ���ҵ����Ϣ");
		ownerInfo.setCreateDate(DateUtil.getCurrent());
		ownerInfo.setCreateUser(userInfo.getUserName());
		ownerInfo.setOweDays(0);
		ownerInfo.setOweTimes(0);
		ownerInfo.setId(idFactoryService.generateId(Constants.OWNER_INFO_ID));
		ownerInfo.setState(OwnerState.NORMAL.getValue());
		ownerInfoDao.save(ownerInfo);
	}

	@Override
	public void update(OwnerInfo ownerInfo, UserInfo userInfo)
			throws BizException {
		ownerInfo.setUpdateDate(DateUtil.getCurrent());
		ownerInfo.setUpdateUser(userInfo.getUserName());
		ownerInfoDao.update(ownerInfo);
	}

	@Override
	public void cancel(String id, UserInfo sessionUser) throws BizException {

		OwnerInfo ownerInfo = ownerInfoDao.findByIdWithLock(id);
		Account account = null;
		if (Symbol.YES.equals(ownerInfo.getHasAcct())) {
			account = ownerInfoDao.findById(Account.class, id);
			if (account != null) {
				if (account.getBalance() != null && account.getBalance() > 0) {
					throw new BizException("ҵ���˻���Ϊ�㣬���Ƚ������ֲ���!");
				}
			}
		}
		ownerInfo.setState(OwnerState.CANCEL.getValue());
		ownerInfo.setCancelDate(DateUtil.getCurrent());
		ownerInfo.setCancelUser(sessionUser.getUserName());
		ownerInfoDao.update(ownerInfo);
		if (account != null) {
			account.setState(OwnerState.CANCEL.getValue());
			ownerInfoDao.update(account);
		}
	}

	@Override
	public void importOwnerInfo(List<OwnerInfo> list, UserInfo userInfo)
			throws BizException {
		for (int i = 0; i < list.size(); i++) {
			OwnerInfo ownerInfo = list.get(i);
			ownerInfo.setId(idFactoryService
					.generateId(Constants.OWNER_INFO_ID));
			ownerInfo.setCarNum(0);
			ownerInfo.setHasAcct(Symbol.NO);
			ownerInfo.setState(OwnerState.NORMAL.getValue());
			ownerInfo.setCreateDate(DateUtil.getCurrent());
			ownerInfo.setCreateUser(userInfo.getUserName());
			ownerInfo.setBranchNo(userInfo.getBranchNo());
			ownerInfoDao.save(ownerInfo);

			HouseInfo houseInfo = new HouseInfo();
			String houseSn = ownerInfo.getHouseSn();
			String[] strs = houseSn.split("-");
			houseInfo.setArea(ownerInfo.getArea());
			houseInfo.setHouseSn(ownerInfo.getHouseSn());
			String buildingNo = strs[0];
			String unitNo = strs[1];
			String position = strs[2];
			String floor = position.substring(0, position.length() - 2);
			houseInfo.setBuildingNo(buildingNo);
			houseInfo.setUnitNo(unitNo);
			houseInfo.setFloor(floor);
			houseInfo.setPosition(position);
			houseInfo.setCreateDate(DateUtil.getCurrent());
			houseInfo.setCreateUser(userInfo.getUserName());
			houseInfo.setBranchNo(userInfo.getBranchNo());
			StringBuffer houseDesc = new StringBuffer();
			houseDesc.append(houseInfo.getBuildingNo());
			houseDesc.append("��¥");
			houseDesc.append(houseInfo.getUnitNo());
			houseDesc.append("��Ԫ");
			houseDesc.append(houseInfo.getPosition());
			houseInfo.setHouseDesc(houseDesc.toString());
			String orderSn = OrderSnGenerator.getOrderSn(houseInfo);
			houseInfo.setOrderSn(orderSn);
			ownerInfoDao.save(houseInfo);
		}

	}

	@Override
	public Integer addFromExcel(List<List<Map<String, Object>>> list, UserInfo userInfo) throws BizException{
		int totalCnt = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> rows = list.get(i);
			for (int j = 0; j < rows.size(); j++) {
				Map<String, Object> map = rows.get(j);
				String houseSn = MapUtils.getString(map, "houseSn");
				if (StringUtils.isEmpty(houseSn)) {
					throw new BizException("���ݱ�Ų���Ϊ��");
				}
				if (ownerInfoDao.getNormalOwner(houseSn) != null) {
					throw new BizException("���ݱ��Ϊ" + houseSn + "��ҵ����Ϣ�Ѵ��ڻ��¼�ظ�");
				}
				String ownerName = MapUtils.getString(map, "ownerName");
				if (StringUtils.isEmpty(ownerName)) {
					throw new BizException("ҵ����������Ϊ��");
				}
				String sexType = "";
				String sexTypeName = MapUtils.getString(map, "gender");
				if (SexType.SEX_M.getName().equals(sexTypeName)) {
					sexType = "M";
				} else if (SexType.SEX_F.getName().equals(sexTypeName)) {
					sexType = "F";
				}
				String mobile = MapUtils.getString(map, "mobile");
				String idCard = MapUtils.getString(map, "idCard");
				String instancyPerson = MapUtils.getString(map, "instancyPerson");
				String instancyTel = MapUtils.getString(map, "instancyTel");
				Double area = MapUtils.getDouble(map, "area");
				OwnerInfo ownerInfo = new OwnerInfo();
				ownerInfo.setId(idFactoryService
						.generateId(Constants.OWNER_INFO_ID));
				ownerInfo.setHouseSn(houseSn);
				ownerInfo.setGender(sexType);
				ownerInfo.setIdCard(idCard);
				ownerInfo.setOwnerName(ownerName);
				ownerInfo.setMobile(mobile);
				ownerInfo.setGrade(OwnerGrade.NORMAL.getValue());
				ownerInfo.setCreateType("�����");
				ownerInfo.setInstancyPerson(instancyPerson);
				ownerInfo.setInstancyTel(instancyTel);
				ownerInfo.setCreateDate(DateUtil.getCurrent());
				ownerInfo.setCreateUser(userInfo.getUserName());
				ownerInfo.setBranchNo(userInfo.getBranchNo());
				ownerInfo.setCarNum(0);
				ownerInfo.setHasAcct(Symbol.NO);
				ownerInfo.setState(OwnerState.NORMAL.getValue());
				ownerInfoDao.save(ownerInfo);
				//����ҵ����Ϣ
				HouseInfo existHouseInfo = houseInfoDao.findById(houseSn);
				if (existHouseInfo != null) {
					if(area != null && area != 0){
						if(area.doubleValue() != existHouseInfo.getArea().doubleValue()){
							throw new BizException("���Ϊ"+houseSn+"�ķ�����Ϣ�Ѵ��ڵ�����뷿����Ϣ���в�һ��");
						}
					}
					//������Ϣ�Ѵ���
					continue;
				}
				HouseInfo houseInfo = new HouseInfo();
				String[] sns = houseSn.split("-");
				houseInfo.setArea(area);
				houseInfo.setHouseSn(houseSn);
				String buildingNo = sns[0];
				//String unitNo = strs[1];
				//String position = strs[2];
				//String floor = position.substring(0, position.length() - 2);
				String unitNo = "";
				String floor = "";
				String position = "";
				if (sns.length == 2) {
					position = sns[1];
					floor = getFloor(position);
					String houseNo = position.substring(position.length()-2, position.length()-1);
					if(Integer.parseInt(houseNo) <= 4){
						unitNo = "1";
					}else {
						unitNo = "2";
					}
				}else if (sns.length == 3) {
					unitNo = sns[1];
					position = sns[2];
					floor = getFloor(position);
				}
				houseInfo.setBuildingNo(buildingNo);
				houseInfo.setUnitNo(unitNo);
				houseInfo.setFloor(floor);
				houseInfo.setPosition(position);
				houseInfo.setCreateDate(DateUtil.getCurrent());
				houseInfo.setCreateUser(userInfo.getUserName());
				houseInfo.setBranchNo(userInfo.getBranchNo());
				StringBuffer houseDesc = new StringBuffer();
				houseDesc.append(houseInfo.getBuildingNo());
				houseDesc.append("��¥");
				houseDesc.append(houseInfo.getUnitNo());
				houseDesc.append("��Ԫ");
				houseDesc.append(houseInfo.getPosition());
				houseInfo.setHouseDesc(houseDesc.toString());
				String orderSn = OrderSnGenerator.getOrderSn(houseInfo);
				houseInfo.setOrderSn(orderSn);
				houseInfoDao.save(houseInfo);
				totalCnt++;
			}
		}
		return totalCnt;
	}
	private String getFloor(String position){
		String floor = "";
		if (position.length() == 3) {
			floor = position.substring(0, 1);

		} else {
			floor = position.substring(0, 2);
		}
		return floor;
	}
}
