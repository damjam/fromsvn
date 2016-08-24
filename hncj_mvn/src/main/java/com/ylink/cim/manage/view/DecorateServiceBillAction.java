package com.ylink.cim.manage.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.DecorateServiceBillDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.domain.DecorateServiceBill;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.service.BillService;
import com.ylink.cim.manage.service.ImportService;
import com.ylink.cim.util.ExportExcelUtil;
import com.ylink.cim.util.ReadExcelUtil;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.util.StringUtil;
import flink.web.BaseAction;
import net.sf.json.JSONObject;

@Scope("prototype")
@Component
public class DecorateServiceBillAction extends BaseAction implements ModelDriven<DecorateServiceBill> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private DecorateServiceBillDao decorateServiceBillDao;
	@Autowired
	private BillService billService;
	@Autowired
	private OwnerInfoDao ownerInfoDao;
	@Autowired
	private ImportService importService;
	public String toAdd() throws Exception {
		YesNoType.setInReq(request);
		return "add";
	}

	public String doAdd() throws Exception {
		try {
			DecorateServiceBill bill = new DecorateServiceBill();
			BeanUtils.copyProperties(bill, model);
			billService.saveDecorateServiceBill(bill, getSessionUser(request));
			String tip = "数据已保存";
			if (bill.getSupFee() != null && bill.getSupFee() > 0) {
				tip += ",请在物业收费模块收取补缴物业费";
			}
			setResult(true, tip, request);
			model.setHouseSn("");
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败" + e.getMessage(), request);
			return toAdd();
		}
		return list();
	}

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		map.put("houseSn", model.getHouseSn());
		map.put("startChargeDate", model.getStartChargeDate());
		map.put("endChargeDate", model.getEndChargeDate());
		map.put("state", model.getState());
		map.put("id", model.getId());
		map.put("year", model.getYear());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = decorateServiceBillDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = decorateServiceBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		return "list";
	}

	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeDecorateFee(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			setResult(false, "操作失败", request);
		}
		return list();
	}

	public String deleteBill() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(DecorateServiceBill.class, id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list();
	}

	public String getHouseInfo() throws Exception {
		JSONObject jsonObject = new JSONObject();
		String ownerName = "";
		String areaStr = "";
		String cleanPrice = "";
		String floor = "";
		String liftFee = "";
		String cleanAmount = "";
		String totalAmount = "";
		String supPay = "";
		String csBillId = "";
		try {
			String houseSn = request.getParameter("houseSn");
			OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(houseSn, getSessionBranchNo(request));
			if (ownerInfo != null) {
				ownerName = ownerInfo.getOwnerName();
				// ownerInfo.getCheckinState();
				// 未入住
				/*
				 * if (!CheckinState.CHECKEDIN.getValue().equals(ownerInfo.
				 * getCheckinState())) { Map<String, Object> map = getParaMap();
				 * map.put("houseSn", houseSn); CommonServiceBill bill =
				 * commonServiceBillDao.getLastBill(map); if (bill != null) { if
				 * (DateUtil.addDays(DateUtil.getCurrent(), 1).getTime() <
				 * DateUtil.formatDate(bill.getEndDate(), "yyyyMMdd").getTime())
				 * { String remark = bill.getRemark(); if (remark != null &&
				 * remark.indexOf("80%") != -1) { Double amt =
				 * bill.getTotalAmount(); supPay = MoneyUtil.getFormatStr2(amt *
				 * 0.25); csBillId = bill.getId(); } } } }
				 */
			}
			HouseInfo houseInfo = decorateServiceBillDao.findById(HouseInfo.class, houseSn);
			Assert.notNull(houseInfo, "编号为" + houseSn + "的房屋信息不存在!");
			Double area = houseInfo.getArea();
			areaStr = String.valueOf(area);
			cleanPrice = ParaManager.getCleanPrice();
			cleanAmount = MoneyUtil.getFormatStr2(Double.parseDouble(cleanPrice) * area);
			floor = houseInfo.getFloor();
			liftFee = ParaManager.getLiftFee(Integer.parseInt(houseInfo.getFloor()));
			totalAmount = MoneyUtil.getFormatStr2(Double.parseDouble(cleanAmount) + Double.parseDouble(liftFee));
		} catch (Exception e) {
			jsonObject.put("error", e.getMessage());
			setResult(false, "失败", request);
			e.printStackTrace();
			respond(response, jsonObject.toString());
			return null;
		}
		jsonObject.put("ownerName", ownerName);
		jsonObject.put("area", areaStr);
		jsonObject.put("cleanPrice", cleanPrice);
		jsonObject.put("cleanAmount", cleanAmount);
		jsonObject.put("floor", floor);
		jsonObject.put("liftFee", liftFee);
		jsonObject.put("amount", totalAmount);
		jsonObject.put("supPay", supPay);
		jsonObject.put("csBillId", csBillId);
		respond(response, jsonObject.toString());
		return null;
	}

	@Override
	public DecorateServiceBill getModel() {
		return model;
	}

	private DecorateServiceBill model = new DecorateServiceBill();

	public String toImport() {
		return "import";
	}
	
	public String doImport() {
		try{
			File file = this.getFile();
			FileInputStream fis = new FileInputStream(file);
			String suffix = fileFileName.substring(fileFileName.lastIndexOf(".")+1);//扩展名
			List<Map<String, String>> rule = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ImportRule");
			List<List<Map<String, Object>>> list = ReadExcelUtil.read(fis, suffix, rule);
			importService.addDSBFromExcel(list, getSessionUser(request));
			setSucResult(request);
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BizException) {
				setResult(false, e.getMessage(), request);
			}else {
				setResult(false, "操作失败", request);
			}
			return toImport();
		}
		return "toMain";
	}
	public String export() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("houseSn", model.getHouseSn());
		map.put("startChargeDate", model.getStartChargeDate());
		map.put("endChargeDate", model.getEndChargeDate());
		map.put("state", model.getState());
		map.put("id", model.getId());
		map.put("year", model.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = decorateServiceBillDao.findPager(map, null);
		List<DecorateServiceBill> list = paginater.getList();
		String branchNo = super.getSessionBranchNo(request);
		List<List<List<Object>>> dataList = new ArrayList<>();
		Map<String, List<List<Object>>> dataMap = new HashMap<>();
		List<String> buildingList = new ArrayList<>();
		for (int i = 0, size = list.size(); i < size; i++) {
			DecorateServiceBill bill = (DecorateServiceBill)list.get(i);
			String houseSn = bill.getHouseSn();
			String buildingNo = houseSn.split("-")[0];
			List<List<Object>> tmpList = dataMap.get(buildingNo);
			if (tmpList == null) {
				tmpList = new ArrayList<>();
				dataMap.put(buildingNo, tmpList);
			}
			List<Object> obj = new ArrayList<>();
			obj.add(bill.getId());
			obj.add(bill.getHouseSn());
			obj.add(bill.getOwnerName());
			obj.add(bill.getArea());
			obj.add(bill.getLiftFee());
			obj.add(bill.getCleanPrice());
			obj.add(bill.getCleanAmount());
			obj.add(bill.getAmount());//合计金额
			obj.add(bill.getPaidAmount());//实收金额
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
		String fileName = branchName+"装修服务费缴费信息-"+DateUtil.getCurrentDate()+"."+ParaManager.getExcelType(getSessionBranchNo(request));
		String title = "";
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ExportRule");
		String excelType = ParaManager.getExcelType(branchName);
		ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, buildingList.toArray(new String[buildingList.size()]), rules, dataList, excelType, response);
		exportExcelUtil.exportSheets();
		return null;
	}
	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	private String fileFileName;

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	
}
