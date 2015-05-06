package com.ylink.cim.manage.view;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.manage.dao.MerchantInfoDao;
import com.ylink.cim.manage.domain.MerchantInfo;
import com.ylink.cim.manage.service.MerchantInfoService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;
@Scope("prototype")
@Component
public class MerchantInfoAction extends BaseAction implements ModelDriven<MerchantInfo>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MerchantInfoDao merchantInfoDao;
	@Autowired
	private MerchantInfoService merchantInfoService;
	private MerchantInfo model = new MerchantInfo();
	public String del() throws Exception {
		
		String id = model.getId();
		try {
			merchantInfoService.delete(id);
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, "操作失败"+e.getMessage(), request);
		}
		return list();
	}
	public String doEdit() throws Exception {
		try {
			MerchantInfo merchantInfo = null;
			Map<String, Object> params = getParaMap();
			params.put("mrname", model.getMrname());
			if (StringUtils.isEmpty(model.getId())) {
				merchantInfo = new MerchantInfo();
			}else {
				params.put("id", model.getId());
				merchantInfo = merchantInfoDao.findById(model.getId());
			}
			if (merchantInfoDao.findList(params).size() >= 1) {
				throw new BizException("商户名已存在，请重新指定");
			}
			BeanUtils.copyProperties(merchantInfo, model);
			merchantInfoService.saveOrUpdate(merchantInfo, getSessionUser(request));
			model.setMrname("");
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败"+e.getMessage(), request);
			return toEdit();
		}
		return list();
	}
	
	public MerchantInfo getModel() {
		return model;
	}
	public String list(
			) throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = merchantInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		//return forward("/pages/manage/merchant/merchantList.jsp");
		return "list";
	}
	public String queryPopUpMerchantInfo() throws Exception {

		Map<String, Object> params = getParaMap();
		params.put("branchNo", getSessionBranchNo(request));
		params.put("mrname", request.getParameter("mrname"));
		Paginater paginater = this.merchantInfoDao.findPager(params, getPager(request));
		saveQueryResult(request, paginater);
		//return forward("/pages/popUp/popUpMerchantInfo.jsp");
		return "popUp";
	}
	public String toEdit() throws Exception {
		
		String id = model.getId();
		if (!StringUtils.isEmpty(id)) {
			MerchantInfo merchantInfo = merchantInfoDao.findById(id);
			BeanUtils.copyProperties(model, merchantInfo);
		}
		//return forward("/pages/manage/merchant/merchantInfo.jsp");
		return "merchantInfo";
	}
}
