package com.ylink.cim.manage.view;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.CarInfoDao;
import com.ylink.cim.manage.domain.CarInfo;
import com.ylink.cim.manage.service.CarInfoService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class CarInfoAction extends BaseDispatchAction {
	private CarInfoDao carInfoDao = (CarInfoDao) getService("carInfoDao");
	private CarInfoService carInfoService = (CarInfoService) getService("carInfoService");

	
	public ActionForward toEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initSelect(request);
		CarInfoActionForm actionForm = (CarInfoActionForm)form;
		String carSn = actionForm.getCarSn();
		CarInfo carInfo = carInfoDao.findById(carSn);
		BeanUtils.copyProperties(actionForm, carInfo);
		return forward("/pages/manage/car/carInfoEdit.jsp");
	}
	
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return forward("/pages/manage/car/carInfoAdd.jsp");
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			CarInfoActionForm actionForm = (CarInfoActionForm) form;
			CarInfo carInfo = carInfoDao.findById(actionForm.getCarSn());
			Assert.isNull(carInfo, "车牌号已存在，请重新指定");
			carInfo = new CarInfo();
			BeanUtils.copyProperties(carInfo, actionForm);
			carInfoService.save(carInfo, getSessionUser(request));
			clearForm(actionForm);
		}catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd(mapping, form, request, response);
		}
		
		return list(mapping, form, request, response);
	}
	
	private void clearForm(CarInfoActionForm actionForm) {
		actionForm.setOwnerName("");
		actionForm.setCarSn("");
		actionForm.setOwnerCel("");
		actionForm.setHouseSn("");
		actionForm.setBrand("");
		actionForm.setModel("");
	}

	public ActionForward doEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			CarInfoActionForm actionForm = (CarInfoActionForm) form;
			CarInfo carInfo = carInfoDao.findById(actionForm.getCarSn());
			if (carInfo == null) {
				carInfo = new CarInfo();
				BeanUtils.copyProperties(carInfo, actionForm);
				carInfoService.save(carInfo, getSessionUser(request));
			}else {
				String createUser = carInfo.getCreateUser();
				Date createDate = carInfo.getCreateDate();
				String ownerId = carInfo.getOwnerId();
				BeanUtils.copyProperties(carInfo, actionForm);
				carInfo.setCreateDate(createDate);
				carInfo.setCreateUser(createUser);
				carInfo.setOwnerId(ownerId);
				carInfoService.update(carInfo, getSessionUser(request));
			}
			setResult(true, "添加成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toEdit(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toEdit(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = getParaMap();
		CarInfoActionForm actionForm = (CarInfoActionForm) form;
		map.put("houseSn", actionForm.getHouseSn());
		map.put("carSn", actionForm.getCarSn());
		map.put("brand", actionForm.getBrand());
		map.put("model", actionForm.getModel());
		map.put("ownerName", actionForm.getOwnerName());
		map.put("ownerCel", actionForm.getOwnerCel());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = carInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect(request);
		return forward("/pages/manage/car/carInfoList.jsp");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		return list(mapping, form, request, response);
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
}
