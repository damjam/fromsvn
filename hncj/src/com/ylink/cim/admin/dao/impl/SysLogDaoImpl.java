package com.ylink.cim.admin.dao.impl;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.SysLogDao;
import com.ylink.cim.admin.domain.SysLog;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

/**
 * 
 * 
 * 
 */
@Component("sysLogDao")
public class SysLogDaoImpl extends BaseDaoHibernateImpl implements SysLogDao {

	
	protected Class getModelClass() {

		return SysLog.class;
	}



	public Paginater getSysLogPagList(SysLog sysLog, Pager pager) throws Exception {
		QueryHelper helper = new QueryHelper();
		helper.append("from SysLog where 1=1");
		helper.append("and limitId = ?", sysLog.getLimitId());
		helper.append("and content like ?", sysLog.getContent(), MatchMode.ANYWHERE);
		helper.append("and logType = ?", sysLog.getLogType());
		helper.append("and userId = ?", sysLog.getUserId());
		helper.append("and state = ?", sysLog.getState());
		helper.append("and logClass = ?", sysLog.getLogClass());
		helper.append("order by id desc ");
		return getPageData(helper, pager);
	}

	public void saveSysLog(SysLog sysLog) throws Exception {
		this.save(sysLog);
	}

	public void updateUserLog(SysLog sysLog) throws Exception {
		super.update(sysLog);
	}

}
