package org.seckill.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flink.etc.BizException;
import flink.util.DateUtil;
@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {

	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;

	@Override
	public Exposer exportSeckillUrl(Integer id) {
		Seckill seckill = seckillDao.findById(id);
		Exposer exposer = new Exposer();
		
		if(seckill == null){
			exposer.setExposed(false);
			exposer.setSeckillId(id);
			return exposer;
		}
		Date startTime = seckill.getBeginTime();
		Date endTime = seckill.getEndTime();
		Date now = DateUtil.getCurrent();
		exposer.setStart(startTime.getTime());
		exposer.setEnd(endTime.getTime());
		if(now.before(startTime) || now.after(endTime)){//时间不到货已结束
			exposer.setExposed(false);
			exposer.setSeckillId(id);
			exposer.setNow(now.getTime());
			return exposer;
		}
		
		exposer.setExposed(true);
		exposer.setMd5(getMD5(id));
		exposer.setSeckillId(id);
		return exposer;
	}
	public SeckillExecution executeSeckill(Integer id, String userPhone, String md5) throws BizException{
		if(StringUtils.isEmpty(md5) || !StringUtils.equals(md5, getMD5(id))){
			throw new SeckillException("请求错误");
		}
		Seckill seckill = seckillDao.findByIdWithLock(id);
		if(seckill.getNumber() <= 0){
			throw new SeckillCloseException("库存不足");
		}
		if(successKilledDao.findList(id, userPhone).size()>0){
			throw new SeckillCloseException("您已经秒杀过,不能再次秒杀");
		}
		seckill.setNumber(seckill.getNumber()-1);
		seckillDao.update(seckill);
		SeckillExecution seckillExcution = new SeckillExecution(id, SeckillStatEnum.SUCCESS);
		
		SuccessKilled successKilled = new SuccessKilled();
		successKilled.setCreateTime(DateUtil.getCurrent());
		successKilled.setUserPhone(userPhone);
		successKilled.setState("1");
		successKilled.setSeckillId(id);
		successKilledDao.save(successKilled);
		seckillExcution.setSuccessKilled(successKilled);
		return seckillExcution;
	}
	
	private String getMD5(Integer id){
		String base = id+"/"+ "ajskdkfl";
	    String md5 = DigestUtils.md2Hex(base);
	    return md5;
	}

}
