package com.ylink.cim.manage.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.CarBrandDao;
import com.ylink.cim.manage.domain.CarBrand;
import com.ylink.cim.manage.service.CarBrandService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.CRUDAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Scope("prototype")
@Component
public class CarBrandAction extends CRUDAction implements ModelDriven<CarBrand> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CarBrandDao carBrandDao;
	@Autowired
	private CarBrandService carBrandService;
	
	public CarBrand getModel() {
		return model;
	}

	private CarBrand model = new CarBrand();

	@Override
	public String list() throws Exception {
		//Map<String, Object> map = getParaMap();
		String brand = model.getBrand();
		Paginater paginater = carBrandDao.findByKeyword(brand, getPager(request));
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
			boolean exist = carBrandDao.isExist(model.getId(), model.getBrand());
			Assert.isTrue(!exist, "数据已存在");
			carBrandService.save(model);
			setSucResult(request);
		}catch(Exception e){
			if (e instanceof BizException) {
				setResult(false, e.getMessage(), request);
			}else {
				setResult(false, "操作失败", request);
			}
			return toAdd();
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
			carBrandDao.deleteById(model.getId());
		}catch (Exception e) {
			
		}
		return "toMain";
	}
	public String loadByKeyword() throws Exception {
		JSONObject object = new JSONObject();
		try{
			JSONArray array = new JSONArray();
			String keyword = request.getParameter("keyword");
			Paginater paginater = carBrandDao.findByKeyword(keyword, getPager(request));
			for(int i=0, size = paginater.getList().size(); i<size; i++){
				CarBrand carBrand = (CarBrand)paginater.getList().get(i);
				array.add(carBrand.getBrand());
			}
			object.put("status", "1");
			object.put("list", array);
			respond(response, object.toString());
		}catch (Exception e) {
			e.printStackTrace();
			object.put("status", "0");
		}
		respond(response, object.toString());
		return null;
	}
	@Override
	public String detail() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
