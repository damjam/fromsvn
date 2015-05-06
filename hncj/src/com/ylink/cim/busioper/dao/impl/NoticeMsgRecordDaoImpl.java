package com.ylink.cim.busioper.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ylink.cim.busioper.dao.NoticeMsgRecordDao;
import com.ylink.cim.busioper.domain.NoticeMsgRecord;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;

@Component("noticeMsgRecordDao")
public class NoticeMsgRecordDaoImpl extends BaseDaoHibernateImpl implements NoticeMsgRecordDao {

	
	public List<NoticeMsgRecord> findByParams(Map<String, Object> map) {
		QueryHelper helper = new QueryHelper();
		helper.append("from NoticeMsgRecord t where 1=1");
		helper.append("and t.custId = ?", map.get("custId"));
		helper.append("and t.read = ?", map.get("read"));
		helper.append("order by t.createTime desc");
		return super.getList(helper);
	}

	protected Class getModelClass() {
		return NoticeMsgRecord.class;
	}

	
	
}
