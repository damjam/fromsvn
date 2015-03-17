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
import com.ylink.cim.manage.dao.MerchantInfoDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.MerchantInfo;
import com.ylink.cim.manage.service.MerchantInfoService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class MerchantInfoAction extends BaseDispatchAction {
	private MerchantInfoDao merchantInfoDao = (MerchantInfoDao)getService("merchantInfoDao");
	private MerchantInfoService merchantInfoService = (MerchantInfoService)getService("merchantInfoService");
	public ActionForward toEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return forward("/pages/manage/merchant/merchantInfo.jsp");
	}
	public ActionForward doEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			MerchantInfoActionForm actionForm = (MerchantInfoActionForm)form;
			MerchantInfo merchantInfo = new MerchantInfo();
			BeanUtils.copyProperties(merchantInfo, actionForm);
			merchantInfoService.saveMerchant(merchantInfo, getSessionUser(request));
			setResult(true, "添加成功", request);
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
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = merchantInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return forward("/pages/manage/merchant/merchantList.jsp");
	}
	
	
}
