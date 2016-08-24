package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.RentRecord;

public interface RentRecordService {

	void save(RentRecord rentRecord, UserInfo sessionUser);

	void update(RentRecord rentRecord);
}
