package com.ylink.cim.busioper.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.busioper.domain.NoticeMsgRecord;

import flink.hibernate.BaseDao;

public interface NoticeMsgRecordDao extends BaseDao {

	List<NoticeMsgRecord> findByParams(Map<String, Object> map);
}
