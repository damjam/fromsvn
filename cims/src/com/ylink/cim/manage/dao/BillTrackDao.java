package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.BillTrack;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface BillTrackDao extends BaseDao {

	Paginater findPaginater(Map<String, Object> map, Pager pager);

	List<BillTrack> findList(Map<String, Object> params);

	public int expireBillTrack(String houseSn, String billType, String branchNo);
}
