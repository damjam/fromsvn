package com.ylink.cim.manage.service;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.Employee;

import flink.etc.BizException;

public interface EmployeeService {

	void save(Employee employee, UserInfo sessionUser) throws BizException;

	void changeState(String id, String state, UserInfo userInfo) throws BizException;

	void addVocation(Employee employee, UserInfo userInfo) throws BizException;

	void transfer(Employee model, UserInfo sessionUser) throws BizException;

	void update(Employee model, UserInfo sessionUser) throws BizException;

	void delete(String id) throws BizException;
	
	Integer addFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws BizException;

}
