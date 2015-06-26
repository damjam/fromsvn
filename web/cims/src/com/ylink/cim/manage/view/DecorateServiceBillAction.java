package com.ylink.cim.manage.view;

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
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.DecorateServiceBillDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.domain.DecorateServiceBill;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.service.BillService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class DecorateServiceBillAction extends BaseDispatchAction {
	private DecorateServiceBillDao decorateServiceBillDao = (DecorateServiceBillDao)getService("decorateServiceBillDao");
	private BillService billService = (BillService)getService("billService");
	private OwnerInfoDao ownerInfoDao = (OwnerInfoDao)getService("ownerInfoDao");
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		YesNoType.setInReq(request);
		return forward("/pages/manage/charge/decorate/decorateServiceBillAdd.jsp");
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			DecorateServiceBillActionForm actionForm = (DecorateServiceBillActionForm)form;
			DecorateServiceBill bill = new DecorateServiceBill();
			BeanUtils.copyProperties(bill, actionForm);
			billService.saveDecorateServiceBill(bill, getSessionUser(request));
			String tip = "数据已保存";
			if (bill.getSupFee() != null && bill.getSupFee() > 0) {
				tip+= ",请在物业收费模块收取补缴物业费";
			}
			setResult(true, tip, request);
			actionForm.setHouseSn("");
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
		DecorateServiceBillActionForm actionForm = (DecorateServiceBillActionForm)form;
		map.put("houseSn", actionForm.getHouseSn());
		map.put("startChargeDate", actionForm.getStartChargeDate());
		map.put("endChargeDate", actionForm.getEndChargeDate());
		map.put("state", actionForm.getState());
		map.put("id", actionForm.getId());
		map.put("year", actionForm.getYear());
		Paginater paginater = decorateServiceBillDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = decorateServiceBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		return forward("/pages/manage/charge/decorate/decorateServiceBillList.jsp");
	}
	public ActionForward charge(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeDecorateFee(id, getSessionUser(request));
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
			billService.deleteBill(DecorateServiceBill.class, id, getSessionUser(request));
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
		String cleanPrice = "";
		String floor = "";
		String liftFee = "";
		String cleanAmount = "";
		String totalAmount = "";
		String supPay = "";
		String csBillId = "";
		try {
			String houseSn = request.getParameter("houseSn");
			OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(houseSn);
			if (ownerInfo != null) {
				ownerName = ownerInfo.getOwnerName();
				//ownerInfo.getCheckinState();
				//未入住
				/*
				if (!CheckinState.CHECKEDIN.getValue().equals(ownerInfo.getCheckinState())) {
					Map<String, Object> map = getParaMap();
					map.put("houseSn", houseSn);
					CommonServiceBill bill = commonServiceBillDao.getLastBill(map);
					if (bill != null) {
						if (DateUtil.addDays(DateUtil.getCurrent(), 1).getTime() < DateUtil.formatDate(bill.getEndDate(), "yyyyMMdd").getTime()) {
							String remark = bill.getRemark();
							if (remark != null && remark.indexOf("80%") != -1) {
								Double amt = bill.getTotalAmount();
								supPay = MoneyUtil.getFormatStr2(amt * 0.25);
								csBillId = bill.getId();
							}
						}
					}
				}*/
			}
			HouseInfo houseInfo = decorateServiceBillDao.findById(HouseInfo.class, houseSn);
			Assert.notNull(houseInfo, "编号为"+houseSn+"的房屋信息不存在!");
			Double area = houseInfo.getArea();
			areaStr = String.valueOf(area);
			cleanPrice = ParaManager.getCleanPrice();
			cleanAmount =  MoneyUtil.getFormatStr2(Double.parseDouble(cleanPrice) * area);
			floor = houseInfo.getFloor();
			liftFee = ParaManager.getLiftFee(Integer.parseInt(houseInfo.getFloor()));
			totalAmount = MoneyUtil.getFormatStr2(Double.parseDouble(cleanAmount) + Double.parseDouble(liftFee));
		} catch (Exception e) {
			jsonObject.put("error", e.getMessage());
			setResult(false, "失败", request);
			e.printStackTrace();
			respond(response, jsonObject.toString());
			return null;
		}
		jsonObject.put("ownerName", ownerName);
		jsonObject.put("area", areaStr);
		jsonObject.put("cleanPrice", cleanPrice);
		jsonObject.put("cleanAmount", cleanAmount);
		jsonObject.put("floor", floor);
		jsonObject.put("liftFee", liftFee);
		jsonObject.put("amount", totalAmount);
		jsonObject.put("supPay", supPay);
		jsonObject.put("csBillId", csBillId);
		respond(response, jsonObject.toString());
		return null;
	}
	
}
