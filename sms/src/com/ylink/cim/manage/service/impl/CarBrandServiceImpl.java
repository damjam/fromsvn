package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.CarBrandDao;
import com.ylink.cim.manage.domain.CarBrand;
import com.ylink.cim.manage.service.CarBrandService;
import com.ylink.cim.util.Cn2PinYinHelper;
@Component("carBrandService")
public class CarBrandServiceImpl implements CarBrandService {

	@Autowired
	private CarBrandDao carBrandDao;

	@Override
	public void save(CarBrand model) {
		model.setFirstLetters(Cn2PinYinHelper.cn2FirstSpell(model.getBrand()));
		carBrandDao.save(model);
	}

	@Override
	public void update(CarBrand model) {
		model.setFirstLetters(Cn2PinYinHelper.cn2FirstSpell(model.getBrand()));
		carBrandDao.update(model);
	}
	
}
