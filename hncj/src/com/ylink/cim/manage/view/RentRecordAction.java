package com.ylink.cim.manage.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.manage.dao.RentRecordDao;
import com.ylink.cim.manage.domain.RentRecord;
import com.ylink.cim.manage.service.RentRecordService;
import com.ylink.cim.util.CopyPropertyUtil;

import flink.util.Paginater;
import flink.web.CRUDAction;
@Scope("prototype")
@Component
public class RentRecordAction extends CRUDAction implements ModelDriven<RentRecord> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RentRecordDao rentRecordDao;
	@Autowired
	private RentRecordService rentRecordService;
	@Override
	public RentRecord getModel() {
		return model;
	}
	
	private RentRecord model = new RentRecord();

	@Override
	public String list() throws Exception {
		Map<String, Object> params = getParaMap();
		params.put("renterId", model.getRenterId());
		Paginater paginater = rentRecordDao.findPaginater(params, getPager(request));
		saveQueryResult(request, paginater);
		return LIST;
	}

	@Override
	public String toAdd() throws Exception {
		return ADD;
	}

	@Override
	public String doAdd() throws Exception {
		try {
			rentRecordService.save(model, getSessionUser(request));
			setSucResult(request);
		} catch (Exception e) {
			e.printStackTrace();
			return toAdd();
		}
		return "toMain";
	}

	@Override
	public String toEdit() throws Exception {
		RentRecord rentRecord = rentRecordDao.findById(model.getId());
		CopyPropertyUtil.copyPropertiesIgnoreNull(rentRecord, model);
		return EDIT;
	}

	@Override
	public String doEdit() throws Exception {
		try{
			rentRecordService.update(model);
			setSucResult(request);
		}catch(Exception e){
			setResult(false, e.getMessage(), request);
			return toEdit();
		}
		return "toMain";
	}

	@Override
	public String delete() throws Exception {
		try {
			rentRecordDao.deleteById(model.getId());
			setSucResult(request);
		} catch (Exception e) {
			setResult(false, e.getMessage(), request);
			return list();
		}
		return "toMain";
	}

	@Override
	public String detail() throws Exception {
		return null;
	}
	
}
