package com.ylink.cim.manage.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.OrderRecord;
import com.ylink.cim.manage.service.OrderRecordService;

import flink.util.Paginater;
import flink.web.CRUDAction;
@Scope("prototype")
@Component
public class OrderRecordAction extends CRUDAction implements ModelDriven<OrderRecord> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private OrderRecordDao orderRecordDao;
	@Autowired
	private OrderRecordService orderRecordService;
	
	public OrderRecord getModel() {
		return model;
	}

	private OrderRecord model = new OrderRecord();

	@Override
	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		Paginater paginater = orderRecordDao.findPaginater(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect();
		return LIST;
	}

	private void initSelect() {
		request.setAttribute("countryTypes", ParaManager.getSysDict(SysDictType.CoutryType.getValue()));
		
	}

	@Override
	public String toAdd() throws Exception {
		initSelect();
		return ADD;
	}

	@Override
	public String doAdd() throws Exception {
		try{
			orderRecordService.save(model);
			setSucResult(request);
		}catch(Exception e){
			setFailResult("²Ù×÷Ê§°Ü", request);
		}
		return "toMain";
	}

	@Override
	public String toEdit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String doEdit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		try{
			orderRecordService.delete(model.getId());
			setSucResult(request);
		}catch(Exception e){
			setFailResult("²Ù×÷Ê§°Ü", request);
		}
		return "toMain";
	}

	@Override
	public String detail() throws Exception {
		return null;
	}
}
