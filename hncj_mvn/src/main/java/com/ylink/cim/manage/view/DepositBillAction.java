package com.ylink.cim.manage.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.DepositBillDao;
import com.ylink.cim.manage.domain.DepositBill;
import com.ylink.cim.manage.service.BillService;
import com.ylink.cim.manage.service.ImportService;
import com.ylink.cim.util.ExportExcelUtil;
import com.ylink.cim.util.ReadExcelUtil;

import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.util.StringUtil;
import flink.web.BaseAction;

/**
 * 收押金
 * 
 * @author libaozhu
 * @date 2015-3-16
 */
@Scope("prototype")
@Component
public class DepositBillAction extends BaseAction implements ModelDriven<DepositBill> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DepositBillDao depositBillDao = (DepositBillDao) getService("depositBillDao");
	private BillService billService = (BillService) getService("billService");
	private ImportService importService = (ImportService)getService("importService");
	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeDepositFee(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list();
	}

	public String deleteDepositBill() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(DepositBill.class, id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list();
	}

	public String doAdd() throws Exception {
		try {

			DepositBill depositBill = new DepositBill();
			BeanUtils.copyProperties(depositBill, model);
			billService.saveDepositBill(depositBill, getSessionUser(request));
			setResult(true, "数据已保存", request);
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
		map.put("state", model.getState());
		map.put("startDepositDate", model.getStartDepositDate());
		map.put("endDepositDate", model.getEndDepositDate());
		map.put("startRefundDate", model.getStartRefundDate());
		map.put("endRefundDate", model.getEndRefundDate());
		map.put("id", model.getId());
		map.put("year", model.getYear());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = depositBillDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = depositBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		// return forward("/pages/manage/charge/deposit/depositBillList.jsp");
		return "list";
	}

	public String refund() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.refund(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			e.printStackTrace();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
		}
		return list();
	}

	public String toAdd() throws Exception {
		YesNoType.setInReq(request);
		// return forward("/pages/manage/charge/deposit/depositBillAdd.jsp");
		return "add";
	}

	public String export() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("state", model.getState());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = depositBillDao.findPager(map, null);
		List<DepositBill> list = paginater.getList();
		List<List<List<Object>>> dataList = new ArrayList<>();
		Map<String, List<List<Object>>> dataMap = new HashMap<>();
		List<String> sheetNameList = new ArrayList<>();
		for (int i = 0, size = list.size(); i < size; i++) {
			DepositBill bill = list.get(i);
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
			obj.add(bill.getPayerName());
			obj.add(bill.getDepositDate());
			obj.add(bill.getAmount());//备用电话
			obj.add(bill.getPurpose());
			obj.add(bill.getDepositUser());
			obj.add(bill.getRefundDate());
			obj.add(bill.getRefundUser());
			obj.add(bill.getRefundAmount());//退款金额
			obj.add(BillState.valueOf(bill.getState()).getName());
			obj.add(bill.getRemark());
			tmpList.add(obj);
 		}
		for (Map.Entry<String, List<List<Object>>> entry : dataMap.entrySet()) {
			dataList.add(entry.getValue());
			sheetNameList.add(entry.getKey());
		}
		String fileName = "押金信息-"+DateUtil.getCurrentDate()+"."+ParaManager.getExcelType(getSessionBranchNo(request));
		String title = "";
		String exportRule = StringUtil.class2Object(this.getModel().getClass().getName())+"ExportRule"; 
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService(exportRule);
		String excelType = ParaManager.getExcelType(getSessionBranchNo(request));
		ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, sheetNameList.toArray(new String[sheetNameList.size()]), rules, dataList, excelType, response);
		exportExcelUtil.exportSheets();
		return null;
	}
	public String toImport() {
		request.setAttribute("templateName", "员工信息导入模板."+ParaManager.getExcelType(getSessionBranchNo(request)));
		return "import";
	}
	public String doImport() {
		try{
			File file = this.getFile();
			FileInputStream fis = new FileInputStream(file);
			String suffix = fileFileName.substring(fileFileName.lastIndexOf(".")+1);//扩展名
			List<Map<String, String>> rule = (List<Map<String, String>>)SpringContext.getService("depositBillImportRule");
			List<List<Map<String, Object>>> list = ReadExcelUtil.read(fis, suffix, rule);
			Integer totalCnt = importService.addDepositBillFromExcel(list, getSessionUser(request));
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
	
	@Override
	public DepositBill getModel() {
		return model;
	}

	private DepositBill model = new DepositBill();
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
