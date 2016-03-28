package com.ylink.cim.appserver.task;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;
import com.ylink.cim.sys.service.TimerDoService;
import com.ylink.cim.busioper.service.BillTrackService;

@Scope("prototype")
@Component("trackBillTask")
public class TrackBillTask extends BaseCmdTask {

	@Autowired
	private BillTrackService billTrackService;
	@Autowired
	private TimerDoDao timerDoDao;
	@Autowired
	private TimerDoService timerDoService;

	@Override
	protected void doRun() {
		String id = getCmdId();
		try {
			billTrackService.exeTimerDo(id);
		} catch (Exception e) {
			e.printStackTrace();
			TimerDo timerDo = timerDoDao.findById(id);
			timerDoService.updateTimerDo(timerDo, TimerDo.BUSINESS_FAILURE,
					StringUtils.abbreviate(e.getMessage(), 100));
		}
	}
}
