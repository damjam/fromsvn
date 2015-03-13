package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.WaterRecord;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface WaterRecordDao extends BaseDao {

	public Paginater findWaterRecordPager(Map<String, Object> params, Pager pager);
	public WaterRecord findPreRecord(String houseSn);
	public List<WaterRecord> findPreRecords(Map<String, Object> params);
	public List<WaterRecord> findRecords(Map<String, Object> params);
}
