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
import com.ylink.cim.manage.dao.CommonServiceBillDao;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.Employee;
import com.ylink.cim.manage.service.ImportService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
@Component("importService")
public class ImportServiceImpl implements ImportService {

	@Autowired
	private HouseInfoDao houseInfoDao;
	@Autowired
	private CommonServiceBillDao commonServiceBillDao;
	@Override
	public Integer addCSBFromExcel(List<List<Map<String, Object>>> list, UserInfo userInfo) throws BizException{
		Integer totalCnt = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> rows = list.get(i);
			for (int j = 0; j < rows.size(); j++) {
				Map<String, Object> map = rows.get(j);
				CommonServiceBill bill = new CommonServiceBill();
				String id = MapUtils.getString(map, "id");
				Assert.isNull(commonServiceBillDao.findById(id), "账单"+id+"已存在");
				Map<String, Object> params = new HashMap<String, Object>();
				String houseSn = MapUtils.getString(map, "houseSn");
				params.put("houseSn", houseSn);
				params.put("startDate", MapUtils.getString(map, "startDate"));
				params.put("endDate", MapUtils.getString(map, "endDate"));
				params.put("branchNo", userInfo.getBranchNo());
				//查询记录是否已存在
				List<CommonServiceBill> exitBills = commonServiceBillDao.findList(params);
				Assert.isEmpty(exitBills, "房屋编号为"+houseSn+"的记录已存在");
				try {
					BeanUtils.populate(bill, map);
				} catch (Exception e) {
					e.printStackTrace();
					throw new BizException("程序出现异常");
				}
				
				String payStateName = MapUtils.getString(map, "state");
				String state = "";
				if (BillState.PAID.getName().equals(payStateName)) {
					state = BillState.PAID.getValue();
				}else if (BillState.PART_PAID.getName().equals(payStateName)) {
					state = BillState.PART_PAID.getValue();
				}else if (BillState.REFUND.getName().equals(payStateName)) {
					state = BillState.REFUND.getValue();
				}else if (BillState.REVERSE.getName().equals(payStateName)) {
					state = BillState.REVERSE.getValue();
				}else if (BillState.SETTLED.getValue().equals(payStateName)) {
					state = BillState.SETTLED.getValue();
				}else if (BillState.UNPAY.getValue().equals(payStateName)) {
					state = BillState.UNPAY.getValue();
				}else if (BillState.VERIFIED.getValue().equals(payStateName)) {
					state = BillState.VERIFIED.getValue();
				}
				bill.setState(state);
				bill.setCreateType("表格导入");
				//bill.setId(IdFactoryHelper.getId(Constants.BILL_ID));
				bill.setCreateDate(DateUtil.getCurrent());
				commonServiceBillDao.save(bill);
				totalCnt++;
			}
		}
		return totalCnt;
	}
	
	
}
