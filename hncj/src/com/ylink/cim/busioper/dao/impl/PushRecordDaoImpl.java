package com.ylink.cim.busioper.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ylink.cim.busioper.dao.PushRecordDao;
import com.ylink.cim.busioper.domain.PushRecord;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;

@Component("pushRecordDao")
public class PushRecordDaoImpl extends BaseDaoImpl implements PushRecordDao {

	@Override
	public List<PushRecord> findByParams(Map<String, Object> map) {
		QueryHelper helper = new QueryHelper();
		helper.append("from PushRecord where 1=1");
		helper.append("and state = ?", map.get("state"));
		helper.append("and planId = ?", map.get("planId"));
		helper.append("order by id desc");
		return super.getList(helper);
	}

	@Override
	protected Class getModelClass() {
		return PushRecord.class;
	}

}
