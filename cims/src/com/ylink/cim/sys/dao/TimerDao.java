package com.ylink.cim.sys.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.sys.domain.Timer;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface TimerDao extends BaseDao {

	List<Timer> findAllValidTimer();

	Paginater getTimerList(Pager pager, Map<String, Object> params);
}
