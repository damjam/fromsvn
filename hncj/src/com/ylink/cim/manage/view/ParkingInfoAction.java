package com.ylink.cim.manage.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.ParkingState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.ParkingInfoDao;
import com.ylink.cim.manage.domain.ParkingInfo;
import com.ylink.cim.manage.service.ParkingInfoService;
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
public class ParkingInfoAction extends BaseAction implements
		ModelDriven<ParkingInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ParkingInfoDao parkingInfoDao;
	@Autowired
	private ParkingInfoService parkingInfoService;

	public String toEdit() throws Exception {
		String id = model.getId();
		ParkingInfo parkingInfo = parkingInfoDao.findById(id);
		BeanUtils.copyProperties(model, parkingInfo);
		ParkingState.setInReq(request);
		return "edit";
	}

	public String toAdd() throws Exception {
		ParkingState.setInReq(request);
		return "add";
	}

	public String doAdd() throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("sn", model.getSn());
			params.put("branchNo", getSessionBranchNo(request));
			Assert.isEmpty(parkingInfoDao.findBy(params), "车位号已存在，请重新指定");
			ParkingInfo parkingInfo = new ParkingInfo();
			BeanUtils.copyProperties(parkingInfo, model);
			parkingInfoService.save(parkingInfo, getSessionUser(request));
		} catch (BizException e) {
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
			ParkingInfo parkingInfo = parkingInfoDao.findById(model.getId());
			String createUser = parkingInfo.getCreateUser();
			Date createDate = parkingInfo.getCreateDate();
			String branchNo = parkingInfo.getBranchNo();
			BeanUtils.copyProperties(parkingInfo, model);
			parkingInfo.setCreateDate(createDate);
			parkingInfo.setCreateUser(createUser);
			parkingInfo.setBranchNo(branchNo);
			parkingInfoService.update(parkingInfo, getSessionUser(request));
			setSucResult("添加成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toEdit();
		}
		return "toMain";
	}

	public String list() throws Exception {
		ParkingState.setInReq(request);
		Map<String, Object> map = getParaMap();
		map.put("sn", model.getSn());
		map.put("carSn", model.getCarSn());
		map.put("ownerName", model.getOwnerName());
		map.put("ownerCel", model.getOwnerCel());
		map.put("endUser", model.getOwnerName());
		map.put("endUserCel", model.getOwnerCel());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = parkingInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return "list";
	}

	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			parkingInfoService.delete(id, getSessionUser(request));
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
	
	public String toImport() throws Exception {
		request.setAttribute("templateName", "车位信息导入模板."+ParaManager.getExcelType(getSessionBranchNo(request)));
		return "import";
	}
	public String doImport() throws Exception{
		try{
			File file = this.getFile();
			FileInputStream fis = new FileInputStream(file);
			String suffix = fileFileName.substring(fileFileName.lastIndexOf(".")+1);//扩展名
			List<Map<String, String>> houseInfoRule = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ImportRule");
			List<List<Map<String, Object>>> list = ReadExcelUtil.read(fis, suffix, houseInfoRule);
			int cnt = parkingInfoService.addFromExcel(list, getSessionUser(request));
			setSucResult(MsgUtils.r("共导入{?}条记录", cnt), request);
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
	public String export() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("sn", model.getSn());
		map.put("carSn", model.getCarSn());
		map.put("ownerName", model.getOwnerName());
		map.put("ownerCel", model.getOwnerCel());
		map.put("endUser", model.getOwnerName());
		map.put("endUserCel", model.getOwnerCel());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = parkingInfoDao.findPager(map, getPager(request));
		List<ParkingInfo> list = paginater.getList();
		String branchNo = super.getSessionBranchNo(request);
		List<List<Object>> dataList = new ArrayList<>();
		for (int i = 0, size = list.size(); i < size; i++) {
			ParkingInfo info = (ParkingInfo)list.get(i);
			List<Object> obj = new ArrayList<>();
			obj.add(info.getSn());
			obj.add(info.getOwnerName());
			obj.add(info.getOwnerCel());
			obj.add(info.getEndUser());
			obj.add(info.getEndUserCel());
			obj.add(ParkingState.valueOf(info.getState()).getName());
			//obj.add(info.getCreateDate());
			obj.add(info.getRemark());
			dataList.add(obj);
 		}
		
		String branchName = BranchType.valueOf(branchNo).getName();
		String fileName = branchName+"车位信息-"+DateUtil.getCurrentDate()+"."+ParaManager.getExcelType(getSessionBranchNo(request));
		String title = "";
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ExportRule");
		String excelType = ParaManager.getExcelType(branchName);
		//ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, buildingList.toArray(new String[buildingList.size()]), rules, dataList, excelType, response);
		ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, "车位信息", rules, dataList, excelType, response);
		exportExcelUtil.exportSheet();
		return null;
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

	public String queryPopup() throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			params.put("avai", true);
			Paginater paginater = this.parkingInfoDao.findPager(params,
					getPager(request));
			saveQueryResult(request, paginater);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return forward("/pages/popUp/popUpParkingInfo.jsp");
		return "popUp";
	}

	@Override
	public ParkingInfo getModel() {
		return model;
	}

	private ParkingInfo model = new ParkingInfo();
}
