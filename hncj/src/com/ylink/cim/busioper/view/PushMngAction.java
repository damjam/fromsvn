package com.ylink.cim.busioper.view;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.busioper.dao.PushPlanDao;
import com.ylink.cim.busioper.domain.PushPlan;
import com.ylink.cim.busioper.service.PushMngService;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.CustType;
import com.ylink.cim.common.type.PushType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.ParaManager;

import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class PushMngAction extends BaseAction implements ModelDriven<PushPlan> {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	@Autowired
	private PushMngService pushMngService;
	@Autowired
	private PushPlanDao pushPlanDao;

	private PushPlan model = new PushPlan();

	public String doAdd() throws Exception {
		try {
			PushPlan plan = new PushPlan();
			BeanUtils.copyProperties(plan, model);
			if (StringUtils.isEmpty(model.getBranchNo())
					&& !BranchType.HQ_0000.getValue().equals(getSessionUser(request).getBranchNo())) {
				plan.setBranchNo(getSessionUser(request).getBranchNo());
			}
			pushMngService.addPushPlan(plan);
			String message = "������ͼƻ��ɹ�";
			LogUtils.r("{?}", 1);
			setResult(true, message, request);
			setReturnUrl("/pushMngAction.do?action=list", request);
			logSuccess(request, UserLogType.ADD.getValue(), message);
			String msg = LogUtils.r("������Ѷ������ӳɹ�,�������idΪ��{?}", plan.getId());
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
		} catch (BizException e) {
			setResult(false, "������ͼƻ�ʧ��,ԭ��:" + e.getMessage(), request);
			return toAdd();
		}
		return SUCCESS;
	}

	public String getBusiType() throws Exception {
		String branchNo = request.getParameter("branchNo");
		JSONObject object = new JSONObject();
		if (StringUtils.isEmpty(branchNo)) {
			branchNo = BranchType.HQ_0000.getValue();
		}
		Map<String, String> map = ParaManager.getSysDict("BusiType" + branchNo.substring(2, 4));
		object.putAll(map);
		respond(response, object.toString());
		return null;
	}

	public PushPlan getModel() {
		return model;
	}

	private void initData(HttpServletRequest request) throws Exception {
		PushType.setInReq(request);
		// ParaManager.setDictInReq(request, SysDictType.BranchType,
		// "branchTypes");
		// ��ǰ����֧�ֵ�ҵ������
		// /ParaManager.setDictInReq(request,
		// SysDictType.valueOf("BusiType"+getSessionBranch(request).getValue().substring(2,
		// 4)), "busiTypes");
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(Symbol.YES, "�Ѷ���");
		request.setAttribute("subsStates", map);
		CustType.setInReq(request);
	}

	public String list() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startCreateDate", model.getStartCreateDate());
		map.put("endCreateDate", model.getEndCreateDate());
		map.put("branchNo", getSessionUser(request).getBranchNo());
		Paginater paginater = pushPlanDao.findPaginater(map, getPager(request));
		saveQueryResult(request, paginater);

		String msg = LogUtils.r("������Ѷ�����ѯ�ɹ�");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		// return forward("/pages/busioper/push/pushPlanList.jsp");
		return "list";
	}

	public String toAdd() throws Exception {
		initData(request);
		// return forward("/pages/busioper/push/pushPlanAdd.jsp");
		return "add";
	}

	public String toList() throws Exception {
		// �˵����뼴��ѯ
		return list();
		// return forward("/pages/busioper/push/pushPlanList.jsp");
	}
}
