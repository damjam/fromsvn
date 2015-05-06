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
public class ParkingInfoAction extends BaseAction implements ModelDriven<ParkingInfo>{
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
		//return forward("/pages/manage/parking/parkingInfoEdit.jsp");
		return "edit";
	}
	
	public String toAdd() throws Exception {
		ParkingState.setInReq(request);
		//return forward("/pages/manage/parking/parkingInfoAdd.jsp");
		return "add";
	}
	public String doAdd(
			) throws Exception {
		try {
			
			Map<String, Object> params = getParaMap();
			params.put("sn", model.getSn());
			params.put("branchNo", getSessionBranchNo(request));
			Assert.isEmpty(parkingInfoDao.findBy(params), "车位号已存在，请重新指定");
			ParkingInfo parkingInfo = new ParkingInfo();
			BeanUtils.copyProperties(parkingInfo, model);
			parkingInfoService.save(parkingInfo, getSessionUser(request));
			clearForm();
		}catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toAdd();
		}
		
		return list();
	}
	
	private void clearForm() {
		model.setOwnerName("");
		model.setOwnerCel("");
		model.setEndUser("");
		model.setEndUserCel("");
		model.setState("");
	}

	public String doEdit(
			) throws Exception {
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
			setResult(true, "添加成功", request);
			clearForm();
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			setResult(false, "添加失败", request);
			e.printStackTrace();
			return toEdit();
		}
		return list();
	}

	public String list() throws Exception {
		ParkingState.setInReq(request);
		Map<String, Object> map = getParaMap();
		
		map.put("carSn", model.getCarSn());
		map.put("ownerName", model.getOwnerName());
		map.put("ownerCel", model.getOwnerCel());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = parkingInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		//return forward("/pages/manage/parking/parkingInfoList.jsp");
		return "list";
	}

	public String delete(
			) throws Exception {
		try {
			String id = request.getParameter("id");
			parkingInfoService.delete(id, getSessionUser(request));
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

	public String queryPopUpParkingInfo() throws Exception {
		try {
			Map<String, Object> params = getParaMap();
			params.put("branchNo", getSessionBranchNo(request));
			params.put("avai", true);
			Paginater paginater = this.parkingInfoDao.findPager(params, getPager(request));
			saveQueryResult(request, paginater);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return forward("/pages/popUp/popUpParkingInfo.jsp");
		return "info";
	}

	public ParkingInfo getModel() {
		return model;
	}
	private ParkingInfo model = new ParkingInfo();
}
