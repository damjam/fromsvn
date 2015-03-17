package com.ylink.cim.manage.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.manage.dao.DepositBillDao;
import com.ylink.cim.manage.domain.DepositBill;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;
/**
 * 收押金
 * @author libaozhu
 * @date 2015-3-16
 */
public class DepositBillAction extends BaseDispatchAction {
	private DepositBillDao depositBillDao = (DepositBillDao)getService("depositBillDao");
	private BillService billService = (BillService)getService("billService");
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		YesNoType.setInReq(request);
		return forward("/pages/manage/charge/deposit/depositBillAdd.jsp");
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			DepositBillActionForm actionForm = (DepositBillActionForm)form;
			DepositBill depositBill = new DepositBill();
			BeanUtils.copyProperties(depositBill, actionForm);
			billService.saveDepositBill(depositBill, getSessionUser(request));
			setResult(true, "数据已保存", request);
			actionForm.setHouseSn("");
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
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		DepositBillActionForm actionForm = (DepositBillActionForm)form;
		map.put("houseSn", actionForm.getHouseSn());
		map.put("state", actionForm.getState());
		map.put("startDepositDate", actionForm.getStartDepositDate());
		map.put("endDepositDate", actionForm.getEndDepositDate());
		map.put("startRefundDate", actionForm.getStartRefundDate());
		map.put("endRefundDate", actionForm.getEndRefundDate());
		map.put("id", actionForm.getId());
		map.put("year", actionForm.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = depositBillDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = depositBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		return forward("/pages/manage/charge/deposit/depositBillList.jsp");
	}
	public ActionForward charge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeDepositFee(id, getSessionUser(request));
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
	public ActionForward deleteDepositBill(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(DepositBill.class, id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list(mapping, form, request, response);
	}
	
	public ActionForward refund(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.refund(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			e.printStackTrace();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
		}
		return list(mapping, form, request, response);
	}
}
