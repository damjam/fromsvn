package com.ylink.cim.manage.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.state.OwnerState;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.domain.Account;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Repository("accountDao")
public class AccountDaoImpl extends BaseDaoHibernateImpl implements AccountDao {

	@Override
	protected Class getModelClass() {
		return Account.class;
	}

	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from Account t where 1=1");
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and houseSn = ?", MapUtils.getString(params, "houseSn"));
		helper.append("and ownerName = ?", MapUtils.getString(params, "ownerName"));
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	public List<String> findAcctByHouseSn(List<String> houseSns) {
		if (CollectionUtils.isEmpty(houseSns)) {
			return Collections.EMPTY_LIST;
		}
		QueryHelper hql = new QueryHelper();
		hql.append("from Account t where t.state = ?", OwnerState.NORMAL.getValue());
		hql.append("and houseSn in ?", houseSns.toArray(new String[0]));

		return getList(hql);
	}

}
