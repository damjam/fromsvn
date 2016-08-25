package com.ylink.cim.sys.dao;

import java.util.List;

import com.ylink.cim.sys.domain.TimerDo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface TimerDoDao extends BaseDao {

	List getAllCommand(String sTriggerDate);

	List<TimerDo> getAllCanExcuteCommand(String sTriggerDate,
			String sCurrentTime);

	public Paginater getPagerList(TimerDo timerDo, Pager pager);
}
