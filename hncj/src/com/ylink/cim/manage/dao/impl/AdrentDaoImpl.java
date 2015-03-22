package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.dao.AdrentBillDao;
import com.ylink.cim.manage.dao.ContactDao;
import com.ylink.cim.manage.domain.AdrentBill;
import com.ylink.cim.manage.domain.Contact;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("contactDao")
public class AdrentDaoImpl extends BaseDaoHibernateImpl implements AdrentBillDao {

	@Override
	protected Class getModelClass() {
		return AdrentBill.class;
	}
	public Paginater findBillPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from AdrentBill where 1=1");
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and merchantName like ?", MapUtils.getString(params, "merchantName"));
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startChargeDate"))) {
			helper.append("and chargeDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startChargeDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endChargeDate"))) {
			helper.append("and chargeDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endChargeDate")));
		}
		helper.append("and remark like ?", MapUtils.getString(params, "remark"));
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	public Map<String, Object> findSumInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
