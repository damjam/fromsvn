package com.ylink.cim.manage.view;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.manage.dao.DepositBillDao;
import com.ylink.cim.manage.domain.DepositBill;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;

/**
 * 收押金
 * 
 * @author libaozhu
 * @date 2015-3-16
 */
@Scope("prototype")
@Component
public class DepositBillAction extends BaseAction implements ModelDriven<DepositBill> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DepositBillDao depositBillDao = (DepositBillDao) getService("depositBillDao");
	private BillService billService = (BillService) getService("billService");

	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeDepositFee(id, getSessionUser(request));
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

	public String deleteDepositBill() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.deleteBill(DepositBill.class, id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list();
	}

	public String doAdd() throws Exception {
		try {

			DepositBill depositBill = new DepositBill();
			BeanUtils.copyProperties(depositBill, model);
			billService.saveDepositBill(depositBill, getSessionUser(request));
			setResult(true, "数据已保存", request);
			model.setHouseSn("");
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败" + e.getMessage(), request);
			return toAdd();
		}
		return list();
	}

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();

		map.put("houseSn", model.getHouseSn());
		map.put("state", model.getState());
		map.put("startDepositDate", model.getStartDepositDate());
		map.put("endDepositDate", model.getEndDepositDate());
		map.put("startRefundDate", model.getStartRefundDate());
		map.put("endRefundDate", model.getEndRefundDate());
		map.put("id", model.getId());
		map.put("year", model.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = depositBillDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = depositBillDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		// return forward("/pages/manage/charge/deposit/depositBillList.jsp");
		return "list";
	}

	public String refund() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.refund(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			e.printStackTrace();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
		}
		return list();
	}

	public String toAdd() throws Exception {
		YesNoType.setInReq(request);
		// return forward("/pages/manage/charge/deposit/depositBillAdd.jsp");
		return "add";
	}

	@Override
	public DepositBill getModel() {
		return model;
	}

	private DepositBill model = new DepositBill();
}
