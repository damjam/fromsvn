package com.ylink.cim.manage.service;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.UserInfo;

import flink.etc.BizException;

public interface ImportService {

	Integer addCSBFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws Exception;

	Integer addDepositBillFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws Exception;

	Integer addDSBFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws BizException;
}
