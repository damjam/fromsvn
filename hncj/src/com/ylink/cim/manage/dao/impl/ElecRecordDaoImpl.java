package com.ylink.cim.manage.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.manage.dao.ElecRecordDao;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.domain.ElecRecord;
import com.ylink.cim.manage.domain.HouseInfo;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.SpringContext;
@Repository("elecRecordDao")
public class ElecRecordDaoImpl extends BaseDaoHibernateImpl implements ElecRecordDao{
	public Paginater findRecordPager(Map<String, Object> params, Pager pager){
		QueryHelper helper = new QueryHelper();
		helper.append("from ElecRecord t where 1=1");
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startCreateDate"))) {
			helper.append("and createDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startCreateDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endCreateDate"))) {
			helper.append("and createDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endCreateDate")));
		}
		helper.append("and curRecordDate >= ?", MapUtils.getString(params, "startRecordDate"));
		helper.append("and curRecordDate <= ?", MapUtils.getString(params, "endRecordDate"));
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		helper.append("order by t.createDate desc");
		Paginater paginater = super.getPageData(helper, pager);
		Collections.sort(paginater.getList(), new java.util.Comparator() {
			HouseInfoDao houseInfoDao = (HouseInfoDao)SpringContext.getService("houseInfoDao");
			public int compare(Object o1, Object o2) {
				try {
					ElecRecord record1 = (ElecRecord) o1;
					ElecRecord record2 = (ElecRecord) o2;
					HouseInfo h1 = houseInfoDao.findById(record1.getHouseSn());
					HouseInfo h2 = houseInfoDao.findById(record2.getHouseSn());
					if (!h1.getBuildingNo().equals(h2.getBuildingNo())) {
						return Integer.parseInt(h1.getBuildingNo()) - Integer.parseInt(h2.getBuildingNo());
					}
					if (!h1.getUnitNo().equals(h2.getUnitNo())) {
						return Integer.parseInt(h1.getUnitNo()) - Integer.parseInt(h2.getUnitNo());
					}
					if (!h1.getPosition().equals(h2.getPosition())) {
						return Integer.parseInt(h1.getPosition()) - Integer.parseInt(h2.getPosition());
					}
					return 0;
				} catch (Exception e) {
					return 0;
				}
				
			}
		});
		return paginater;
	}
	protected static List getOrderedList(List list) {
		return list;
	}
	@Override
	protected Class getModelClass() {
		return ElecRecord.class;
	}

	public ElecRecord findPreRecord(String houseSn) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ElecRecord where 1=1");
		helper.append("and houseSn = ?", houseSn);
		helper.append("order by recordMonth desc");
		List list = super.getList(helper);
		if (list.size() > 0) {
			return (ElecRecord)list.get(0);
		}else {
			return null;
		}
	}

	public List<ElecRecord> findPreRecords(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ElecRecord where 1=1");
		helper.append("and houseSn = ?", params.get("houseSn"));
		helper.append("and recordMonth = ?", params.get("recordMonth"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		return super.getList(helper);
	}
	public List<ElecRecord> findRecords(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ElecRecord where 1=1");
		helper.append("and state = ?", params.get("state"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		return super.getList(helper);
	}
	
	
}
