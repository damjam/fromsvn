package com.ylink.cim.manage.view;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
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
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class DecorateServiceBillAction extends BaseAction implements ModelDriven<DecorateServiceBill> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DecorateServiceBillDao decorateServiceBillDao = (DecorateServiceBillDao) getService("decorateServiceBillDao");
	private BillService billService = (BillService) getService("billService");
	private OwnerInfoDao ownerInfoDao = (OwnerInfoDao) getService("ownerInfoDao");

	public String toAdd() throws Exception {
		YesNoType.setInReq(request);
		// return
		// forward("/pages/manage/charge/decorate/decorateServiceBillAdd.jsp");
		return "add";
	}

	public String doAdd() throws Exception {
		try {
			DecorateServiceBill bill = new DecorateServiceBill();
			BeanUtils.copyProperties(bill, model);
			billService.saveDecorateServiceBill(bill, getSessionUser(request));
			String tip = "�����ѱ���";
			if (bill.getSupFee() != null && bill.getSupFee() > 0) {
				tip += ",������ҵ�շ�ģ����ȡ������ҵ��";
			}
			setResult(true, tip, request);
			model.setHouseSn("");
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��" + e.getMessage(), request);
			return toAdd();
		}
		return list();
	}

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		map.put("houseSn", model.getHouseSn());
		map.put("startChargeDate", model.getStartChargeDate());
		map.put("endChargeDate", model.getEndChargeDate());
		map.put("state", model.getState());
		map.put("id", model.getId());
		map.put("year", model.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = decorateServiceBillDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = decorateServiceBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		// return
		// forward("/pages/manage/charge/decorate/decorateServiceBillList.jsp");
		return "list";
	}

	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeDecorateFee(id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			setResult(false, "����ʧ��", request);
		}
		return list();
	}

	public String deleteBill() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(DecorateServiceBill.class, id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
		} catch (Exception e) {
			setResult(false, "ɾ��ʧ��", request);
			e.printStackTrace();
		}
		return list();
	}

	public String getHouseInfo() throws Exception {
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
				// ownerInfo.getCheckinState();
				// δ��ס
				/*
				 * if (!CheckinState.CHECKEDIN.getValue().equals(ownerInfo.
				 * getCheckinState())) { Map<String, Object> map = getParaMap();
				 * map.put("houseSn", houseSn); CommonServiceBill bill =
				 * commonServiceBillDao.getLastBill(map); if (bill != null) { if
				 * (DateUtil.addDays(DateUtil.getCurrent(), 1).getTime() <
				 * DateUtil.formatDate(bill.getEndDate(), "yyyyMMdd").getTime())
				 * { String remark = bill.getRemark(); if (remark != null &&
				 * remark.indexOf("80%") != -1) { Double amt =
				 * bill.getTotalAmount(); supPay = MoneyUtil.getFormatStr2(amt *
				 * 0.25); csBillId = bill.getId(); } } } }
				 */
			}
			HouseInfo houseInfo = decorateServiceBillDao.findById(HouseInfo.class, houseSn);
			Assert.notNull(houseInfo, "���Ϊ" + houseSn + "�ķ�����Ϣ������!");
			Double area = houseInfo.getArea();
			areaStr = String.valueOf(area);
			cleanPrice = ParaManager.getCleanPrice();
			cleanAmount = MoneyUtil.getFormatStr2(Double.parseDouble(cleanPrice) * area);
			floor = houseInfo.getFloor();
			liftFee = ParaManager.getLiftFee(Integer.parseInt(houseInfo.getFloor()));
			totalAmount = MoneyUtil.getFormatStr2(Double.parseDouble(cleanAmount) + Double.parseDouble(liftFee));
		} catch (Exception e) {
			jsonObject.put("error", e.getMessage());
			setResult(false, "ʧ��", request);
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

	@Override
	public DecorateServiceBill getModel() {
		return model;
	}

	private DecorateServiceBill model = new DecorateServiceBill();

}
