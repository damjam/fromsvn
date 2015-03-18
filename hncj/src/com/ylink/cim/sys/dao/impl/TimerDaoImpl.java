package com.ylink.cim.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ylink.cim.sys.dao.TimerDao;
import com.ylink.cim.sys.domain.Timer;

import flink.hibernate.BaseDaoHibernateImpl;

@Component("timerDao")
public class TimerDaoImpl extends BaseDaoHibernateImpl implements TimerDao {

	protected Class getModelClass() {
		return Timer.class;
	}

	public List<Timer> findAllValidTimer() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
