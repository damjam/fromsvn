package org.seckill.service.impl;

import org.seckill.dao.SuccessKilledDao;
import org.seckill.service.SuccessKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("successKillService")
public class SuccessKillServiceImpl implements SuccessKillService {

	@Autowired
	private SuccessKilledDao successKilledDao;
	
	public int insertSuccessKilled(Long seckillId, String userPhone) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
