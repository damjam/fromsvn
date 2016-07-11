package com.ylink.cim.manage.view;

import java.util.List;
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
import net.sf.json.JSONObject;
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
			setFailResult("操作失败", request);
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

	public String changeState() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	//发货
	public String sendOut() throws Exception {
		orderDetailService.sendOut(model.getId());
		return null;
	}
	public String refund() throws Exception {
		
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
	//取消订单库存不变
	public String cancel() throws Exception {
		try{
			orderDetailService.cancel(model.getId(), model.getRefundAmt());
		}catch (Exception e) {
			
		}
		return "details";
	}
	//退货,退货后库存增加
	public String returnGoods() throws Exception {
		try{
			orderDetailService.returnGoods(model.getId(), model.getRefundAmt());
			
		}catch (Exception e) {
			
		}
		return "details";
	}
	
	
	
	@Override
	public String detail() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public String detailList() throws Exception {
		try{
			Map<String, Object> map = getParaMap();
			map.put("orderId", request.getParameter("id"));
			List<OrderDetail> list = orderDetailDao.findList(map);
			saveQueryResult(request, list);
		}catch(Exception e){
			setFailResult("操作失败", request);
		}
		return "detailList";
	}
}
