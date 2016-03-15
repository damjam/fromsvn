package com.ylink.cim.manage.view;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.ParkingState;
import com.ylink.cim.manage.dao.ParkingInfoDao;
import com.ylink.cim.manage.domain.ParkingInfo;
import com.ylink.cim.manage.service.ParkingInfoService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class ParkingInfoAction extends BaseAction implements
		ModelDriven<ParkingInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ParkingInfoDao parkingInfoDao;
	@Autowired
	private ParkingInfoService parkingInfoService;

	public String toEdit() throws Exception {
		String id = model.getId();
		ParkingInfo parkingInfo = parkingInfoDao.findById(id);
		BeanUtils.copyProperties(model, parkingInfo);
		ParkingState.setInReq(request);
		// return forward("/pages/manage/parking/parkingInfoEdit.jsp");
		return "edit";
	}

	public String toAdd() throws Exception {
		ParkingState.setInReq(request);
		// return forward("/pages/manage/parking/parkingInfoAdd.jsp");
		return "add";
	}

	public String doAdd() throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("sn", model.getSn());
			params.put("branchNo", getSessionBranchNo(request));
			Assert.isEmpty(parkingInfoDao.findBy(params), "��λ���Ѵ��ڣ�������ָ��");
			ParkingInfo parkingInfo = new ParkingInfo();
			BeanUtils.copyProperties(parkingInfo, model);
			parkingInfoService.save(parkingInfo, getSessionUser(request));
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			setResult(false, "���ʧ��", request);
			e.printStackTrace();
			return toAdd();
		}

		return "toMain";
	}

	public String doEdit() throws Exception {
		try {

			ParkingInfo parkingInfo = parkingInfoDao.findById(model.getCarSn());
			String createUser = parkingInfo.getCreateUser();
			Date createDate = parkingInfo.getCreateDate();
			String branchNo = parkingInfo.getBranchNo();
			BeanUtils.copyProperties(parkingInfo, model);
			parkingInfo.setCreateDate(createDate);
			parkingInfo.setCreateUser(createUser);
			parkingInfo.setBranchNo(branchNo);
			parkingInfoService.update(parkingInfo, getSessionUser(request));
			setSucResult("��ӳɹ�", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			setResult(false, "���ʧ��", request);
			e.printStackTrace();
			return toEdit();
		}
		return list();
	}

	public String list() throws Exception {
		ParkingState.setInReq(request);
		Map<String, Object> map = getParaMap();
		map.put("sn", model.getSn());
		map.put("carSn", model.getCarSn());
		map.put("ownerName", model.getOwnerName());
		map.put("ownerCel", model.getOwnerCel());
		map.put("endUser", model.getOwnerName());
		map.put("endUserCel", model.getOwnerCel());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = parkingInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return "list";
	}

	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			parkingInfoService.delete(id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��", request);
		}
		return list();
	}

	public String queryPopUpParkingInfo() throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			params.put("avai", true);
			Paginater paginater = this.parkingInfoDao.findPager(params,
					getPager(request));
			saveQueryResult(request, paginater);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return forward("/pages/popUp/popUpParkingInfo.jsp");
		return "popUp";
	}

	@Override
	public ParkingInfo getModel() {
		return model;
	}

	private ParkingInfo model = new ParkingInfo();
}
