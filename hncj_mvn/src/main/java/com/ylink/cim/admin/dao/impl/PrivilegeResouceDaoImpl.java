package com.ylink.cim.admin.dao.impl;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.PrivilegeResourceDao;
import com.ylink.cim.admin.domain.PrivilegeResource;

import flink.etc.Symbol;
import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("privilegeResourceDao")
public class PrivilegeResouceDaoImpl extends BaseDaoImpl implements
		PrivilegeResourceDao {

	@Override
	protected Class getModelClass() {

		return PrivilegeResource.class;
	}

	@Override
	public Paginater getPrivilegeResourceList(
			PrivilegeResource privilegeResource, Pager pager) {

		QueryHelper helper = new QueryHelper();
		helper.append("from PrivilegeResource p ");
		helper.append("where 1=1");
		helper.append("and p.id=?", privilegeResource.getId());
		helper.append("and p.limitId=?", privilegeResource.getLimitId());
		helper.append("and p.url=?", privilegeResource.getUrl());
		helper.append("and p.param=?", privilegeResource.getParam());
		helper.append("and p.isEntry=?", privilegeResource.getIsEntry());

		return super.getPageData(helper, pager);
	}

	@Override
	public Paginater findPrivRes(PrivilegeResource privilegeResource,
			Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from PrivilegeResource where 1=1");
		helper.append("and limitId = ?", privilegeResource.getLimitId());
		helper.append("and url like ?", privilegeResource.getUrl(),
				MatchMode.ANYWHERE);
		helper.append("and param like ?", privilegeResource.getParam(),
				MatchMode.ANYWHERE);
		return super.getPageData(helper, pager);
	}

	@Override
	public boolean existPriRes(String limitId, Long id) {
		QueryHelper helper = new QueryHelper();
		helper.append("from PrivilegeResource where limitId = ?", limitId);
		if (id != null && id == 0) {
			id = null;
		}
		helper.append("and id <> ?", id);
		helper.append("and isEntry = ?", Symbol.YES);
		return super.getList(helper).size() > 0 ? true : false;
	}
}
