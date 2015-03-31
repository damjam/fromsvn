package com.ylink.cim.busioper.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.busioper.dao.BillTrackDao;
import com.ylink.cim.busioper.service.BillTrackService;
import com.ylink.cim.common.state.BillTrackState;
import com.ylink.cim.manage.domain.BillTrack;
import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;
import com.ylink.cim.user.domain.UserInfo;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.LogUtils;

@Component("billTrackService")
public class BillTrackServiceImpl implements BillTrackService {

	@Autowired
	private TimerDoDao timerDoDao;
	@Autowired
	private BillTrackDao billTrackDao;
	@Autowired
	private IdFactoryService idFactoryService;

	public void exeTimerDo(String timerDoId) {
		TimerDo timerDo = timerDoDao.findByIdWithLock(timerDoId);
		if (TimerDo.BUSINESS_SUCESS.equals(timerDo.getState())) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", BillTrackState.VALID.getValue());
		List<BillTrack> list = billTrackDao.findList(params);
		for (int i = 0; i < list.size(); i++) {
			BillTrack track = list.get(i);
			billTrackDao.lock(track, LockMode.UPGRADE);
			Integer leftDays = track.getLeftDays();
			Integer overDays = track.getOverDays();
			if (leftDays > 0) {
				track.setLeftDays(leftDays-1);
			}
			if (overDays > 0) {
				track.setOverDays(overDays+1);
			}
			billTrackDao.update(track);
			if (i % 100 == 0) {
				billTrackDao.flushAndClear();
			}
		}
		timerDo.setState(TimerDo.BUSINESS_SUCESS);
		timerDoDao.update(timerDo);
	}


	public void addBillTrack(String houseSn, String billType, String billId, String expireDate, String ownerName, String ownerCel, String branchNo) throws BizException {
		Date endDate = DateUtil.getDayEndByYYYMMDD(expireDate);
		Date today = DateUtil.getCurrent();
		int leftDays = DateUtil.getDateDiffDays(endDate, today);
		if (leftDays <= 0) {
			return;
		}
		BillTrack track = new BillTrack();
		track.setBillType(billType);
		String id = idFactoryService.generateId(Constants.BILL_TRACK_ID);
		track.setId(id);
		track.setHouseSn(houseSn);
		track.setOwnerName(ownerName);
		track.setOwnerCel(ownerCel);
		track.setBillType(billType);
		track.setLeftDays(leftDays);
		track.setOverDays(0);
		track.setCreateDate(today);
		track.setBillId(billId);
		track.setExpireDate(expireDate);
		track.setState(BillTrackState.VALID.getValue());
		track.setBranchNo(branchNo);
		track.setNoticeTimes(0);
		billTrackDao.save(track);
	}

	public void expirePreTracks(String houseSn, String billType, String branchNo) {
		billTrackDao.expireBillTrack(houseSn, billType, branchNo);
	}

	public void delete(String id, UserInfo userInfo) throws BizException {
		billTrackDao.deleteById(id);
	}


	public void add(BillTrack billTrack, UserInfo userInfo) throws BizException {
		// TODO Auto-generated method stub
		
	}


	public void sendNotice(String id) throws BizException{
		//·¢ËÍ¶ÌÐÅ
		BillTrack track = billTrackDao.findById(id);
		billTrackDao.lock(track, LockMode.UPGRADE);
		String cel = track.getOwnerCel();
		String content = LogUtils.r(Constants.SERVICE_BILL_DUE_MSG, track.getExpireDate());
		track.setNoticeTimes(track.getNoticeTimes()+1);
		billTrackDao.update(track);
		//SendMobilMsgUtil.sendMsgFw(cel, content);
	}


	public void discard(String id, UserInfo userInfo) throws BizException {
		BillTrack track = billTrackDao.findById(id);
		track.setState(BillTrackState.EXPIRED.getValue());
		billTrackDao.update(track);
	}
	
}
