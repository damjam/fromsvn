package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.dao.RepairDao;
import com.ylink.cim.manage.dao.RepairTrackDao;
import com.ylink.cim.manage.domain.Repair;
import com.ylink.cim.manage.service.RepairService;

import flink.etc.BizException;
import flink.util.DateUtil;
@Component("repairService")
public class RepairServiceImpl implements RepairService {
	@Autowired
	private RepairDao repairDao;

	private RepairTrackDao repairTrackDao;
	@Override
	public void save(Repair model, UserInfo sessionUser) throws BizException {
		model.setBranchNo(sessionUser.getBranchNo());
		model.setCreateDate(DateUtil.getCurrent());
		model.setCreateUser(sessionUser.getUserName());
		repairDao.save(model);
	}

	@Override
	public void delete(String id) throws BizException {
		repairDao.deleteById(id);
		repairTrackDao.deleteByPepairId(id);
	}
}
