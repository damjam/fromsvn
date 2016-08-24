package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.Contact;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface ContactDao extends BaseDao {

	public Paginater findPager(Map<String, Object> params, Pager pager);

	public List<Contact> findList(Map<String, Object> params);

}
