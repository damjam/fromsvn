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
import com.ylink.cim.manage.dao.MerchantInfoDao;
import com.ylink.cim.manage.domain.MerchantInfo;
import com.ylink.cim.manage.service.MerchantInfoService;
import com.ylink.cim.util.CopyPropertyUtil;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
@Component
public class MerchantInfoAction extends BaseAction implements
		ModelDriven<MerchantInfo> {
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
			setResult(false, "操作失败" + e.getMessage(), request);
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
			} else {
				params.put("id", model.getId());
				merchantInfo = merchantInfoDao.findById(model.getId());
			}
			if (merchantInfoDao.findList(params).size() >= 1) {
				throw new BizException("商户名已存在，请重新指定");
			}
			CopyPropertyUtil.copyPropertiesIgnoreNull(model, merchantInfo);
			merchantInfoService.saveOrUpdate(merchantInfo,
					getSessionUser(request));
			setSucResult("操作成功", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败" + e.getMessage(), request);
			return toEdit();
		}
		return "toMain";
	}

	@Override
	public MerchantInfo getModel() {
		return model;
	}

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		map.put("mrname", model.getMrname());
		map.put("mobile", model.getMobile());
		Paginater paginater = merchantInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		// return forward("/pages/manage/merchant/merchantList.jsp");
		return "list";
	}

	public String queryPopUpMerchantInfo() throws Exception {

		Map<String, Object> params = getParaMap();
		//params.put("branchNo", getSessionBranchNo(request));
		params.put("mrname", request.getParameter("mrname"));
		params.put("mobile", request.getParameter("mobile"));
		Paginater paginater = this.merchantInfoDao.findPager(params,
				getPager(request,10));
		saveQueryResult(request, paginater);
		request.setAttribute("bindCode", request.getParameter("bindCode"));
		request.setAttribute("bindName", request.getParameter("bindName"));
		return "popUp";
	}

	public String toEdit() throws Exception {

		String id = model.getId();
		if (!StringUtils.isEmpty(id)) {
			MerchantInfo merchantInfo = merchantInfoDao.findById(id);
			BeanUtils.copyProperties(model, merchantInfo);
		}
		return "edit";
	}
	
	public String getMerchantInfo() throws Exception {
		JSONObject object = new JSONObject();
		try{
			Map<String, Object> map = getParaMap();
			Assert.notEmpty(model.getMrname(), "不能为空");
			map.put("mrname", model.getMrname());
			List<MerchantInfo> list = merchantInfoDao.findList(map);
			if (list.size() > 0) {
				MerchantInfo merchantInfo = list.get(0);
				object.put("status", "1");
				object.put("mobile", merchantInfo.getMobile());
				object.put("manager", merchantInfo.getManager());
				object.put("addr", merchantInfo.getAddr());
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("status", "0");
		}
		respond(response, object.toString());
		return null;
	}
	public String loadByKeyword() throws Exception {
		JSONObject object = new JSONObject();
		try{
			String keyword = request.getParameter("keyword");
			Assert.notEmpty(keyword, "不能为空");
			List<MerchantInfo> list = merchantInfoDao.findByKeyword(keyword);
			JSONArray array = new JSONArray();
			for(int i=0; i<list.size(); i++){
				MerchantInfo merchantInfo = list.get(i);
				array.add(merchantInfo.getMrname());
			}
			object.put("list", array);
			object.put("status", "1");
		}catch(Exception e){
			e.printStackTrace();
			object.put("status", "0");
		}
		respond(response, object.toString());
		return null;
	}
}
