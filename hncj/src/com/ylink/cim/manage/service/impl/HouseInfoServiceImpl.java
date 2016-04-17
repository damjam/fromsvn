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
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.state.DecorateState;
import com.ylink.cim.common.state.RecordState;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.AccountJournalDao;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.dao.WaterBillDao;
import com.ylink.cim.manage.dao.WaterRecordDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.AccountDetail;
import com.ylink.cim.manage.domain.AccountJournal;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.domain.WaterBill;
import com.ylink.cim.manage.domain.WaterRecord;
import com.ylink.cim.manage.service.HouseInfoService;
import com.ylink.cim.util.OrderSnGenerator;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.SpringContext;

@Component("houseInfoService")
public class HouseInfoServiceImpl implements HouseInfoService {
	@Autowired
	private HouseInfoDao houseInfoDao;
	@Autowired
	private OwnerInfoDao ownerInfoDao;

	@Override
	public void delete(String id, UserInfo userInfo) throws BizException {
		HouseInfo houseInfo = houseInfoDao.findById(id);
		if (houseInfo == null) {
			throw new BizException("要删除的记录不存在!");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("houseSn", houseInfo.getHouseSn());
		map.put("state", houseInfo.getHouseSn());
		List<OwnerInfo> list = ownerInfoDao.findByInfos(map);
		Assert.isEmpty(list, "该房屋关联有业主信息，无法删除!");
		houseInfoDao.deleteById(id);
	}

	@Override
	public void add(HouseInfo houseInfo, UserInfo userInfo) throws BizException {
		StringBuffer houseSn = new StringBuffer();
		houseSn.append(houseInfo.getBuildingNo());
		houseSn.append("-");
		// houseSn.append(houseInfo.getUnitNo());
		// houseSn.append("-");
		houseSn.append(houseInfo.getPosition());
		String position = houseInfo.getPosition();
		String floor = position.substring(0, position.length() - 2);
		houseInfo.setFloor(floor);
		String sn = houseSn.toString();
		Assert.isNull(houseInfoDao.findById(sn), "房屋信息已存在!");
		houseInfo.setHouseSn(sn);
		houseInfo.setCreateDate(DateUtil.getCurrent());
		houseInfo.setCreateUser(userInfo.getUserName());
		houseInfo.setHouseDesc(getHouseDesc(houseInfo));
		String orderSn = OrderSnGenerator.getOrderSn(houseInfo);
		houseInfo.setOrderSn(orderSn);
		houseInfoDao.save(houseInfo);
	}
	private static String getHouseDesc(HouseInfo houseInfo){
		StringBuffer houseDesc = new StringBuffer();
		houseDesc.append(houseInfo.getBuildingNo());
		houseDesc.append("号楼");
		houseDesc.append(houseInfo.getUnitNo());
		houseDesc.append("单元");
		houseDesc.append(houseInfo.getPosition());
		return houseDesc.toString();
	}
	private void update() {
		WaterBillDao waterBillDao = (WaterBillDao) SpringContext
				.getService("waterBillDao");
		OwnerInfoDao ownerInfoDao = (OwnerInfoDao) SpringContext
				.getService("ownerInfoDao");
		List list = waterBillDao.findErr();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String id = (String) map.get("id");
			WaterBill bill = waterBillDao.findById(id);
			String sn = bill.getHouseSn();
			OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(sn);
			Account account = waterBillDao.findById(Account.class,
					ownerInfo.getId());
			//
			if (bill.getPaidAmt() > 0) {
				account.setBalance(account.getBalance() + bill.getPaidAmt());
				ownerInfoDao.update(account);
				AccountDetail ad = waterBillDao.getstDeta(account.getId());
				ownerInfoDao.delete(ad);
			}
			ownerInfoDao.delete(bill);
		}
	}

	private void setRem() {
		AccountJournalDao accountJournalDao = (AccountJournalDao) SpringContext
				.getService("accountJournalDao");
		;
		WaterBillDao waterBillDao = (WaterBillDao) SpringContext
				.getService("waterBillDao");
		List list = accountJournalDao.findAll();
		for (int i = 0; i < list.size(); i++) {
			AccountJournal aj = (AccountJournal) list.get(i);
			if (aj.getRemark() != null && aj.getRemark().indexOf("收业主") != -1) {
				String id = aj.getBillId();
				WaterBill wb = waterBillDao.findById(id);

				if (wb == null) {
					continue;
				}
				aj.setRemark(aj.getRemark().replace("收业主",
						"收" + wb.getHouseSn() + "业主"));
				accountJournalDao.update(aj);
			}
		}

	}

	private void updat() {
		WaterBillDao waterBillDao = (WaterBillDao) SpringContext
				.getService("waterBillDao");

		List list = waterBillDao.findAll();
		for (int i = 0; i < list.size(); i++) {
			WaterBill w = (WaterBill) list.get(i);
			if ("03".equals(w.getState())
					&& w.getPaidAmt().equals(w.getAmount())) {
				w.setState("01");
				w.setRemark("水费从预存账户中扣除,当前余额" + MoneyUtil.getFormatStr2(0d)
						+ "元");
				waterBillDao.update(w);
			}
		}

	}

	private void addBi(UserInfo userInfo) throws BizException {
		WaterRecordDao waterRecordDao = (WaterRecordDao) SpringContext
				.getService("waterRecordDao");
		IdFactoryService idFactoryService = (IdFactoryService) SpringContext
				.getService("idFactoryService");
		List list = waterRecordDao.findAll();
		for (int i = 0; i < list.size(); i++) {
			WaterRecord waterRecord = (WaterRecord) list.get(i);
			waterRecord.setState(RecordState.CHECKED.getValue());
			waterRecord.setCheckDate(DateUtil.getCurrent());
			waterRecord.setCheckUser(userInfo.getUserName());
			waterRecordDao.save(waterRecord);
			// 填账单
			WaterBill waterBill = new WaterBill();
			String price = ParaManager.getWaterPrice();
			Double amount = Double.parseDouble(price) * waterRecord.getNum();
			// 查询未缴费的账单
			waterBill.setAmount(amount);
			waterBill.setPreRecordDate(waterRecord.getPreRecordDate());
			waterBill.setCurRecordDate(waterRecord.getCurRecordDate());
			waterBill.setId(idFactoryService.generateId(Constants.BILL_ID));
			waterBill.setHouseSn(waterRecord.getHouseSn());
			waterBill.setPrenum(waterRecord.getPrenum());
			waterBill.setCurnum(waterRecord.getCurnum());
			waterBill.setNum(waterRecord.getNum());
			waterBill.setState(BillState.UNPAY.getValue());
			waterBill.setCreateDate(DateUtil.getCurrent());
			waterBill.setCreateUser(userInfo.getUserName());
			waterBill.setAmount(amount);
			waterBill.setRecordMonth(waterRecord.getRecordMonth());
			waterBill.setPrice(Double.parseDouble(ParaManager.getWaterPrice()));
			OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(waterBill
					.getHouseSn());
			waterBill.setOwnerName(ownerInfo.getOwnerName());
			waterRecordDao.save(waterBill);
		}

	}

	private void addWR(UserInfo userInfo) {
		WaterRecordDao waterRecordDao = (WaterRecordDao) SpringContext
				.getService("waterRecordDao");
		List list = waterRecordDao.findAll();
		for (int i = 0; i < list.size(); i++) {
			WaterRecord r = (WaterRecord) list.get(i);
			r.setNum(r.getCurnum() - r.getPrenum());
			r.setPreRecordDate("20140628");
			r.setCurRecordDate("20140827");
			r.setCreateUser(userInfo.getUserName());
			r.setCreateDate(DateUtil.getCurrent());
			r.setState(RecordState.UNCHECK.getValue());
		}
	}

	private void addownerInfo() throws BizException {
		List list = ownerInfoDao.findAll();
		IdFactoryService idFactoryService = (IdFactoryService) SpringContext
				.getService("idFactoryService");
		for (int i = 0; i < list.size(); i++) {
			OwnerInfo inf = (OwnerInfo) list.get(i);
			inf.setHasAcct("N");
			inf.setCreateDate(DateUtil.getCurrent());
			// inf.setCheckinDate(DateUtil.getDate(DateUtil.formatDate(inf.getCheckinDate(),
			// "yyyy.M")));
			inf.setState("00");
			ownerInfoDao.update(inf);
		}
	}

	private void addhouseInfo(UserInfo userInfo) {
		List list = houseInfoDao.findAll();
		for (int i = 0; i < list.size(); i++) {
			HouseInfo house = (HouseInfo) list.get(i);
			if (!StringUtils.isEmpty(house.getBuildingNo())) {
				continue;
			}
			String sn = house.getHouseSn();
			String[] sns = sn.split("-");
			house.setBuildingNo(sns[0]);
			house.setUnitNo(sns[1]);
			String s = sns[2];
			house.setPosition(s);
			if (s.length() == 3) {
				house.setFloor(s.substring(0, 1));

			} else {
				house.setFloor(s.substring(0, 2));
			}
			StringBuffer houseDesc = new StringBuffer();
			houseDesc.append(house.getBuildingNo());
			houseDesc.append("号楼");
			houseDesc.append(house.getUnitNo());
			houseDesc.append("单元");
			houseDesc.append(house.getPosition());
			house.setHouseDesc(houseDesc.toString());
			String orderSn = OrderSnGenerator.getOrderSn(house);
			house.setOrderSn(orderSn);
			house.setCreateDate(DateUtil.getCurrent());
			house.setCreateUser(userInfo.getUserName());
			houseInfoDao.update(house);
		}
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
	public void test2() throws BizException {

	}

	@Override
	public Integer addFromExcel(List<List<Map<String, Object>>> list, UserInfo userInfo) throws BizException {
		Integer totalCnt = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> rows = list.get(i);
			for (int j = 0; j < rows.size(); j++) {
				Map<String, Object> map = rows.get(j);
				HouseInfo houseInfo = new HouseInfo();
				String houseSn = MapUtils.getString(map, "houseSn");
				Assert.isNull(houseInfoDao.findById(houseSn), "已存在房屋编号为"+houseSn+"的信息");
				houseInfo.setHouseSn(houseSn);
				houseInfo.setArea(MapUtils.getDouble(map, "area"));
				String deliveryDate = MapUtils.getString(map, "deliverDate");
				houseInfo.setDeliveryDate(deliveryDate);
				String decorateStateName = MapUtils.getString(map, "decorateState");
				String decorateState = DecorateState.STATE_02.getValue();//默认已装修
				if (DecorateState.STATE_00.getName().equals(decorateStateName)) {
					decorateState = DecorateState.STATE_00.getValue();
				}else if (DecorateState.STATE_01.getName().equals(decorateStateName)) {
					decorateState = DecorateState.STATE_01.getValue();
				}else if (DecorateState.STATE_02.getName().equals(decorateStateName)) {
					decorateState = DecorateState.STATE_02.getValue();
				}
				houseInfo.setDecorateState(decorateState);
				String remark = MapUtils.getString(map, "remark");
				houseInfo.setRemark(remark);
				houseInfo.setCreateDate(DateUtil.getCurrent());
				houseInfo.setCreateUser(userInfo.getUserName());
				houseInfo.setBranchNo(userInfo.getBranchNo());
				String[] sns = houseSn.split("-");
				String buildingNo = sns[0];
				String unit = "";
				String floor = "";
				String position = "";
				if (sns.length == 2) {
					position = sns[1];
					floor = getFloor(position);
					String houseNo = position.substring(position.length()-2, position.length()-1);
					if(Integer.parseInt(houseNo) <= 4){
						unit = "1";
					}else {
						unit = "2";
					}
				}else if (sns.length == 3) {
					unit = sns[1];
					position = sns[2];
					floor = getFloor(position);
				}
				houseInfo.setBuildingNo(buildingNo);
				houseInfo.setUnitNo(unit);
				houseInfo.setFloor(floor);
				houseInfo.setPosition(position);
				houseInfo.setHouseDesc(getHouseDesc(houseInfo));
				houseInfoDao.save(houseInfo);
				totalCnt++;
			}
		}
		return totalCnt;
	}

}
