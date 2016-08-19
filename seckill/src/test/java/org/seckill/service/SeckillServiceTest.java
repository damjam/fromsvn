package org.seckill.service;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flink.etc.BizException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class SeckillServiceTest {

	
	@Autowired
	private SeckillService seckillService;
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSeckill() {
		Integer seckillId = 1;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()) {//ø…“‘√Î…±
			System.out.println(ReflectionToStringBuilder.toString(exposer));
			String userPhone = "13876734841";
			String md5 = exposer.getMd5();
			try{
				SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
				System.out.println(ReflectionToStringBuilder.toString(seckillExecution));
				
			}catch(RepeatKillException e){
				System.out.println(e.getMessage());
			}catch(SeckillCloseException e){
				System.out.println(e.getMessage());
			}catch(BizException e){
				e.printStackTrace();
			}
		}else {
			System.out.println(ReflectionToStringBuilder.toString(exposer));
		}
	}

}
