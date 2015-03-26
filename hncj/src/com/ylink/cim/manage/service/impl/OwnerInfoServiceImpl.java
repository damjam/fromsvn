package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.common.state.OwnerState;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.service.OwnerInfoService;
import com.ylink.cim.user.domain.UserInfo;
import com.ylink.cim.util.OrderSnGenerator;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;
@Component("ownerInfoService")
public class OwnerInfoServiceImpl implements OwnerInfoService{
	@Autowired
	private OwnerInfoDao ownerInfoDao;
	@Autowired
	private IdFactoryService idFactoryService;
	
	public void delete(String id, UserInfo userInfo) throws BizException {
		ownerInfoDao.deleteById(id);
		Account account = ownerInfoDao.findById(Account.class, id);
		if (account != null) {
			ownerInfoDao.delete(account);
		}
	}
	public void add(OwnerInfo ownerInfo, UserInfo userInfo) throws BizException{
		String houseSn = ownerInfo.getHouseSn();
		HouseInfo houseInfo = ownerInfoDao.findById(HouseInfo.class, houseSn);
		Assert.notNull(houseInfo, "不存在房屋编号"+houseSn);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("houseSn", houseInfo.getHouseSn());
		Assert.isNull(ownerInfoDao.getNormalOwner(houseSn), "房屋"+houseSn+"已关联业主信息");
		ownerInfo.setCreateDate(DateUtil.getCurrent());
		ownerInfo.setCreateUser(userInfo.getUserName());
		ownerInfo.setOweDays(0);
		ownerInfo.setOweTimes(0);
		ownerInfo.setId(idFactoryService.generateId(Constants.OWNER_INFO_ID));
		ownerInfo.setState(OwnerState.NORMAL.getValue());
		ownerInfoDao.save(ownerInfo);
	}
	public void update(OwnerInfo ownerInfo, UserInfo userInfo) throws BizException{
		ownerInfo.setUpdateDate(DateUtil.getCurrent());
		ownerInfo.setUpdateUser(userInfo.getUserName());
		ownerInfoDao.update(ownerInfo);
	}
	public void cancel(String id, UserInfo sessionUser) throws BizException {
		
		OwnerInfo ownerInfo = ownerInfoDao.findByIdWithLock(id);
		Account account = null;
		if (Symbol.YES.equals(ownerInfo.getHasAcct())) {
			account = ownerInfoDao.findById(Account.class, id);
			if (account != null) {
				if (account.getBalance() != null && account.getBalance() > 0) {
					throw new BizException("业主账户余额不为零，请先进行提现操作!");
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
	public void importOwnerInfo(List<OwnerInfo> list, UserInfo userInfo) throws BizException{
		for (int i = 0; i < list.size(); i++) {
			OwnerInfo ownerInfo = list.get(i);
			ownerInfo.setId(idFactoryService.generateId(Constants.OWNER_INFO_ID));
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
			String floor = position.substring(0, position.length()-2);
			houseInfo.setBuildingNo(buildingNo);
			houseInfo.setUnitNo(unitNo);
			houseInfo.setFloor(floor);
			houseInfo.setPosition(position);
			houseInfo.setCreateDate(DateUtil.getCurrent());
			houseInfo.setCreateUser(userInfo.getUserName());
			houseInfo.setBranchNo(userInfo.getBranchNo());
			StringBuffer houseDesc = new StringBuffer();
			houseDesc.append(houseInfo.getBuildingNo());
			houseDesc.append("号楼");
			houseDesc.append(houseInfo.getUnitNo());
			houseDesc.append("单元");
			houseDesc.append(houseInfo.getPosition());
			houseInfo.setHouseDesc(houseDesc.toString());
			String orderSn = OrderSnGenerator.getOrderSn(houseInfo);
			houseInfo.setOrderSn(orderSn);
			ownerInfoDao.save(houseInfo);
		}
		
	}

}
