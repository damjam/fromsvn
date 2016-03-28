package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.dao.MaterialDao;
import com.ylink.cim.manage.domain.Material;
import com.ylink.cim.manage.service.MaterialService;
import com.ylink.cim.util.CopyPropertyUtil;

import flink.IdFactoryHelper;
import flink.etc.BizException;
import flink.util.DateUtil;
@Component("materialService")
public class MaterialServiceImpl implements MaterialService {
	@Autowired
	private MaterialDao materialDao;

	@Override
	public void add(Material material, UserInfo userInfo) throws BizException {
		material.setCreateDate(DateUtil.getCurrent());
		material.setCreateUser(userInfo.getUserName());
		material.setBranchNo(userInfo.getBranchNo());
		material.setId(IdFactoryHelper.getId(Material.class));
		materialDao.save(material);
	}

	@Override
	public void update(Material model, UserInfo userInfo) throws BizException {
		Material material = materialDao.findById(model.getId());
		CopyPropertyUtil.copyPropertiesIgnoreNull(model, material);
		material.setUpdateDate(DateUtil.getCurrent());
		material.setUpdateUser(userInfo.getUserName());
		materialDao.update(material);
	}
	
}
