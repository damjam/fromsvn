package com.ylink.cim.manage.view;

import java.util.Date;
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
import com.ylink.cim.manage.dao.ChargeItemDao;
import com.ylink.cim.manage.domain.ChargeItem;
import com.ylink.cim.manage.service.ChargeParamService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;

/**
 * 计费参数管理
 * 
 * @author libaozhu
 * @date 2015-4-16
 */
@Scope("prototype")
@Component
public class ChargeItemAction extends BaseAction implements
		ModelDriven<ChargeItem> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ChargeItemDao chargeItemDao;
	@Autowired
	private ChargeParamService chargeParamService;

	public String toEdit() throws Exception {
		initSelect(request);
		if (!StringUtils.isEmpty(model.getId())) {
			ChargeItem chargeItem = chargeItemDao.findById(model.getId());
			BeanUtils.copyProperties(model, chargeItem);
		}
		return "edit";
	}

	public String toAdd() throws Exception {
		initSelect(request);
		return "edit";
	}

	public String doEdit() throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			params.put("itemName", model.getItemName());
			if (StringUtils.isEmpty(model.getId())) {
				List<ChargeItem> list = chargeItemDao.findBy(params);
				Assert.isEmpty(list, "计费项名称已存在，请重新指定");
				ChargeItem chargeItem = new ChargeItem();
				BeanUtils.copyProperties(chargeItem, model);
				chargeParamService.saveOrUpdateItem(chargeItem,
						getSessionUser(request));
			} else {
				params.put("exceptId", model.getId());
				List<ChargeItem> list = chargeItemDao.findBy(params);
				Assert.isEmpty(list, "计费项名称已存在，请重新指定");
				ChargeItem chargeItem = chargeItemDao.findById(model.getId());
				String branchNo = chargeItem.getBranchNo();
				Date createDate = chargeItem.getCreateDate();
				BeanUtils.copyProperties(chargeItem, model);
				chargeItem.setBranchNo(branchNo);
				chargeItem.setCreateDate(createDate);
				chargeParamService.saveOrUpdateItem(chargeItem,
						getSessionUser(request));
			}
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toEdit();
		}

		return list();
	}

	public String doAdd() throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			ChargeItem chargeItem = new ChargeItem();
			BeanUtils.copyProperties(chargeItem, model);
			chargeParamService.saveOrUpdateItem(chargeItem,
					getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd();
		}

		return list();
	}

	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = chargeItemDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect(request);
		return "list";
	}

	private void initSelect(HttpServletRequest request) {
		ChargeType.setInReq(request);
		ChargeWay.setInReq(request);
	}

	public String delete() throws Exception {
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
		return list();
	}

	public String queryPopUpItem() throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			params.put("avai", true);
			Paginater paginater = this.chargeItemDao.findPager(params,
					getPager(request));
			saveQueryResult(request, paginater);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "pop";
	}

	@Override
	public ChargeItem getModel() {
		return model;
	}

	private ChargeItem model = new ChargeItem();
}
