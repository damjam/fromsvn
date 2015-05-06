package com.ylink.cim.manage.view;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.service.HouseInfoService;

import flink.etc.BizException;
import flink.util.Paginater;
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
		//return forward("/pages/manage/house/houseInfoAdd.jsp");
		return "";
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
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = houseInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect(request);
		//return forward("/pages/manage/house/houseInfoList.jsp");
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

	public HouseInfo getModel() {
		return model;
	}
	private HouseInfo model = new HouseInfo();
}
