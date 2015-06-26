package com.ylink.cim.cust.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.state.CustState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.PushType;
import com.ylink.cim.cust.dao.CustInfoDao;
import com.ylink.cim.cust.domain.CustInfo;

import flink.etc.Symbol;
import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("custInfoDao")
public class CustInfoDaoImpl extends BaseDaoImpl implements CustInfoDao{
	/* (non-Javadoc)
	 * @see com.ylink.cim.cust.dao.impl.CustInfoDao#findByParams(java.util.Map)
	 */
	public List<CustInfo> findByParams(Map<String, Object> params) {
		if (params == null) {
			params = MapUtils.EMPTY_MAP;
		}
		QueryHelper helper = new QueryHelper();
		helper.append("from CustInfo t where 1=1");
		helper.append("and t.userId = ?", params.get("userId"));
		helper.append("and t.custType = ?", params.get("custType"));
		helper.append("and t.name like ?", params.get("name"));
		helper.append("and t.idCard = ?", params.get("idCard"));
		if (!BranchType.HQ_0000.getValue().equals(params.get("branchNo"))) {
			helper.append("and exists (select distinct(s.custId) from AipAcct s where s.custId = t.id and s.branchNo = ?)", params.get("branchNo"));
		}
		helper.append("and exists (select s.id from ServiceRecord s where s.custId = t.id and s.busiType = ?)", params.get("busiType"));
		return super.getList(helper);
	}
	/* (non-Javadoc)
	 * @see com.ylink.cim.cust.dao.impl.CustInfoDao#findByParamsForNotice(java.util.Map, flink.util.Pager)
	 */
	public List<CustInfo> findByParamsForNotice(Map<String, Object> params,
			Pager pager) {
		if (params == null) {
			params = MapUtils.EMPTY_MAP;
		}
		QueryHelper helper = new QueryHelper();
		helper.append("from CustInfo t where 1=1");
		helper.append("and t.userId = ?", params.get("userId"));
		helper.append("and t.custType = ?", params.get("custType"));
		helper.append("and t.name like ?", params.get("name"));
		helper.append("and t.idCard = ?", params.get("idCard"));
		if (!BranchType.HQ_0000.getValue().equals(params.get("branchNo"))) {
			helper.append("and exists (select distinct(s.custId) from AipAcct s where s.custId = t.id and s.branchNo = ?)", params.get("branchNo"));
		}
		helper.append("and exists (select s.id from ServiceRecord s where s.custId = t.id and s.busiType = ?)", params.get("busiType"));
		return super.getPageData(helper, pager).getList();
	}

	/* (non-Javadoc)
	 * @see com.ylink.cim.cust.dao.impl.CustInfoDao#findCustPager(com.ylink.cim.cust.domain.CustInfo, com.ylink.cim.admin.domain.UserInfo, flink.util.Pager)
	 */
	public Paginater findCustPager(CustInfo custInfo, UserInfo userInfo, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CustInfo t where 1=1");
		helper.append("and t.mobile like ? ", custInfo.getMobile(), MatchMode.ANYWHERE);
		helper.append("and t.idCard like ? ", custInfo.getIdCard(), MatchMode.ANYWHERE);
		helper.append("and t.email like ? ", custInfo.getEmail(), MatchMode.ANYWHERE);
		helper.append("and t.custType = ?", custInfo.getCustType());
		//操作员仅能查看在自己机构下开通业务的客户信息 
		if (!BranchType.HQ_0000.getValue().equals(userInfo.getBranchNo())) {
			helper.append("and exists (select distinct(s.custId) from AipAcct s where s.custId = t.id and s.branchNo = ?)", userInfo.getBranchNo());
		}
		//helper.append(hql);
		return super.getPageData(helper, pager);
	}
	
	/* (non-Javadoc)
	 * @see com.ylink.cim.cust.dao.impl.CustInfoDao#findForPushPlan(java.util.Map, flink.util.Pager)
	 */
	public List<CustInfo> findForPushPlan(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CustInfo t where 1=1");
		helper.append("and state = ?", CustState.NORMAL.getValue());
		helper.append("and custType = ?", params.get("custType"));
		if (!BranchType.HQ_0000.getValue().equals(params.get("branchNo"))) {
			helper.append("and exists (select distinct(s.custId) from AipAcct s where s.custId = t.id and s.branchNo = ?)", params.get("branchNo"));
		}
//		helper.append("and exists (select distinct(s.custId) from AipAcct s where s.custId = t.id and s.branchNo = ?)", params.get("branchNo"));
		helper.append("and exists (select s.id from ServiceRecord s where s.custId = t.id and s.busiType = ?)", params.get("busiType"));
		if (Symbol.YES.equals(params.get("subsState"))) {
			String pushType = (String)params.get("pushType");
			if (PushType.MAIL.getValue().equals(pushType)) {
				helper.append("and t.subsEmail = ?", Symbol.YES);
			} else if (PushType.MOBILE.getValue().equals(pushType)) {
				helper.append("and t.subsPhone = ?", Symbol.YES);
			} 
			else {
				helper.append("and (t.subsEmail = 'Y' or t.subsPhone ='Y')");
//				helper.append("and t.subsPhone = ?)", Symbol.YES);
			}
		}
		return super.getPageData(helper, pager).getList();
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.ylink.cim.cust.dao.impl.CustInfoDao#getCustInfoByUserId(java.lang.String)
	 */
	public CustInfo getCustInfoByUserId(String userId) {
		return (CustInfo) getUniqueResult(CustInfo.class, "userId", userId);
	}
	
	protected Class<CustInfo> getModelClass() {
		return CustInfo.class;
	}
	/* (non-Javadoc)
	 * @see com.ylink.cim.cust.dao.impl.CustInfoDao#getUniqueCustInfo(java.lang.String, java.lang.Object)
	 */
	public CustInfo getUniqueCustInfo(String property, Object value) {
		return (CustInfo) getUniqueResult(CustInfo.class, property, value);
	}
	
}
