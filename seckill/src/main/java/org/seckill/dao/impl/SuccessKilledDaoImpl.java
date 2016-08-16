package org.seckill.dao.impl;

import java.util.List;

import org.seckill.dao.SuccessKilledDao;
import org.seckill.entity.SuccessKill;
import org.springframework.stereotype.Repository;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
@Repository("successKilledDao")
public class SuccessKilledDaoImpl extends BaseDaoImpl implements SuccessKilledDao {

	@Override
	protected Class getModelClass() {
		return SuccessKill.class;
	}

	@Override
	public List<SuccessKill> findList(Integer seckillId, String userPhone) {
		QueryHelper helper = new QueryHelper();
		helper.append("from SuccessKill where 1=1");
		helper.append("and seckillId = ?", seckillId);
		helper.append("and userPhone = ?", userPhone);
		return super.getList(helper);
	}

	
}
