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

import com.ylink.cim.common.type.ChargeType;
import com.ylink.cim.common.type.ChargeWay;
import com.ylink.cim.common.type.ParamRange;
import com.ylink.cim.manage.dao.ChargeItemDao;
import com.ylink.cim.manage.dao.ChargeParamDao;
import com.ylink.cim.manage.dao.ChargeParamItemDao;
import com.ylink.cim.manage.domain.ChargeItem;
import com.ylink.cim.manage.domain.ChargeParam;
import com.ylink.cim.manage.domain.ChargeParamItem;
import com.ylink.cim.manage.service.ChargeParamService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;
/**
 * 计费参数管理
 * @author libaozhu
 * @date 2015-4-15
 */
public class ChargeParamAction extends BaseDispatchAction {
	private ChargeParamDao chargeParamDao = (ChargeParamDao) getService("chargeParamDao");
	private ChargeParamService chargeParamService = (ChargeParamService) getService("chargeParamService");
	private ChargeParamItemDao chargeParamItemDao = (ChargeParamItemDao)getService("chargeParamItemDao");
	private ChargeItemDao chargeItemDao = (ChargeItemDao)getService("chargeItemDao");
	public ActionForward toChargeParamMng(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return forward("/pages/admin/chargeParam/chargeParamManager.jsp");
	}
	
	public ActionForward toEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ChargeParamActionForm actionForm = (ChargeParamActionForm)form;
		String id = actionForm.getId();
		ChargeParam chargeParam = chargeParamDao.findById(id);
		BeanUtils.copyProperties(actionForm, chargeParam);
		initSelect(request);
		return forward("/pages/admin/chargeParam/chargeParamEdit.jsp");
	}
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initSelect(request);
		return forward("/pages/admin/chargeParam/chargeParamEdit.jsp");
	}
	
	private void initSelect(HttpServletRequest request) {
		ChargeWay.setInReq(request);
		ChargeType.setInReq(request);
		ParamRange.setInReq(request);
	}

	public ActionForward doEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ChargeParamActionForm actionForm = (ChargeParamActionForm) form;
			//Map<String, Object> params = getParaMap();
			//params.put("branchNo", getSessionBranchNo(request));
			//Assert.isEmpty(chargeParamDao.findBy(params), "计费参数已存在，请重新指定");
			ChargeParam chargeParam = null;
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			params.put("chargeObj", actionForm.getChargeObj());
			if (StringUtils.isEmpty(actionForm.getId())) {
				List<ChargeParam> list = chargeParamDao.findBy(params);
				Assert.isEmpty(list, "计费对象已存在，请重新指定");
				chargeParam = new ChargeParam();
				BeanUtils.copyProperties(chargeParam, actionForm);
				chargeParamService.saveParam(chargeParam, getSessionUser(request));
			}else {
				params.put("exceptId", actionForm.getId());
				List<ChargeParam> list = chargeParamDao.findBy(params);
				Assert.isEmpty(list, "计费对象已存在，请重新指定");
				chargeParam = chargeParamDao.findById(actionForm.getId());
				String branchNo = chargeParam.getBranchNo();
				BeanUtils.copyProperties(chargeParam, actionForm);
				chargeParam.setBranchNo(branchNo);
				chargeParamService.updateParam(chargeParam, getSessionUser(request));
			}
			setResult(true, "操作成功", request);
			clearForm(actionForm);
		}catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return list(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return list(mapping, form, request, response);
		}
		
		return list(mapping, form, request, response);
	}
	
	public ActionForward toAddItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = getParaMap();
		ChargeParamActionForm actionForm = (ChargeParamActionForm) form;
		String id = actionForm.getId();
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = chargeItemDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		map.put("paramId", id);
		List<ChargeParamItem> list = chargeParamItemDao.findBy(map);
		String[] itemIds = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ChargeParamItem cpi = list.get(i);
			itemIds[i] = cpi.getId().getItemId();
		}
		actionForm.setItemIds(itemIds);
		return forward("/pages/admin/chargeParam/chargeParamItemAdd.jsp");
	}
	public ActionForward doAddItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ChargeParamActionForm actionForm = (ChargeParamActionForm) form;
		String id = actionForm.getId();
		String[] itemIds = actionForm.getItemIds();
		try {
			chargeParamService.saveParamItem(id, itemIds, getSessionUser(request));
			setResult(true, "操作成功", request);
			return list(mapping, actionForm, request, response);
		} catch (Exception e) {
			setResult(false, e.getMessage(), request);
			return toAddItem(mapping, form, request, response);
		}
	}
	
	private void clearForm(ChargeParamActionForm actionForm) {
		actionForm.setRangeCode("");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ChargeParamActionForm actionForm = (ChargeParamActionForm)form;
		Map<String, Object> map = getParaMap();
		map.put("branchNo", getSessionBranchNo(request));
		map.put("rangeCode", actionForm.getRangeCode());
		Paginater paginater = chargeParamDao.findPager(map, getPager(request));
		for (int i = 0; i < paginater.getList().size(); i++) {
			ChargeParam chargeParam = (ChargeParam)paginater.getList().get(i);
			map.put("paramId", chargeParam.getId());
			List<ChargeParamItem> paramItems = chargeParamItemDao.findBy(map);
			StringBuilder builder = new StringBuilder();
			for (int j = 0; j < paramItems.size(); j++) {
				ChargeParamItem paramItem = paramItems.get(j);
				String itemId = paramItem.getId().getItemId();
				ChargeItem item = chargeItemDao.findById(itemId);
				builder.append(item.getItemName());
				if (j != paramItems.size()-1) {
					builder.append(",");
				}
			}
			chargeParam.setChargeDesc(builder.toString());
		}
		saveQueryResult(request, paginater);
		initSelect(request);
		return forward("/pages/admin/chargeParam/chargeParamList.jsp");
	}

	
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			chargeParamService.deleteParam(id, getSessionUser(request));
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
