package com.ylink.cim.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;

@Component("timerDoDao")
public class TimerDoDaoImpl extends BaseDaoHibernateImpl implements TimerDoDao {

	protected Class getModelClass() {
		return TimerDo.class;
	}

	public List getAllCommand(String sTriggerDate) {
		QueryHelper helper = new QueryHelper();
		helper.append("from TimerDo where 1=1");
		helper.append("and triggerDate=? ", sTriggerDate);
		return getList(helper);
	}

	public List<TimerDo> getAllCanExcuteCommand(String sTriggerDate, String sCurrentTime) {
		QueryHelper helper = new QueryHelper();
		helper.append("from TimerDo ti where 1=1");
		helper.append(" and ti.state in ?", TimerDo.NEED_EXC_STRINGS);
		helper.append(" and ti.triggerDate= ?", sTriggerDate);
		helper.append(" and ti.triggerTime <=? order by ti.triggerTime", sCurrentTime);
		return getList(helper);
	}
	
}
