package com.ylink.cim.manage.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.OrderDetailDao;
import com.ylink.cim.manage.domain.OrderDetail;
import com.ylink.cim.manage.service.OrderDetailService;

import flink.util.Paginater;
import flink.web.CRUDAction;
@Scope("prototype")
@Component
public class OrderDetailAction extends CRUDAction implements ModelDriven<OrderDetail> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Autowired
	private OrderDetailService orderDetailService;
	
	public OrderDetail getModel() {
		return model;
	}

	private OrderDetail model = new OrderDetail();

	@Override
	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		Paginater paginater = orderDetailDao.findPaginater(map, getPager(request));
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
			orderDetailService.save(model);
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
			orderDetailDao.deleteById(model.getId());
		}catch (Exception e) {
			
		}
		return "toMain";
	}

	@Override
	public String detail() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
