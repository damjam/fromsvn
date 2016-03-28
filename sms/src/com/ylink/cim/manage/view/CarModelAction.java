package com.ylink.cim.manage.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.manage.dao.CarModelDao;
import com.ylink.cim.manage.domain.CarModel;
import com.ylink.cim.manage.service.CarModelService;

import flink.util.Paginater;
import flink.web.CRUDAction;
@Scope("prototype")
@Component
public class CarModelAction extends CRUDAction implements ModelDriven<CarModel> {

	
	private static final long serialVersionUID = 1L;
	@Autowired
	private CarModelDao carModelDao;
	@Autowired
	private CarModelService carModelService;
	@Override
	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		Paginater paginater = carModelDao.findPaginater(map, getPager(request));
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
			carModelService.save(model);
			setSucResult(request);
		}catch(Exception e){
			setFailResult("²Ù×÷Ê§°Ü", request);
		}
		return "toMain";
	}

	@Override
	public String toEdit() throws Exception {
		// TODO Auto-generated method stub
		return EDIT;
	}

	@Override
	public String doEdit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		try{
			carModelDao.deleteById(model.getId());
			setSucResult(request);
		}catch (Exception e) {
			setFailResult("²Ù×÷Ê§°Ü", request);
		}
		return "toMain";
	}

	@Override
	public String detail() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CarModel getModel() {
		return model;
	}

	private CarModel model = new CarModel();
}
