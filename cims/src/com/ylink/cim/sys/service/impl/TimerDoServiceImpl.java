package com.ylink.cim.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.sys.dao.TimerDao;
import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.Timer;
import com.ylink.cim.sys.domain.TimerDo;
import com.ylink.cim.sys.service.TimerDoService;

import flink.consant.Constants;
import flink.etc.BizException;

@Component("timerDoService")
public class TimerDoServiceImpl implements TimerDoService {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private TimerDoDao timerDoDao;
	@Autowired
	private TimerDao timerDao;
	@Autowired
	private IdFactoryService idFactoryService;

	public void addTimerDo(Class<?> clazz, Date date) {

	}

	private void initTimerDo(String sTriggerDate) throws BizException {
		List<Timer> timerList = timerDao.findAll();
		if (CollectionUtils.isEmpty(timerList)) {
			logger.info("��ʱ����Ϊ�գ�����Ҫ����(" + sTriggerDate + ")��ʱ��������");
			return;
		}

		for (int i = 0; i < timerList.size(); i++) {
			Timer timer = timerList.get(i);
			TimerDo timerdo = new TimerDo();
			timerdo.setId(idFactoryService.generateId(Constants.TIMER_DO_ID));
			timerdo.setBeanName(timer.getBeanName());
			timerdo.setBeanNameCh(timer.getBeanNameCh());
			timerdo.setTriggerDate(sTriggerDate);
			timerdo.setTriggerTime(timer.getTriggertime());
			timerdo.setState(TimerDo.INIT);
			timerdo.setPara2(timer.getPara2());
			timerdo.setPara1(timer.getPara1());
			timerDoDao.save(timerdo);
			logger.info("���ɵ��춨ʱ����:" + timer.getBeanName());
		}
		logger.info("����(" + sTriggerDate + ")��ʱ�����������!");

	}

	public boolean insertInitTask(String sTriggerDate) throws BizException {
		try {
			List lstResult = timerDoDao.getAllCommand(sTriggerDate);
			if (lstResult.size() != 0) {
				logger.info("�����ж�ʱ����,���ٳ�ʼ�����������");
				return false;
			}
			this.initTimerDo(sTriggerDate);

		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
		return true;
	}

	public void updateTimerDo(TimerDo timerDo, String state, String remark) {
		timerDo.setState(state);
		timerDo.setRemark(remark);
		timerDoDao.update(timerDo);
	}

}
