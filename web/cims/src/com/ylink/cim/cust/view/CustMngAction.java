package com.ylink.cim.cust.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.type.CustFromType;
import com.ylink.cim.common.type.CustType;
import com.ylink.cim.common.type.EmailType;
import com.ylink.cim.common.type.IdCardType;
import com.ylink.cim.common.type.SexType;
import com.ylink.cim.common.type.SymbolType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.type.UserType;
import com.ylink.cim.common.util.FeildUtils;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.cust.dao.CustInfoDao;
import com.ylink.cim.cust.domain.CustInfo;
import com.ylink.cim.cust.service.CustInfoService;
import com.ylink.cim.invest.dao.SignContractDao;
import com.ylink.cim.invest.domain.SignContract;
import com.ylink.cim.util.DataValidator;

import flink.MD5Util;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

/**
 * 客户信息管理，用于业务人员对客户信息的操作
 * 
 * @author libaozhu
 * @date 2013-5-2
 */
public class CustMngAction extends BaseDispatchAction {

	private CustInfoService custInfoService = (CustInfoService) getService("custInfoService");
	private CustInfoDao custInfoDao = (CustInfoDao) getService("custInfoDao");
	private SignContractDao signContractDao = (SignContractDao) getService("signContractDao");

	public ActionForward toList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*
		 * initData(request); return
		 * forward("/pages/busioper/custInfoList.jsp");
		 */
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			initData(request);
			Assert.equals(getSessionUser(request).getUserType(), UserType.BSUI_OPER.getValue(), "您无权查看客户信息");
			CustInfoActionForm custInfoActionForm = (CustInfoActionForm) form;
			Paginater paginater = custInfoDao.findCustPager(getCustInfoByForm(custInfoActionForm),
					getSessionUser(request), super.getPager(request));
			saveQueryResult(request, paginater);
			initData(request);
			String msg = MsgUtils.r("客户信息管理查询成功");
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (BizException e) {
			setResult(false, "查询失败,失败原因:" + e.getMessage(), request);
			String msg = MsgUtils.r("查询客户信息管理失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		return forward("/pages/busioper/custInfoList.jsp");
	}

	/**
	 * 客户信息查询的查询条件需要在這里添加
	 * 
	 * @param custInfoActionForm
	 * @return
	 * @throws Exception
	 */
	private CustInfo getCustInfoByForm(CustInfoActionForm custInfoActionForm) throws Exception {
		CustInfo custInfo = new CustInfo();
		BeanUtils.copyProperties(custInfo, custInfoActionForm);
		return custInfo;
	}

	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		return forward("/pages/busioper/custInfoAdd.jsp");
	}

	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustInfoActionForm actionForm = (CustInfoActionForm) form;
		initData(request);
		// 电话号码输入校验
		String t_mobile = actionForm.getMobile();
		try {
			Assert.notEmpty(t_mobile, "手机号不能为空");
			if (!DataValidator.isMobileNO(t_mobile)) {
				setResult(false, "手机号码格式不正确", request);
				return toAdd(mapping, actionForm, request, response);
			}
			CustInfo existCustInfo = custInfoDao.getUniqueCustInfo("mobile", t_mobile);
			Assert.isNull(existCustInfo, MsgUtils.r("号码{?}已被使用，请更改其他手机号码", t_mobile));

			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			custInfo.setBranchNo(getSessionBranch(request).getValue());
			custInfo.setCustType(CustType.TYPE_NORMAL.getValue());
			custInfo.setFromType(CustFromType.TYPE_0.getValue());
			// 初始密码为手机号后六位
			String init_pwd = MD5Util.MD5(t_mobile.substring(t_mobile.length() - 6));
			custInfo.setLoginPwd(init_pwd);
			// 添加客户
			custInfoService.addCustBasicInfo(custInfo);
			setResult(true, "添加成功!", request);
			setReturnUrl("/custMngAction.do?action=list", request);
			String msg = MsgUtils.r("添加新客户成功,添加内容为：{?}", FeildUtils.toString(custInfo));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			actionForm.setMobile("");
			setResult(false, e.getMessage(), request);
			String msg = MsgUtils.r("添加新客户失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return toAdd(mapping, actionForm, request, response);
		}
		// return forward("/pages/busioper/custInfoAdd.jsp");
	}

	public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			CustInfo custInfo = custInfoDao.findById(id);
			request.setAttribute("cust", custInfo);
			String msg = MsgUtils.r("查看明细成功");
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
			return forward("/pages/busioper/custInfoDetail.jsp");
		} catch (Exception e) {
			setResult(false, e.getMessage(), request);
			String msg = MsgUtils.r("查看明细失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
			return toList(mapping, form, request, response);
		}
	}

	public ActionForward viewCustInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = getSessionCustId(request);
			Assert.notEmpty(id, "该客户不存在");
			CustInfo custInfo = custInfoDao.findById(id);
			Assert.isNull(custInfo, MsgUtils.r("该客户{?}不是合法用户，请先注册", id));
			if (CustType.TYPE_MEMBER.getValue().equals(custInfo.getCustType())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("custId", id);
				List<SignContract> list = signContractDao.findByParams(params);
				SignContract sign = list.get(0);
				request.setAttribute("sign", sign);
			}
			request.setAttribute("cust", custInfo);
			String msg = MsgUtils.r("查看客户信息成功");
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
			return forward("/pages/cust/custInfo.jsp");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			String msg = MsgUtils.r("查看客户信息失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
			return forward("/pages/cust/custRegister.jsp");
		}
	}

	public ActionForward checkDate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String date = request.getParameter("date");
		JSONObject object = new JSONObject();
		try {
			DateUtil.string2Date(date, "yyyyMMdd");
			object.put("isDate", true);
		} catch (Exception e) {
			object.put("isDate", false);
		}
		respond(response, object.toString());
		return null;
	}

	private void initData(HttpServletRequest request) throws Exception {
		ParaManager.setDictInReq(request,
				SysDictType.valueOf("SignChnl" + getSessionBranch(request).getValue().substring(2, 4)), "payChnlTypes");
		SexType.setInReq(request);
		IdCardType.setInReq(request);
		SymbolType.setInReq(request);
		CustType.setInReq(request);
	}

	private void initEmailType(HttpServletRequest request) {
		request.setAttribute("emailTypes", EmailType.ALL.values());
		EmailType.setInReq(request);
	}
}
