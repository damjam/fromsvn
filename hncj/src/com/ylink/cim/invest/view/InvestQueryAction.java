package com.ylink.cim.invest.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.state.PlanState;
import com.ylink.cim.common.state.SignState;
import com.ylink.cim.common.type.AipMode;
import com.ylink.cim.common.type.AipPeriod;
import com.ylink.cim.common.type.BSFlag;
import com.ylink.cim.common.type.BusiType;
import com.ylink.cim.common.type.EntrSource;
import com.ylink.cim.common.type.PayChnlType;
import com.ylink.cim.common.type.RedeemType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.invest.dao.SignContractDao;
import com.ylink.cim.invest.domain.SignContract;
import com.ylink.cim.invest.service.InvestQueryService;

import flink.etc.Assert;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.web.BaseDispatchAction;

/**
 * 客户查询
 * 
 * @author Administrator
 * 
 */
public class InvestQueryAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(InvestQueryAction.class);
	private InvestQueryService investQueryService = (InvestQueryService) getService("investQueryService");
	private SignContractDao signContractDao = (SignContractDao) SpringContext.getService("signContractDao");

	/**
	 * 定投计划查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toQueryPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		return forward("/pages/invest/query/planList.jsp");
	}

	/**
	 * 定投计划查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		InvestInfoActionForm investInfoActionForm = (InvestInfoActionForm) form;
		Map<String, String> msgMap = getReqMap(request);
		msgMap.put(MsgField.aip_no.getFieldCode(), investInfoActionForm.getAcctNo());
		msgMap.put(MsgField.order_no.getFieldCode(), investInfoActionForm.getPlanNo());
		msgMap.put(MsgField.plan_stat.getFieldCode(), investInfoActionForm.getPlanState());
		// msgMap.put(MsgField.aip_type.getFieldCode(), "Au");
		msgMap.put(MsgField.start_date.getFieldCode(), investInfoActionForm.getStartDate());
		msgMap.put(MsgField.end_date.getFieldCode(), investInfoActionForm.getEndDate());
		try {
			Paginater paginater = investQueryService.queryPlan(msgMap, getPager(request));
			saveQueryResult(request, paginater);
			String msg = LogUtils.r("定投计划查询成功,查询账号为：{?}", investInfoActionForm.getAcctNo());
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "查询失败,失败原因:" + e.getMessage(), request);
			String msg = LogUtils.r("定投计划查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		return forward("/pages/invest/query/planList.jsp");
	}

	/**
	 * 黄金账户查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toQueryGoldAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);

		return forward("/pages/invest/query/acctList.jsp");
	}

	/**
	 * 黄金账户查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryGoldAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		InvestInfoActionForm investInfoActionForm = (InvestInfoActionForm) form;
		Map<String, String> msgMap = getReqMap(request);
		msgMap.put(MsgField.aip_no.getFieldCode(), investInfoActionForm.getAcctNo());
		msgMap.put(MsgField.start_date.getFieldCode(), investInfoActionForm.getStartDate());
		msgMap.put(MsgField.end_date.getFieldCode(), investInfoActionForm.getEndDate());
		try {
			Map<String, MsgField> resMap = investQueryService.queryGoldAcctTatol(msgMap);

			List<Map<String, MsgField>> list = investQueryService.queryGoldAcct(msgMap);

			request.setAttribute("resMap", resMap);
			saveQueryResult(request, list);
			String msg = LogUtils.r("黄金账户查询成功,查询账号为：{?}", investInfoActionForm.getAcctNo());
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "查询失败,失败原因:" + e.getMessage(), request);
			String msg = LogUtils.r("黄金账户查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		return forward("/pages/invest/query/acctList.jsp");
	}

	/**
	 * 资金账户查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward toQueryCashAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		return forward("/pages/invest/query/cashAcctList.jsp");
	}

	/**
	 * 资金账户查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryCashAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> map = getReqMap(request);
		InvestInfoActionForm investInfoActionForm = (InvestInfoActionForm) form;
		map.put(MsgField.aip_no.getFieldCode(), investInfoActionForm.getAcctNo());
		map.put(MsgField.start_date.getFieldCode(), investInfoActionForm.getStartDate());
		map.put(MsgField.end_date.getFieldCode(), investInfoActionForm.getEndDate());
		try {
			Paginater paginater = investQueryService.queryCashAcct(map, getPager(request));
			saveQueryResult(request, paginater);
			String msg = LogUtils.r("资金账户查询成功,查询账号为：{?}", investInfoActionForm.getAcctNo());
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			setResult(true, e.getMessage(), request);
			String msg = LogUtils.r("资金账户查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		initData(request);
		return forward("/pages/invest/query/cashAcctList.jsp");
	}

	/**
	 * 积存情况查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toQueryDepositRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		return forward("/pages/invest/query/depositList.jsp");
	}

	/**
	 * 积存情况查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryDepositRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		InvestInfoActionForm investInfoActionForm = (InvestInfoActionForm) form;
		Map<String, String> msgMap = getReqMap(request);
		msgMap.put(MsgField.aip_no.getFieldCode(), investInfoActionForm.getAcctNo());
		msgMap.put(MsgField.start_date.getFieldCode(), investInfoActionForm.getStartDate());
		msgMap.put(MsgField.end_date.getFieldCode(), investInfoActionForm.getEndDate());
		msgMap.put(MsgField.bs_flag.getFieldCode(), BSFlag.BUY.getValue());// 买卖委托
		try {
			Paginater paginater = investQueryService.queryDepositRecord(msgMap, getPager(request));
			saveQueryResult(request, paginater);
			String msg = LogUtils.r("积存情况查询成功,查询账号为：{?}", investInfoActionForm.getAcctNo());
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			setResult(true, e.getMessage(), request);
			String msg = LogUtils.r("积存情况查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		return forward("/pages/invest/query/depositList.jsp");
	}

	/**
	 * 提货记录查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toQueryTakeoutRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		request.setAttribute("totalWeight", 0d);
		return forward("/pages/invest/query/takeoutList.jsp");
	}

	/**
	 * 提货记录查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryTakeoutRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		InvestInfoActionForm investInfoActionForm = (InvestInfoActionForm) form;
		Map<String, String> msgMap = getReqMap(request);
		msgMap.put(MsgField.aip_no.getFieldCode(), investInfoActionForm.getAcctNo());
		msgMap.put(MsgField.start_date.getFieldCode(), investInfoActionForm.getStartDate());
		msgMap.put(MsgField.end_date.getFieldCode(), investInfoActionForm.getEndDate());
		try {
			Paginater paginater = investQueryService.queryRedeemRecord(msgMap, getPager(request));
			saveQueryResult(request, paginater);
			List<Map<String, MsgField>> sumList = investQueryService.querySumGoldAcct(msgMap,
					getSessionInvestAcctNos(request));
			Double totalWeight = 0d;
			for (int i = 0; i < sumList.size(); i++) {
				Map<String, MsgField> map = sumList.get(i);
				String weight = map.get(MsgField.curr_take_weight.getFieldCode()).getValue();
				totalWeight += Double.parseDouble(weight);
			}
			request.setAttribute("totalWeight", totalWeight);
			String msg = LogUtils.r("提货记录查询成功,查询账号为：{?}", investInfoActionForm.getAcctNo());
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			request.setAttribute("totalWeight", 0d);
			setResult(true, e.getMessage(), request);
			String msg = LogUtils.r("提货记录查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		return forward("/pages/invest/query/takeoutList.jsp");
	}

	// 卖出
	public ActionForward toQuerySoldRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		request.setAttribute("totalWeight", 0d);
		return forward("/pages/invest/query/soldoutList.jsp");
	}

	public ActionForward querySoldRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);

		InvestInfoActionForm investInfoActionForm = (InvestInfoActionForm) form;
		Map<String, String> msgMap = getReqMap(request);
		msgMap.put(MsgField.aip_no.getFieldCode(), investInfoActionForm.getAcctNo());
		// msgMap.put(MsgField.aip_type.getFieldCode(), "Au");
		msgMap.put(MsgField.entr_source.getFieldCode(), EntrSource.TRADINGCOMMISSION.getValue());// 买卖委托
		msgMap.put(MsgField.bs_flag.getFieldCode(), BSFlag.SELL.getValue());// 买卖委托
		msgMap.put(MsgField.start_date.getFieldCode(), investInfoActionForm.getStartDate());
		msgMap.put(MsgField.end_date.getFieldCode(), investInfoActionForm.getEndDate());
		try {
			Paginater paginater = investQueryService.querySoldRecord(msgMap, getPager(request));
			saveQueryResult(request, paginater);
			List<Map<String, MsgField>> sumList = investQueryService.querySumGoldAcct(msgMap,
					getSessionInvestAcctNos(request));
			Double totalWeight = 0d;
			for (int i = 0; i < sumList.size(); i++) {
				Map<String, MsgField> map = sumList.get(i);
				String weight = map.get(MsgField.curr_sell_weight.getFieldCode()).getValue();
				totalWeight += Double.parseDouble(weight);
			}
			request.setAttribute("totalWeight", totalWeight);
			String msg = LogUtils.r("卖出记录查询成功,查询账号为：{?}", investInfoActionForm.getAcctNo());
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			// e.getMessage();
			// logger.error(e.getMessage());
			setResult(true, e.getMessage(), request);
			request.setAttribute("totalWeight", 0d);
			String msg = LogUtils.r("卖出记录查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		return forward("/pages/invest/query/soldoutList.jsp");
	}

	/**
	 * 账户管理费查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toQueryMngFee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		return forward("/pages/invest/query/mngFee.jsp");
	}

	/**
	 * 账户管理费查询
	 * 
	 */
	public ActionForward queryMngFee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		initData(request);
		InvestInfoActionForm investInfoActionForm = (InvestInfoActionForm) form;
		Map<String, String> msgMap = getReqMap(request);
		msgMap.put(MsgField.aip_no.getFieldCode(), investInfoActionForm.getAcctNo());
		msgMap.put(MsgField.start_date.getFieldCode(), investInfoActionForm.getStartDate());
		msgMap.put(MsgField.end_date.getFieldCode(), investInfoActionForm.getEndDate());
		try {
			Paginater list = investQueryService.queryMngFee(msgMap, getPager(request));
			saveQueryResult(request, list);
			String msg = LogUtils.r("账户管理费查询成功,查询账号为：{?}", investInfoActionForm.getAcctNo());
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			setResult(true, e.getMessage(), request);
			String msg = LogUtils.r("账户管理费查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		return forward("/pages/invest/query/mngFee.jsp");
	}

	public ActionForward operList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm) form;
		try {
			String planNo = actionForm.getPlanNo();
			Assert.notNull(planNo, "计划号不能为空");
			Map<String, String> map = getReqMap(request);
			map.put(MsgField.order_no.getFieldCode(), planNo);
			List<Map<String, MsgField>> list = investQueryService.queryPlanOperList(map);
			saveQueryResult(request, list);
			String msg = LogUtils.r("操作记录查询成功,查询计划号为：{?}", planNo);
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			setResult(true, e.getMessage(), request);
			String msg = LogUtils.r("操作记录查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		return forward("/pages/invest/query/operList.jsp");
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
		RedeemType.setInReq(request);
		PlanState.setInReq(request);
		AipPeriod.setInReq(request);
	}
}
