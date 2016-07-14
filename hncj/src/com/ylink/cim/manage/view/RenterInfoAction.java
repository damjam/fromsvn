package com.ylink.cim.manage.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.manage.dao.RenterInfoDao;
import com.ylink.cim.manage.domain.RenterInfo;
import com.ylink.cim.manage.service.RenterInfoService;
import com.ylink.cim.util.CopyPropertyUtil;

import flink.util.Paginater;
import flink.web.CRUDAction;
@Scope("prototype")
@Component
public class RenterInfoAction extends CRUDAction implements ModelDriven<RenterInfo> {

	
	private static final long serialVersionUID = 1L;
	@Autowired
	private RenterInfoDao renterInfoDao;
	@Autowired
	private RenterInfoService renterInfoService;
	
	@Override
	public RenterInfo getModel() {
		return model;
	}
	
	private RenterInfo model = new RenterInfo();

	@Override
	public String list() throws Exception {
		Map<String, Object> params = getParaMap();
		Paginater paginater = renterInfoDao.findPaginater(params, getPager(request));
		saveQueryResult(request, paginater);
		return LIST;
	}

	@Override
	public String toAdd() throws Exception {
		return ADD;
	}

	@Override
	public String doAdd() throws Exception {
		try{
			renterInfoService.save(model, getSessionUser(request));
			setSucResult(request);
		}catch (Exception e){
			return ADD;
		}
		return "toMain";
	}

	@Override
	public String toEdit() throws Exception {
		RenterInfo renterInfo = renterInfoDao.findById(model.getId());
		CopyPropertyUtil.copyPropertiesIgnoreNull(renterInfo, model);
		return EDIT;
	}

	@Override
	public String doEdit() throws Exception {
		try{
			renterInfoService.update(model);
			setSucResult(request);
		}catch(Exception e){
			e.printStackTrace();
			return toEdit();
		}
		return "toMain";
	}

	@Override
	public String delete() throws Exception {
		try{
			//只有搬离状态才能删除
			renterInfoService.delete(model.getId());
			setSucResult(request);
		}catch(Exception e){
			setResult(false, e.getMessage(), request);
			return list();
		}
		return "toMain";
	}

	public String checkout() throws Exception {
		try{
			renterInfoService.checkout(model.getId());
			setSucResult(request);
		}catch(Exception e){
			setResult(false, e.getMessage(), request);
			return list();
		}
		return "toMain";
	}
	@Override
	public String detail() throws Exception {
		try {
			renterInfoDao.findById(model.getId());
		} catch (Exception e) {
			
		}
		return DETAIL;
	}
	
}
