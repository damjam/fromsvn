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
import com.ylink.cim.common.type.SexType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.EmpTransferDao;
import com.ylink.cim.manage.dao.EmployeeDao;
import com.ylink.cim.manage.domain.EmpTransfer;
import com.ylink.cim.manage.domain.Employee;
import com.ylink.cim.manage.service.EmployeeService;
import com.ylink.cim.manage.service.ImportService;
import com.ylink.cim.util.ExportExcelUtil;
import com.ylink.cim.util.ReadExcelUtil;

import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.web.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Scope("prototype")
@Component
public class EmployeeAction extends BaseAction implements ModelDriven<Employee> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private EmpTransferDao empTransferDao;
	@Autowired
	private ImportService importService;
	
	@Override
	public Employee getModel() {
		return model;
	}
	private Employee model = new Employee();

	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("startCreateDate", model.getStartCreateDate());
		map.put("endCreateDate", model.getEndCreateDate());
		map.put("state", model.getState());
		map.put("tel", model.getTel());
		map.put("name", model.getName());
		
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		map.put("position", model.getPosition());
		Paginater paginater = employeeDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect();
		return "list";
	}
	public String toAdd() throws Exception {
		initSelect();
		return "add";
	}
	public String toEdit() throws Exception {
		Employee employee = employeeDao.findById(model.getId());
		BeanUtils.copyProperties(model, employee);
		initSelect();
		return "edit";
	}
	public String doAdd() throws Exception {
		try {
			employeeService.save(model, getSessionUser(request));
			setSucResult("数据已保存", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败" + e.getMessage(), request);
			return toEdit();
		}
		return "toMain";
	}
	public String doEdit() throws Exception {
		try {
			employeeService.update(model, getSessionUser(request));
			setSucResult("数据已保存", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败" + e.getMessage(), request);
			return toEdit();
		}
		return "toMain";
	}
	public String detail() throws Exception {
		try {
			String id = model.getId();
			Employee employee = employeeDao.findById(id);
			request.setAttribute("branchName", ParaManager.getBranches(true).get(employee.getBranchNo()));
			request.setAttribute("positionName", ParaManager.getAllPositions().get(employee.getPosition()));
			BeanUtils.copyProperties(model, employee);
			List<EmpTransfer> list = empTransferDao.findByEmpId(model.getId());
			request.setAttribute("transferSize", list.size());
			saveQueryResult(request, list, "transfers");
		} catch (Exception e) {
			setFailResult("系统错误", request);
			e.printStackTrace();
			return "toMain";
		}
		return "detail";
	}
	
	public String delete() throws Exception {
		try {
			String id = model.getId();
			employeeService.delete(id);
			setSucResult("操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return "toMain";
	}
	public String changeState() throws Exception {
		try {
			String id = model.getId();
			String state = model.getState();
			employeeService.changeState(id, state, getSessionUser(request));
			setSucResult("操作成功", request);
		} catch (Exception e) {
			setFailResult("操作失败", request);
			e.printStackTrace();
		}
		return "toMain";
	}
	
	public String toTransfer() throws Exception {
		initSelect();
		return "transfer";
	}
	public String doTransfer() throws Exception {
		try{
			employeeService.transfer(model, getSessionUser(request));
			return "transfer";
		}catch(BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
		}catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return toTransfer();
	}
	public String showTransfer() throws Exception {
		List<EmpTransfer> list = empTransferDao.findByEmpId(model.getId());
		saveQueryResult(request, list);
		return "transferList";
	}
	private void initSelect() {
		request.setAttribute("branches", ParaManager.getBranches(true));
		request.setAttribute("positionTypes", ParaManager.getSysDict(SysDictType.BranchPostType.getValue()));
		SexType.setInReq(request);
	}
	public String toAddVocation() throws Exception {
		
		return "vocation";
	}
	public String addVocation() throws Exception {
		try {
			EmpTransfer empTransfer = model.getEmpTransfer();
			if (empTransfer.getEndDate().compareTo(empTransfer.getBeginDate()) <= 0) {
				throw new BizException("结束日期必须在开始日期之后");
			}
			employeeService.addVocation(model, getSessionUser(request));
			setSucResult("操作成功", request);
		} catch(BizException e){
			setResult(false, e.getMessage(), request);
			e.printStackTrace();
			return toAddVocation();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toAddVocation();
		}
		return "toMain";
	}
	public String export() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("state", model.getState());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = employeeDao.findPager(map, null);
		List<Employee> list = paginater.getList();
		List<List<List<Object>>> dataList = new ArrayList<>();
		Map<String, List<List<Object>>> dataMap = new HashMap<>();
		List<String> sheetNameList = new ArrayList<>();
		for (int i = 0, size = list.size(); i < size; i++) {
			Employee employee = list.get(i);
			String branchNo = employee.getBranchNo();
			String branchName = ParaManager.getBranches(true).get(branchNo);
			List<List<Object>> tmpList = dataMap.get(branchName);
			if (tmpList == null) {
				tmpList = new ArrayList<>();
				dataMap.put(branchName, tmpList);
			}
			List<Object> obj = new ArrayList<>();
			obj.add(employee.getName());
			obj.add(employee.getGender());
			obj.add(employee.getPositionName());
			obj.add(employee.getTel());
			obj.add(employee.getSpareTel());//备用电话
			obj.add(employee.getLivePlace());
			obj.add(employee.getHireDate());
			obj.add(employee.getState());
			//
			obj.add(employee.getIdCard());
			obj.add(employee.getBirthday());
			obj.add(employee.getNativePlace());//户籍地址
			obj.add(employee.getDegree());
			obj.add(employee.getMajor());
			obj.add(employee.getSchool());
			obj.add(employee.getInstancyPerson());
			obj.add(employee.getInstancyTel());
			obj.add(employee.getEmail());
			obj.add(employee.getWeixin());
			obj.add(employee.getWeibo());
			obj.add(employee.getQq());
			obj.add(employee.getHobby());
			obj.add(employee.getReview());
			tmpList.add(obj);
 		}
		for (Map.Entry<String, List<List<Object>>> entry : dataMap.entrySet()) {
			dataList.add(entry.getValue());
			sheetNameList.add(entry.getKey());
		}
		String fileName = "员工信息-"+DateUtil.getCurrentDate()+"."+ParaManager.getExcelType(getSessionBranchNo(request));
		String title = "";
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService("employeeExportRule");
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
			List<Map<String, String>> rule = (List<Map<String, String>>)SpringContext.getService("employeeImportRule");
			List<List<Map<String, Object>>> list = ReadExcelUtil.read(fis, suffix, rule);
			Integer totalCnt = employeeService.addFromExcel(list, getSessionUser(request));
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
	public String loadPosts() throws Exception {
		JSONObject object = new JSONObject();
		try {
			JSONArray array = new JSONArray();
			Map<String, String> positionMap = null;
			if (isHQ()) {
				positionMap = ParaManager.getSysDict(SysDictType.CenterPostType.getValue());
			}else {
				positionMap = ParaManager.getSysDict(SysDictType.BranchPostType.getValue());
			}
			for (Map.Entry<String, String> entry : positionMap.entrySet()) {
				JSONObject position = new JSONObject();
				position.put("key", entry.getKey());
				position.put("value", entry.getValue());
				array.add(position);
			}
			object.put("positions", array);
			object.put("status", "1");
		} catch (Exception e) {
			object.put("status", "0");
		}
		respond(response, object.toString());
		return null;
	}
	public String queryPopup() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("state", model.getState());
		map.put("tel", model.getTel());
		map.put("branchNo", model.getBranchNo());
		map.put("userInfo", getSessionUser(request));
		map.put("name", model.getName());
		map.put("position", model.getPosition());
		Paginater paginater = employeeDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect();
		request.setAttribute("bindCode", request.getParameter("bindCode"));
		request.setAttribute("bindName", request.getParameter("bindName"));
		return "popup";
	}
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
