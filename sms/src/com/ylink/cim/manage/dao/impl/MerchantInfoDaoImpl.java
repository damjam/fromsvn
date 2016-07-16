package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.dao.MerchantInfoDao;
import com.ylink.cim.manage.domain.MerchantInfo;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Repository("merchantInfoDao")
public class MerchantInfoDaoImpl extends BaseDaoImpl implements MerchantInfoDao {

	@Override
	protected Class getModelClass() {
		return MerchantInfo.class;
	}

	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from MerchantInfo where 1=1");
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and mrname like ?", MapUtils.getString(params, "mrname"), MatchMode.ANYWHERE);
		helper.append("and mobile like ?", MapUtils.getString(params, "mobile"), MatchMode.START);
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	@Override
	public List<MerchantInfo> findList(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from MerchantInfo t where 1=1");
		helper.append("and id <> ?", MapUtils.getString(params, "id"));
		helper.append("and mrname = ?", MapUtils.getString(params, "mrname"));
		helper.append("and mobile = ?", MapUtils.getString(params, "mobile"));
		helper.append("and remark = ?", MapUtils.getString(params, "remark"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		helper.append("order by id desc");
		return super.getList(helper);
	}

	@Override
	public List<MerchantInfo> findByKeyword(String keyword, String branchNo) {
		QueryHelper helper = new QueryHelper();
		helper.append("from MerchantInfo t where 1=1");
		helper.append("and (mrname like ?", keyword, MatchMode.ANYWHERE);
		helper.append("or pinyin like ?", keyword, MatchMode.ANYWHERE);
		helper.append("or firstLetters like ?)", keyword, MatchMode.ANYWHERE);
		helper.append("and branchNo = ?", branchNo);
		return super.getList(helper);
	}
}
