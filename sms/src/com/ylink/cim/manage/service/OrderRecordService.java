package com.ylink.cim.manage.service;

import com.ylink.cim.manage.domain.OrderRecord;

public interface OrderRecordService {

	void save(OrderRecord model);

	void delete(String id);

}
