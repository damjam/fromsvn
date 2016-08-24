package com.ylink.cim.manage.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.CarInfoDao;
import com.ylink.cim.manage.domain.CarInfo;
import com.ylink.cim.manage.service.CarInfoService;
import com.ylink.cim.util.CopyPropertyUtil;
import com.ylink.cim.util.ExportExcelUtil;
import com.ylink.cim.util.ReadExcelUtil;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.util.StringUtil;
import flink.web.BaseAction;
@Scope("prototype")
@Component
public class CarInfoAction extends BaseAction implements ModelDriven<CarInfo>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CarInfoDao carInfoDao;
	@Autowired
	private CarInfoService carInfoService;

	private CarInfo model = new CarInfo();
	
	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			carInfoService.delete(id, getSessionUser(request));
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
	
	public String doAdd() throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("carSn", model.getCarSn());
			params.put("branchNo", model.getBranchNo());
			List<CarInfo> list = carInfoDao.findList(params);
			Assert.isEmpty(list, "车牌号已存在，请重新指定");
			CarInfo carInfo = new CarInfo();
			BeanUtils.copyProperties(carInfo, model);
			carInfoService.save(carInfo, getSessionUser(request));
			setSucResult(request);
		}catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd();
		}
		
		return "toMain";
	}

	public String doEdit() throws Exception {
		try {
			CarInfo carInfo = carInfoDao.findById(model.getId());
			CopyPropertyUtil.copyPropertiesIgnoreNull(model, carInfo);
			carInfoService.update(carInfo, getSessionUser(request));
			setSucResult("修改成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			setResult(false, "修改失败", request);
			e.printStackTrace();
			return toEdit();
		}
		return "toMain";
	}

	@Override
	public CarInfo getModel() {
		return model;
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

	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("houseSn", model.getHouseSn());
		map.put("carSn", model.getCarSn());
		map.put("brand", model.getBrand());
		map.put("model", model.getModel());
		map.put("ownerName", model.getOwnerName());
		map.put("ownerCel", model.getOwnerCel());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = carInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect(request);
		return "list";
	}

	public String toAdd() throws Exception {
		
		//return forward("/pages/manage/car/carInfoAdd.jsp");
		return "add";
	}
	public String toEdit() throws Exception {
		initSelect(request);
		
		String id = model.getId();
		CarInfo carInfo = carInfoDao.findById(id);
		BeanUtils.copyProperties(model, carInfo);
		//return forward("/pages/manage/car/carInfoEdit.jsp");
		return "edit";
	}
	public String export() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("houseSn", model.getHouseSn());
		map.put("carSn", model.getCarSn());
		map.put("brand", model.getBrand());
		map.put("model", model.getModel());
		map.put("ownerName", model.getOwnerName());
		map.put("ownerCel", model.getOwnerCel());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = carInfoDao.findPager(map, null);
		List<CarInfo> list = paginater.getList();
		String branchNo = super.getSessionBranchNo(request);
		List<List<Object>> dataList = new ArrayList<>();
		for (int i = 0, size = list.size(); i < size; i++) {
			CarInfo info = (CarInfo)list.get(i);
			List<Object> obj = new ArrayList<>();
			obj.add(info.getCarSn());
			obj.add(info.getBrand()+info.getModel());
			obj.add(info.getOwnerName());
			obj.add(info.getOwnerCel());
			obj.add(info.getHouseSn());
			obj.add(info.getParkingSn());
			obj.add(info.getCreateDate());
			obj.add(info.getRemark());
			dataList.add(obj);
 		}
		
		String branchName = BranchType.valueOf(branchNo).getName();
		String fileName = branchName+"车辆信息-"+DateUtil.getCurrentDate()+"."+ParaManager.getExcelType(getSessionBranchNo(request));
		String title = "";
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ExportRule");
		String excelType = ParaManager.getExcelType(branchName);
		//ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, buildingList.toArray(new String[buildingList.size()]), rules, dataList, excelType, response);
		ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, "车辆信息", rules, dataList, excelType, response);
		exportExcelUtil.exportSheet();
		return null;
	}
	public String toImport() throws Exception {
		request.setAttribute("templateName", "车辆信息导入模板."+ParaManager.getExcelType(getSessionBranchNo(request)));
		return "import";
	}
	public String doImport() throws Exception {
		try{
			File file = this.getFile();
			FileInputStream fis = new FileInputStream(file);
			String suffix = fileFileName.substring(fileFileName.lastIndexOf(".")+1);//扩展名
			List<Map<String, String>> houseInfoRule = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ImportRule");
			List<List<Map<String, Object>>> list = ReadExcelUtil.read(fis, suffix, houseInfoRule);
			int cnt = carInfoService.addFromExcel(list, getSessionUser(request));
			setSucResult(MsgUtils.r("共导入{?}条记录", cnt), request);
		}catch (Exception e){
			e.printStackTrace();
			if (e instanceof BizException) {
				setResult(false, e.getMessage(), request);
			}else {
				setResult(false, "操作失败:"+e.getMessage(), request);
			}
			return toImport();
		}
		return "toMain";
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
