package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.Account;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface AccountDao extends BaseDao {

	public Paginater findPager(Map<String, Object> params, Pager pager);

	public List<String> findAcctByHouseSn(List<String> houseSns);

	public List<Account> findList(Map<String, Object> params);

}
