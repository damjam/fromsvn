package com.ylink.cim.invest.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.common.state.ServiceState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.invest.dao.ServiceRecordDao;
import com.ylink.cim.invest.domain.ServiceRecord;
import com.ylink.cim.invest.domain.SignContract;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
@Component("serviceRecordDao")
public class ServiceRecordDaoImpl extends BaseDaoHibernateImpl implements ServiceRecordDao {

	
	protected Class getModelClass() {
		return ServiceRecord.class;
	}

	public void addServiceByContract(SignContract signContract, String branchNo) {
		String busiType = signContract.getBusiType();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("branchNo", branchNo);
		map.put("busiType", signContract.getBusiType());
		List<ServiceRecord> list = findByCondition(map);
		ServiceRecord service = null;
		if (list.size() > 1) {
			service = list.get(0);
			if (!ServiceState.NORMAL.getValue().equals(service.getState())) {
				service.setState(ServiceState.NORMAL.getValue());
				super.update(service);
			}
			return;
		}else {
			service = new ServiceRecord();
		}
		service = new ServiceRecord();
		if (StringUtils.isEmpty(branchNo)) {
			branchNo = BranchType.SZGOLD.getValue();
		}
		service.setBranchNo(branchNo);
		service.setBusiType(busiType);
		service.setCreateTime(DateUtil.getCurrent());
		service.setCustId(signContract.getCustId());
		service.setState(ServiceState.NORMAL.getValue());
		super.save(service);
	}

	public List<ServiceRecord> findByCondition(Map<String, Object> map) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ServiceRecord t where 1=1");
		helper.append("and t.custId = ?", map.get("custId"));
		helper.append("and t.busiType = ?", map.get("busiType"));
		helper.append("and t.state = ?", map.get("state"));
		helper.append("and t.branchNo = ?", map.get("branchNo"));
		return super.getList(helper);
	}


	

}
