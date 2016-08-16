package org.seckill.dao;

import java.util.List;

import org.seckill.entity.SuccessKill;

import flink.hibernate.BaseDao;

public interface SuccessKilledDao extends BaseDao{

	List<SuccessKill> findList(Integer id, String userPhone);
}
