package com.ylink.cim.manage.view;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.ElecBillDao;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class ElecBillAction extends BaseDispatchAction {
	private ElecBillDao elecBillDao = (ElecBillDao)getService("elecBillDao");
	private BillService billService = (BillService)getService("billService");
	
	public ActionForward charge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeElecFee(id, getSessionUser(request));
			setResult(true, "操作成功", request);
			String log = LogUtils.r("用户{?}收水费，单号{?}", getSessionUser(request).getUserName(), id);
			logSuccess(request, UserLogType.OTHER.getValue(), StringUtils.abbreviate(log, 100));
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		ElecBillActionForm actionForm = (ElecBillActionForm)form;
		map.put("startCreateDate", actionForm.getStartCreateDate());
		map.put("endCreateDate", actionForm.getEndCreateDate());
		map.put("startChargeDate", actionForm.getStartChargeDate());
		map.put("endChargeDate", actionForm.getEndChargeDate());
		map.put("houseSn", actionForm.getHouseSn());
		map.put("state", actionForm.getState());
		map.put("id", actionForm.getId());
		map.put("buildingNo", actionForm.getBuildingNo());
		map.put("year", actionForm.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = elecBillDao.findBillPager(map, getPager(request));
		//List<String> houseSns = BoUtils.getProperties(paginater.getList(), "houseSn");
		//List<String> accounts = accountDao.findAcctByHouseSn(houseSns);
		//BoUtils.addProperty(paginater.getList(), "houseSn", "balance", accounts, "houseSn", "balance");
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = elecBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		Map<String, String> buildingNos = new LinkedHashMap<String, String>();
		Map<String, String> rent = ParaManager.getSysDict(SysDictType.RentType.getValue());
		Map<String, String> economical = ParaManager.getSysDict(SysDictType.EconomicalType.getValue());
		buildingNos.putAll(economical);
		buildingNos.putAll(rent);
		request.setAttribute("buildingNos", buildingNos);
		return forward("/pages/manage/charge/elec/elecBillList.jsp");
	}
}
