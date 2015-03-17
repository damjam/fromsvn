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
import com.ylink.cim.manage.dao.ContactDao;
import com.ylink.cim.manage.domain.Contact;
import com.ylink.cim.manage.service.ContactService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class ContactAction extends BaseDispatchAction {
	private ContactDao contactDao = (ContactDao)getService("contactDao");
	private ContactService contactService = (ContactService)getService("contactService");
	public ActionForward toEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		YesNoType.setInReq(request);
		ContactActionForm actionForm = (ContactActionForm)form;
		String id = request.getParameter("id");
		Contact contact = (Contact)contactDao.findById(id);
		BeanUtils.copyProperties(actionForm, contact);
		return forward("/pages/manage/contact/contact.jsp");
	}
	public ActionForward doEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ContactActionForm actionForm = (ContactActionForm)form;
			Contact contact = new Contact();
			BeanUtils.copyProperties(contact, actionForm);
			contactService.save(contact, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toEdit(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败"+e.getMessage(), request);
			return toEdit(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		ContactActionForm actionForm = (ContactActionForm)form;
		map.put("branchNo", getSessionBranchNo(request));
		map.put("mobile", actionForm.getMobile());
		Paginater paginater = contactDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return forward("/pages/manage/contact/contactList.jsp");
	}
	
	
}
