package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.CarModelDao;
import com.ylink.cim.manage.domain.CarModel;
import com.ylink.cim.manage.service.CarModelService;
@Component("carModelService")
public class CarModelServiceImpl implements CarModelService {

	@Autowired
	private CarModelDao carModelDao;

	@Override
	public void save(CarModel model) {
		carModelDao.save(model);
	}
	
}
