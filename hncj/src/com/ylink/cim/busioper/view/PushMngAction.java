package com.ylink.cim.busioper.view;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.busioper.dao.PushPlanDao;
import com.ylink.cim.busioper.domain.PushPlan;
import com.ylink.cim.busioper.service.PushMngService;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.CustType;
import com.ylink.cim.common.type.PushType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.ParaManager;

import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class PushMngAction extends BaseDispatchAction{

	private PushMngService pushMngService = (PushMngService)getService("pushMngService");
	private PushPlanDao pushPlanDao = (PushPlanDao)getService("pushPlanDao");
	public ActionForward toList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//菜单进入即查询
		return list(mapping, form, request, response);
		//return forward("/pages/busioper/push/pushPlanList.jsp");
	}
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PushMngActionForm actionForm = (PushMngActionForm)form;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startCreateDate", actionForm.getStartCreateDate());
		map.put("endCreateDate", actionForm.getEndCreateDate());
		map.put("branchNo", getSessionUser(request).getBranchNo());
		Paginater paginater = pushPlanDao.findPaginater(map, getPager(request));
		saveQueryResult(request, paginater);
		
		String msg = LogUtils.r("推送资讯管理查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return forward("/pages/busioper/push/pushPlanList.jsp");
	}
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		return forward("/pages/busioper/push/pushPlanAdd.jsp");
	}
	private void initData(HttpServletRequest request) throws Exception{
		PushType.setInReq(request);
		//ParaManager.setDictInReq(request, SysDictType.BranchType, "branchTypes");
		//当前机构支持的业务类型
		ParaManager.setDictInReq(request, SysDictType.valueOf("BusiType"+getSessionBranch(request).getValue().substring(2, 4)), "busiTypes");
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(Symbol.YES, "已订阅");
		request.setAttribute("subsStates", map);
		CustType.setInReq(request);
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			PushMngActionForm actionForm = (PushMngActionForm)form;
			PushPlan plan = new PushPlan();
			BeanUtils.copyProperties(plan, actionForm);
			if (StringUtils.isEmpty(actionForm.getBranchNo()) 
					&& !BranchType.SZGOLD.getValue().equals(getSessionUser(request).getBranchNo())) {
				plan.setBranchNo(getSessionUser(request).getBranchNo());
			}
			pushMngService.addPushPlan(plan);
			String message = "添加推送计划成功"; LogUtils.r("{?}", 1);
			setResult(true, message, request);
			setReturnUrl("/pushMngAction.do?action=list", request);
			logSuccess(request, UserLogType.ADD.getValue(), message);
			String msg = LogUtils.r("推送资讯管理添加成功,添加内容id为：{?}",plan.getId());
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
		} catch (BizException e) {
			setResult(false, "添加推送计划失败,原因:"+e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		}
		return success(mapping);
	}
	public ActionForward getBusiType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String branchNo = request.getParameter("branchNo");
		JSONObject object = new JSONObject();
		if (StringUtils.isEmpty(branchNo)) {
			branchNo = BranchType.SZGOLD.getValue();
		}
		Map<String, String> map = ParaManager.getSysDict("BusiType"+branchNo.substring(2, 4));
		object.putAll(map);
		respond(response, object.toString());
		return null;
	}
}
