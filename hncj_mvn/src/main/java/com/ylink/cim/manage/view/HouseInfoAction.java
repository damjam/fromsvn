package com.ylink.cim.manage.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.DecorateState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.service.HouseInfoService;
import com.ylink.cim.util.ExportExcelUtil;
import com.ylink.cim.util.ReadExcelUtil;

import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.util.StringUtil;
import flink.web.BaseAction;
@Scope("prototype")
@Component
public class HouseInfoAction extends BaseAction implements ModelDriven<HouseInfo>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private HouseInfoDao houseInfoDao;
	@Autowired
	private HouseInfoService houseInfoService;

	public String toAdd() throws Exception {
		initSelect(request);
		return "add";
	}

	public String doAdd() throws Exception {
		try {
			HouseInfo houseInfo = new HouseInfo();
			BeanUtils.copyProperties(houseInfo, model);
			houseInfoService.add(houseInfo, getSessionUser(request));
			setResult(true, "添加成功", request);
			model.setBuildingNo("");
			model.setUnitNo("");
			model.setFloor("");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd();
		}
		return list();
	}

	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("houseSn", model.getHouseSn());
		map.put("buildingNo", model.getBuildingNo());
		map.put("unitNo", model.getUnitNo());
		map.put("floor", model.getFloor());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = houseInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect(request);
		return "list";
	}

	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			houseInfoService.delete(id, getSessionUser(request));
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
	public String export() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("houseSn", model.getHouseSn());
		map.put("buildingNo", model.getBuildingNo());
		map.put("unitNo", model.getUnitNo());
		map.put("floor", model.getFloor());
		if(!super.isHQ()){
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = houseInfoDao.findPager(map, null);//不再分页
		List<List<List<Object>>> dataList = new ArrayList<>();
		Map<String, List<List<Object>>> dataMap = new HashMap<>();
		List<String> buildingList = new ArrayList<>();
		for (int i = 0, size = paginater.getList().size(); i < size; i++) {
			HouseInfo houseInfo = (HouseInfo)paginater.getList().get(i);
			List<List<Object>> tmpList = dataMap.get(houseInfo.getBuildingNo());
			if (tmpList == null) {
				tmpList = new ArrayList<>();
				dataMap.put(houseInfo.getBuildingNo(), tmpList);
			}
			List<Object> obj = new ArrayList<>();
			obj.add(houseInfo.getHouseSn());
			obj.add(houseInfo.getArea());
			obj.add(houseInfo.getDeliveryDate());
			if(StringUtils.isNotBlank(houseInfo.getDecorateState())){
				obj.add(DecorateState.valueOf(houseInfo.getDecorateState()).getName());
			}else {
				obj.add("");
			}
			obj.add(houseInfo.getRemark());
			tmpList.add(obj);
 		}
		for (Map.Entry<String, List<List<Object>>> entry : dataMap.entrySet()) {
			dataList.add(entry.getValue());
			buildingList.add(entry.getKey());
		}
		String branchName = "";
		if(!super.isHQ()){
			String branchNo = getSessionBranchNo(request);
			branchName = BranchType.valueOf(branchNo).getName();
		}
		String fileName = branchName+"房屋信息-"+DateUtil.getCurrentDate()+"."+ParaManager.getExcelType(getSessionBranchNo(request));
		String title = "";
		/*Map<String, String> houseMap = ParaManager.getBranchDict(getSessionBranchNo(request), BranchDictType.HouseType.getValue());
		Map<String, String> flatMap = ParaManager.getBranchDict(getSessionBranchNo(request), BranchDictType.FlatType.getValue());
		List<String> buildingNos = new ArrayList<>();
		for (Map.Entry<String, String> entry : houseMap.entrySet()) {
			buildingNos.add(entry.getValue());
		}
		for (Map.Entry<String, String> entry : flatMap.entrySet()) {
			buildingNos.add(entry.getValue());
		}*/
		
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ExportRule");
		String excelType = ParaManager.getExcelType(getSessionBranchNo(request));
		ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, buildingList.toArray(new String[buildingList.size()]), rules, dataList, excelType, response);
		exportExcelUtil.exportSheets();
		return null;
	}
	public void initSelect(HttpServletRequest request) throws Exception {
		Map<String, String> buildingNos = new LinkedHashMap<String, String>();
		Map<String, String> unitsNos = new LinkedHashMap<String, String>();
		Map<String, String> floors = new LinkedHashMap<String, String>();
		for (int i = 1; i < 4; i++) {
			unitsNos.put(String.valueOf(i), String.valueOf(i));
		}
		Map<String, String> rent = ParaManager.getSysDict(SysDictType.RentType.getValue());
		Map<String, String> economical = ParaManager.getSysDict(SysDictType.EconomicalType.getValue());

		buildingNos.putAll(economical);
		buildingNos.putAll(rent);
		// buildingNos
		for (int i = 1; i < 13; i++) {
			floors.put(String.valueOf(i), String.valueOf(i));
		}
		request.setAttribute("unitNos", unitsNos);
		request.setAttribute("buildingNos", buildingNos);
		request.setAttribute("floors", floors);
	}

	@Override
	public HouseInfo getModel() {
		return model;
	}
	private HouseInfo model = new HouseInfo();
	public String toImport() {
		request.setAttribute("templateName", "房屋信息导入模板."+ParaManager.getExcelType(getSessionBranchNo(request)));
		return "import";
	}
	
	public String doImport() {
		try{
			File file = this.getFile();
			FileInputStream fis = new FileInputStream(file);
			String suffix = fileFileName.substring(fileFileName.lastIndexOf(".")+1);//扩展名
			List<Map<String, String>> houseInfoRule = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ImportRule");
			List<List<Map<String, Object>>> list = ReadExcelUtil.read(fis, suffix, houseInfoRule);
			houseInfoService.addFromExcel(list, getSessionUser(request));
			setSucResult(request);
		}catch (Exception e){
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
	private String suffix;

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
}
