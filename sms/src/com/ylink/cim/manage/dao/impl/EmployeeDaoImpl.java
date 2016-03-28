package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.EmployeeDao;
import com.ylink.cim.manage.domain.Employee;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("employeeDao")
public class EmployeeDaoImpl extends BaseDaoImpl implements EmployeeDao {

	@Override
	protected Class getModelClass() {
		return Employee.class;
	}

	@Override
	public Paginater findPager(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from Employee where 1=1");
		helper.append("and state = ?", MapUtils.getString(map, "state"));
		helper.append("and name like ?", MapUtils.getString(map, "name"), MatchMode.ANYWHERE);
		helper.append("and degree = ?", MapUtils.getString(map, "degree"));
		helper.append("and branchNo = ?", MapUtils.getString(map, "branchNo"));
		helper.append("and tel like ?", MapUtils.getString(map, "tel"), MatchMode.START);
		helper.append("and gender = ?", MapUtils.getString(map, "gender"));
		helper.append("and position = ?", MapUtils.getString(map, "position"));
		helper.append("and branchNo = ?", MapUtils.getString(map, "branchNo"));
		helper.orderBy("id", "desc");
		return super.getPageData(helper, pager);
	}

}
