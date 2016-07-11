package com.ylink.cim.manage.view;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.OrderDetailDao;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.OrderDetail;
import com.ylink.cim.manage.domain.OrderRecord;
import com.ylink.cim.manage.service.OrderRecordService;

import flink.etc.BizException;
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
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	public OrderRecord getModel() {
		return model;
	}

	private OrderRecord model = new OrderRecord();

	@Override
	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("clientName", model.getClientName());
		map.put("clientTel", model.getClientTel());
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
			orderRecordService.save(model, getSessionUser(request));
			setSucResult(request);
		}catch(Exception e){
			e.printStackTrace();
			setResult(false, "操作失败", request);
			return toAdd();
		}
		return "toMain";
	}

	@Override
	public String toEdit() throws Exception {
		try{
			boolean flag = orderRecordService.canEdit(model.getId());
			if (!flag) {
				throw new BizException("存在已发货的商品明细，订单不可更改");
			}
			OrderRecord orderRecord = orderRecordDao.findById(model.getId());
			BeanUtils.copyProperties(model, orderRecord);
			Map<String, Object> map = getParaMap();
			map.put("orderId", orderRecord.getId());
			List<OrderDetail> details = orderDetailDao.findList(map);
			saveQueryResult(request, details);
		} catch(Exception e){
			setResult(false, e.getMessage(), request);
			return list();
		}
		return EDIT;
	}

	@Override
	public String doEdit() throws Exception {
		try{
			orderRecordService.update(model, getSessionUser(request));
			setSucResult(request);
		}catch(Exception e){
			e.printStackTrace();
			setResult(false, "操作失败", request);
			return toAdd();
		}
		return "toMain";
	}

	public String changeState() throws Exception {
		try{
			String state = model.getState();
			String id = model.getId();
			orderRecordService.updateState(id, state);
			setSucResult(request);
		}catch(Exception e){
			e.printStackTrace();
			setResult(false, "操作失败", request);
			return toAdd();
		}
		return "toMain";
	}
	@Override
	public String delete() throws Exception {
		try{
			orderRecordService.delete(model.getId());
			setSucResult(request);
		}catch(Exception e){
			setFailResult("操作失败", request);
		}
		return "toMain";
	}

	public String showDetail() throws Exception {
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
	@Override
	public String detail() throws Exception {
		return null;
	}
	
}
