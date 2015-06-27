package com.ylink.cim.manage.view;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.ElecBillDao;
import com.ylink.cim.manage.domain.ElecBill;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class ElecBillAction extends BaseAction implements ModelDriven<ElecBill> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ElecBillDao elecBillDao;
	@Autowired
	private BillService billService;

	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeElecFee(id, getSessionUser(request));
			setResult(true, "操作成功", request);
			String log = MsgUtils.r("用户{?}收水费，单号{?}", getSessionUser(request)
					.getUserName(), id);
			logSuccess(request, UserLogType.OTHER.getValue(),
					StringUtils.abbreviate(log, 100));
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
		map.put("startChargeDate", model.getStartChargeDate());
		map.put("endChargeDate", model.getEndChargeDate());
		map.put("houseSn", model.getHouseSn());
		map.put("state", model.getState());
		map.put("id", model.getId());
		map.put("buildingNo", model.getBuildingNo());
		map.put("year", model.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = elecBillDao.findBillPager(map, getPager(request));
		// List<String> houseSns = BoUtils.getProperties(paginater.getList(),
		// "houseSn");
		// List<String> accounts = accountDao.findAcctByHouseSn(houseSns);
		// BoUtils.addProperty(paginater.getList(), "houseSn", "balance",
		// accounts, "houseSn", "balance");
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = elecBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		Map<String, String> buildingNos = new LinkedHashMap<String, String>();
		Map<String, String> rent = ParaManager.getSysDict(SysDictType.RentType
				.getValue());
		Map<String, String> economical = ParaManager
				.getSysDict(SysDictType.EconomicalType.getValue());
		buildingNos.putAll(economical);
		buildingNos.putAll(rent);
		request.setAttribute("buildingNos", buildingNos);
		// return forward("/pages/manage/charge/elec/elecBillList.jsp");
		return "list";
	}

	@Override
	public ElecBill getModel() {
		return model;
	}

	private ElecBill model = new ElecBill();

}
