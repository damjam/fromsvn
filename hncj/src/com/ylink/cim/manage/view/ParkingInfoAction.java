package com.ylink.cim.manage.view;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.state.ParkingState;
import com.ylink.cim.manage.dao.ParkingInfoDao;
import com.ylink.cim.manage.domain.ParkingInfo;
import com.ylink.cim.manage.service.ParkingInfoService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class ParkingInfoAction extends BaseDispatchAction {
	private ParkingInfoDao parkingInfoDao = (ParkingInfoDao) getService("parkingInfoDao");
	private ParkingInfoService parkingInfoService = (ParkingInfoService) getService("parkingInfoService");

	
	public ActionForward toEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ParkingInfoActionForm actionForm = (ParkingInfoActionForm)form;
		String id = actionForm.getId();
		ParkingInfo parkingInfo = parkingInfoDao.findById(id);
		BeanUtils.copyProperties(actionForm, parkingInfo);
		ParkingState.setInReq(request);
		return forward("/pages/manage/parking/parkingInfoEdit.jsp");
	}
	
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ParkingState.setInReq(request);
		request.setAttribute("parkingStates", ParkingState.ALL.values());
		return forward("/pages/manage/parking/parkingInfoAdd.jsp");
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ParkingInfoActionForm actionForm = (ParkingInfoActionForm) form;
			Map<String, Object> params = getParaMap();
			params.put("sn", actionForm.getSn());
			params.put("branchNo", getSessionBranchNo(request));
			Assert.isEmpty(parkingInfoDao.findBy(params), "车位号已存在，请重新指定");
			ParkingInfo parkingInfo = new ParkingInfo();
			BeanUtils.copyProperties(parkingInfo, actionForm);
			parkingInfoService.save(parkingInfo, getSessionUser(request));
			clearForm(actionForm);
		}catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd(mapping, form, request, response);
		}
		
		return list(mapping, form, request, response);
	}
	
	private void clearForm(ParkingInfoActionForm actionForm) {
		actionForm.setOwnerName("");
		actionForm.setOwnerCel("");
		actionForm.setEndUser("");
		actionForm.setEndUserCel("");
		actionForm.setState("");
	}

	public ActionForward doEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ParkingInfoActionForm actionForm = (ParkingInfoActionForm) form;
			ParkingInfo parkingInfo = parkingInfoDao.findById(actionForm.getCarSn());
			String createUser = parkingInfo.getCreateUser();
			Date createDate = parkingInfo.getCreateDate();
			String branchNo = parkingInfo.getBranchNo();
			BeanUtils.copyProperties(parkingInfo, actionForm);
			parkingInfo.setCreateDate(createDate);
			parkingInfo.setCreateUser(createUser);
			parkingInfo.setBranchNo(branchNo);
			parkingInfoService.update(parkingInfo, getSessionUser(request));
			setResult(true, "添加成功", request);
			clearForm(actionForm);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toEdit(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toEdit(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ParkingState.setInReq(request);
		Map<String, Object> map = getParaMap();
		ParkingInfoActionForm actionForm = (ParkingInfoActionForm) form;
		map.put("carSn", actionForm.getCarSn());
		map.put("ownerName", actionForm.getOwnerName());
		map.put("ownerCel", actionForm.getOwnerCel());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = parkingInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return forward("/pages/manage/parking/parkingInfoList.jsp");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			parkingInfoService.delete(id, getSessionUser(request));
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

	public ActionForward queryPopUpParkingInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			params.put("avai", true);
			Paginater paginater = this.parkingInfoDao.findPager(params, getPager(request));
			saveQueryResult(request, paginater);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward("/pages/popUp/popUpParkingInfo.jsp");
	}
}
