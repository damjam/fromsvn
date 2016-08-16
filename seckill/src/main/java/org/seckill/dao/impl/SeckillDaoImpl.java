package org.seckill.dao.impl;

import java.util.List;

import org.hibernate.annotations.Parameter;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.stereotype.Repository;

import flink.hibernate.BaseDaoImpl;
@Repository("seckillDao")
public class SeckillDaoImpl extends BaseDaoImpl implements SeckillDao {

	public List<Seckill> queryAll(int offset, int limit) {
		return null;
	}

	@Override
	protected Class getModelClass() {
		return Seckill.class;
	}

	@Override
	public List<Seckill> getSeckillList() {
		return this.queryAll(0, 4);
	}
    
	
}
