package com.ylink.cim.manage.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.manage.dao.StorageJournalDao;
import com.ylink.cim.manage.dao.StorageJournalDao;
import com.ylink.cim.manage.domain.Storage;
import com.ylink.cim.manage.domain.StorageJournal;
import com.ylink.cim.manage.service.StorageJournalService;

import flink.util.Paginater;
import flink.web.CRUDAction;
@Scope("prototype")
@Component
public class StorageJournalAction extends CRUDAction implements ModelDriven<StorageJournal> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private StorageJournalDao storageJournalDao;
	@Autowired
	private StorageJournalService storageJournalService;
	
	@Override
	public StorageJournal getModel() {
		return model;
	}

	private StorageJournal model = new StorageJournal();

	@Override
	public String list() throws Exception {
		Map<String, Object> params = getParaMap();
		params.put("shelf", model.getShelf());
		params.put("carModel", model.getCarModel());
		params.put("productType", model.getProductType());
		Paginater paginater = storageJournalDao.findPager(params, getPager(request));
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
			storageJournalService.save(model, getSessionUser(request));
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
			storageJournalService.update(model);
			setSucResult(request);
		}catch(Exception e){
			
			return toEdit();
		}
		return "toMain";
	}

	@Override
	public String delete() throws Exception {
		try{
			storageJournalService.update(model);
			setSucResult(request);
		}catch(Exception e){
			setResult(false, e.getMessage(), request);
		}
		return "toMain";
	}

	@Override
	public String detail() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
