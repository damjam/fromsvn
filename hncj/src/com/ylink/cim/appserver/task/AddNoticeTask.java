package com.ylink.cim.appserver.task;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ylink.cim.busioper.service.NoticeMngService;
import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;
import com.ylink.cim.sys.service.TimerDoService;

@Scope("prototype")
@Component("addNoticeTask")
public class AddNoticeTask extends BaseCmdTask {
	@Autowired
	private NoticeMngService noticeMsgService;
	@Autowired
	private TimerDoService timerDoService;
	@Autowired
	private TimerDoDao timerDoDao;
	protected void doRun() {
		super.doRun();
		Long id = getCmdId();
		try {
			noticeMsgService.exeTimerDo(id);
		} catch (Exception e) {
			e.printStackTrace();
			TimerDo timerDo = timerDoDao.findById(id);
			timerDoService.updateTimerDo(timerDo, TimerDo.BUSINESS_FAILURE, StringUtils.abbreviate(e.getMessage(), 100));
		}
		
	}
}
