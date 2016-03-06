package com.ylink.cim.manage.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.state.RepairState;
import com.ylink.cim.manage.dao.RepairDao;
import com.ylink.cim.manage.dao.RepairTrackDao;
import com.ylink.cim.manage.domain.Repair;
import com.ylink.cim.manage.domain.RepairTrack;
import com.ylink.cim.manage.service.RepairService;

import flink.IdFactoryHelper;
import flink.etc.BizException;
import flink.util.DateUtil;
@Component("repairService")
public class RepairServiceImpl implements RepairService {
	@Autowired
	private RepairDao repairDao;
	@Autowired
	private RepairTrackDao repairTrackDao;
	
	@Override
	public void save(Repair model, UserInfo sessionUser) throws BizException {
		model.setBranchNo(sessionUser.getBranchNo());
		model.setCreateDate(DateUtil.getCurrent());
		model.setCreateUser(sessionUser.getUserName());
		model.setId(IdFactoryHelper.getId(Repair.class));
		if(StringUtils.isBlank(model.getProcessor())){
			model.setState(RepairState.UNDER_DEAL.getValue());
		}else {
			model.setState(RepairState.DEALING.getValue());
		}
		repairDao.save(model);
		if (StringUtils.isNotBlank(model.getProcessor())) {//处理中
			RepairTrack repairTrack = new RepairTrack();
			repairTrack.setProcessor(model.getProcessor());
			repairTrack.setCreateDate(DateUtil.getCurrent());
			repairTrack.setRepairId(model.getId());
			repairTrack.setCreateUser(sessionUser.getUserName());
			repairTrack.setId(IdFactoryHelper.getId(RepairTrack.class));
			repairTrackDao.save(repairTrack);
		}
	}

	@Override
	public void delete(String id) throws BizException {
		repairDao.deleteById(id);
		repairTrackDao.deleteByPepairId(id);
	}

	@Override
	public void cancel(String id, UserInfo userInfo) throws BizException {
		Repair repair = repairDao.findByIdWithLock(id);
		repair.setFeedback("已撤销");
		Date date = DateUtil.getCurrent();
		repair.setFeedbackDate(date);
		RepairTrack repairTrack = new RepairTrack();
		repairTrack.setFeedback("已撤销");
		repairTrack.setCreateDate(date);
		repairTrack.setFeedbackDate(date);
		repairTrack.setId(IdFactoryHelper.getId(RepairTrack.class));
		repairTrackDao.save(repairTrack);
	}

	@Override
	public void addTrack(Repair model, UserInfo sessionUser) throws BizException {
		RepairTrack track = model.getTrack();
		String result = track.getResult();
		Repair repair = repairDao.findByIdWithLock(model.getId());
		if ("00".equals(result) || "01".equals(result)) {
			RepairTrack repairTrack = repairTrackDao.findLast(model.getId());
			if (repairTrack == null) {
				repairTrack = new RepairTrack();
			}else {
				repairTrackDao.lock(repairTrack, LockMode.PESSIMISTIC_WRITE);
			}
			repairTrack.setFeedback(track.getFeedback());
			repairTrack.setFeedbackDate(DateUtil.getCurrent());
			if (StringUtils.isEmpty(repairTrack.getRemark())) {
				repairTrack.setRemark(track.getRemark());
			}else {
				repairTrack.setRemark(repairTrack.getRemark()+","+track.getRemark());
			}
			repairTrackDao.update(repairTrack);
			if ("00".equals(result)) {
				repair.setState(RepairState.DEALED.getValue());
			}
			repair.setFeedback(track.getFeedback());
			repair.setFeedbackDate(DateUtil.getCurrent());
			repairDao.update(repair);
		}else if("02".equals(track.getResult())){//重新指派
			//重新指派
			track.setId(IdFactoryHelper.getId(RepairTrack.class));
			track.setCreateDate(DateUtil.getCurrent());
			track.setCreateUser(sessionUser.getUserName());
			track.setProcessor(track.getProcessor());
			track.setRepairId(model.getId());
			repairTrackDao.save(track);
			repair.setProcessor(track.getProcessor());
			Date feedbackDate = DateUtil.getCurrent();
			String feedback = "重新指派责任人";
			repair.setFeedbackDate(feedbackDate);
			repair.setFeedback(feedback);
			repairDao.update(repair);
			RepairTrack lastTrack = repairTrackDao.findLast(model.getId());
			lastTrack.setFeedback(feedback);
			lastTrack.setFeedbackDate(DateUtil.getCurrent());
			repairTrackDao.update(lastTrack);
		}
	}
	
}
