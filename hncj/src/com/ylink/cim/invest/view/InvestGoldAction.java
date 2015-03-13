package com.ylink.cim.invest.view;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.state.PlanState;
import com.ylink.cim.common.state.SignState;
import com.ylink.cim.common.type.AipMode;
import com.ylink.cim.common.type.AipPeriod;
import com.ylink.cim.common.type.AipType;
import com.ylink.cim.common.type.BSFlag;
import com.ylink.cim.common.type.BusiType;
import com.ylink.cim.common.type.PayChnlType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.DoubleUtil;
import com.ylink.cim.common.util.SendMobilMsgUtil;
import com.ylink.cim.invest.dao.SignContractDao;
import com.ylink.cim.invest.domain.SignContract;
import com.ylink.cim.invest.service.GoldAcctService;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.SpringContext;
import flink.web.BaseDispatchAction;
/**
 * 黄金账户管理
 * @date 2013-5-22
 */
public class InvestGoldAction extends BaseDispatchAction {

	private SignContractDao signContractDao = (SignContractDao)SpringContext.getService("signContractDao");
	private GoldAcctService goldAcctService = (GoldAcctService)SpringContext.getService("goldAcctService");
	
	//提货
	public ActionForward toRedeemGold(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		setBackUrl(request);
		//InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
		if (Symbol.YES.equals(request.getParameter("updateSymbol"))){
			InvestInfoActionForm sessionForm = (InvestInfoActionForm)WebUtils.getSessionAttribute(request, "form");
			if (sessionForm != null) {
				 {
					BeanUtils.copyProperties(form, sessionForm);
				}
			}
		}
		Map<String, String> map = getReqMap(request);
		String[] investAcctNos = getSessionInvestAcctNos(request);
		Map<String, String> acctMap = goldAcctService.findGoldStock(map, investAcctNos);
		request.getSession().setAttribute("aipAccts", acctMap);
		request.setAttribute("aipAccts", acctMap);
		
		//提示可提货城市
		initData(request);
		return forward("/pages/invest/goldacct/redeemGold.jsp");
	}
	
	
	private void setGoldVariety(HttpServletRequest request) throws BizException{
		Map<String, String> map = getReqMap(request);
		List<Map<String, MsgField>> list = goldAcctService.findGoldVariety(map);
		saveQueryResult(request, list, "goldTypes");
	}
	private void setStoreCity(HttpServletRequest request) throws BizException{
		Map<String, String> cityMap = goldAcctService.findStoreCity();
		request.setAttribute("cityMap", cityMap);
	}
	
	public ActionForward queryTakeDate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> map = getReqMap(request);
		map.put(MsgField.branch_id.getFieldCode(), request.getParameter("branchId"));
		Date today = DateUtil.getCurrent();
		String[] takeMonths = {DateUtil.getDateYYYYMM(today), DateUtil.getDateYYYYMM(DateUtil.addMonths(today, 1))};
		List<Map<String, MsgField>> list = goldAcctService.findTakeDate(map, takeMonths);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Map<String, MsgField> takeMonth = list.get(i);
			String takeDate = takeMonth.get(MsgField.take_date.getFieldCode()).getValue();
			array.add(takeDate);
		}
		respond(response, array.toString());
		return null;
	}
	public ActionForward verifyRedeemGold(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//验证
		initData(request);
		InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
		try {
			//Map<String, String> acctMap = (Map<String, String>)request.getSession().getAttribute("aipAccts");
			String balance = actionForm.getBalance();
			Assert.isTrue(Double.parseDouble(actionForm.getAmount())< Double.parseDouble(balance), "赎回重量不能大于积存总重量");
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/invest/goldacct/redeemGoldVerify.jsp");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			initData(request);
			return forward("/pages/invest/goldacct/redeemGold.jsp");
			//return toRedeemGold(mapping, actionForm, request, response);
		}
	}
	
	public ActionForward doRedeemGold(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//验证
		//InvestInfoActionForm actionForm = (InvestInfoActionForm)WebUtils.getSessionAttribute(request, "form");
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm)request.getSession().getAttribute("form");
			//BeanUtils.copyProperties(actionForm, sessionForm);
			Map<String, String> map = new HashMap<String, String>();
			//map.put("signContractId", actionForm.getSignContractId());
			map.put(MsgField.take_weight.getFieldCode(), DoubleUtil.doubleToString(Double.parseDouble(actionForm.getAmount()), 18, 4));
			map.put(MsgField.aip_no.getFieldCode(), actionForm.getAcctNo());
			map.put(MsgField.plan_exch_date.getFieldCode(), actionForm.getTakeDate());
			map.put(MsgField.variety_id.getFieldCode(), actionForm.getGoldType());
			map.put(MsgField.city_code.getFieldCode(), actionForm.getCityCode());
			map.put(MsgField.branch_id.getFieldCode(), actionForm.getTakeBank());
			String orderNo = goldAcctService.addRedeemGold(map);
			setResult(true, "实物赎回已受理，处理流水号"+orderNo, request);
			setReturnUrl("/investGoldAction.do?action=toRedeemGold", request);
			String msg = LogUtils.r("实物赎回已受理,流水号{?},提货重量{?},定投账号{?},计划提货日{?},品种代码{?},提货城市{?},代理机构{?}", orderNo
					,actionForm.getAmount(),
					actionForm.getAcctNo(),actionForm.getTakeDate(),actionForm.getGoldType()
					,actionForm.getCityCode(),actionForm.getTakeBank()
					);
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
//			//Date date=Calendar.getInstance().getTime();
//			String year=DateUtil.formatDate("yyyy");
//			String month=DateUtil.formatDate("MM");
//			String day=DateUtil.formatDate("dd");
//			
//			String mobileMsg=LogUtils.r(Constants.TAKEOUT_MOBILE_MSG, year,month,day,
//					actionForm.getAmount(),
//					"","","");
//			String sessMobile = (String)WebUtils.getSessionAttribute(request, "mobile");
//			SendMobilMsgUtil.sendMsgFw(sessMobile,mobileMsg);//发送信息
			return success(mapping);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("实物赎回失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return failure(mapping);
		}
	}
	//
	public ActionForward toRedeemCash(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		setBackUrl(request);
		if (Symbol.YES.equals(request.getParameter("updateSymbol"))) {
			InvestInfoActionForm sessionForm = (InvestInfoActionForm)WebUtils.getSessionAttribute(request, "form");
			if (sessionForm != null) {
				BeanUtils.copyProperties(form, sessionForm);
			}
		}
		//查询黄金积存量
		Map<String, String> map = getReqMap(request);
		//map.put("", value);
		String[] investAcctNos = getSessionInvestAcctNos(request);
		Map<String, String> acctMap = goldAcctService.findGoldStock(map, investAcctNos);
		request.getSession().setAttribute("aipAccts", acctMap);
		request.setAttribute("aipAccts", acctMap);
		return forward("/pages/invest/goldacct/redeemCash.jsp");
	}
	public ActionForward verifyRedeemCash(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
		try {
			WebUtils.setSessionAttribute(request, "form", form);
			String acctNo = actionForm.getAcctNo();
			Map<String, String> aipAcctMap = (Map<String, String>)request.getSession().getAttribute("aipAccts");
			String balance = aipAcctMap.get(acctNo);
			actionForm.setBalance(balance);
			Assert.isTrue(Double.parseDouble(actionForm.getAmount())< Double.parseDouble(balance), "赎回重量不能大于可赎回重量");
			return forward("/pages/invest/goldacct/redeemCashVerify.jsp");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			//查询黄金积存量
			Map<String, String> map = getReqMap(request);
			//map.put("", value);
			String[] investAcctNos = getSessionInvestAcctNos(request);
			Map<String, String> acctMap = goldAcctService.findGoldStock(map, investAcctNos);
			request.getSession().setAttribute("aipAccts", acctMap);
			request.setAttribute("aipAccts", acctMap);
			return forward("/pages/invest/goldacct/redeemCash.jsp");
		}
	}
	//查询提货网点
	public ActionForward queryStoreBranch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String cityCode = request.getParameter("cityCode");
		Assert.notNull(cityCode, "城市代码不能为空");
		Map<String, String> map = new HashMap<String, String>();
		map.put(MsgField.city_code.getFieldCode(), cityCode);
		List<Map<String, String>> list = goldAcctService.findStoreBranch(map);
		saveQueryResult(request, list);
		return forward("/pages/popUp/popUpStoreBranch.jsp");
	}
	//查询黄金品种
	public ActionForward queryGoldVariety(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> map = getReqMap(request);
		List<Map<String, MsgField>> list = goldAcctService.findGoldVariety(map);
		saveQueryResult(request, list);
		return forward("/pages/popUp/popUpGoldVariety.jsp");
	}
	public ActionForward doRedeemCash(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm)WebUtils.getSessionAttribute(request, "form");
		try {
			Map<String, String> map = getReqMap(request);
			map.put("signContractId", actionForm.getSignContractId());
			map.put("aip_no", actionForm.getAcctNo());
			map.put("bs_flag", BSFlag.SELL.getValue());
			map.put("aip_mode", AipMode.MODE_2.getValue());
			map.put("aip_type", BusiType.GOLD.getCode());
			map.put("aip_amount", DoubleUtil.doubleToString(Double.parseDouble(actionForm.getAmount()), 18, 4));
			String orderNo = goldAcctService.addRedeemCash(map);
			setResult(true, "现金赎回已受理，流水单号"+orderNo, request);
			setReturnUrl("/investGoldAction.do?action=toRedeemCash", request);
			String msg = LogUtils.r("现金赎回已受理,流水号{?},定投账号{?},赎回重量{?}", orderNo
					,actionForm.getAcctNo(),actionForm.getAmount()
					);
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			//Date date=Calendar.getInstance().getTime();
//			String year=DateUtil.formatDate("yyyy");
//			String month=DateUtil.formatDate("MM");
//			String day=DateUtil.formatDate("dd");
//			String mobileMsg=LogUtils.r(Constants.SOLD_MOBILE_MSG, year,month,day,
//					actionForm.getAmount(),
//					"","","");
//			String sessMobile = (String)WebUtils.getSessionAttribute(request, "mobile");
//			SendMobilMsgUtil.sendMsgFw(sessMobile,mobileMsg);//发送信息
			return success(mapping);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("现金赎回失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return failure(mapping);
		}
	}
	
	private void initData(HttpServletRequest request) throws Exception{
		Map<String, Object> map = getParaMap();
		map.put("custId", getSessionCustId(request));
		map.put("busiCode", BusiType.GOLD.getValue());
		map.put("state", SignState.SIGNED.getValue());
		map.put("branchNo", getSessionBranch(request).getValue());
		List<SignContract> list = signContractDao.findByParams(map);
		for (int i = 0; i < list.size(); i++) {
			SignContract contract = list.get(i);
			contract.setPayChnlTypeName(PayChnlType.valueOf(contract.getPayChnlType()).getName());
		}
		request.setAttribute("payChnls", list);
		AipMode.setInReq(request);
		AipPeriod.setInReq(request);
		PlanState.setInReq(request);
		AipType.setInReq(request);
		setStoreCity(request);
		setGoldVariety(request);
	}
}
