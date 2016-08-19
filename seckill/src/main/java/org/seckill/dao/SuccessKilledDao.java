package org.seckill.dao;

import java.util.List;

import org.seckill.entity.SuccessKilled;

import flink.hibernate.BaseDao;

public interface SuccessKilledDao extends BaseDao{

	List<SuccessKilled> findList(Integer id, String userPhone);
}
