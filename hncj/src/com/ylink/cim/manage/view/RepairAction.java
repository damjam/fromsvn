package com.ylink.cim.manage.view;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.RepairState;
import com.ylink.cim.common.type.EmgIndexType;
import com.ylink.cim.common.type.ImpIndexType;
import com.ylink.cim.common.type.RateType;
import com.ylink.cim.common.type.ReporterType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.RepairDao;
import com.ylink.cim.manage.dao.RepairTrackDao;
import com.ylink.cim.manage.domain.Repair;
import com.ylink.cim.manage.domain.RepairTrack;
import com.ylink.cim.manage.service.RepairService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;
@Scope("prototype")
@Component
public class RepairAction extends BaseAction implements ModelDriven<Repair> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private RepairService repairService;
	@Autowired
	private RepairDao repairDao;
	@Autowired
	private RepairTrackDao repairTrackDao;
	@Autowired
	@Override
	public Repair getModel() {
		return model;
	}
	private Repair model = new Repair();

	public String list() throws Exception {
		initSelect();
		Map<String, Object> map = getParaMap();
		map.put("startCreateDate", model.getStartCreateDate());
		map.put("endCreateDate", model.getEndCreateDate());
		map.put("state", model.getState());
		Paginater paginater = repairDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		
		return "list";
	}
	private void initSelect() {
		RepairState.setInReq(request);
		ReporterType.setInReq(request);
		ImpIndexType.setInReq(request);
		EmgIndexType.setInReq(request);
		RateType.setInReq(request);
		request.setAttribute("branches", ParaManager.getBranches(true));
	}
	public String toAdd() throws Exception {
		initSelect();
		return "add";
	}
	public String toAddTrack() throws Exception {
		initSelect();
		return "repairTrackAdd";
	}
	public String doAddTrack() throws Exception {
		try{
			repairService.addTrack(model, getSessionUser(request));
			setSucResult("操作成功", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toAddTrack();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败", request);
			return toAddTrack();
		}
		return "toMain";
	}
	public String showTrack() throws Exception {
		List<RepairTrack> list = repairTrackDao.findList(model.getId());
		saveQueryResult(request, list);
		return "repairTrackList";
	}
	public String detail() throws Exception {
		Repair repair = repairDao.findById(model.getId());
		BeanUtils.copyProperties(model, repair);
		List<RepairTrack> list = repairTrackDao.findList(model.getId());
		saveQueryResult(request, list);
		return "detail";
	}
	public String doAdd() throws Exception {
		try {
			repairService.save(model, getSessionUser(request));
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
	

	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			repairService.delete(id);
			setSucResult("操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return "toMain";
	}
	public String cancel() throws Exception {
		try {
			String id = request.getParameter("id");
			repairService.cancel(id, getSessionUser(request));
			setSucResult("操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
			return list();
		}
		return "toMain";
	}
	
}
