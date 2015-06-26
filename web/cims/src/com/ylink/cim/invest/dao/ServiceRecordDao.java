package com.ylink.cim.invest.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.invest.domain.ServiceRecord;
import com.ylink.cim.invest.domain.SignContract;

import flink.hibernate.BaseDao;

public interface ServiceRecordDao extends BaseDao {

	void addServiceByContract(SignContract signContract, String branchNo);

	List<ServiceRecord> findByCondition(Map<String, Object> map);
}
