package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.manage.dao.ContactDao;
import com.ylink.cim.manage.domain.Contact;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("contactDao")
public class ContactDaoImpl extends BaseDaoImpl implements ContactDao {

	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from Contact t where 1=1");
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and contactName like ?", MapUtils.getString(params, "contactName"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		helper.append("and remark like ?", MapUtils.getString(params, "remark"));
		helper.append("and mobile like ?", MapUtils.getString(params, "mobile"));
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	@Override
	protected Class getModelClass() {
		return Contact.class;
	}

	@Override
	public List<Contact> findList(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from Contact t where 1=1");
		helper.append("and mobile = ?", MapUtils.getString(params, "mobile"));
		helper.append("and id <> ?", MapUtils.getString(params, "excludeId"));
		return super.getList(helper);
	}

	
}
