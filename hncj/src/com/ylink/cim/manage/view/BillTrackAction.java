package com.ylink.cim.manage.view;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.busioper.dao.BillTrackDao;
import com.ylink.cim.busioper.service.BillTrackService;
import com.ylink.cim.common.state.BillTrackState;
import com.ylink.cim.common.type.BillType;
import com.ylink.cim.common.type.RemainDays;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.domain.BillTrack;

import flink.etc.BizException;
import flink.util.LogUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class BillTrackAction extends BaseAction implements
		ModelDriven<BillTrack> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private BillTrackDao billTrackDao;
	@Autowired
	private BillTrackService billTrackService;

	public String toAdd() throws Exception {
		initSelect(request);
		// return forward("/pages/manage/track/trackInfoAdd.jsp");
		return "add";
	}

	public String doAdd() throws Exception {
		try {

			BillTrack billTrack = new BillTrack();
			BeanUtils.copyProperties(billTrack, model);
			billTrackService.add(billTrack, getSessionUser(request));
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

		map.put("houseSn", model.getHouseSn());
		map.put("billId", model.getBillId());
		map.put("ownerName", model.getOwnerName());
		map.put("ownerCel", model.getOwnerCel());
		map.put("billType", model.getBillType());
		map.put("leftDays", model.getLeftDays());
		map.put("branchNo", getSessionBranchNo(request));
		map.put("billType", model.getBillType());
		map.put("state", BillTrackState.VALID.getValue());
		Paginater paginater = billTrackDao
				.findPaginater(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect(request);
		// return forward("/pages/manage/track/billTrackList.jsp");
		return "list";
	}

	public String sendNotice() throws Exception {
		try {
			String id = request.getParameter("id");
			billTrackService.sendNotice(id);
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
			billTrackService.delete(id, getSessionUser(request));
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

	public String discard() throws Exception {
		try {
			String id = request.getParameter("id");
			billTrackService.discard(id, getSessionUser(request));
			String msg = MsgUtils.r("废弃账单到期提醒,记录id为{?}", id);
			logUserOperate(request, UserLogType.DELETE.getValue(),
					StringUtils.abbreviate(msg, 100));
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

	public void initSelect(HttpServletRequest request) throws Exception {
		Map<String, String> buildingNos = new LinkedHashMap<String, String>();
		Map<String, String> unitsNos = new LinkedHashMap<String, String>();
		Map<String, String> floors = new LinkedHashMap<String, String>();
		for (int i = 1; i < 4; i++) {
			unitsNos.put(String.valueOf(i), String.valueOf(i));
		}
		Map<String, String> rent = ParaManager.getSysDict(SysDictType.RentType
				.getValue());
		Map<String, String> economical = ParaManager
				.getSysDict(SysDictType.EconomicalType.getValue());

		buildingNos.putAll(economical);
		buildingNos.putAll(rent);
		// buildingNos
		for (int i = 1; i < 13; i++) {
			floors.put(String.valueOf(i), String.valueOf(i));
		}
		request.setAttribute("unitNos", unitsNos);
		request.setAttribute("buildingNos", buildingNos);
		request.setAttribute("floors", floors);
		BillType.setInReq(request);
		RemainDays.setInReq(request);
	}

	@Override
	public BillTrack getModel() {
		return model;
	}

	private BillTrack model = new BillTrack();
}
