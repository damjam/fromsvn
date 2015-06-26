package com.ylink.cim.manage.view;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.state.CheckinState;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.manage.dao.GeneralBillDao;
import com.ylink.cim.manage.domain.GeneralBill;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;
/**
 * 通用收款
 * @author libaozhu
 * @date 2015-3-17
 */
@Scope("prototype")
@Component
public class GeneralBillAction extends BaseAction implements ModelDriven<GeneralBill>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private GeneralBillDao generalBillDao;
	@Autowired
	private BillService billService;
	
	private GeneralBill model = new GeneralBill();
	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeGeneralBill(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return list();
	}
	public String deleteBill() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(GeneralBill.class, id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list();
	}
	public String doAdd() throws Exception {
		try {
			GeneralBill bill = new GeneralBill();
			BeanUtils.copyProperties(bill, model);
			billService.saveGeneralBill(bill, getSessionUser(request));
			setResult(true, "数据已保存", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败"+e.getMessage(), request);
			return toAdd();
		}
		return list();
	}
	public GeneralBill getModel() {
		return model;
	}
	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		map.put("startChargeDate", model.getStartChargeDate());
		map.put("endChargeDate", model.getEndChargeDate());
		map.put("state", model.getState());
		map.put("id", model.getId());
		map.put("branchNo", getSessionBranchNo(request));
		map.put("keyword", model.getKeyword());
		Paginater paginater = generalBillDao.findBillPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = generalBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		//return forward("/pages/manage/charge/general/generalBillList.jsp");
		return "list";
	}
	public String toAdd() throws Exception {
		YesNoType.setInReq(request);
		CheckinState.setInReq(request);
		//return forward("/pages/manage/charge/general/generalBillAdd.jsp");
		return "add";
	}
}
