package com.ylink.cim.manage.dao;

import java.util.List;

import com.ylink.cim.manage.domain.EmpTransfer;

import flink.hibernate.BaseDao;

public interface EmpTransferDao extends BaseDao {

	List<EmpTransfer> findByEmpId(String id);

}
