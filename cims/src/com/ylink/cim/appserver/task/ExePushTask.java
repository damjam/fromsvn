package com.ylink.cim.appserver.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ylink.cim.busioper.service.PushMngService;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;
import com.ylink.cim.sys.service.TimerDoService;

import flink.etc.Symbol;

/**
 * ִ����������
 * 
 * @author libaozhu
 * @date 2013-5-5
 */
@Scope("prototype")
@Component("exePushTask")
public class ExePushTask extends BaseCmdTask {
	@Autowired
	private PushMngService pushMngService;
	@Autowired
	private TimerDoService timerDoService;
	@Autowired
	private TimerDoDao timerDoDao;
	protected void doRun() {
		super.doRun();
		String id = getCmdId();
		try {
			
			pushMngService.exeSendMsgTask(id);
			
		} catch (Exception e) {
			e.printStackTrace();
			TimerDo timerDo = timerDoDao.findById(id);
			timerDoService.updateTimerDo(timerDo, TimerDo.BUSINESS_FAILURE, StringUtils.abbreviate(e.getMessage(), 100));
		}

	}
}
