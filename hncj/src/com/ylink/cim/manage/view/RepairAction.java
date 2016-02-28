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
	public String doAdd() throws Exception {
		try {
			repairService.save(model, getSessionUser(request));
			setSucResult("�����ѱ���", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��" + e.getMessage(), request);
			return toAdd();
		}
		return "toMain";
	}
	

	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			repairService.delete(id);
			setSucResult("�����ɹ�", request);
		} catch (Exception e) {
			setResult(false, "ɾ��ʧ��", request);
			e.printStackTrace();
		}
		return "toMain";
	}
	
}
