package com.ylink.cim.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ylink.cim.sys.dao.TimerDao;
import com.ylink.cim.sys.domain.Timer;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("timerDao")
public class TimerDaoImpl extends BaseDaoHibernateImpl implements TimerDao {

	public List<Timer> findAllValidTimer() {
		return null;
	}

	protected Class getModelClass() {
		return Timer.class;
	}

	public Paginater getTimerList(Pager pager, Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from Timer where 1=1");

		return super.getPageData(helper, pager);
	}

}
