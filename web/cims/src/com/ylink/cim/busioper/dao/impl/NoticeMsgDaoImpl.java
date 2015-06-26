package com.ylink.cim.busioper.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.busioper.dao.NoticeMsgDao;
import com.ylink.cim.busioper.domain.NoticeMsg;
import com.ylink.cim.common.type.BranchType;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;

@Component("noticeMsgDao")
public class NoticeMsgDaoImpl extends BaseDaoHibernateImpl implements NoticeMsgDao {

	public Paginater findPaginater(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from NoticeMsg t where 1=1");
		if (map.get("startCreateDate") != null) {
			helper.append("and t.createTime >= ?", DateUtil.string2Date(MapUtils.getString(map, "startCreateDate"), "yyyyMMdd"));
		}
		if (map.get("endCreateDate") != null) {
			helper.append("and t.createTime <= ?", DateUtil.string2Date(MapUtils.getString(map, "endCreateDate"), "yyyyMMdd"));
		}
		if (!BranchType.HQ_0000.getValue().equals(map.get("branchNo"))) {
			helper.append("and t.branchNo = ?", map.get("branchNo"));
		}
		helper.append("and t.subject like ?", (String)map.get("subject"), MatchMode.ANYWHERE);
		helper.append("order by t.createTime desc");
		return getPageData(helper, pager);
	}
	protected Class getModelClass() {
		return NoticeMsg.class;
	}

	
}
