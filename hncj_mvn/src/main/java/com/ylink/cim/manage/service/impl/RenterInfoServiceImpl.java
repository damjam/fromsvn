package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.state.RenterState;
import com.ylink.cim.manage.dao.RenterInfoDao;
import com.ylink.cim.manage.domain.RenterInfo;
import com.ylink.cim.manage.service.RentalHouseService;
import com.ylink.cim.manage.service.RenterInfoService;

import flink.IdFactoryHelper;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
@Component("renterInfoService")
public class RenterInfoServiceImpl implements RenterInfoService {
	@Autowired
	private RenterInfoDao renterInfoDao;
	@Autowired
	private RentalHouseService rentalHouseService;
	@Override
	public void save(RenterInfo renterInfo, UserInfo userInfo) throws BizException {
		renterInfo.setCreateDate(DateUtil.getCurrent());
		renterInfo.setBranchNo(userInfo.getBranchNo());
		renterInfo.setId(IdFactoryHelper.getId(RenterInfo.class));
		renterInfoDao.save(renterInfo);
		rentalHouseService.occupy(renterInfo.getHouseSn());
	}

	@Override
	public void update(RenterInfo renterInfo) throws BizException {
		renterInfoDao.update(renterInfo);
	}

	@Override
	public void checkout(String id) throws BizException {
		RenterInfo renterInfo = renterInfoDao.findByIdWithLock(id);
		renterInfo.setState(RenterState.LIVEOUT.getValue());//搬离
		rentalHouseService.release(renterInfo.getHouseSn());
	}

	@Override
	public void delete(String id) throws BizException {
		RenterInfo renterInfo = renterInfoDao.findByIdWithLock(id);
		Assert.notNull(renterInfo, "租客信息不存在");
		Assert.equals(RenterState.LIVEOUT.getValue(), renterInfo.getState(), "只能删除状态为已搬离的租客信息");
		renterInfoDao.deleteById(id);
	}
	
}
