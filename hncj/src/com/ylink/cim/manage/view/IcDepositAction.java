package com.ylink.cim.manage.view;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.IcCardType;
import com.ylink.cim.manage.dao.IcDepositDao;
import com.ylink.cim.manage.domain.IcDeposit;
import com.ylink.cim.manage.service.BillService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;

/**
 * IC����ֵ
 * 
 * @author libaozhu
 * @date 2015-3-16
 */
@Scope("prototype")
@Component
public class IcDepositAction extends BaseAction implements
		ModelDriven<IcDeposit> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IcDepositDao icDepositDao;
	@Autowired
	private BillService billService;

	public String toAdd() throws Exception {
		IcCardType.setInReq(request);
		// return forward("/pages/manage/charge/prestore/icDepositAdd.jsp");
		return "add";
	}

	public String doAdd() throws Exception {
		try {
			IcDeposit icDeposit = new IcDeposit();
			BeanUtils.copyProperties(icDeposit, model);
			billService.saveIcDeposit(icDeposit, getSessionUser(request));
			setResult(true, "�����ѱ���", request);
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
		map.put("state", model.getState());
		map.put("startChargeDate", model.getStartChargeDate());
		map.put("endChargeDate", model.getEndChargeDate());
		map.put("id", model.getId());
		map.put("year", model.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = icDepositDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = icDepositDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		// return forward("/pages/manage/charge/prestore/icDepositList.jsp");
		return "list";
	}

	public String charge() throws Exception {
		try {
			String id = request.getParameter("id");
			billService.chargeDepositFee(id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
			model.setId("");
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��", request);
		}
		return list();
	}

	public String deleteIcDeposit() throws Exception {
		try {
			String id = request.getParameter("id");
			billService
					.deleteBill(IcDeposit.class, id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
			model.setId("");
		} catch (Exception e) {
			setResult(false, "ɾ��ʧ��", request);
			e.printStackTrace();
		}
		return list();
	}

	@Override
	public IcDeposit getModel() {
		return model;
	}

	private IcDeposit model = new IcDeposit();
}
