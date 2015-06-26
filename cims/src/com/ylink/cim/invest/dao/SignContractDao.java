package com.ylink.cim.invest.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.invest.domain.SignContract;

import flink.hibernate.BaseDao;

public interface SignContractDao extends BaseDao {

	List<SignContract> findByParams(Map<String, Object> params);
	public String[] findAcctByCust(Map<String, Object> params);
}
