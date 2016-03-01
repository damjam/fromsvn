package com.ylink.cim.manage.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.manage.dao.RepairDao;
import com.ylink.cim.manage.domain.Repair;
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
	
	@Override
	public Repair getModel() {
		return model;
	}
	private Repair model = new Repair();

	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("startCreateDate", model.getStartCreateDate());
		map.put("endCreateDate", model.getEndCreateDate());
		map.put("state", model.getState());
		Paginater paginater = repairDao.findPager(map, getPager(request));
		for(int i=0; i<paginater.getList().size(); i++){
			
		}
		return "list";
	}
	public String toAdd() throws Exception {
		return "add";
	}
	public String toAddTrack() throws Exception {
		return "track";
	}
	public String doAddTrack() throws Exception {
		return "track";
	}
	public String showTrack() throws Exception {
		return "trackList";
	}
	public String detail() throws Exception {
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
	
	
}
