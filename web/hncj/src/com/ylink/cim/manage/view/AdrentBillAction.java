package com.ylink.cim.manage.view;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.manage.dao.AdrentBillDao;
import com.ylink.cim.manage.domain.AdrentBill;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class AdrentBillAction extends BaseAction implements ModelDriven<AdrentBill> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private AdrentBillDao adrentBillDao;
	@Autowired
	private BillService billService;

	public String toAdd() throws Exception {
		// return forward("/pages/manage/charge/adrent/adrentBillAdd.jsp");
		return "add";
	}

	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeAdRent(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list();
	}

	public String doAdd() throws Exception {
		try {

			AdrentBill adRent = new AdrentBill();
			BeanUtils.copyProperties(adRent, model);
			billService.saveAdRentBill(adRent, getSessionUser(request));
			setResult(true, "添加成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd();
		}
		return list();
	}

	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("branchNo", getSessionBranchNo(request));

		map.put("id", model.getId());
		map.put("state", model.getState());
		map.put("startChargeDate", model.getStartChargeDate());
		map.put("endChargeDate", model.getEndChargeDate());
		Paginater paginater = adrentBillDao.findBillPager(map, getPager(request));
		saveQueryResult(request, paginater);
		BillState.setInReq(request);
		Map<String, Object> sumInfo = adrentBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		// return forward("/pages/manage/charge/adrent/adrentBillList.jsp");
		return "list";
	}

	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(AdrentBill.class, id, getSessionUser(request));
			setResult(true, "操作成功", request);

			model.setId("");
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list();
	}

	public AdrentBill getModel() {
		return model;
	}

	private AdrentBill model = new AdrentBill();
}
