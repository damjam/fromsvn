package com.ylink.cim.manage.view;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
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
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class ContactAction extends BaseAction implements ModelDriven<Contact> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private ContactService contactService;
	@Autowired
	private MerchantInfoDao merchantInfoDao;

	public String toEdit() throws Exception {
		SexType.setInReq(request);

		String id = model.getId();
		if (!StringUtils.isEmpty(id)) {
			Contact contact = (Contact) contactDao.findById(id);
			if (StringUtils.isNotEmpty(contact.getMerchantNo())) {
				MerchantInfo merchantInfo = merchantInfoDao.findById(contact.getMerchantNo());
				contact.setMerchantName(merchantInfo.getMrname());
			}
			BeanUtils.copyProperties(model, contact);
		}
		// return forward("/pages/manage/contact/contact.jsp");
		return "edit";
	}

	public String doEdit() throws Exception {
		try {
			Contact contact = new Contact();
			BeanUtils.copyProperties(contact, model);
			contactService.save(contact, getSessionUser(request));
			setResult(true, "操作成功", request);
			model.setContactName("");
			model.setMobile("");
			model.setKeyword("");
			model.setContactName("");
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败" + e.getMessage(), request);
			return toEdit();
		}
		return list();
	}

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();

		map.put("contactName", model.getContactName());
		map.put("branchNo", getSessionBranchNo(request));
		map.put("mobile", model.getMobile());
		map.put("remark", model.getRemark());
		Paginater paginater = contactDao.findPager(map, getPager(request));
		Map<String, Object> params = getParaMap();
		params.put("branchNo", getSessionBranchNo(request));
		List<MerchantInfo> list = merchantInfoDao.findList(params);
		BoUtils.addProperty(paginater.getList(), "merchantNo", "merchantName", list, "id", "mrname");
		saveQueryResult(request, paginater);
		// return forward("/pages/manage/contact/contactList.jsp");
		return "list";
	}

	public String del() throws Exception {

		String id = model.getId();
		try {
			contactService.delete(id);
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, "操作失败" + e.getMessage(), request);
		}
		return list();
	}

	public Contact getModel() {
		return model;
	}

	private Contact model = new Contact();

}
