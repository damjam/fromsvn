package com.ylink.cim.manage.view;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
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
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class ParkingBillAction extends BaseAction implements
		ModelDriven<ParkingBill> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ParkingBillDao parkingBillDao;
	@Autowired
	private BillService billService;
	@Autowired
	private OwnerInfoDao ownerInfoDao;

	public String toAdd() throws Exception {
		YesNoType.setInReq(request);
		// return forward("/pages/manage/charge/parking/parkingBillAdd.jsp");
		return "add";
	}

	public String doAdd() throws Exception {
		try {

			ParkingBill parkingBill = new ParkingBill();
			BeanUtils.copyProperties(parkingBill, model);
			billService.saveParkingBill(parkingBill, getSessionUser(request));
			model.setHouseSn("");
			model.setCarSn("");
			model.setParkingSn("");
			setResult(true, "数据已保存", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			setResult(false, "保存失败" + e.getMessage(), request);
			return toAdd();
		}
		return list();
	}

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		map.put("houseSn", model.getHouseSn());
		map.put("carSn", model.getCarSn());
		map.put("parkingSn", model.getParkingSn());
		map.put("id", model.getId());
		map.put("year", model.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = parkingBillDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = parkingBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		// return forward("/pages/manage/charge/parking/parkingBillList.jsp");
		return "list";
	}

	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeParkingFee(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			setResult(false, "操作失败", request);
		}
		return list();
	}

	public String deleteBill() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(ParkingBill.class, id,
					getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list();
	}

	public String getOwnerName() throws Exception {
		JSONObject jsonObject = new JSONObject();
		String ownerName = "";
		String mobile = "";
		try {
			String houseSn = request.getParameter("houseSn");
			Assert.notNull(parkingBillDao.findById(HouseInfo.class, houseSn),
					"编号为" + houseSn + "的房屋信息不存在!");
			OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(houseSn);
			if (ownerInfo != null) {
				ownerName = ownerInfo.getOwnerName();
				mobile = ownerInfo.getMobile();
			}
			// return null;
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

	public String getOwnerInfo() throws Exception {
		JSONObject jsonObject = new JSONObject();
		String ownerName = "";
		String mobile = "";
		try {
			String houseSn = request.getParameter("houseSn");
			// Assert.notNull(parkingBillDao.findById(HouseInfo.class, houseSn),
			// "编号为" + houseSn + "的房屋信息不存在!");
			OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(houseSn);
			if (ownerInfo != null) {
				ownerName = ownerInfo.getOwnerName();
				mobile = ownerInfo.getMobile();
			}
			// return null;
		} catch (Exception e) {
			jsonObject.put("ownerName", ownerName);
			jsonObject.put("mobile", mobile);
			respond(response, jsonObject.toString());
			return null;
		}
		jsonObject.put("ownerName", ownerName);
		jsonObject.put("mobile", mobile);
		respond(response, jsonObject.toString());
		return null;
	}

	public String getAcctInfo() throws Exception {
		JSONObject jsonObject = new JSONObject();
		String endDateStr = "";
		Double amount = 0d;
		try {
			String startDate = request.getParameter("beginDate");
			String monthNumStr = request.getParameter("monthNum");
			String priceStr = request.getParameter("price");
			Integer monthNum = Integer.parseInt(monthNumStr);
			Date endDate = DateUtil.addMonths(
					DateUtil.getDateByYYYMMDD(startDate), monthNum);
			endDate = DateUtil.addDays(endDate, -1);
			endDateStr = DateUtil.getDateYYYYMMDD(endDate);
			// amount =
			// Double.parseDouble(priceStr)*Integer.parseInt(monthNumStr);
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

	@Override
	public ParkingBill getModel() {
		return model;
	}

	private ParkingBill model = new ParkingBill();

}
