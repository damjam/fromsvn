package com.ylink.cim.manage.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.type.ChargeType;
import com.ylink.cim.common.type.ChargeWay;
import com.ylink.cim.manage.dao.ChargeItemDao;
import com.ylink.cim.manage.domain.ChargeItem;
import com.ylink.cim.manage.service.ChargeParamService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;
/**
 * 计费参数管理
 * @author libaozhu
 * @date 2015-4-16
 */
public class ChargeItemAction extends BaseDispatchAction {
	private ChargeItemDao chargeItemDao = (ChargeItemDao) getService("chargeItemDao");
	private ChargeParamService chargeParamService = (ChargeParamService) getService("chargeParamService");

	public ActionForward toEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initSelect(request);
		ChargeItemActionForm actionForm = (ChargeItemActionForm) form;
		if (!StringUtils.isEmpty(actionForm.getId())) {
			ChargeItem chargeItem = chargeItemDao.findById(actionForm.getId());
			BeanUtils.copyProperties(actionForm, chargeItem);
		}
		return forward("/pages/admin/chargeItem/chargeItemEdit.jsp");
	}
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initSelect(request);
		return forward("/pages/admin/chargeItem/chargeItemEdit.jsp");
	}

	
	public ActionForward doEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ChargeItemActionForm actionForm = (ChargeItemActionForm) form;
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			params.put("itemName", actionForm.getItemName());
			if (StringUtils.isEmpty(actionForm.getId())) {
				List<ChargeItem> list = chargeItemDao.findBy(params);
				Assert.isEmpty(list, "计费项名称已存在，请重新指定");
				ChargeItem chargeItem = new ChargeItem();
				BeanUtils.copyProperties(chargeItem, actionForm);
				chargeParamService.saveOrUpdateItem(chargeItem, getSessionUser(request));
			}else {
				params.put("exceptId", actionForm.getId());
				List<ChargeItem> list = chargeItemDao.findBy(params);
				Assert.isEmpty(list, "计费项名称已存在，请重新指定");
				ChargeItem chargeItem = chargeItemDao.findById(actionForm.getId());
				String branchNo = chargeItem.getBranchNo();
				Date createDate = chargeItem.getCreateDate();
				BeanUtils.copyProperties(chargeItem, actionForm);
				chargeItem.setBranchNo(branchNo);
				chargeItem.setCreateDate(createDate);
				chargeParamService.saveOrUpdateItem(chargeItem, getSessionUser(request));
			}
			setResult(true, "操作成功", request);
			clearForm(actionForm);
		}catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toEdit(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toEdit(mapping, form, request, response);
		}
		
		return list(mapping, form, request, response);
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ChargeItemActionForm actionForm = (ChargeItemActionForm) form;
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			ChargeItem chargeItem = new ChargeItem();
			BeanUtils.copyProperties(chargeItem, actionForm);
			chargeParamService.saveOrUpdateItem(chargeItem, getSessionUser(request));
			setResult(true, "操作成功", request);
			clearForm(actionForm);
		}catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd(mapping, form, request, response);
		}
		
		return list(mapping, form, request, response);
	}
	private void clearForm(ChargeItemActionForm actionForm) {
		
	}


	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = chargeItemDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect(request);
		return forward("/pages/admin/chargeItem/chargeItemList.jsp");
	}

	private void initSelect(HttpServletRequest request) {
		ChargeType.setInReq(request);
		ChargeWay.setInReq(request);
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			chargeParamService.deleteItem(id, getSessionUser(request));
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

	
	public ActionForward queryPopUpItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			params.put("avai", true);
			Paginater paginater = this.chargeItemDao.findPager(params, getPager(request));
			saveQueryResult(request, paginater);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward("/pages/popUp/popUpChargeItem.jsp");
	}
	
}
