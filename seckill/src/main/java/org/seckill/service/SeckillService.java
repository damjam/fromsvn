package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;

import flink.etc.BizException;

public interface SeckillService {


	Exposer exportSeckillUrl(Integer id);
	
	SeckillExecution executeSeckill(Integer id, String userPhone, String md5) throws BizException;
}
