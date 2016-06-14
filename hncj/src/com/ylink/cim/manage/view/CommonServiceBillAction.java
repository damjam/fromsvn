package com.ylink.cim.manage.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.state.CheckinState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.CommonServiceBillDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.service.BillService;
import com.ylink.cim.manage.service.ExportService;
import com.ylink.cim.manage.service.ImportService;
import com.ylink.cim.util.ReadExcelUtil;
import com.ylink.cim.util.ExportExcelUtil;

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
public class CommonServiceBillAction extends BaseAction implements
		ModelDriven<CommonServiceBill> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CommonServiceBillDao commonServiceBillDao;
	@Autowired
	private BillService billService;
	@Autowired
	private OwnerInfoDao ownerInfoDao;
	@Autowired
	private ImportService importService;
	@Autowired
	private ExportService exportSerice;
	Logger log = Logger.getLogger(CommonServiceBillAction.class);

	public String toAdd() throws Exception {
		YesNoType.setInReq(request);
		CheckinState.setInReq(request);
		return "add";
	}

	public String doAdd() throws Exception {
		try {
			CommonServiceBill bill = new CommonServiceBill();
			BeanUtils.copyProperties(bill, model);
			billService.saveServiceBill(bill, getSessionUser(request));
			setResult(true, "数据已保存", request);
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
		Paginater paginater = commonServiceBillDao.findPager(map,
				getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = commonServiceBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		// return
		// forward("/pages/manage/charge/common/commonServiceBillList.jsp");
		return "list";
	}

	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeCommonFee(id, getSessionUser(request));
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
			billService.deleteBill(CommonServiceBill.class, id,
					getSessionUser(request));
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
		String servicePrice = "";
		String lightPrice = "";
		String checkinState = "";
		try {
			String houseSn = request.getParameter("houseSn");
			OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(houseSn, getSessionBranchNo(request));
			if (ownerInfo != null) {
				ownerName = ownerInfo.getOwnerName();
				checkinState = ownerInfo.getCheckinState();
			}
			HouseInfo houseInfo = commonServiceBillDao.findById(
					HouseInfo.class, houseSn);
			Assert.notNull(houseInfo, "编号为" + houseSn + "的房屋信息不存在!");
			Double area = houseInfo.getArea();
			areaStr = String.valueOf(area);
			servicePrice = ParaManager.getServicePrice(Integer
					.parseInt(houseInfo.getFloor()));
			lightPrice = ParaManager.getLightPrice(houseInfo.getBuildingNo());
			// return null;
		} catch (Exception e) {
			jsonObject.put("error", e.getMessage());
			setResult(false, "失败", request);
			e.printStackTrace();
			respond(response, jsonObject.toString());
			return null;
		}
		jsonObject.put("ownerName", ownerName);
		jsonObject.put("area", areaStr);
		jsonObject.put("servicePrice", servicePrice);
		jsonObject.put("lightPrice", lightPrice);
		jsonObject.put("checkinState", checkinState);
		respond(response, jsonObject.toString());
		return null;
	}

	public String getAcctInfo() throws Exception {
		JSONObject jsonObject = new JSONObject();
		String endDateStr = "";
		String serviceAmount = "";
		String lightAmount = "";
		String totalAmount = "";
		try {
			String startDate = request.getParameter("startDate");
			String monthNumStr = request.getParameter("monthNum");
			String servicePriceStr = request.getParameter("servicePrice");
			String lightPrice = request.getParameter("lightPrice");
			// String houseSn = request.getParameter("houseSn");
			String areaStr = request.getParameter("area");
			Double area = Double.parseDouble(areaStr);
			// HouseInfo houseInfo =
			// commonServiceBillDao.findById(HouseInfo.class, houseSn);
			Integer monthNum = Integer.parseInt(monthNumStr);
			Date endDate = DateUtil.addMonths(
					DateUtil.getDateByYYYMMDD(startDate), monthNum);
			endDate = DateUtil.addDays(endDate, -1);
			endDateStr = DateUtil.getDateYYYYMMDD(endDate);

			serviceAmount = MoneyUtil.getFormatStr2(Double
					.parseDouble(servicePriceStr) * area * monthNum);

			lightAmount = MoneyUtil.getFormatStr2(Double
					.parseDouble(lightPrice) * monthNum);
			totalAmount = MoneyUtil.getFormatStr2(Double
					.parseDouble(serviceAmount)
					+ Double.parseDouble(lightAmount));
		} catch (Exception e) {
			setResult(false, "失败", request);
			e.printStackTrace();
			endDateStr = "";
		}
		jsonObject.put("endDate", endDateStr);
		jsonObject.put("serviceAmount", serviceAmount);
		jsonObject.put("lightAmount", lightAmount);
		jsonObject.put("totalAmount", totalAmount);
		respond(response, jsonObject.toString());
		return null;
	}

	@Override
	public CommonServiceBill getModel() {
		return model;
	}

	public String toImport() {
		request.setAttribute("templateName", "物业费信息导入模板."+ParaManager.getExcelType(getSessionBranchNo(request)));
		return "import";
	}
	public String doImport() {
		try{
			File file = this.getFile();
			FileInputStream fis = new FileInputStream(file);
			String suffix = fileFileName.substring(fileFileName.lastIndexOf(".")+1);//扩展名
			List<Map<String, String>> csbRule = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ImportRule");
			List<List<Map<String, Object>>> list = ReadExcelUtil.read(fis, suffix, csbRule);
			Integer totalCnt = importService.addCSBFromExcel(list, getSessionUser(request));
			setSucResult("共导入"+totalCnt+"条记录",request);
		}catch (Exception e){
			e.printStackTrace();
			if (e instanceof BizException) {
				setResult(false, e.getMessage(), request);
			}else {
				setResult(false, "操作失败"+e.getMessage(), request);
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
		Paginater paginater = commonServiceBillDao.findPager(map, null);
		List<CommonServiceBill> list = paginater.getList();
		String branchNo = super.getSessionBranchNo(request);
		List<List<List<Object>>> dataList = new ArrayList<>();
		Map<String, List<List<Object>>> dataMap = new HashMap<>();
		List<String> buildingList = new ArrayList<>();
		for (int i = 0, size = list.size(); i < size; i++) {
			CommonServiceBill bill = (CommonServiceBill)list.get(i);
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
		String fileName = branchName+"物业费缴费信息-"+DateUtil.getCurrentDate()+"."+ParaManager.getExcelType(getSessionBranchNo(request));
		String title = "";
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ExportRule");
		String excelType = ParaManager.getExcelType(branchName);
		ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, buildingList.toArray(new String[buildingList.size()]), rules, dataList, excelType, response);
		exportExcelUtil.exportSheets();
		return null;
	}
	private CommonServiceBill model = new CommonServiceBill();
	
	private File file;
	private String fileFileName;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
}
