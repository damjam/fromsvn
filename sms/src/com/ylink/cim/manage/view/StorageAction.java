package com.ylink.cim.manage.view;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.dao.StorageDao;
import com.ylink.cim.manage.dao.StorageJournalDao;
import com.ylink.cim.manage.domain.Storage;
import com.ylink.cim.manage.service.StorageService;

import flink.util.Paginater;
import flink.web.CRUDAction;
@Scope("prototype")
@Component
public class StorageAction extends CRUDAction implements ModelDriven<Storage> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private StorageDao storageDao;
	@Autowired
	private StorageService storageService;
	
	@Override
	public Storage getModel() {
		return model;
	}

	private Storage model = new Storage();

	@Override
	public String list() throws Exception {
		Map<String, Object> params = getParaMap();
		params.put("shelf", model.getShelf());
		params.put("carModel", model.getCarModel());
		params.put("productType", model.getProductType());
		Paginater paginater = storageDao.findPager(params, getPager(request));
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
			storageService.save(model, getSessionUser(request));
			setSucResult(request);
		}catch(Exception e){
			
			setResult(false, e.getMessage(), request);
			toAdd();
		}
		return "toMain";
	}

	@Override
	public String toEdit() throws Exception {
		
		return EDIT;
	}

	@Override
	public String doEdit() throws Exception {
		try{
			storageService.update(model);
			setSucResult(request);
		}catch(Exception e){
			
			return toEdit();
		}
		return "toMain";
	}

	public String instock() throws Exception {
		try{
			storageService.instock(model.getId(), model.getInoutNum(), getSessionUser(request));
			setSucResult(request);
		}catch(Exception e){
			setResult(false, e.getMessage(), request);
			return list();
		}
		return "toMain";
	}
	public String outstock() throws Exception {
		try{
			storageService.outstock(model.getId(), model.getInoutNum(), model.getOrderId(), model.getRemark(), getSessionUser(request));
			setSucResult(request);
		}catch(Exception e){
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toOutstock();
		}
		return "toMain";
	}
	public String toOutstock() throws Exception {
		Storage storage = storageDao.findById(model.getId());
		model.setNum(storage.getNum());
		return "outstock";
	}
	@Override
	public String delete() throws Exception {
		try{
			storageService.update(model);
			setSucResult(request);
		}catch(Exception e){
			setResult(false, e.getMessage(), request);
		}
		return "toMain";
	}

	@Override
	public String detail() throws Exception {
		return null;
	}
	
}
