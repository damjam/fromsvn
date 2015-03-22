package com.ylink.cim.manage.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.manage.dao.AdrentBillDao;
import com.ylink.cim.manage.domain.AdrentBill;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class AdrentBillAction extends BaseDispatchAction {
	private AdrentBillDao adrentBillDao = (AdrentBillDao)getService("adrentBillDao");
	private BillService billService = (BillService)getService("billService");
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return forward("/pages/manage/charge/adrent/adrentBillAdd.jsp");
	}
	public ActionForward charge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeAdRent(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			AdrentBillActionForm actionForm = (AdrentBillActionForm)form;
			AdrentBill adRent = new AdrentBill();
			BeanUtils.copyProperties(adRent, actionForm);
			billService.saveAdRentBill(adRent, getSessionUser(request));
			setResult(true, "添加成功", request);
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
		map.put("branchNo", getSessionBranchNo(request));
		AdrentBillActionForm actionForm = (AdrentBillActionForm)form;
		map.put("id", actionForm.getId());
		map.put("state", actionForm.getState());
		map.put("startChargeDate", actionForm.getStartChargeDate());
		map.put("endChargeDate", actionForm.getEndChargeDate());
		Paginater paginater = adrentBillDao.findBillPager(map, getPager(request));
		saveQueryResult(request, paginater);
		BillState.setInReq(request);
		Map<String, Object> sumInfo = adrentBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		return forward("/pages/manage/charge/adrent/adrentBillList.jsp");
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(AdrentBill.class, id, getSessionUser(request));
			setResult(true, "操作成功", request);
			AdrentBillActionForm actionForm = (AdrentBillActionForm)form;
			actionForm.setId("");
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
	    } catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list(mapping, form, request, response);
	}
	
}
