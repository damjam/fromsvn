package com.ylink.cim.manage.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
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
import flink.web.BaseAction;

/**
 * 计费参数管理
 * 
 * @author libaozhu
 * @date 2015-4-15
 */
@Scope("prototype")
@Component
public class ChargeParamAction extends BaseAction implements ModelDriven<ChargeParam> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ChargeParamDao chargeParamDao;
	@Autowired
	private ChargeParamService chargeParamService;
	@Autowired
	private ChargeParamItemDao chargeParamItemDao;
	@Autowired
	private ChargeItemDao chargeItemDao;

	public String toChargeParamMng() throws Exception {

		// return forward("/pages/admin/chargeParam/chargeParamManager.jsp");
		return "manager";
	}

	public String toEdit() throws Exception {

		String id = model.getId();
		ChargeParam chargeParam = chargeParamDao.findById(id);
		BeanUtils.copyProperties(model, chargeParam);
		initSelect(request);
		// return forward("/pages/admin/chargeParam/chargeParamEdit.jsp");
		return "edit";
	}

	public String toAdd() throws Exception {
		initSelect(request);
		// return forward("/pages/admin/chargeParam/chargeParamEdit.jsp");
		return "edit";
	}

	private void initSelect(HttpServletRequest request) {
		ChargeWay.setInReq(request);
		ChargeType.setInReq(request);
		ParamRange.setInReq(request);
	}

	public String doEdit() throws Exception {
		try {

			// Map<String, Object> params = getParaMap();
			// params.put("branchNo", getSessionBranchNo(request));
			// Assert.isEmpty(chargeParamDao.findBy(params), "计费参数已存在，请重新指定");
			ChargeParam chargeParam = null;
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			params.put("chargeObj", model.getChargeObj());
			if (StringUtils.isEmpty(model.getId())) {
				List<ChargeParam> list = chargeParamDao.findBy(params);
				Assert.isEmpty(list, "计费对象已存在，请重新指定");
				chargeParam = new ChargeParam();
				BeanUtils.copyProperties(chargeParam, model);
				chargeParamService.saveParam(chargeParam, getSessionUser(request));
			} else {
				params.put("exceptId", model.getId());
				List<ChargeParam> list = chargeParamDao.findBy(params);
				Assert.isEmpty(list, "计费对象已存在，请重新指定");
				chargeParam = chargeParamDao.findById(model.getId());
				String branchNo = chargeParam.getBranchNo();
				BeanUtils.copyProperties(chargeParam, model);
				chargeParam.setBranchNo(branchNo);
				chargeParamService.updateParam(chargeParam, getSessionUser(request));
			}
			setResult(true, "操作成功", request);
			// clearForm(model);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return list();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return list();
		}

		return list();
	}

	public String toAddItem() throws Exception {
		Map<String, Object> map = getParaMap();

		String id = model.getId();
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
		model.setItemIds(itemIds);
		// return forward("/pages/admin/chargeParam/chargeParamItemAdd.jsp");
		return "item_add";
	}

	public String doAddItem() throws Exception {
		String id = model.getId();
		String[] itemIds = model.getItemIds();
		try {
			chargeParamService.saveParamItem(id, itemIds, getSessionUser(request));
			setResult(true, "操作成功", request);
			// return list(mapping, model, request, response);
			return "list";
		} catch (Exception e) {
			setResult(false, e.getMessage(), request);
			return toAddItem();
		}
	}

	public String list() throws Exception {

		Map<String, Object> map = getParaMap();
		map.put("branchNo", getSessionBranchNo(request));
		map.put("rangeCode", model.getRangeCode());
		Paginater paginater = chargeParamDao.findPager(map, getPager(request));
		for (int i = 0; i < paginater.getList().size(); i++) {
			ChargeParam chargeParam = (ChargeParam) paginater.getList().get(i);
			map.put("paramId", chargeParam.getId());
			List<ChargeParamItem> paramItems = chargeParamItemDao.findBy(map);
			StringBuilder builder = new StringBuilder();
			for (int j = 0; j < paramItems.size(); j++) {
				ChargeParamItem paramItem = paramItems.get(j);
				String itemId = paramItem.getId().getItemId();
				ChargeItem item = chargeItemDao.findById(itemId);
				builder.append(item.getItemName());
				if (j != paramItems.size() - 1) {
					builder.append(",");
				}
			}
			chargeParam.setChargeDesc(builder.toString());
		}
		saveQueryResult(request, paginater);
		initSelect(request);
		// return forward("/pages/admin/chargeParam/chargeParamList.jsp");
		return "list";
	}

	public String delete() throws Exception {
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
		return list();
	}

	@Override
	public ChargeParam getModel() {
		return model;
	}

	private ChargeParam model = new ChargeParam();

}
