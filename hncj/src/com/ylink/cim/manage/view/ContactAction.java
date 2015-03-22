package com.ylink.cim.manage.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.accessibility.internal.resources.accessibility;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.SexType;
import com.ylink.cim.manage.dao.ContactDao;
import com.ylink.cim.manage.dao.MerchantInfoDao;
import com.ylink.cim.manage.domain.Contact;
import com.ylink.cim.manage.domain.MerchantInfo;
import com.ylink.cim.manage.service.ContactService;

import flink.etc.BizException;
import flink.util.BoUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class ContactAction extends BaseDispatchAction {
	private ContactDao contactDao = (ContactDao)getService("contactDao");
	private ContactService contactService = (ContactService)getService("contactService");
	private MerchantInfoDao merchantInfoDao = (MerchantInfoDao)getService("merchantInfoDao");
	public ActionForward toEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SexType.setInReq(request);
		ContactActionForm actionForm = (ContactActionForm)form;
		String id = actionForm.getId();
		if (!StringUtils.isEmpty(id)) {
			Contact contact = (Contact)contactDao.findById(id);
			if (StringUtils.isNotEmpty(contact.getMerchantNo())) {
				MerchantInfo merchantInfo = merchantInfoDao.findById(contact.getMerchantNo());
				contact.setMerchantName(merchantInfo.getMrname());
			}
			BeanUtils.copyProperties(actionForm, contact);
		}
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
			actionForm.setContactName("");
			actionForm.setMobile("");
			actionForm.setKeyword("");
			actionForm.setContactName("");
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
		map.put("contactName", actionForm.getContactName());
		map.put("branchNo", getSessionBranchNo(request));
		map.put("mobile", actionForm.getMobile());
		map.put("remark", actionForm.getRemark());
		Paginater paginater = contactDao.findPager(map, getPager(request));
		Map<String, Object> params = getParaMap();
		params.put("branchNo", getSessionBranchNo(request));
		List<MerchantInfo> list = merchantInfoDao.findList(params);
		BoUtils.addProperty(paginater.getList(), "merchantNo", "merchantName", list, "id", "mrname");
		saveQueryResult(request, paginater);
		return forward("/pages/manage/contact/contactList.jsp");
	}
	public ActionForward del(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ContactActionForm actionForm = (ContactActionForm) form;
		String id = actionForm.getId();
		try {
			contactService.delete(id);
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, "操作失败"+e.getMessage(), request);
		}
		return list(mapping, form, request, response);
	}
}
