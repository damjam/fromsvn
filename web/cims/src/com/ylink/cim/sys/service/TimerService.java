package com.ylink.cim.sys.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.sys.domain.Timer;

import flink.etc.BizException;

public interface TimerService {

	public void delete(String id) throws BizException;

	public void save(Timer timer, UserInfo userInfo) throws BizException;

	public void update(Timer time, UserInfo userInfo) throws BizException;
}
