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

import com.ylink.cim.admin.service.IdFactoryService;
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

import flink.consant.Constants;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;
import flink.util.LogUtils;
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
	@Autowired
	private IdFactoryService idFactoryService;
	public void exeAddRecordTask(String timerDoId) throws BizException{
		TimerDo timerDo = timerDoDao.findByIdWithLock(timerDoId);
		if (TimerDo.BUSINESS_SUCESS.equals(timerDo.getState())) {
			logger.debug("任务已执行");
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
				//邮件
				String pushAdd = "";
				if (PushType.MAIL.getValue().endsWith(pushType)) {
					if(StringUtils.isNotEmpty(pushPlan.getSubsState())){//只给订阅的发
						if(pushPlan.getSubsState().equals(custInfo.getSubsEmail())){
							pushAdd = custInfo.getEmail();
						}
					}else{//都发
							pushAdd = custInfo.getEmail();
					}
				} else if(PushType.MOBILE.getValue().equals(pushType)){
					String time=ParaManager.getSubPhone();
					if(StringUtils.isNotEmpty(time)){
						if(Symbol.YES.equals(time)){//为Y立即执行
//							pushAdd = custInfo.getMobile();
							if(StringUtils.isNotEmpty(pushPlan.getSubsState())){//只给订阅的发
								if(pushPlan.getSubsState().equals(custInfo.getSubsPhone())){
									pushAdd = custInfo.getMobile();
								}
							}else{//都发
									pushAdd = custInfo.getMobile();
							}
//							SendMobilMsgUtil.sendMsgFw(adds, plan.getSubject()+":"+plan.getContent());
						}else if(Symbol.NO.equals(time)){
							//为N，不执行
						}else{
							SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
							String nowTime=sdf.format(new Date());
							if(nowTime.equals(time)){
//								pushAdd = custInfo.getMobile();
								if(StringUtils.isNotEmpty(pushPlan.getSubsState())){//只给订阅的发
									if(pushPlan.getSubsState().equals(custInfo.getSubsPhone())){
										pushAdd = custInfo.getMobile();
									}
								}else{//都发
										pushAdd = custInfo.getMobile();
								}
							}
						}
					}
				}
				if (StringUtils.isEmpty(pushAdd)) {
					logger.debug("推送地址为空");
					continue;
				}
				pushRecord.setPushAdd(pushAdd);
				pushRecord.setState(Symbol.NO);
				pushRecord.setPlanId(planId);
				pushRecordDao.save(pushRecord);
				cnt++;
			}
			pushRecordDao.flush();
			//本次查出的不足100条，不再查询
			if (list.size() < pageSize) {
				break;
			}
			pageNumber++;
			pager = new Pager(pageNumber, pageSize);
			list = custInfoDao.findForPushPlan(map, pager);
		}
		timerDo.setState(TimerDo.BUSINESS_SUCESS);
		logger.debug(LogUtils.r("本次添加推送信息共执行{?}条记录", cnt));
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
			//未生成推送记录
			return;
		}
		int cnt = 0;
		List<String> addList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			PushRecord record = list.get(i);
			pushPlanDao.lock(record, LockMode.UPGRADE);
			pushPlanDao.refresh(record);
			if (Symbol.YES.endsWith(record.getState())) {
				logger.debug(LogUtils.r("推送记录{?}已推送,无须再次推送", record.getId()));
			}
			
			record.setPushTime(DateUtil.getCurrent());
			record.setState(Symbol.YES);
			pushPlanDao.update(record);
			if (i > 100 && i%100 == 0){
				pushRecordDao.flush();
			}
			addList.add(record.getPushAdd());
			if((i > 100 && i%100 == 0) || i == list.size()-1){
				String[] adds = addList.toArray(new String[addList.size()]);
				if (PushType.MAIL.getValue().equals(plan.getPushType())) {
					SendMailUtil.sendTextMail(adds, plan.getSubject(), plan.getContent());
				} else if (PushType.MOBILE.getValue().equals(plan.getPushType())) {
									SendMobilMsgUtil.sendMsgFw(adds, plan.getSubject()+":"+plan.getContent());
							}
				}
			cnt++;
		}
		timerDo.setState(TimerDo.BUSINESS_SUCESS);
		timerDoDao.update(timerDo);
		plan.setState(Symbol.YES);
		pushPlanDao.update(plan);
		logger.debug(LogUtils.r("本次向客户添加推送信息共执行{?}条记录", cnt));
	}
	
	public void addPushPlan(PushPlan pushPlan) throws BizException{
		pushPlan.setCreateTime(DateUtil.getCurrent());
		pushPlan.setExpPushTime(DateUtil.getCurrent());
		pushPlan.setState(Symbol.NO);
		pushPlanDao.save(pushPlan);
		addPushTask(pushPlan);
		exePushTask(pushPlan);
		//添加计划
		
	}
	private void addPushTask(PushPlan pushPlan) throws BizException{
		TimerDo timerDo = new TimerDo();
		timerDo.setId(idFactoryService.generateId(Constants.TIMER_DO_ID));
		timerDo.setBeanName("addPushTask");
		timerDo.setBeanNameCh("添加推送消息");
		timerDo.setPara1(Long.parseLong(pushPlan.getId()));
		timerDo.setPara2(pushPlan.getBranchNo());
		timerDo.setTriggerDate(DateUtil.getCurrentDate());
		timerDo.setTriggerTime(DateUtil.getCurrentTime());
		timerDo.setState(TimerDo.INIT);
		timerDoDao.save(timerDo);
	}
	private void exePushTask(PushPlan pushPlan) throws BizException{
		TimerDo timerDo = new TimerDo();
		timerDo.setId(idFactoryService.generateId(Constants.TIMER_DO_ID));
		timerDo.setBeanName("exePushTask");
		timerDo.setBeanNameCh("执行推送");
		timerDo.setPara1(Long.parseLong(pushPlan.getId()));
		timerDo.setPara2(pushPlan.getBranchNo());
		timerDo.setTriggerDate(DateUtil.getCurrentDate());
		//10分钟后执行
		timerDo.setTriggerTime(DateUtil.formatDate("HHmmss", DateUtil.addMins(DateUtil.getCurrent(), 1)));
		timerDo.setState(TimerDo.INIT);
		timerDoDao.save(timerDo);
	}
}
