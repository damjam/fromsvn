package com.ylink.cim.manage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.EmpTransferDao;
import com.ylink.cim.manage.domain.EmpTransfer;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
@Component("empTransferDao")
public class EmpTranferDaoImpl extends BaseDaoImpl implements EmpTransferDao {

	@Override
	protected Class getModelClass() {
		return EmpTransfer.class;
	}

	@Override
	public List<EmpTransfer> findByEmpId(String id) {
		QueryHelper helper = new QueryHelper();
		helper.append("from EmpTransfer where 1=1");
		helper.append("and empId = ?", id);
		return super.getList(helper);
	}

	
}
