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
	private static long sleepTime = 60000L;// һ����
	private Logger logger = Logger.getLogger(TimerAppServer.class);

	@Autowired
	private TimerDoDao timerDoDao;// = (TimerDoDao)
									// SpringContext.getService("timerDoDao");
	@Autowired
	private TimerDoService timerDoService;// = (TimerDoService)
											// SpringContext.getService("timerDoService");

	private SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");// ���ڸ�ʽ��

	private SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");// ʱ���ʽ��

	@Override
	public void doProcess() throws InterruptedException {
		logger.info("��ʼ��ʱ����");
		String sTriggerDate;// ��������
		String sCurrentTime;// ����ʱ��
		Map<String, String> mapOfInit = new HashMap<String, String>();

		while (true) {
			Date now = new Date();
			sTriggerDate = dayFormat.format(now);
			sCurrentTime = timeFormat.format(now);

			String init = mapOfInit.get(sTriggerDate);// ���յĶ�ʱ�����Ƿ��Ѿ���ʼ��
			if (init == null) {
				try {
					timerDoService.insertInitTask(sTriggerDate);

					mapOfInit.clear();
					mapOfInit.put(sTriggerDate, "init");
				} catch (Exception e) {
					logger.error("��ʼ����ʱ����initTask�쳣,�Ժ����³�ʼ��" + e, e);
					Thread.sleep(sleepTime);
					continue;
				}
			}

			List<TimerDo> timerDoList = timerDoDao.getAllCanExcuteCommand(
					sTriggerDate, sCurrentTime);// ��ǰ����ִ�е�����
			if (CollectionUtils.isEmpty(timerDoList)) {
				// logger.info("��ǰ��ִ�еĶ�ʱ����Ϊ�գ�");
				Thread.sleep(sleepTime);
				continue;
			}

			TimerDo timerDo = null;
			for (int i = 0; i < timerDoList.size(); i++) {
				timerDo = timerDoList.get(i);
				logger.info("��ʱ���ʼִ�д���:" + timerDo.getBeanName());
				// �����߳�
				try {
					// BaseCmdTask baseCmd = (BaseCmdTask)
					// Class.forName(timerDo.getClassName()).newInstance();
					BaseCmdTask baseCmd = (BaseCmdTask) SpringContext
							.getService(timerDo.getBeanName());
					baseCmd.setCmdName(timerDo.getBeanNameCh());
					baseCmd.setCmdId(timerDo.getId());
					baseCmd.start();
				} catch (NullPointerException e) {
					logger.error("��ʱ��ϸִ��ʧ�ܡ�beanû�ҵ�:" + e, e);
					timerDoService.updateTimerDo(timerDo,
							TimerDo.BUSINESS_SUCESS,
							"beanû�ҵ�[" + e.getMessage() + "]");
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("��ʱ��ϸִ��ʧ�ܡ���������:" + e, e);
					timerDoService.updateTimerDo(timerDo,
							TimerDo.BUSINESS_SUCESS, "δ֪����");
				}
			}
			Thread.sleep(sleepTime);
		}
	}

}
