package com.ylink.cim.invest.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.invest.dao.SignContractDao;
import com.ylink.cim.invest.domain.SignContract;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;

@Component("signContractDao")
public class SignContractDaoImpl extends BaseDaoHibernateImpl implements SignContractDao{

	public String[] findAcctByCust(Map<String, Object> params){
		List<SignContract> list = findByParams(params);
		String[] accts = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			accts[i] = list.get(i).getInvestAcctNo();
		}
		return accts;
	}
	
	public List<SignContract> findByParams(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from SignContract where 1=1");
		helper.append("and custId = ?", params.get("custId"));
		helper.append("and busiType = ?", params.get("busiCode"));
		helper.append("and payChnlNo = ?", params.get("payChnlNo"));
		helper.append("and serviceId = ?", params.get("serviceId"));
		helper.append("and state = ?", params.get("state"));
		helper.append("and accreditId = ?", params.get("accreditId"));
		if (!BranchType.HQ_0000.getValue().equals(params.get("branchNo"))) {
			helper.append("and exists (select s.id from ServiceRecord s where serviceId = s.id and s.branchNo = ?)", params.get("branchNo"));
		}
		return super.getList(helper);
	}
	protected Class getModelClass() {
		return SignContract.class;
	}
	
}
