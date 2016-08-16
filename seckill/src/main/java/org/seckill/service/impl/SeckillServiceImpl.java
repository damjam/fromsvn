package org.seckill.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKill;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flink.util.DateUtil;
@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {

	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	public int reduceNumber(Long seckillId, Date killTime) {
		return 0;
	}

	@Override
	public Exposer exportSeckillUrl(Integer id) {
		Seckill seckill = seckillDao.findById(id);
		if(seckill == null){
			Exposer exposer = new Exposer();
			exposer.setExposed(false);
			exposer.setSeckillId(id);
			return exposer;
		}
		Date startTime = seckill.getBeginTime();
		Date endTime = seckill.getEndTime();
		Date now = DateUtil.getCurrent();
		if(now.before(startTime) || now.after(endTime)){
			Exposer exposer = new Exposer();
			exposer.setExposed(false);
			exposer.setSeckillId(id);
			exposer.setStart(startTime.getTime());
			exposer.setEnd(endTime.getTime());
			exposer.setNow(now.getTime());
			return exposer;
		}
		
		
		Exposer exposer = new Exposer();
		exposer.setExposed(true);
		exposer.setMd5(getMD5(id));
		exposer.setSeckillId(id);
		return exposer;
	}
	
	public SeckillExcution executeSeckill(Integer id, String userPhone, String md5){
		if(StringUtils.isEmpty(md5) || StringUtils.equals(md5, getMD5(id))){
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
		SeckillExcution seckillExcution = new SeckillExcution();
		seckillExcution.setSeckillId(id);
		seckillExcution.setState(1);
		seckillExcution.setStateInfo("秒杀成功");
		
		SuccessKill successKill = new SuccessKill();
		successKill.setCreateTime(DateUtil.getCurrent());
		successKill.setUserPhone(userPhone);
		successKill.setState("1");
		successKill.setSeckillId(id);
		successKilledDao.save(successKill);
		seckillExcution.setSuccessKill(successKill);
		return seckillExcution;
	}
	
	private String getMD5(Integer id){
		String base = id+"/"+ "ajskdkfl";
	    String md5 = DigestUtils.md2Hex(base);
	    return base;
	}

}
