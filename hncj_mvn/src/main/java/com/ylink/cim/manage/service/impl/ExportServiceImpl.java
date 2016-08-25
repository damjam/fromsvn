package com.ylink.cim.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.service.ExportService;
import com.ylink.cim.util.ExportExcelUtil;

import flink.util.DateUtil;
import flink.util.SpringContext;

@Component("exportService")
public class ExportServiceImpl implements ExportService {

	@Override
	public void exportCSB(List<CommonServiceBill> list, String branchNo, HttpServletResponse response) throws Exception {
		List<List<List<Object>>> dataList = new ArrayList<>();
		Map<String, List<List<Object>>> dataMap = new HashMap<>();
		List<String> buildingList = new ArrayList<>();
		for (int i = 0, size = list.size(); i < size; i++) {
			CommonServiceBill bill = list.get(i);
			String houseSn = bill.getHouseSn();
			String buildingNo = houseSn.split("-")[0];
			List<List<Object>> tmpList = dataMap.get(buildingNo);
			if (tmpList == null) {
				tmpList = new ArrayList<>();
				dataMap.put(buildingNo, tmpList);
			}
			List<Object> obj = new ArrayList<>();
			obj.add(bill.getHouseSn());
			obj.add(bill.getArea());
			obj.add(bill.getOwnerName());
			obj.add(bill.getStartDate());
			obj.add(bill.getEndDate());
			obj.add(bill.getServicePrice());
			obj.add(bill.getLightPrice());
			obj.add(bill.getTotalAmount());
			obj.add(bill.getPaidAmount());
			obj.add(bill.getChargeDate());
			obj.add(bill.getChargeUser());
			obj.add(BillState.valueOf(bill.getState()).getName());
			obj.add(bill.getRemark());
			tmpList.add(obj);
 		}
		for (Map.Entry<String, List<List<Object>>> entry : dataMap.entrySet()) {
			dataList.add(entry.getValue());
			buildingList.add(entry.getKey());
		}
		String branchName = BranchType.valueOf(branchNo).getName();
		String fileName = branchName+"物业费缴费信息-"+DateUtil.getCurrentDate()+".xlsx";
		String title = "";
		
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService("houseInfoExportRule");
		String excelType = ParaManager.getExcelType(branchName);
		ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, buildingList.toArray(new String[buildingList.size()]), rules, dataList, excelType, response);
		exportExcelUtil.exportSheets();
	}

}
