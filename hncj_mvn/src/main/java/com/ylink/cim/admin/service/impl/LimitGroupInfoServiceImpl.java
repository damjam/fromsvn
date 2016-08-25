package com.ylink.cim.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.LimitGroupDao;
import com.ylink.cim.admin.dao.LimitGroupInfoDao;
import com.ylink.cim.admin.domain.LimitGroup;
import com.ylink.cim.admin.domain.LimitGroupId;
import com.ylink.cim.admin.domain.LimitGroupInfo;
import com.ylink.cim.admin.domain.SysDict;
import com.ylink.cim.admin.service.LimitGroupInfoService;

import flink.etc.BizException;
import flink.util.ExceptionUtils;
import flink.util.Pager;
import flink.util.Paginater;

@Component("limitGroupInfoService")
public class LimitGroupInfoServiceImpl implements LimitGroupInfoService {

	@Autowired
	private LimitGroupInfoDao limitGroupInfoDao;
	@Autowired
	private LimitGroupDao limitGroupDao;

	@Override
	public void deleteLimitGroupInfo(String limitGroupId) throws BizException {
		try {
			this.limitGroupInfoDao.deleteById(limitGroupId);
			this.limitGroupDao.deleteByLimitGroupId(limitGroupId);
		} catch (Exception e) {
			ExceptionUtils.logBizException(LimitGroupInfoServiceImpl.class,
					e.getMessage());
		}
	}

	@Override
	public List<LimitGroupInfo> getAll() throws BizException {

		return this.limitGroupInfoDao.getAll();
	}

	@Override
	public LimitGroupInfo getLimitGroupInfoById(String limitGroupId)
			throws BizException {

		return this.limitGroupInfoDao.findById(limitGroupId);
	}

	@Override
	public Paginater getLimitGroupInfoPageList(LimitGroupInfo limitGroupInfo,
			Pager pager) throws BizException {

		return this.limitGroupInfoDao.getLimitGroupInfoPageList(limitGroupInfo,
				pager);
	}

	@Override
	public List<SysDict> getSysDictNoLimitGroup() throws BizException {

		return this.limitGroupInfoDao.getSysDictNoLimitGroup();
	}

	@Override
	public void saveLimitGroup(LimitGroupInfo limitGroupInfo)
			throws BizException {

		if (null == limitGroupInfo.getLimitIds()
				|| limitGroupInfo.getLimitIds().length == 0) {
			ExceptionUtils.logBizException(LimitGroupInfoService.class,
					"请选择权限点");
		}

		try {
			this.limitGroupInfoDao.save(limitGroupInfo);
			this.saveLimitGroups(limitGroupInfo);

		} catch (Exception e) {
			ExceptionUtils.logBizException(LimitGroupInfoDao.class,
					e.getMessage());
			e.printStackTrace();
		}
	}

	private void saveLimitGroups(LimitGroupInfo limitGroupInfo) {
		for (int i = 0; i < limitGroupInfo.getLimitIds().length; i++) {
			LimitGroupId id = new LimitGroupId();
			id.setLimitGroupId(limitGroupInfo.getLimitGroupId());
			id.setLimitId(limitGroupInfo.getLimitIds()[i]);

			LimitGroup limitGroup = new LimitGroup();
			limitGroup.setId(id);
			this.limitGroupDao.save(limitGroup);
		}
	}

	public void setLimitGroupDao(LimitGroupDao limitGroupDao) {
		this.limitGroupDao = limitGroupDao;
	}

	public void setLimitGroupInfoDao(LimitGroupInfoDao limitGroupInfoDao) {
		this.limitGroupInfoDao = limitGroupInfoDao;
	}

	@Override
	public void updateLimitGroupInfo(LimitGroupInfo limitGroupInfo)
			throws BizException {

		try {
			this.limitGroupDao.deleteByLimitGroupId(limitGroupInfo
					.getLimitGroupId());

			saveLimitGroups(limitGroupInfo);
			limitGroupInfoDao.update(limitGroupInfo);
		} catch (Exception e) {
			ExceptionUtils.logBizException(LimitGroupInfoServiceImpl.class,
					e.getMessage());
		}
	}

}
