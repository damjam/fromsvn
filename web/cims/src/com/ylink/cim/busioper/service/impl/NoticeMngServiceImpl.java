package com.ylink.cim.busioper.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.busioper.dao.NoticeMsgDao;
import com.ylink.cim.busioper.dao.NoticeMsgRecordDao;
import com.ylink.cim.busioper.domain.NoticeMsg;
import com.ylink.cim.busioper.domain.NoticeMsgRecord;
import com.ylink.cim.busioper.service.NoticeMngService;
import com.ylink.cim.common.state.CustState;
import com.ylink.cim.cust.dao.CustInfoDao;
import com.ylink.cim.cust.domain.CustInfo;
import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;

import flink.etc.Symbol;
import flink.util.DateUtil;
import flink.util.Pager;

@Component("noticeMngService")
public class NoticeMngServiceImpl implements NoticeMngService {

	@Autowired
	private NoticeMsgDao noticeMsgDao;
	@Autowired
	private CustInfoDao custInfoDao;
	@Autowired
	private NoticeMsgRecordDao noticeMsgRecordDao;
	@Autowired
	private TimerDoDao timerDoDao;
	public void saveNoticeMsg(NoticeMsg noticeMsg) {
		noticeMsg.setCreateTime(new Date());
		noticeMsgDao.save(noticeMsg);
		//添加到任务
		this.addTimerDo(noticeMsg);
		return;
//		List<CustInfo> list = custInfoDao.findAll();
//		for (int i = 0; i < list.size(); i++) {
//			NoticeMsgRecord record = new NoticeMsgRecord();
//			record.setCustId(list.get(i).getId());
//			record.setCreateTime(DateUtil.getCurrent());
//			record.setRead(Symbol.NO);
//			record.setSubject(noticeMsg.getSubject());
//			record.setContent(noticeMsg.getContent());
//			noticeMsgDao.save(record);
//			if (i%100 == 0) {
//				noticeMsgDao.flush();
//			}
//		}
	}

	private void addTimerDo(NoticeMsg noticeMsg) {
		TimerDo timerDo = new TimerDo();
		timerDo.setBeanName("addNoticeTask");
		timerDo.setBeanNameCh("添加系统消息");
		timerDo.setPara1(Long.parseLong(noticeMsg.getId()));
		timerDo.setTriggerDate(DateUtil.getCurrentDate());
		timerDo.setTriggerTime(DateUtil.getCurrentTime());
		timerDo.setState(TimerDo.INIT);
		timerDoDao.save(timerDo);
	}

	public void updateMsgRecord(NoticeMsgRecord record) {
		noticeMsgRecordDao.update(record);
	}
	
	public void saveNoticeRecord(NoticeMsg noticeMsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", CustState.NORMAL.getValue());
		map.put("branchNo", noticeMsg.getBranchNo());
		map.put("custType", noticeMsg.getCustType());
		map.put("busiType", noticeMsg.getBusiType());
		int pageNumber = 1;
		int pageSize = 100;
		Pager pager = new Pager(pageNumber, 100);
		List<CustInfo> list = custInfoDao.findByParamsForNotice(map, pager);
		while (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				NoticeMsgRecord record = new NoticeMsgRecord();
				record.setCustId(list.get(i).getId());
				record.setCreateTime(DateUtil.getCurrent());
				record.setRead(Symbol.NO);
				record.setNoticeMsgId(noticeMsg.getId());
				noticeMsgDao.save(record);
				if (i > 100 && i%100 == 0){
					noticeMsgDao.flush();
				}
			}
			//本次查出的不足100条，不再查询
			if (list.size() < pageSize) {
				break;
			}
			pageNumber++;
			pager = new Pager(pageNumber, pageSize);
			list = custInfoDao.findByParamsForNotice(map, pager);
		}
	}

	public void exeTimerDo(String timerDoId) {
		TimerDo timerDo = timerDoDao.findByIdWithLock(timerDoId);
		if (TimerDo.BUSINESS_SUCESS.equals(timerDo.getState())) {
			return;
		}
		Long para1 = timerDo.getPara1();
		String noticeMsgId = String.valueOf(para1);
		NoticeMsg noticeMsg = noticeMsgDao.findById(noticeMsgId);
		
		saveNoticeRecord(noticeMsg);
		timerDo.setState(TimerDo.BUSINESS_SUCESS);
		timerDoDao.update(timerDo);
	}

}
