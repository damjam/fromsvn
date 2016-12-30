package com.ylink.cim.manage.view;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.CarModelDao;
import com.ylink.cim.manage.domain.CarBrand;
import com.ylink.cim.manage.domain.CarModel;
import com.ylink.cim.manage.service.CarModelService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
import flink.util.StringUtil;
import flink.web.CRUDAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
		String keyword = model.getName();
		if(StringUtils.isNotEmpty(keyword)){
			String[] keyArray = keyword.split("-");
			if(keyArray.length == 2){
				keyword = keyArray[1];
			}
		}
		map.put("name", keyword);
		map.put("brand", model.getBrand());
		Paginater paginater = carModelDao.findPaginater(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect();
		return LIST;
	}

	private void initSelect() {
		request.setAttribute("carTypes", ParaManager.getSysDict(SysDictType.CarType.getValue()));
	}

	@Override
	public String toAdd() throws Exception {
		initSelect();
		return ADD;
	}

	@Override
	public String doAdd() throws Exception {
		try{
			boolean exist = carModelDao.isExist(model.getName(), model.getBrand());
			Assert.isTrue(!exist, "数据已存在");
			carModelService.save(model);
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
		return EDIT;
	}

	@Override
	public String doEdit() throws Exception {
		try{
			carModelService.update(model);
			setSucResult(request);
		}catch(Exception e){
			setFailResult("操作失败", request);
		}
		return "toMain";
	}

	@Override
	public String delete() throws Exception {
		try{
			carModelDao.deleteById(model.getId());
			setSucResult(request);
		}catch (Exception e) {
			setFailResult("操作失败", request);
		}
		return "toMain";
	}

	@Override
	public String detail() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String loadByKeyword() throws Exception {
		JSONObject object = new JSONObject();
		try{
			JSONArray array = new JSONArray();
			String keyword = request.getParameter("keyword");
			Paginater paginater = carModelDao.findByKeyword(keyword, getPager(request));
			for(int i=0, size = paginater.getList().size(); i<size; i++){
				CarModel carModel = (CarModel)paginater.getList().get(i);
				if(StringUtils.isNotEmpty(carModel.getBrand())){
					array.add(carModel.getBrand()+"-"+carModel.getName());
				}else {
					array.add(carModel.getName());
				}
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
	public CarModel getModel() {
		return model;
	}

	private CarModel model = new CarModel();
}
