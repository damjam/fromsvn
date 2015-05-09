package com.ylink.cim.sys.dao.impl;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("timerDoDao")
public class TimerDoDaoImpl extends BaseDaoHibernateImpl implements TimerDoDao {

	public List<TimerDo> getAllCanExcuteCommand(String sTriggerDate, String sCurrentTime) {
		QueryHelper helper = new QueryHelper();
		helper.append("from TimerDo ti where 1=1");
		helper.append(" and ti.state in ?", TimerDo.NEED_EXC_STRINGS);
		helper.append(" and ti.triggerDate= ?", sTriggerDate);
		helper.append(" and ti.triggerTime <=? order by ti.triggerTime", sCurrentTime);
		return getList(helper);
	}

	public List getAllCommand(String sTriggerDate) {
		QueryHelper helper = new QueryHelper();
		helper.append("from TimerDo where 1=1");
		helper.append("and triggerDate=? ", sTriggerDate);
		return getList(helper);
	}

	protected Class getModelClass() {
		return TimerDo.class;
	}

	public Paginater getPagerList(TimerDo timerDo, Pager pager) {
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.append("from TimerDo t");
		queryHelper.append("where 1=1 ");
		queryHelper.append("and t.beanName like ?", timerDo.getBeanName(), MatchMode.ANYWHERE);
		queryHelper.append("and t.beanNameCh like ?", timerDo.getBeanNameCh(), MatchMode.ANYWHERE);
		queryHelper.append("and t.state = ?", timerDo.getState());
		queryHelper.append("order by t.id desc");
		return super.getPageData(queryHelper, pager);
	}
}
