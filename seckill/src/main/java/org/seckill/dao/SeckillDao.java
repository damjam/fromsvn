package org.seckill.dao;

import java.util.List;

import org.seckill.entity.Seckill;

import flink.hibernate.BaseDao;


public interface SeckillDao extends BaseDao{

	List<Seckill> queryAll(int offset, int limit);
	
	List<Seckill> getSeckillList();
	
}
