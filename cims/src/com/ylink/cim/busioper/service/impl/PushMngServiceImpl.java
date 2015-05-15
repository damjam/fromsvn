package com.ylink.cim.busioper.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.busioper.dao.PushPlanDao;
import com.ylink.cim.busioper.dao.PushRecordDao;
import com.ylink.cim.busioper.domain.PushPlan;
import com.ylink.cim.busioper.domain.PushRecord;
import com.ylink.cim.busioper.service.PushMngService;
import com.ylink.cim.common.state.CustState;
import com.ylink.cim.common.type.PushType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.common.util.SendMailUtil;
import com.ylink.cim.common.util.SendMobilMsgUtil;
import com.ylink.cim.cust.dao.CustInfoDao;
import com.ylink.cim.cust.domain.CustInfo;
import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;

import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.MsgUtils;
import flink.util.Pager;

@Component("pushMngService")
public class PushMngServiceImpl implements PushMngService {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private PushPlanDao pushPlanDao;
	@Autowired
	private PushRecordDao pushRecordDao;
	@Autowired
	private TimerDoDao timerDoDao;
	@Autowired
	private CustInfoDao custInfoDao;

	public void exeAddRecordTask(String timerDoId) throws BizException {
		TimerDo timerDo = timerDoDao.findByIdWithLock(timerDoId);
		if (TimerDo.BUSINESS_SUCESS.equals(timerDo.getState())) {
			logger.debug("������ִ��");
			return;
		}
		Long para1 = timerDo.getPara1();
		String planId = String.valueOf(para1);
		PushPlan pushPlan = pushPlanDao.findById(planId);
		String pushType = pushPlan.getPushType();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", CustState.NORMAL.getValue());
		map.put("branchNo", pushPlan.getBranchNo());
		map.put("busiType", pushPlan.getBusiType());
		map.put("subsState", pushPlan.getSubsState());
		map.put("custType", pushPlan.getCustType());
		int pageNumber = 1;
		int pageSize = 100;
		Pager pager = new Pager(pageNumber, 100);
		List<CustInfo> list = custInfoDao.findForPushPlan(map, pager);
		int cnt = 0;
		while (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				CustInfo custInfo = list.get(i);

				PushRecord pushRecord = new PushRecord();
				// �ʼ�
				String pushAdd = "";
				if (PushType.MAIL.getValue().endsWith(pushType)) {
					if (StringUtils.isNotEmpty(pushPlan.getSubsState())) {// ֻ�����ĵķ�
						if (pushPlan.getSubsState().equals(custInfo.getSubsEmail())) {
							pushAdd = custInfo.getEmail();
						}
					} else {// ����
						pushAdd = custInfo.getEmail();
					}
				} else if (PushType.MOBILE.getValue().equals(pushType)) {
					String time = ParaManager.getSubPhone();
					if (StringUtils.isNotEmpty(time)) {
						if (Symbol.YES.equals(time)) {// ΪY����ִ��
						// pushAdd = custInfo.getMobile();
							if (StringUtils.isNotEmpty(pushPlan.getSubsState())) {// ֻ�����ĵķ�
								if (pushPlan.getSubsState().equals(custInfo.getSubsPhone())) {
									pushAdd = custInfo.getMobile();
								}
							} else {// ����
								pushAdd = custInfo.getMobile();
							}
							// SendMobilMsgUtil.sendMsgFw(adds,
							// plan.getSubject()+":"+plan.getContent());
						} else if (Symbol.NO.equals(time)) {
							// ΪN����ִ��
						} else {
							SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
							String nowTime = sdf.format(new Date());
							if (nowTime.equals(time)) {
								// pushAdd = custInfo.getMobile();
								if (StringUtils.isNotEmpty(pushPlan.getSubsState())) {// ֻ�����ĵķ�
									if (pushPlan.getSubsState().equals(custInfo.getSubsPhone())) {
										pushAdd = custInfo.getMobile();
									}
								} else {// ����
									pushAdd = custInfo.getMobile();
								}
							}
						}
					}
				}
				if (StringUtils.isEmpty(pushAdd)) {
					logger.debug("���͵�ַΪ��");
					continue;
				}
				pushRecord.setPushAdd(pushAdd);
				pushRecord.setState(Symbol.NO);
				pushRecord.setPlanId(planId);
				pushRecordDao.save(pushRecord);
				cnt++;
			}
			pushRecordDao.flush();
			// ���β���Ĳ���100�������ٲ�ѯ
			if (list.size() < pageSize) {
				break;
			}
			pageNumber++;
			pager = new Pager(pageNumber, pageSize);
			list = custInfoDao.findForPushPlan(map, pager);
		}
		timerDo.setState(TimerDo.BUSINESS_SUCESS);
		logger.debug(MsgUtils.r("�������������Ϣ��ִ��{?}����¼", cnt));
	}

	public void exeSendMsgTask(String timerDoId) throws BizException {
		TimerDo timerDo = timerDoDao.findByIdWithLock(timerDoId);
		Long para1 = timerDo.getPara1();
		String planId = String.valueOf(para1);
		PushPlan plan = pushPlanDao.findByIdWithLock(planId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", Symbol.NO);
		map.put("planId", planId);
		List<PushRecord> list = pushRecordDao.findByParams(map);
		if (list.size() == 0) {
			// δ�������ͼ�¼
			return;
		}
		int cnt = 0;
		List<String> addList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			PushRecord record = list.get(i);
			pushPlanDao.lock(record, LockMode.UPGRADE);
			pushPlanDao.refresh(record);
			if (Symbol.YES.endsWith(record.getState())) {
				logger.debug(MsgUtils.r("���ͼ�¼{?}������,�����ٴ�����", record.getId()));
			}

			record.setPushTime(DateUtil.getCurrent());
			record.setState(Symbol.YES);
			pushPlanDao.update(record);
			if (i > 100 && i % 100 == 0) {
				pushRecordDao.flush();
			}
			addList.add(record.getPushAdd());
			if ((i > 100 && i % 100 == 0) || i == list.size() - 1) {
				String[] adds = addList.toArray(new String[addList.size()]);
				if (PushType.MAIL.getValue().equals(plan.getPushType())) {
					SendMailUtil.sendTextMail(adds, plan.getSubject(), plan.getContent());
				} else if (PushType.MOBILE.getValue().equals(plan.getPushType())) {
					SendMobilMsgUtil.sendMsgFw(adds, plan.getSubject() + ":" + plan.getContent());
				}
			}
			cnt++;
		}
		timerDo.setState(TimerDo.BUSINESS_SUCESS);
		timerDoDao.update(timerDo);
		plan.setState(Symbol.YES);
		pushPlanDao.update(plan);
		logger.debug(MsgUtils.r("������ͻ����������Ϣ��ִ��{?}����¼", cnt));
	}

	public void addPushPlan(PushPlan pushPlan) {
		pushPlan.setCreateTime(DateUtil.getCurrent());
		pushPlan.setExpPushTime(DateUtil.getCurrent());
		pushPlan.setState(Symbol.NO);
		pushPlanDao.save(pushPlan);
		addPushTask(pushPlan);
		exePushTask(pushPlan);
		// ��Ӽƻ�

	}

	private void addPushTask(PushPlan pushPlan) {
		TimerDo timerDo = new TimerDo();
		timerDo.setBeanName("addPushTask");
		timerDo.setBeanNameCh("���������Ϣ");
		timerDo.setPara1(Long.parseLong(pushPlan.getId()));
		timerDo.setPara2(pushPlan.getBranchNo());
		timerDo.setTriggerDate(DateUtil.getCurrentDate());
		timerDo.setTriggerTime(DateUtil.getCurrentTime());
		timerDo.setState(TimerDo.INIT);
		timerDoDao.save(timerDo);
	}

	private void exePushTask(PushPlan pushPlan) {
		TimerDo timerDo = new TimerDo();
		timerDo.setBeanName("exePushTask");
		timerDo.setBeanNameCh("ִ������");
		timerDo.setPara1(Long.parseLong(pushPlan.getId()));
		timerDo.setPara2(pushPlan.getBranchNo());
		timerDo.setTriggerDate(DateUtil.getCurrentDate());
		// 10���Ӻ�ִ��
		timerDo.setTriggerTime(DateUtil.formatDate("HHmmss", DateUtil.addMins(DateUtil.getCurrent(), 1)));
		timerDo.setState(TimerDo.INIT);
		timerDoDao.save(timerDo);
	}
}
