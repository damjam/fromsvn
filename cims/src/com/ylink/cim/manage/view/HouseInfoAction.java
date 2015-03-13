package com.ylink.cim.manage.view;

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
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.service.HouseInfoService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class HouseInfoAction extends BaseDispatchAction {
	private HouseInfoDao houseInfoDao = (HouseInfoDao)getService("houseInfoDao");
	private HouseInfoService houseInfoService = (HouseInfoService)getService("houseInfoService");
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initSelect(request);
		houseInfoService.test();
		return forward("/pages/manage/house/houseInfoAdd.jsp");
	}
	
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HouseInfoActionForm actionForm = (HouseInfoActionForm)form;
			HouseInfo houseInfo = new HouseInfo();
			BeanUtils.copyProperties(houseInfo, actionForm);
			houseInfoService.add(houseInfo, getSessionUser(request));
			setResult(true, "添加成功", request);
			actionForm.setBuildingNo("");
			actionForm.setUnitNo("");
			actionForm.setFloor("");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = getParaMap();
		HouseInfoActionForm actionForm = (HouseInfoActionForm)form;
		map.put("houseSn", actionForm.getHouseSn());
		map.put("buildingNo", actionForm.getBuildingNo());
		map.put("unitNo", actionForm.getUnitNo());
		map.put("floor", actionForm.getFloor());
		Paginater paginater = houseInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect(request);
		return forward("/pages/manage/house/houseInfoList.jsp");
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		//buildingNos
		for (int i = 1; i < 13; i++) {
			floors.put(String.valueOf(i), String.valueOf(i));
		}
		request.setAttribute("unitNos", unitsNos);
		request.setAttribute("buildingNos", buildingNos);
		request.setAttribute("floors", floors);
	}
}
