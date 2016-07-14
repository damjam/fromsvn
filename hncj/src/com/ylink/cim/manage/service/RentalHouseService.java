package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.RentalHouse;

import flink.etc.BizException;

public interface RentalHouseService {

	void save(RentalHouse model, UserInfo sessionUser);
	
	void update(RentalHouse rentalHouse);

	void release(String houseSn) throws BizException;
	
	void occupy(String houseSn) throws BizException;

	void delete(String houseSn) throws BizException;
}
