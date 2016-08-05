package com.ylink.cim.appserver.task;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.service.DailyOrderService;
import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;
import com.ylink.cim.sys.service.TimerDoService;

/**
 * 执行推送任务
 * 
 * @author libaozhu
 * @date 2016-08-05
 */
@Scope("prototype")
@Component("dailySumTask")
public class DailySumTask extends BaseCmdTask {
	@Autowired
	private DailyOrderService dailyOrderService;
	@Autowired
	private TimerDoService timerDoService;
	@Autowired
	private TimerDoDao timerDoDao;

	@Override
	protected void doRun() {
		super.doRun();
		String id = getCmdId();
		try {
			dailyOrderService.exeDailySumTask(id);
		} catch (Exception e) {
			e.printStackTrace();
			TimerDo timerDo = timerDoDao.findById(id);
			timerDoService.updateTimerDo(timerDo, TimerDo.BUSINESS_FAILURE,
					StringUtils.abbreviate(e.getMessage(), 100));
		}

	}
}
