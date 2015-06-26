package com.ylink.cim.busioper.service;

import com.ylink.cim.busioper.domain.NoticeMsg;
import com.ylink.cim.busioper.domain.NoticeMsgRecord;

import flink.etc.BizException;

public interface NoticeMngService {

	void saveNoticeMsg(NoticeMsg noticeMsg) throws BizException;

	void updateMsgRecord(NoticeMsgRecord record) throws BizException;

	void exeTimerDo(String timerDoId) throws BizException;
}
