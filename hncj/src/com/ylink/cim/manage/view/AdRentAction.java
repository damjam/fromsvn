package com.ylink.cim.manage.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.manage.dao.AdRentBillDao;
import com.ylink.cim.manage.domain.AdRent;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class AdRentAction extends BaseDispatchAction {
	private AdRentBillDao adRentBillDao = (AdRentBillDao)getService("adRentBillDao");
	private BillService billService = (BillService)getService("billService");
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return forward("/pages/manage/charge/adRent/adRentAdd.jsp");
	}
	
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			AdRentActionForm actionForm = (AdRentActionForm)form;
			AdRent hadRent = new AdRent();
			BeanUtils.copyProperties(hadRent, actionForm);
			billService.saveAdRentBill(hadRent, getSessionUser(request));
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
		Paginater paginater = adRentBillDao.findBillPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return forward("/pages/manage/charge/adRent/adRentList.jsp");
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(AdRent.class, id, getSessionUser(request));
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
	
}
