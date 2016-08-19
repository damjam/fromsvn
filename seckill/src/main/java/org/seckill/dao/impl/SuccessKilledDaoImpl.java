package org.seckill.dao.impl;

import java.util.List;

import org.seckill.dao.SuccessKilledDao;
import org.seckill.entity.SuccessKilled;
import org.springframework.stereotype.Repository;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
@Repository("successKilledDao")
public class SuccessKilledDaoImpl extends BaseDaoImpl implements SuccessKilledDao {

	@Override
	protected Class getModelClass() {
		return SuccessKilled.class;
	}

	@Override
	public List<SuccessKilled> findList(Integer seckillId, String userPhone) {
		QueryHelper helper = new QueryHelper();
		helper.append("from SuccessKilled where 1=1");
		helper.append("and seckillId = ?", seckillId);
		helper.append("and userPhone = ?", userPhone);
		return super.getList(helper);
	}

	
}
