package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.dao.ContactDao;
import com.ylink.cim.manage.domain.Contact;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("contactDao")
public class ContactDaoImpl extends BaseDaoHibernateImpl implements ContactDao {

	@Override
	protected Class getModelClass() {
		return Contact.class;
	}

	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from Contact t where 1=1");
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and contactName like ?", MapUtils.getString(params, "contactName"));
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		helper.append("and remark like ?", MapUtils.getString(params, "remark"));
		helper.append("and mobile like ?", MapUtils.getString(params, "mobile"));
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	
}
