package com.ylink.cim.manage.view;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.busioper.dao.BillTrackDao;
import com.ylink.cim.busioper.service.BillTrackService;
import com.ylink.cim.common.state.BillTrackState;
import com.ylink.cim.common.type.BillType;
import com.ylink.cim.common.type.RemainDays;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.domain.BillTrack;

import flink.etc.BizException;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class BillTrackAction extends BaseDispatchAction {
	private BillTrackDao billTrackDao = (BillTrackDao) getService("billTrackDao");
	private BillTrackService billTrackService = (BillTrackService) getService("billTrackService");

	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initSelect(request);
		return forward("/pages/manage/track/trackInfoAdd.jsp");
	}

	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			BillTrackActionForm actionForm = (BillTrackActionForm) form;
			BillTrack billTrack = new BillTrack();
			BeanUtils.copyProperties(billTrack, actionForm);
			billTrackService.add(billTrack, getSessionUser(request));
			setResult(true, "添加成功", request);
			
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = getParaMap();
		BillTrackActionForm actionForm = (BillTrackActionForm) form;
		map.put("houseSn", actionForm.getHouseSn());
		map.put("billId", actionForm.getBillId());
		map.put("ownerName", actionForm.getOwnerName());
		map.put("ownerCel", actionForm.getOwnerCel());
		map.put("billType", actionForm.getBillType());
		map.put("leftDays", actionForm.getLeftDays());
		map.put("branchNo", getSessionBranchNo(request));
		map.put("billType", actionForm.getBillType());
		map.put("state", BillTrackState.VALID.getValue());
		Paginater paginater = billTrackDao.findPaginater(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect(request);
		return forward("/pages/manage/track/billTrackList.jsp");
	}

	public ActionForward sendNotice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billTrackService.sendNotice(id);
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billTrackService.delete(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward discard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billTrackService.discard(id, getSessionUser(request));
			String msg = LogUtils.r("废弃账单到期提醒,记录id为{?}", id);
			logUserOperate(request, UserLogType.DELETE.getValue(), StringUtils.abbreviate(msg, 100));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list(mapping, form, request, response);
	}
	public void initSelect(HttpServletRequest request) throws Exception {
		Map<String, String> buildingNos = new LinkedHashMap<String, String>();
		Map<String, String> unitsNos = new LinkedHashMap<String, String>();
		Map<String, String> floors = new LinkedHashMap<String, String>();
		for (int i = 1; i < 4; i++) {
			unitsNos.put(String.valueOf(i), String.valueOf(i));
		}
		Map<String, String> rent = ParaManager.getSysDict(SysDictType.RentType.getValue());
		Map<String, String> economical = ParaManager.getSysDict(SysDictType.EconomicalType.getValue());

		buildingNos.putAll(economical);
		buildingNos.putAll(rent);
		// buildingNos
		for (int i = 1; i < 13; i++) {
			floors.put(String.valueOf(i), String.valueOf(i));
		}
		request.setAttribute("unitNos", unitsNos);
		request.setAttribute("buildingNos", buildingNos);
		request.setAttribute("floors", floors);
		BillType.setInReq(request);
		RemainDays.setInReq(request);
	}
}
