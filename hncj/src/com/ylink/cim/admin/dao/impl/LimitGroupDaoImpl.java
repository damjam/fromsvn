package com.ylink.cim.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.LimitGroupDao;
import com.ylink.cim.admin.domain.LimitGroup;
import com.ylink.cim.admin.domain.LimitGroupInfo;
import com.ylink.cim.admin.domain.Privilege;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;

@Component("limitGroupDao")
public class LimitGroupDaoImpl extends BaseDaoHibernateImpl implements LimitGroupDao {

	private List<LimitGroup> convert(List list) {

		List<LimitGroup> limitGroups = new ArrayList<LimitGroup>();
		if (null == list) {
			return limitGroups;
		}

		for (int i = 0; i < list.size(); i++) {
			Object[] objects = (Object[]) list.get(i);

			LimitGroup limitGroup = (LimitGroup) objects[0];
			LimitGroupInfo limitGroupInfo = (LimitGroupInfo) objects[1];
			Privilege p = (Privilege) objects[2];

			limitGroup.setLimitGroupName(limitGroupInfo.getLimitGroupName());
			limitGroup.setLimitName(p.getLimitName());
			limitGroup.setPid(p.getParent());

			limitGroups.add(limitGroup);
		}

		return limitGroups;
	}

	public void deleteByLimitGroupId(String limitGroupId) {

		QueryHelper helper = new QueryHelper();
		helper.append("delete from LimitGroup lg");
		helper.append("where 1=1 ");
		helper.append("and lg.id.limitGroupId = ?", limitGroupId);

		super.execute(helper);
	}

	public List<LimitGroup> getByLimitGroupId(String limitGroupId) {

		QueryHelper helper = new QueryHelper();
		helper.append("from LimitGroup lg ");
		helper.append("where 1=1 ");
		helper.append("and lg.id.limitGroupId = ?", limitGroupId);

		return super.getList(helper);
	}

	public List<LimitGroup> getLimitGroup(LimitGroup limitGroup) {

		QueryHelper helper = new QueryHelper();
		helper.append("from LimitGroup lg,LimitGroupInfo lgi,Privilege p");
		helper.append("where 1=1");
		helper.append("and lg.id.limitGroupId=lgi.limitGroupId");
		helper.append("and lg.id.limitId=p.limitId");
		helper.append("and lg.id.limitGroupId=?", limitGroup.getId().getLimitGroupId());

		List list = super.getList(helper);
		List<LimitGroup> limitGroups = convert(list);

		return limitGroups;
	}

	protected Class<LimitGroup> getModelClass() {
		return LimitGroup.class;
	}

}