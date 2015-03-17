package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.ElecRecord;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface ElecRecordDao extends BaseDao {

	public Paginater findRecordPager(Map<String, Object> params, Pager pager);
	public ElecRecord findPreRecord(String houseSn);
	public List<ElecRecord> findPreRecords(Map<String, Object> params);
	public List<ElecRecord> findRecords(Map<String, Object> params);
}
