package com.ylink.cim.appserver.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.appserver.task.BaseCmdTask;
import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;
import com.ylink.cim.sys.service.TimerDoService;

import flink.util.SpringContext;

/*

 */
@Component("timerAppServer")
public class TimerAppServer implements IAppServer {
	private static long sleepTime = 60000L;// 一分钟
	private Logger logger = Logger.getLogger(TimerAppServer.class);

	@Autowired
	private TimerDoDao timerDoDao;// = (TimerDoDao)
									// SpringContext.getService("timerDoDao");
	@Autowired
	private TimerDoService timerDoService;// = (TimerDoService)
											// SpringContext.getService("timerDoService");

	private SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");// 日期格式化

	private SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");// 时间格式化

	public void doProcess() throws InterruptedException {
		logger.info("开始定时主控");
		String sTriggerDate;// 触发日期
		String sCurrentTime;// 触发时间
		Map<String, String> mapOfInit = new HashMap<String, String>();

		while (true) {
			Date now = new Date();
			sTriggerDate = dayFormat.format(now);
			sCurrentTime = timeFormat.format(now);

			String init = mapOfInit.get(sTriggerDate);// 当日的定时任务是否已经初始化
			if (init == null) {
				try {
					timerDoService.insertInitTask(sTriggerDate);

					mapOfInit.clear();
					mapOfInit.put(sTriggerDate, "init");
				} catch (Exception e) {
					logger.error("初始化定时服务initTask异常,稍后重新初始化" + e, e);
					Thread.sleep(sleepTime);
					continue;
				}
			}

			List<TimerDo> timerDoList = timerDoDao.getAllCanExcuteCommand(sTriggerDate, sCurrentTime);// 当前可以执行的命令
			if (CollectionUtils.isEmpty(timerDoList)) {
				// logger.info("当前待执行的定时命令为空！");
				Thread.sleep(sleepTime);
				continue;
			}

			TimerDo timerDo = null;
			for (int i = 0; i < timerDoList.size(); i++) {
				timerDo = timerDoList.get(i);
				logger.info("定时命令开始执行代码:" + timerDo.getBeanName());
				// 派生线程
				try {
					// BaseCmdTask baseCmd = (BaseCmdTask)
					// Class.forName(timerDo.getClassName()).newInstance();
					BaseCmdTask baseCmd = (BaseCmdTask) SpringContext.getService(timerDo.getBeanName());
					baseCmd.setCmdName(timerDo.getBeanNameCh());
					baseCmd.setCmdId(timerDo.getId());
					baseCmd.start();
				} catch (NullPointerException e) {
					logger.error("定时明细执行失败。bean没找到:" + e, e);
					timerDoService.updateTimerDo(timerDo, TimerDo.BUSINESS_SUCESS, "bean没找到[" + e.getMessage() + "]");
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("定时明细执行失败。其他错误:" + e, e);
					timerDoService.updateTimerDo(timerDo, TimerDo.BUSINESS_SUCESS, "未知错误");
				}
			}
			Thread.sleep(sleepTime);
		}
	}

}
