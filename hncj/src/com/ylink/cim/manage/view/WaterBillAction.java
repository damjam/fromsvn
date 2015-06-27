package com.ylink.cim.manage.view;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.WaterBillDao;
import com.ylink.cim.manage.domain.WaterBill;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class WaterBillAction extends BaseAction implements
		ModelDriven<WaterBill> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private WaterBillDao waterBillDao;
	@Autowired
	private BillService billService;

	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeWaterFee(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list();
	}

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		map.put("startCreateDate", model.getStartCreateDate());
		map.put("endCreateDate", model.getEndCreateDate());
		map.put("houseSn", model.getHouseSn());
		map.put("state", model.getState());
		map.put("id", model.getId());
		map.put("buildingNo", model.getBuildingNo());
		map.put("year", model.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = waterBillDao.findWaterBillPager(map,
				getPager(request));
		// List<String> houseSns = BoUtils.getProperties(paginater.getList(),
		// "houseSn");
		// List<String> accounts = accountDao.findAcctByHouseSn(houseSns);
		// BoUtils.addProperty(paginater.getList(), "houseSn", "balance",
		// accounts, "houseSn", "balance");
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = waterBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		Map<String, String> buildingNos = new LinkedHashMap<String, String>();
		Map<String, String> rent = ParaManager.getSysDict(SysDictType.RentType
				.getValue());
		Map<String, String> economical = ParaManager
				.getSysDict(SysDictType.EconomicalType.getValue());
		buildingNos.putAll(economical);
		buildingNos.putAll(rent);
		request.setAttribute("buildingNos", buildingNos);
		// return forward("/pages/manage/charge/water/waterBillList.jsp");
		return "list";
	}

	@Override
	public WaterBill getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	private WaterBill model = new WaterBill();
}
