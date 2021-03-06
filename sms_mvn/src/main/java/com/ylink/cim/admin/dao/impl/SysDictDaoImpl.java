package com.ylink.cim.admin.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.SysDictDao;
import com.ylink.cim.admin.domain.SysDict;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("sysDictDao")
public class SysDictDaoImpl extends BaseDaoImpl implements SysDictDao {

	@Override
	protected Class getModelClass() {

		return SysDict.class;
	}

	@Override
	public Paginater getSysDictPageList(SysDict sysDict, Pager pager) {

		QueryHelper helper = new QueryHelper();
		helper.append("from SysDict sd");
		helper.append("where 1=1");
		helper.append("and sd.id.dictValue=?", sysDict.getId().getDictValue());
		helper.append("and sd.id.dictType=?", sysDict.getId().getDictType());
		helper.append("and sd.dictName=?", sysDict.getDictName());
		helper.append("order by sd.id.dictType, sd.sort");
		return super.getPageData(helper, pager);
	}

	@Override
	public List<SysDict> getSysDictByDictType(String dictType) {

		QueryHelper helper = new QueryHelper();
		helper.append("from SysDict sd where 1=1");
		helper.append("and sd.id.dictType=?", dictType);
		helper.append("order by sd.id.dictType, sd.sort");
		return super.getList(helper);
	}

	@Override
	public List<SysDict> findByParam(Map<String, Object> map) {
		QueryHelper helper = new QueryHelper();
		helper.append("from SysDict t where 1=1");
		helper.append("and t.id.dictType = ?", map.get("dictType"));
		helper.append("and t.id.dictValue = ?", map.get("dictValue"));
		helper.append("and t.remark = ?", map.get("remark"));
		helper.append("order by t.id.dictType, t.sort");
		return super.getList(helper);
	}

}
