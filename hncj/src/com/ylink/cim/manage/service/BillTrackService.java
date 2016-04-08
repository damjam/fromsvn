package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.BillTrack;

import flink.etc.BizException;

public interface BillTrackService {

	void exeTimerDo(String timerDoId) throws BizException;

	void addBillTrack(String houseSn, String billType, String billId, String expireDate, String ownerName,
			String ownerCel, String branchNo) throws BizException;

	void expirePreTracks(String houseSn, String billType, String branchNo) throws BizException;

	void delete(String id, UserInfo userInfo) throws BizException;

	void add(BillTrack billTrack, UserInfo userInfo) throws BizException;

	void sendNotice(String id) throws BizException;

	void discard(String id, UserInfo userInfo) throws BizException;
}
