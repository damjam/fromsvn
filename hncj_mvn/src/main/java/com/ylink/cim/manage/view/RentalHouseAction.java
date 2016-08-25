package com.ylink.cim.manage.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.DecorateState;
import com.ylink.cim.common.state.RentState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.DecorateType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.RentalHouseDao;
import com.ylink.cim.manage.dao.RenterInfoDao;
import com.ylink.cim.manage.domain.RentalHouse;
import com.ylink.cim.manage.service.RentalHouseService;
import com.ylink.cim.util.CopyPropertyUtil;
import com.ylink.cim.util.ExportExcelUtil;

import flink.etc.Assert;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.util.StringUtil;
import flink.web.CRUDAction;
@Scope("prototype")
@Component
public class RentalHouseAction extends CRUDAction implements ModelDriven<RentalHouse> {

	
	private static final long serialVersionUID = 1L;
	@Autowired
	private RentalHouseDao rentalHouseDao;
	@Autowired
	private RentalHouseService rentalHouseService;
	@Autowired
	private RenterInfoDao renterInfoDao;
	
	@Override
	public RentalHouse getModel() {
		return model;
	}
	
	private RentalHouse model = new RentalHouse();

	@Override
	public String list() throws Exception {
		Map<String, Object> params = getParaMap();
		if (isHQ()) {//总部
			params.put("branchNo", model.getBranchNo());
		}else {//机构
			params.put("branchNo", getSessionBranchNo(request));
		}
		params.put("houseSn", model.getHouseSn());
		params.put("state", model.getState());
		params.put("roomConfig", model.getRoomConfig());
		params.put("decorateType", model.getDecorateType());
		Paginater paginater = rentalHouseDao.findPaginater(params, getPager(request));
		saveQueryResult(request, paginater);
		initSelect();
		return LIST;
	}

	@Override
	public String toAdd() throws Exception {
		initSelect();
		StringBuilder builder = new StringBuilder();
		builder.append("<optgroup>");
		String[] configArray = model.getConfigArray();
		Map<String, String> roomConfigMap = ParaManager.getSysDict(SysDictType.RoomConfig.getValue());
		Set<String> roomConfigKey = roomConfigMap.keySet();
		for(String roomConfig: roomConfigKey) {
			builder.append("<option value=\""+roomConfig);
			builder.append("\"");
			if(ArrayUtils.contains(configArray, roomConfig)){
				builder.append(" selected");
			}
			builder.append(">");
			builder.append(roomConfig);
			builder.append("</option>");
		}
		builder.append("</optgroup>");
		request.setAttribute("optStr", builder.toString());
		return ADD;
	}

	@Override
	public String doAdd() throws Exception {
		try{
			RentalHouse rentalHouse = rentalHouseDao.findById(model.getHouseSn());
			Assert.isNull(rentalHouse, MsgUtils.r("已存在编号为{?}的房屋信息",model.getHouseSn()));
			String roomConfig = StringUtil.array2String(model.getConfigArray());
			model.setRoomConfig(roomConfig);
			rentalHouseService.save(model, getSessionUser(request));
			setSucResult(request);
		}catch (Exception e){
			setResult(false, e.getMessage(), request);
			return toAdd();
		}
		return "toMain";
	}

	@Override
	public String toEdit() throws Exception {
		RentalHouse rentalHouse = rentalHouseDao.findById(request.getParameter("id"));
		CopyPropertyUtil.copyPropertiesIgnoreNull(rentalHouse, model);
		StringBuilder builder = new StringBuilder();
		builder.append("<optgroup>");
		String roomConfig = rentalHouse.getRoomConfig();
		String[] configArray = null;
		if(StringUtils.isNotBlank(roomConfig)){
			configArray = roomConfig.split(",");
		}
		
		Map<String, String> roomConfigMap = ParaManager.getSysDict(SysDictType.RoomConfig.getValue());
		Set<String> roomConfigKey = roomConfigMap.keySet();
		for(String config: roomConfigKey) {
			builder.append("<option value=\""+config);
			builder.append("\"");
			if(ArrayUtils.contains(configArray, config)){
				builder.append(" selected");
			}
			builder.append(">");
			builder.append(config);
			builder.append("</option>");
		}
		builder.append("</optgroup>");
		request.setAttribute("optStr", builder.toString());
		initSelect();
		return EDIT;
	}

	@Override
	public String doEdit() throws Exception {
		try{
			String roomConfig = StringUtil.array2String(model.getConfigArray());
			model.setRoomConfig(roomConfig);
			RentalHouse rentalHouse = rentalHouseDao.findById(model.getHouseSn());
			CopyPropertyUtil.copyPropertiesIgnoreNull(model, rentalHouse);
			rentalHouseService.update(rentalHouse);
			setSucResult(request);
		}catch(Exception e){
			e.printStackTrace();
			return toEdit();
		}
		return "toMain";
	}

	@Override
	public String delete() throws Exception {
		try{
			rentalHouseService.delete(request.getParameter("id"));
			setSucResult(request);
		}catch(Exception e){
			setResult(false, e.getMessage(), request);
			return list();
		}
		return "toMain";
	}

	@Override
	public String detail() throws Exception {
		try {
			RentalHouse rentalHouse = rentalHouseDao.findById(model.getHouseSn());
			BeanUtils.copyProperties(model, rentalHouse);
		} catch (Exception e) {
			
		}
		return DETAIL;
	}
	
	private void initSelect() {
		request.setAttribute("rentStates", RentState.ALL.values());
		request.setAttribute("decorateTypes", DecorateType.ALL.values());
	}
	
	public String export() throws Exception {
		Map<String, Object> map = getParaMap();
		if(!super.isHQ()){
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = rentalHouseDao.findPaginater(map, null);//不再分页
		List<List<List<Object>>> dataList = new ArrayList<>();
		Map<String, List<List<Object>>> dataMap = new HashMap<>();
		List<String> buildingList = new ArrayList<>();
		for (int i = 0, size = paginater.getList().size(); i < size; i++) {
			RentalHouse rentalHouse = (RentalHouse)paginater.getList().get(i);
			List<List<Object>> tmpList = dataMap.get(rentalHouse.getBuildingNo());
			if (tmpList == null) {
				tmpList = new ArrayList<>();
				dataMap.put(rentalHouse.getBuildingNo(), tmpList);
			}
			List<Object> obj = new ArrayList<>();
			obj.add(rentalHouse.getHouseSn());
			obj.add(RentState.valueOf(rentalHouse.getState()).getName());
			obj.add(rentalHouse.getArea());
			obj.add(rentalHouse.getHouseType());
			if(StringUtils.isNotBlank(rentalHouse.getDecorateType())){
				obj.add(DecorateState.valueOf(rentalHouse.getDecorateType()).getName());
			}else {
				obj.add("");
			}
			obj.add(rentalHouse.getRoomConfig());
			obj.add(rentalHouse.getPayRule());
			obj.add(rentalHouse.getOtherRule());
			obj.add(rentalHouse.getRemark());
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
		String fileName = branchName+"出租屋信息-"+DateUtil.getCurrentDate()+"."+ParaManager.getExcelType(getSessionBranchNo(request));
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
}
