package com.ylink.cim.manage.view;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.CarInfoDao;
import com.ylink.cim.manage.domain.CarInfo;
import com.ylink.cim.manage.service.CarInfoService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
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
	
	private void clearForm() {
		model.setOwnerName("");
		model.setCarSn("");
		model.setOwnerCel("");
		model.setHouseSn("");
		model.setBrand("");
		model.setModel("");
	}
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
			setResult(true, "操作成功", request);
			clearForm();
		}catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd();
		}
		
		return list();
	}

	public String doEdit() throws Exception {
		try {
			
			CarInfo carInfo = carInfoDao.findById(model.getId());
			String createUser = carInfo.getCreateUser();
			Date createDate = carInfo.getCreateDate();
			String ownerId = carInfo.getOwnerId();
			BeanUtils.copyProperties(carInfo, model);
			carInfo.setCreateDate(createDate);
			carInfo.setCreateUser(createUser);
			carInfo.setOwnerId(ownerId);
			carInfoService.update(carInfo, getSessionUser(request));
			clearForm();
			setResult(true, "修改成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			setResult(false, "修改失败", request);
			e.printStackTrace();
			return toEdit();
		}
		return list();
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
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = carInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect(request);
		//return forward("/pages/manage/car/carInfoList.jsp");
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
}
