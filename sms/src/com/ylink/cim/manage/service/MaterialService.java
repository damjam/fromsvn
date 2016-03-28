package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.Material;

import flink.etc.BizException;

public interface MaterialService {

	void add(Material material, UserInfo userInfo) throws BizException;

	void update(Material material, UserInfo userInfo) throws BizException;
}
