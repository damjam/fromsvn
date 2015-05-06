package com.ylink.cim.sys.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.sys.dao.TimerDao;
import com.ylink.cim.sys.domain.Timer;
import com.ylink.cim.sys.service.TimerService;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;

@Component("timerService")
public class TimerServiceImpl implements TimerService {
	@Autowired
	private TimerDao timerDao;
	@Autowired
	private IdFactoryService idFactoryService;

	public void delete(String id) throws BizException {
		timerDao.deleteById(id);

	}

	public Paginater findPager(Pager pager, Map<String, Object> params) throws BizException {
		return timerDao.getTimerList(pager, params);
	}

	public void save(Timer timer, UserInfo userInfo) throws BizException {
		timer.setId(idFactoryService.generateId(Constants.TIMER_ID));
		timer.setCreateTime(DateUtil.getCurrent());
		timer.setCreateUser(userInfo.getUserName());
		timer.setAuditTime(DateUtil.getCurrent());
		timerDao.save(timer);
	}

	public void update(Timer timer, UserInfo userInfo) throws BizException {
		this.timerDao.update(timer);
	}

}
