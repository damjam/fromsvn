package com.ylink.cim.busioper.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.busioper.domain.PushRecord;

import flink.hibernate.BaseDao;

public interface PushRecordDao extends BaseDao {

	public List<PushRecord> findByParams(Map<String, Object> map);
}
