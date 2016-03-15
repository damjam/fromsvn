package com.ylink.cim.manage.view;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.manage.dao.HouseRentBillDao;
import com.ylink.cim.manage.dao.MerchantInfoDao;
import com.ylink.cim.manage.domain.HouseRentBill;
import com.ylink.cim.manage.domain.MerchantInfo;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;
@Scope("prototype")
@Component
public class HouseRentBillAction extends BaseAction implements ModelDriven<HouseRentBill> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private HouseRentBillDao houseRentBillDao;
	@Autowired
	private BillService billService;
	@Autowired
	private MerchantInfoDao merchantInfoDao;
	@Override
	public HouseRentBill getModel() {
		return model;
	}
	private HouseRentBill model = new HouseRentBill();

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		map.put("startChargeDate", model.getStartChargeDate());
		map.put("endChargeDate", model.getEndChargeDate());
		map.put("state", model.getState());
		map.put("id", model.getId());
		Paginater paginater = houseRentBillDao.findPager(map, getPager(request));
		for(int i=0; i<paginater.getList().size(); i++){
			HouseRentBill bill = (HouseRentBill)paginater.getList().get(i);
			String merchantNo = bill.getMerchantNo();
			if (StringUtils.isNotEmpty(merchantNo)) {
				MerchantInfo merchantInfo = merchantInfoDao.findById(merchantNo);
				bill.setMerchantName(merchantInfo.getMrname());
			}
		}
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = houseRentBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		return "list";
	}
	public String toAdd() throws Exception {
		return "add";
	}
	public String doAdd() throws Exception {
		try {
			HouseRentBill bill = new HouseRentBill();
			BeanUtils.copyProperties(bill, model);
			billService.saveHouseRentBill(bill, getSessionUser(request));
			setSucResult("数据已保存", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败" + e.getMessage(), request);
			return toAdd();
		}
		return "toMain";
	}
	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeHouseRent(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list();
	}

	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(HouseRentBill.class, id,
					getSessionUser(request));
			setSucResult("操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return "toMain";
	}
	
}
