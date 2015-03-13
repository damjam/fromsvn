package com.ylink.cim.busioper.service;

import com.ylink.cim.busioper.domain.NoticeMsg;
import com.ylink.cim.busioper.domain.NoticeMsgRecord;

public interface NoticeMngService {

	void saveNoticeMsg(NoticeMsg noticeMsg);
	
	void updateMsgRecord(NoticeMsgRecord record);
	
	void exeTimerDo(Long timerDoId); 
}
