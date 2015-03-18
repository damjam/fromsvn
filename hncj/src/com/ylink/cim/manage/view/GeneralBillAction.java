package com.ylink.cim.manage.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.state.CheckinState;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.manage.dao.GeneralBillDao;
import com.ylink.cim.manage.domain.GeneralBill;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;
/**
 * 通用收款
 * @author libaozhu
 * @date 2015-3-17
 */
public class GeneralBillAction extends BaseDispatchAction {
	private GeneralBillDao generalBillDao = (GeneralBillDao)getService("generalBillDao");
	private BillService billService = (BillService)getService("billService");
	
	public ActionForward charge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeGeneralBill(id, getSessionUser(request));
			setResult(true, "操作成功", request);
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
		GeneralBillActionForm actionForm = (GeneralBillActionForm)form;
		map.put("startChargeDate", actionForm.getStartChargeDate());
		map.put("endChargeDate", actionForm.getEndChargeDate());
		map.put("state", actionForm.getState());
		map.put("id", actionForm.getId());
		map.put("branchNo", getSessionBranchNo(request));
		map.put("keyword", actionForm.getKeyword());
		Paginater paginater = generalBillDao.findBillPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = generalBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		return forward("/pages/manage/charge/general/generalBillList.jsp");
	}
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		YesNoType.setInReq(request);
		CheckinState.setInReq(request);
		return forward("/pages/manage/charge/general/generalBillAdd.jsp");
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			GeneralBillActionForm actionForm = (GeneralBillActionForm)form;
			GeneralBill bill = new GeneralBill();
			BeanUtils.copyProperties(bill, actionForm);
			billService.saveGeneralBill(bill, getSessionUser(request));
			setResult(true, "数据已保存", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败"+e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward deleteBill(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(GeneralBill.class, id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list(mapping, form, request, response);
	}
}
