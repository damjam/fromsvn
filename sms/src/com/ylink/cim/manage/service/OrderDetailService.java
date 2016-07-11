package com.ylink.cim.manage.service;

import com.ylink.cim.manage.domain.OrderDetail;

import flink.etc.BizException;

public interface OrderDetailService {

	void save(OrderDetail model);

	void returnGoods(String id, Double refundAmt) throws BizException;

}
