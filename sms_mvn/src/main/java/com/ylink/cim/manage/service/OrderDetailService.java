package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.OrderDetail;

import flink.etc.BizException;

public interface OrderDetailService {

	void save(OrderDetail model);

	void returnGoods(String id, Double refundAmt, UserInfo userInfo) throws BizException;

	void sendOut(String id) throws BizException;

	void cancel(String id, Double refundAmt) throws BizException;

	void receive(String id) throws BizException;
}
