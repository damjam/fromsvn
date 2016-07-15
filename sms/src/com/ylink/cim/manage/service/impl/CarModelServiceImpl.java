package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.CarModelDao;
import com.ylink.cim.manage.domain.CarModel;
import com.ylink.cim.manage.service.CarModelService;
import com.ylink.cim.util.Cn2PinYinHelper;
@Component("carModelService")
public class CarModelServiceImpl implements CarModelService {

	@Autowired
	private CarModelDao carModelDao;

	@Override
	public void save(CarModel model) {
		model.setFirstLetters(Cn2PinYinHelper.cn2FirstSpell(model.getName()));
		model.setPinyin(Cn2PinYinHelper.cn2Spell(model.getName()));
		carModelDao.save(model);
	}
	public void update(CarModel model) {
		model.setFirstLetters(Cn2PinYinHelper.cn2FirstSpell(model.getName()));
		model.setPinyin(Cn2PinYinHelper.cn2Spell(model.getName()));
		carModelDao.update(model);
	}
	@Override
	public void addIfNotExist(String modelName) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", modelName);
		boolean exist = carModelDao.isExist(modelName, null);
		if(!exist){
			CarModel carModel = new CarModel();
			carModel.setFirstLetters(Cn2PinYinHelper.cn2FirstSpell(modelName));
			carModel.setPinyin(Cn2PinYinHelper.cn2Spell(modelName));
			carModel.setName(modelName);
			carModelDao.save(carModel);
		}
	}
}
