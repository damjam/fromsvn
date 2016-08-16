package org.seckill.dao;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class SeckillDaoTest {
	
	@Resource
	private SeckillDao seckillDao;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		Seckill seckill = seckillDao.findById(1);
		System.out.println(ReflectionToStringBuilder.toString(seckill));
	}
	@Test
	public void test2() {
		Seckill seckill = seckillDao.findById(2);
		System.out.println(ReflectionToStringBuilder.toString(seckill));
	}
}
