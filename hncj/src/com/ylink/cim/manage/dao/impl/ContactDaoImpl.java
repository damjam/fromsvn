package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.manage.dao.ContactDao;
import com.ylink.cim.manage.domain.MerchantInfo;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("contactDao")
public class ContactDaoImpl extends BaseDaoHibernateImpl implements ContactDao {

	@Override
	protected Class getModelClass() {
		return MerchantInfo.class;
	}

	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from MerchantInfo t where 1=1");
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and name like ?", MapUtils.getString(params, "name"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	
}
