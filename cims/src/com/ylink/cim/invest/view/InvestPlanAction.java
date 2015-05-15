package com.ylink.cim.invest.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
import com.ylink.cim.common.type.BusiType;
import com.ylink.cim.common.type.PayChnlType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.invest.dao.SignContractDao;
import com.ylink.cim.invest.domain.SignContract;
import com.ylink.cim.invest.service.InvestPlanService;

import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.LogUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.web.BaseDispatchAction;

public class InvestPlanAction extends BaseDispatchAction {

	private InvestPlanService investPlanService = (InvestPlanService) SpringContext.getService("investPlanService");
	private SignContractDao signContractDao = (SignContractDao) SpringContext.getService("signContractDao");

	// 查询
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm) form;
		Map<String, String> msgMap = getReqMap(request);
		msgMap.put(MsgField.aip_type.getFieldCode(), "Au");
		String[] aipNos = getSessionInvestAcctNos(request);
		if (StringUtils.isNotEmpty(actionForm.getAcctNo())) {
			aipNos = new String[] { actionForm.getAcctNo() };
		}
		msgMap.put(MsgField.aip_no.getFieldCode(), actionForm.getAcctNo());
		msgMap.put(MsgField.plan_stat.getFieldCode(), actionForm.getPlanState());
		initData(request);
		List<SignContract> contracts = (List<SignContract>) request.getAttribute("payChnls");
		request.setAttribute("signContracts", contracts);
		List<Map<String, MsgField>> list = null;
		try {
			Paginater paginater = investPlanService.queryPlan(msgMap, getPager(request));
			saveQueryResult(request, paginater);
			String msg = MsgUtils.r("定投计划查询成功,查询账号为：{?}", actionForm.getAcctNo());
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "查询失败,失败原因:" + e.getMessage(), request);
			// setResult(true, LogUtils.r("查询失败,失败原因为:{?}", e.getMessage()),
			// request);
			String msg = MsgUtils.r("定投计划查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}

		return forward("/pages/invest/plan/investPlanList.jsp");
	}

	// 进入查询的页面
	public ActionForward toList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		List<SignContract> contracts = (List<SignContract>) request.getAttribute("payChnls");
		request.setAttribute("signContracts", contracts);
		return forward("/pages/invest/plan/investPlanList.jsp");
	}

	// 转向计划添加页面
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		setBackUrl(request);
		List<SignContract> contracts = (List<SignContract>) request.getAttribute("payChnls");
		request.setAttribute("signContracts", contracts);
		Object sessionForm = WebUtils.getSessionAttribute(request, "form");
		if (sessionForm != null && Symbol.YES.equals(request.getParameter("updateSymbol"))) {
			BeanUtils.copyProperties(form, sessionForm);
		}
		return forward("/pages/invest/plan/planAdd.jsp");
	}

	public ActionForward verify(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 验证
		initData(request);
		InvestInfoActionForm actionForm = (InvestInfoActionForm) form;
		try {
			String signContractId = actionForm.getSignContractId();
			// SignContract signContract =
			// signContractDao.findById(signContractId);
			// actionForm.setPayChnlAlias(PayChnlType.valueOf(signContract.getPayChnlType()).getName()+signContract.getPayChnlNo());
			WebUtils.setSessionAttribute(request, "form", form);
			Object object = WebUtils.getSessionAttribute(request, "form");
			return forward("/pages/invest/plan/planVerify.jsp");
		} catch (Exception e) {
			setResult(false, e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		}
	}

	public ActionForward updateVerify(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 验证
		initData(request);
		InvestInfoActionForm actionForm = (InvestInfoActionForm) form;
		try {
			// String signContractId = actionForm.getSignContractId();
			// SignContract signContract =
			// signContractDao.findById(signContractId);
			// actionForm.setPayChnlAlias(PayChnlType.valueOf(signContract.getPayChnlType()).getName()+signContract.getPayChnlNo());
			WebUtils.setSessionAttribute(request, "form", form);
			Object object = WebUtils.getSessionAttribute(request, "form");
			return forward("/pages/invest/plan/updatePlanVerify.jsp");
		} catch (Exception e) {
			return toUpdate(mapping, form, request, response);
		}
	}

	// 执行添加计划
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm) form;

			Map<String, String> msgMap = getReqMap(request);
			msgMap.put(MsgField.aip_no.getFieldCode(), actionForm.getAcctNo());
			msgMap.put(MsgField.aip_mode.getFieldCode(), actionForm.getAipMode());
			msgMap.put(MsgField.aip_period.getFieldCode(), actionForm.getAipPeriod());
			msgMap.put(MsgField.aip_amount.getFieldCode(), actionForm.getAipAmount());
			msgMap.put(MsgField.aip_type.getFieldCode(), "Au");
			msgMap.put(MsgField.is_delay.getFieldCode(), actionForm.getIsDelay());
			msgMap.put(MsgField.is_effect.getFieldCode(), actionForm.getIsEffect());

			if (actionForm.getInvestDays() != null) {
				String[] days = actionForm.getInvestDays();
				StringBuffer sb = new StringBuffer();

				int len = days.length;
				for (int i = 0; i < len - 1; i++) {
					sb.append(days[i]).append(",");
				}
				sb.append(days[len - 1]);
				msgMap.put(MsgField.chn_aip_date.getFieldCode(), sb.toString());
			} else {

				msgMap.put(MsgField.chn_aip_date.getFieldCode(), "");
			}

			String planNo = investPlanService.addPlan(msgMap);

			setReturnUrl("/investPlanAction.do?action=toList", request);
			setResult(true, "添加定投计划成功,申请流水号为:" + planNo, request);
			String msg = MsgUtils.r(
					"添加定投计划现已受理,流水号{?},代理机构{?},定投账号{?},定投模式{?},定投周期{?},定投金额/重量{?},定投类别{?},是否无限延期{?},是否立即生效{?},定投日{?}",
					planNo, getSessionBranch(request).getValue(), actionForm.getAcctNo(), actionForm.getAipMode(),
					actionForm.getAipPeriod(), actionForm.getAipAmount(), "Au", actionForm.getIsDelay(),
					actionForm.getIsEffect(), msgMap.get(MsgField.chn_aip_date.getFieldCode()));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, "添加定投计划失败,失败原因:" + e.getMessage(), request);
			String msg = MsgUtils.r("添加定投计划失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return verify(mapping, form, request, response);
		}

	}

	// 转向计划更改页面
	public ActionForward toUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		setBackUrl(request);
		InvestInfoActionForm actionForm = (InvestInfoActionForm) form;
		Map<String, String> msgMap = getReqMap(request);
		msgMap.put(MsgField.aip_type.getFieldCode(), "Au");
		msgMap.put(MsgField.aip_no.getFieldCode(), actionForm.getAcctNo());
		msgMap.put(MsgField.order_no.getFieldCode(), actionForm.getOrderNo());
		try {
			Map<String, MsgField> map = investPlanService.queryOnlyPlan(msgMap);
			request.setAttribute("map", map);
		} catch (Exception e) {
			setResult(false, MsgUtils.r("修改定投计划失败,失败原因:{?}", e.getMessage()), request);
		}
		return forward("/pages/invest/plan/planUpdate.jsp");
	}

	// 执行更改计划
	public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm) form;

			Map<String, String> msgMap = getReqMap(request);
			msgMap.put(MsgField.aip_no.getFieldCode(), actionForm.getAcctNo());
			msgMap.put(MsgField.aip_mode.getFieldCode(), actionForm.getAipMode());
			msgMap.put(MsgField.aip_period.getFieldCode(), actionForm.getAipPeriod());
			msgMap.put(MsgField.aip_amount.getFieldCode(), actionForm.getAipAmount());
			msgMap.put(MsgField.is_delay.getFieldCode(), actionForm.getIsDelay());
			msgMap.put(MsgField.order_no.getFieldCode(), actionForm.getOrderNo());
			msgMap.put(MsgField.memo.getFieldCode(), getSessionCustId(request) + "修改定投计划");

			if (actionForm.getInvestDays() != null) {
				String[] days = actionForm.getInvestDays();
				StringBuffer sb = new StringBuffer();

				int len = days.length;
				for (int i = 0; i < len - 1; i++) {
					sb.append(days[i]).append(",");
				}
				sb.append(days[len - 1]);
				msgMap.put(MsgField.chn_aip_date.getFieldCode(), sb.toString());
			} else {

				msgMap.put(MsgField.chn_aip_date.getFieldCode(), "");
			}

			String planNo = investPlanService.updatePlan(msgMap);
			setReturnUrl("/investPlanAction.do?action=toList", request);
			setResult(true, "修改定投计划成功,计划号为:" + planNo, request);
			String msg = MsgUtils.r(
					"修改定投计划现已受理,流水号{?},代理机构{?},定投账号{?},定投模式{?},定投周期{?},定投金额/重量{?},定投类别{?},是否无限延期{?},定投日{?},原计划号{?}",
					planNo, getSessionBranch(request).getValue(), actionForm.getAcctNo(), actionForm.getAipMode(),
					actionForm.getAipPeriod(), actionForm.getAipAmount(), "Au", actionForm.getIsDelay(),
					msgMap.get(MsgField.chn_aip_date.getFieldCode()), actionForm.getOrderNo());
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, "修改定投计划失败,失败原因:" + e.getMessage(), request);
			String msg = MsgUtils.r("修改定投计划失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return updateVerify(mapping, form, request, response);
		}
	}

	// 暂停计划
	public ActionForward doPause(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm) form;

			Map<String, String> msgMap = getReqMap(request);
			msgMap.put(MsgField.aip_no.getFieldCode(), actionForm.getAcctNo());
			msgMap.put(MsgField.order_no.getFieldCode(), actionForm.getOrderNo());
			msgMap.put(MsgField.is_stop.getFieldCode(), YesNoType.YES.getValue());

			String planNo = investPlanService.doPauseOrRecover(msgMap);

			setReturnUrl("/investPlanAction.do?action=toList", request);
			setResult(true, MsgUtils.r("暂停成功,计划号为:{?}", planNo), request);
			String msg = MsgUtils.r("暂停定投计划现已受理,流水号{?},代理机构{?},定投账号{?},计划号{?}", planNo, getSessionBranch(request)
					.getValue(), actionForm.getAcctNo(), actionForm.getOrderNo());
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, MsgUtils.r("暂停失败,失败原因:{?}", e.getMessage()), request);
			String msg = MsgUtils.r("暂停定投计划失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return list(mapping, form, request, response);
		}
	}

	// 恢复计划
	public ActionForward doRecover(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm) form;

			Map<String, String> msgMap = getReqMap(request);
			msgMap.put(MsgField.aip_no.getFieldCode(), actionForm.getAcctNo());
			msgMap.put(MsgField.order_no.getFieldCode(), actionForm.getOrderNo());
			msgMap.put(MsgField.is_stop.getFieldCode(), YesNoType.NO.getValue());

			String planNo = investPlanService.doPauseOrRecover(msgMap);

			setReturnUrl("/investPlanAction.do?action=toList", request);
			setResult(true, MsgUtils.r("恢复成功,计划号为:{?}", planNo), request);
			String msg = MsgUtils.r("恢复定投计划现已受理,流水号{?},代理机构{?},定投账号{?},计划号{?}", planNo, getSessionBranch(request)
					.getValue(), actionForm.getAcctNo(), actionForm.getOrderNo());
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, MsgUtils.r("恢复失败,失败原因:{?}", e.getMessage()), request);
			String msg = MsgUtils.r("恢复定投计划失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return list(mapping, form, request, response);
		}
	}

	// 执行取消计划
	public ActionForward doCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm) form;

			Map<String, String> msgMap = getReqMap(request);
			msgMap.put(MsgField.aip_no.getFieldCode(), actionForm.getAcctNo());
			msgMap.put(MsgField.order_no.getFieldCode(), actionForm.getOrderNo());
			String planNo = investPlanService.doCancel(msgMap);
			setReturnUrl("/investPlanAction.do?action=toList", request);
			setResult(true, MsgUtils.r("取消定投计划成功,计划号为:{?}", planNo), request);
			String msg = MsgUtils.r("取消定投计划现已受理,流水号{?},代理机构{?},定投账号{?},计划号{?}", planNo, getSessionBranch(request)
					.getValue(), actionForm.getAcctNo(), actionForm.getOrderNo());
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, MsgUtils.r("取消定投计划成功失败,失败原因:{?}", e.getMessage()), request);
			String msg = MsgUtils.r("取消定投计划失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return list(mapping, form, request, response);
		}
	}

	private void initData(HttpServletRequest request) throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("custId", getSessionCustId(request));
		map.put("busiCode", BusiType.GOLD.getValue());
		map.put("state", SignState.SIGNED.getValue());
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
		YesNoType.setInReq(request);

		List<List<Integer>> investDays = new ArrayList<List<Integer>>();
		int day = 1;
		for (int i = 0; i < 4; i++) {
			List<Integer> row = new ArrayList<Integer>();
			for (int j = 0; j < 7; j++) {
				row.add(day);
				if (day >= 28) {
					break;
				}
				day++;
			}
			investDays.add(row);
		}
		request.setAttribute("investDays", investDays);
	}
}
