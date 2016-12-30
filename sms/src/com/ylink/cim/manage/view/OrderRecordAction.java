package com.ylink.cim.manage.view;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.DeliveryState;
import com.ylink.cim.common.state.OrderState;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.common.util.SendMsgUtilAlidayu;
import com.ylink.cim.manage.dao.OrderDetailDao;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.OrderDetail;
import com.ylink.cim.manage.domain.OrderRecord;
import com.ylink.cim.manage.service.OrderRecordService;

import flink.etc.Assert;
import flink.util.DateUtil;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.CRUDAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
		map.put("payState", model.getPayState());
		map.put("beginOrderDate", model.getBeginOrderDate());
		map.put("endOrderDate", model.getEndOrderDate());
		if (isHQ()) {//�ܲ�
			map.put("branchNo", model.getBranchNo());
		}else {//����
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = orderRecordDao.findPaginater(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = orderRecordDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		initSelect();
		return LIST;
	}
	
	
	private void initSelect() {
		request.setAttribute("countryTypes", ParaManager.getSysDict(SysDictType.CoutryType.getValue()));
		
	}

	@Override
	public String toAdd() throws Exception {
		initSelect();
		model.setOrderDate(DateUtil.getCurrentDate());
		return ADD;
	}

	@Override
	public String doAdd() throws Exception {
		try{
			orderRecordService.save(model, getSessionUser(request));
			setSucResult(request);
		}catch(Exception e){
			e.printStackTrace();
			setResult(false, "����ʧ��", request);
			return toAdd();
		}
		return "toMain";
	}

	@Override
	public String toEdit() throws Exception {
		try{
			OrderRecord orderRecord = orderRecordDao.findById(model.getId());
			Assert.equals(orderRecord.getState(), OrderState.INIT.getValue(), "�����޸�״̬Ϊ�����еĶ���");
			BeanUtils.copyProperties(model, orderRecord);
			Map<String, Object> map = getParaMap();
			map.put("orderId", orderRecord.getId());
			map.put("excludeState", DeliveryState.CANCELED.getValue());
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
			setResult(false, "����ʧ��", request);
			return toAdd();
		}
		return "toMain";
	}
	//�տ�ȷ��
	public String pay() throws Exception {
		try{
			orderRecordService.pay(model.getId(), getSessionUser(request));
			setSucResult(request);
		}catch(Exception e){
			e.printStackTrace();
			setResult(false, "����ʧ��", request);
			return list();
		}
		return list();
	}
	public String changeState() throws Exception {
		try{
			String state = model.getState();
			String id = model.getId();
			orderRecordService.updateState(id, state);
			setSucResult(request);
		}catch(Exception e){
			e.printStackTrace();
			setResult(false, "����ʧ��", request);
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
			setFailResult("����ʧ��", request);
		}
		return "toMain";
	}
	public String cancel() throws Exception {
		try{
			orderRecordService.cancel(model.getId());
			setSucResult(request);
		}catch(Exception e){
			setFailResult("����ʧ��", request);
		}
		return "toMain";
	}
	
	public String loadMaterials() throws Exception {
		JSONObject object = new JSONObject();
		try{
			JSONArray array = new JSONArray();
			String keyword = request.getParameter("keyword");
			String productName = request.getParameter("productName");
			Map<String, String> map = null;
			if(StringUtils.contains(productName, "�ŵ�")){
				map = ParaManager.getSysDict(SysDictType.FMaterialType.getValue());
			} else if (StringUtils.contains(productName, "�����")) {
				map = ParaManager.getSysDict(SysDictType.BMaterialType.getValue());
			}
			for (Map.Entry<String, String> entry : map.entrySet()) {
				array.add(entry.getKey());
			}
			object.put("status", "1");
			object.put("list", array);
			respond(response, object.toString());
		}catch (Exception e) {
			object.put("status", "0");
		}
		respond(response, object.toString());
		return null;
	}
	public String loadColors() throws Exception {
		JSONObject object = new JSONObject();
		try{
			JSONArray array = new JSONArray();
			String keyword = request.getParameter("keyword");
			String productName = request.getParameter("productName");
			Map<String, String> map = null;
			if(StringUtils.contains(productName, "�ŵ�")){
				map = ParaManager.getSysDict(SysDictType.FColorType.getValue());
			} else if (StringUtils.contains(productName, "�����")) {
				map = ParaManager.getSysDict(SysDictType.BColorType.getValue());
			}
			for (Map.Entry<String, String> entry : map.entrySet()) {
				array.add(entry.getKey());
			}
			object.put("status", "1");
			object.put("list", array);
			respond(response, object.toString());
		}catch (Exception e) {
			object.put("status", "0");
		}
		respond(response, object.toString());
		return null;
	}
	@Override
	public String detail() throws Exception {
		return null;
	}
	//������
	public String sendTextMsg() {
		JSONObject object = new JSONObject();
		try{
			String id = model.getId();
			OrderRecord orderRecord = orderRecordDao.findById(id);
			String clientTel = orderRecord.getClientTel();
			Map<String, Object> map = getParaMap();
			map.put("orderId", id);
			List<OrderDetail> list = orderDetailDao.findList(map);
			StringBuilder builder = new StringBuilder();
			for (int i = 0,size=list.size(); i < size; i++) {
				OrderDetail orderDetail = list.get(i);
				builder.append(orderDetail.getProductName());
				if(i != size-1){
					builder.append(",");
				}
			}
			String template = "SMS_13785268";
			String productNames = builder.toString();
			String expressCom = "������ͨ";
			String expressNo = "28347838743";
			String comTel = " 18303952238";
			String param = MsgUtils.r("\"a\":\"{?}\",\"b\":\"{?}\",\"c\":\"{?}\",\"d\":\"{?}\"", productNames, expressCom, expressNo, comTel);
			param = "{"+param+"}";
			SendMsgUtilAlidayu.sendNotice(template, param, clientTel);
			object.put("state", "1");
			
		}catch(Exception e){
			object.put("state", "0");
		}
		respond(response, object.toString());
		return null;
	}
}
