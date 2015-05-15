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

	// ��ѯ
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
			String msg = MsgUtils.r("��Ͷ�ƻ���ѯ�ɹ�,��ѯ�˺�Ϊ��{?}", actionForm.getAcctNo());
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "��ѯʧ��,ʧ��ԭ��:" + e.getMessage(), request);
			// setResult(true, LogUtils.r("��ѯʧ��,ʧ��ԭ��Ϊ:{?}", e.getMessage()),
			// request);
			String msg = MsgUtils.r("��Ͷ�ƻ���ѯʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}

		return forward("/pages/invest/plan/investPlanList.jsp");
	}

	// �����ѯ��ҳ��
	public ActionForward toList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		List<SignContract> contracts = (List<SignContract>) request.getAttribute("payChnls");
		request.setAttribute("signContracts", contracts);
		return forward("/pages/invest/plan/investPlanList.jsp");
	}

	// ת��ƻ����ҳ��
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
		// ��֤
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
		// ��֤
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

	// ִ����Ӽƻ�
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
			setResult(true, "��Ӷ�Ͷ�ƻ��ɹ�,������ˮ��Ϊ:" + planNo, request);
			String msg = MsgUtils.r(
					"��Ӷ�Ͷ�ƻ���������,��ˮ��{?},�������{?},��Ͷ�˺�{?},��Ͷģʽ{?},��Ͷ����{?},��Ͷ���/����{?},��Ͷ���{?},�Ƿ���������{?},�Ƿ�������Ч{?},��Ͷ��{?}",
					planNo, getSessionBranch(request).getValue(), actionForm.getAcctNo(), actionForm.getAipMode(),
					actionForm.getAipPeriod(), actionForm.getAipAmount(), "Au", actionForm.getIsDelay(),
					actionForm.getIsEffect(), msgMap.get(MsgField.chn_aip_date.getFieldCode()));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, "��Ӷ�Ͷ�ƻ�ʧ��,ʧ��ԭ��:" + e.getMessage(), request);
			String msg = MsgUtils.r("��Ӷ�Ͷ�ƻ�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return verify(mapping, form, request, response);
		}

	}

	// ת��ƻ�����ҳ��
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
			setResult(false, MsgUtils.r("�޸Ķ�Ͷ�ƻ�ʧ��,ʧ��ԭ��:{?}", e.getMessage()), request);
		}
		return forward("/pages/invest/plan/planUpdate.jsp");
	}

	// ִ�и��ļƻ�
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
			msgMap.put(MsgField.memo.getFieldCode(), getSessionCustId(request) + "�޸Ķ�Ͷ�ƻ�");

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
			setResult(true, "�޸Ķ�Ͷ�ƻ��ɹ�,�ƻ���Ϊ:" + planNo, request);
			String msg = MsgUtils.r(
					"�޸Ķ�Ͷ�ƻ���������,��ˮ��{?},�������{?},��Ͷ�˺�{?},��Ͷģʽ{?},��Ͷ����{?},��Ͷ���/����{?},��Ͷ���{?},�Ƿ���������{?},��Ͷ��{?},ԭ�ƻ���{?}",
					planNo, getSessionBranch(request).getValue(), actionForm.getAcctNo(), actionForm.getAipMode(),
					actionForm.getAipPeriod(), actionForm.getAipAmount(), "Au", actionForm.getIsDelay(),
					msgMap.get(MsgField.chn_aip_date.getFieldCode()), actionForm.getOrderNo());
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, "�޸Ķ�Ͷ�ƻ�ʧ��,ʧ��ԭ��:" + e.getMessage(), request);
			String msg = MsgUtils.r("�޸Ķ�Ͷ�ƻ�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return updateVerify(mapping, form, request, response);
		}
	}

	// ��ͣ�ƻ�
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
			setResult(true, MsgUtils.r("��ͣ�ɹ�,�ƻ���Ϊ:{?}", planNo), request);
			String msg = MsgUtils.r("��ͣ��Ͷ�ƻ���������,��ˮ��{?},�������{?},��Ͷ�˺�{?},�ƻ���{?}", planNo, getSessionBranch(request)
					.getValue(), actionForm.getAcctNo(), actionForm.getOrderNo());
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, MsgUtils.r("��ͣʧ��,ʧ��ԭ��:{?}", e.getMessage()), request);
			String msg = MsgUtils.r("��ͣ��Ͷ�ƻ�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return list(mapping, form, request, response);
		}
	}

	// �ָ��ƻ�
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
			setResult(true, MsgUtils.r("�ָ��ɹ�,�ƻ���Ϊ:{?}", planNo), request);
			String msg = MsgUtils.r("�ָ���Ͷ�ƻ���������,��ˮ��{?},�������{?},��Ͷ�˺�{?},�ƻ���{?}", planNo, getSessionBranch(request)
					.getValue(), actionForm.getAcctNo(), actionForm.getOrderNo());
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, MsgUtils.r("�ָ�ʧ��,ʧ��ԭ��:{?}", e.getMessage()), request);
			String msg = MsgUtils.r("�ָ���Ͷ�ƻ�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return list(mapping, form, request, response);
		}
	}

	// ִ��ȡ���ƻ�
	public ActionForward doCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm) form;

			Map<String, String> msgMap = getReqMap(request);
			msgMap.put(MsgField.aip_no.getFieldCode(), actionForm.getAcctNo());
			msgMap.put(MsgField.order_no.getFieldCode(), actionForm.getOrderNo());
			String planNo = investPlanService.doCancel(msgMap);
			setReturnUrl("/investPlanAction.do?action=toList", request);
			setResult(true, MsgUtils.r("ȡ����Ͷ�ƻ��ɹ�,�ƻ���Ϊ:{?}", planNo), request);
			String msg = MsgUtils.r("ȡ����Ͷ�ƻ���������,��ˮ��{?},�������{?},��Ͷ�˺�{?},�ƻ���{?}", planNo, getSessionBranch(request)
					.getValue(), actionForm.getAcctNo(), actionForm.getOrderNo());
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, MsgUtils.r("ȡ����Ͷ�ƻ��ɹ�ʧ��,ʧ��ԭ��:{?}", e.getMessage()), request);
			String msg = MsgUtils.r("ȡ����Ͷ�ƻ�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
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
