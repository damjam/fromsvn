package org.seckill.service;

import java.util.Date;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

public interface SeckillService {

	int reduceNumber(Long seckillId, Date killTime);

	Exposer exportSeckillUrl(Integer id);
	
	SeckillExcution executeSeckill(Integer id, String userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException;
}
