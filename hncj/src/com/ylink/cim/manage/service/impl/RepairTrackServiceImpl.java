package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.RepairTrackDao;
import com.ylink.cim.manage.service.RepairTrackService;
@Component("repairTrackService")
public class RepairTrackServiceImpl implements RepairTrackService {
	@Autowired
	private RepairTrackDao repairTrackDao;

	
}
