package com.ylink.cim.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.BranchDictDao;
import com.ylink.cim.admin.domain.BranchDict;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("branchDictDao")
public class BranchDictDaoImpl extends BaseDaoImpl implements BranchDictDao {

	@Override
	protected Class getModelClass() {

		return BranchDict.class;
	}

	@Override
	public Paginater getDictPageList(BranchDict branchDict, Pager pager) {

		QueryHelper helper = new QueryHelper();
		helper.append("from BranchDict sd");
		helper.append("where 1=1");
		helper.append("and sd.id.dictValue=?", branchDict.getId().getDictValue());
		helper.append("and sd.id.dictType=?", branchDict.getId().getDictType());
		helper.append("and sd.dictName=?", branchDict.getDictName());
		helper.append("order by sd.id.dictType, sd.id.dictValue");
		return super.getPageData(helper, pager);
	}

	@Override
	public List<BranchDict> getDictByDictType(String dictType) {

		QueryHelper helper = new QueryHelper();
		helper.append("from BranchDict sd");
		helper.append("where 1=1");
		helper.append("and sd.id.dictType=?", dictType);

		return super.getList(helper);
	}

	@Override
	public List<BranchDict> findByParam(Map<String, Object> map) {
		QueryHelper helper = new QueryHelper();
		helper.append("from BranchDict t where 1=1");
		helper.append("and t.id.dictType = ?", map.get("dictType"));
		helper.append("and t.id.dictValue = ?", map.get("dictValue"));
		helper.append("and t.remark = ?", map.get("remark"));
		helper.append("order by t.id.dictType, t.id.dictValue");
		return super.getList(helper);
	}

}
