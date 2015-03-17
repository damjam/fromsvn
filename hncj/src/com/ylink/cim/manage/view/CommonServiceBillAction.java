package com.ylink.cim.manage.view;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.state.CheckinState;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.CommonServiceBillDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.service.BillService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class CommonServiceBillAction extends BaseDispatchAction {
	private CommonServiceBillDao commonServiceBillDao = (CommonServiceBillDao)getService("commonServiceBillDao");
	private BillService billService = (BillService)getService("billService");
	private OwnerInfoDao ownerInfoDao = (OwnerInfoDao)getService("ownerInfoDao");
	Logger log = Logger.getLogger(CommonServiceBillAction.class);
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		YesNoType.setInReq(request);
		CheckinState.setInReq(request);
		return forward("/pages/manage/charge/common/commonServiceBillAdd.jsp");
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			CommonServiceBillActionForm actionForm = (CommonServiceBillActionForm)form;
			CommonServiceBill bill = new CommonServiceBill();
			BeanUtils.copyProperties(bill, actionForm);
			billService.saveServiceBill(bill, getSessionUser(request));
			setResult(true, "数据已保存", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败"+e.getMessage(), request);
			return toAdd(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		CommonServiceBillActionForm actionForm = (CommonServiceBillActionForm)form;
		map.put("houseSn", actionForm.getHouseSn());
		map.put("startChargeDate", actionForm.getStartChargeDate());
		map.put("endChargeDate", actionForm.getEndChargeDate());
		map.put("state", actionForm.getState());
		map.put("id", actionForm.getId());
		map.put("year", actionForm.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = commonServiceBillDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = commonServiceBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		return forward("/pages/manage/charge/common/commonServiceBillList.jsp");
	}
	public ActionForward charge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeCommonFee(id, getSessionUser(request));
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
			billService.deleteBill(CommonServiceBill.class, id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list(mapping, form, request, response);
	}
	public ActionForward getHouseInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String ownerName = "";
		String areaStr = "";
		String servicePrice = "";
		String lightPrice = "";
		String checkinState = "";
		try {
			String houseSn = request.getParameter("houseSn");
			OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(houseSn);
			if (ownerInfo != null) {
				ownerName = ownerInfo.getOwnerName();
				checkinState = ownerInfo.getCheckinState();
			}
			HouseInfo houseInfo = commonServiceBillDao.findById(HouseInfo.class, houseSn);
			Assert.notNull(houseInfo, "编号为"+houseSn+"的房屋信息不存在!");
			Double area = houseInfo.getArea();
			areaStr = String.valueOf(area);
			servicePrice = ParaManager.getServicePrice(Integer.parseInt(houseInfo.getFloor()));
			lightPrice = ParaManager.getLightPrice(houseInfo.getBuildingNo());
			//return null;
		} catch (Exception e) {
			jsonObject.put("error", e.getMessage());
			setResult(false, "失败", request);
			e.printStackTrace();
			respond(response, jsonObject.toString());
			return null;
		}
		jsonObject.put("ownerName", ownerName);
		jsonObject.put("area", areaStr);
		jsonObject.put("servicePrice", servicePrice);
		jsonObject.put("lightPrice", lightPrice);
		jsonObject.put("checkinState", checkinState);
		respond(response, jsonObject.toString());
		return null;
	}
	public ActionForward getAcctInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String endDateStr = "";
		String serviceAmount = "";
		String lightAmount = "";
		String totalAmount = "";
		try {
			String startDate = request.getParameter("startDate");
			String monthNumStr = request.getParameter("monthNum");
			String servicePriceStr = request.getParameter("servicePrice");
			String lightPrice = request.getParameter("lightPrice");
			//String houseSn = request.getParameter("houseSn");
			String areaStr = request.getParameter("area");
			Double area = Double.parseDouble(areaStr);
			//HouseInfo houseInfo = commonServiceBillDao.findById(HouseInfo.class, houseSn);
			Integer monthNum = Integer.parseInt(monthNumStr);
			Date endDate = DateUtil.addMonths(DateUtil.getDateByYYYMMDD(startDate), monthNum);
			endDate = DateUtil.addDays(endDate, -1);
			endDateStr = DateUtil.getDateYYYYMMDD(endDate);
			
			serviceAmount = MoneyUtil.getFormatStr2(Double.parseDouble(servicePriceStr) * area * monthNum);
			
			lightAmount = MoneyUtil.getFormatStr2(Double.parseDouble(lightPrice) * monthNum);
			totalAmount = MoneyUtil.getFormatStr2(Double.parseDouble(serviceAmount) + Double.parseDouble(lightAmount));
		} catch (Exception e) {
			setResult(false, "失败", request);
			e.printStackTrace();
			endDateStr = "";
		}
		jsonObject.put("endDate", endDateStr);
		jsonObject.put("serviceAmount", serviceAmount);
		jsonObject.put("lightAmount", lightAmount);
		jsonObject.put("totalAmount", totalAmount);
		respond(response, jsonObject.toString());
		return null;
	}
	
}
