package com.ylink.cim.sys.dao;

import java.util.List;

import com.ylink.cim.sys.domain.Timer;

import flink.hibernate.BaseDao;

public interface TimerDao extends BaseDao {

	List<Timer> findAllValidTimer();

}
