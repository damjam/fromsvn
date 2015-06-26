package com.ylink.cim.busioper.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.busioper.dao.BillTrackDao;
import com.ylink.cim.common.state.BillTrackState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.domain.BillTrack;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;

@Component("billTrackDao")
public class BillTrackDaoImpl extends BaseDaoImpl implements BillTrackDao {

	public Paginater findPaginater(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from BillTrack t where 1=1");
		if (map.get("startCreateDate") != null) {
			helper.append("and t.createTime >= ?",
					DateUtil.string2Date(MapUtils.getString(map, "startCreateDate"), "yyyyMMdd"));
		}
		if (map.get("endCreateDate") != null) {
			helper.append("and t.createTime <= ?",
					DateUtil.string2Date(MapUtils.getString(map, "endCreateDate"), "yyyyMMdd"));
		}
		if (!BranchType.HQ_0000.getValue().equals(map.get("branchNo"))) {
			helper.append("and t.branchNo = ?", map.get("branchNo"));
		}
		helper.append("and houseSn = ?", MapUtils.getString(map, "houseSn"));
		helper.append("and ownerCel = ?", MapUtils.getString(map, "ownerCel"));
		helper.append("and ownerName = ?", MapUtils.getString(map, "ownerName"));
		helper.append("and state = ?", MapUtils.getString(map, "state"));
		if (MapUtils.getInteger(map, "leftDays") != null && MapUtils.getInteger(map, "leftDays") > 0) {
			helper.append("and leftDays <= ?", MapUtils.getInteger(map, "leftDays"));
		}
		helper.append("and billType = ?", MapUtils.getString(map, "billType"));
		helper.append("order by t.id desc");
		return getPageData(helper, pager);
	}

	protected Class getModelClass() {
		return BillTrack.class;
	}

	public List<BillTrack> findList(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from BillTrack t where 1=1");
		helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and houseSn = ?", MapUtils.getString(params, "houseSn"));
		helper.append("and billType = ?", MapUtils.getString(params, "billType"));
		if (!BranchType.HQ_0000.getValue().equals(params.get("branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		return super.getList(helper);
	}

	public int expireBillTrack(String houseSn, String billType, String branchNo) {
		QueryHelper helper = new QueryHelper();
		helper.append("update BillTrack t set state = ? where 1=1", BillTrackState.EXPIRED.getValue());
		helper.append("and houseSn = ?", houseSn);
		helper.append("and billType = ?", billType);
		helper.append("and state = ?", BillTrackState.VALID.getValue());
		helper.append("and branchNo = ?", branchNo);
		return super.execute(helper);
	}

}
