package com.ylink.cim.manage.view;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.dao.ParkingBillDao;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.domain.ParkingBill;
import com.ylink.cim.manage.service.BillService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class ParkingBillAction extends BaseDispatchAction {
	private ParkingBillDao parkingBillDao = (ParkingBillDao)getService("parkingBillDao");
	private BillService billService = (BillService)getService("billService");
	private OwnerInfoDao ownerInfoDao = (OwnerInfoDao)getService("ownerInfoDao");
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		YesNoType.setInReq(request);
		return forward("/pages/manage/charge/parking/parkingBillAdd.jsp");
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ParkingBillActionForm actionForm = (ParkingBillActionForm)form;
			ParkingBill parkingBill = new ParkingBill();
			BeanUtils.copyProperties(parkingBill, actionForm);
			billService.saveParkingBill(parkingBill, getSessionUser(request));
			actionForm.setHouseSn("");
			actionForm.setCarSn("");
			actionForm.setParkingSn("");
			setResult(true, "数据已保存", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "保存失败"+e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		ParkingBillActionForm actionForm = (ParkingBillActionForm)form;
		map.put("houseSn", actionForm.getHouseSn());
		map.put("carSn", actionForm.getCarSn());
		map.put("parkingSn", actionForm.getParkingSn());
		map.put("id", actionForm.getId());
		map.put("year", actionForm.getYear());
		map.put("orderType", actionForm.getOrderType());
		Paginater paginater = parkingBillDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = parkingBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		return forward("/pages/manage/charge/parking/parkingBillList.jsp");
	}
	public ActionForward charge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeParkingFee(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			setResult(false, "操作失败", request);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward deleteBill(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(ParkingBill.class, id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list(mapping, form, request, response);
	}
	
	public ActionForward getOwnerName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String ownerName = "";
		String mobile = "";
		try {
			String houseSn = request.getParameter("houseSn");
			Assert.notNull(parkingBillDao.findById(HouseInfo.class, houseSn), "编号为"+houseSn+"的房屋信息不存在!");
			OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(houseSn);
			if (ownerInfo != null) {
				ownerName = ownerInfo.getOwnerName();
				mobile = ownerInfo.getMobile();
			}
			//return null;
		} catch (Exception e) {
			setResult(false, "失败", request);
			e.printStackTrace();
			jsonObject.put("error", e.getMessage());
			respond(response, jsonObject.toString());
			return null;
		}
		jsonObject.put("ownerName", ownerName);
		jsonObject.put("mobile", mobile);
		respond(response, jsonObject.toString());
		return null;
	}
	public ActionForward getAcctInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String endDateStr = "";
		Double amount = 0d;
		try {
			String startDate = request.getParameter("beginDate");
			String monthNumStr = request.getParameter("monthNum");
			String priceStr = request.getParameter("price");
			Integer monthNum = Integer.parseInt(monthNumStr);
			Date endDate = DateUtil.addMonths(DateUtil.getDateByYYYMMDD(startDate), monthNum);
			endDate = DateUtil.addDays(endDate, -1);
			endDateStr = DateUtil.getDateYYYYMMDD(endDate);
			//amount = Double.parseDouble(priceStr)*Integer.parseInt(monthNumStr);
		} catch (Exception e) {
			setResult(false, "失败", request);
			e.printStackTrace();
			endDateStr = "";
		}
		jsonObject.put("endDate", endDateStr);
		jsonObject.put("amount", amount);
		respond(response, jsonObject.toString());
		return null;
	}
}
