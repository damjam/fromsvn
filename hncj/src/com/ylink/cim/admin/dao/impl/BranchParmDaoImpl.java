package com.ylink.cim.admin.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.BranchParmDao;
import com.ylink.cim.admin.domain.BranchParm;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("branchParmDao")
public class BranchParmDaoImpl extends BaseDaoHibernateImpl implements BranchParmDao {

	public void deleteBranchParmById(String id) throws Exception {

		this.deleteById(id);

	}

	public BranchParm findBranchParmById(String id) throws Exception {

		return (BranchParm) this.findById(id);

	}

	public String generateFeeSettingIndex() {
		BranchParm sysParm = findById("7211");
		int index = Integer.parseInt(sysParm.getParvalue()) + 1;
		sysParm.setParvalue(Integer.toString(index));
		update(sysParm);
		return Integer.toString(index);
	}

	public Date getDbTime() {
		QueryHelper sql = new QueryHelper();
		sql.append("select current_timestamp as t from dual");
		sql.appendScalar("t", Hibernate.TIMESTAMP);

		return (Date) getUniqueResultBySql(sql);
	}

	protected Class getModelClass() {
		return BranchParm.class;
	}

	public Paginater getPageList(Pager pager, BranchParm sysParm) throws Exception {

		QueryHelper helper = new QueryHelper();
		helper.append("from BranchParm where 1=1");
		helper.append("and code like ?", sysParm.getCode(), MatchMode.ANYWHERE);
		helper.append("and parname like ?", sysParm.getParname(), MatchMode.ANYWHERE);
		return this.getPageData(helper, pager);
	}

	public BranchParm getBranchParmInfo(String code) {

		return (BranchParm) findById(BranchParm.class, code);
	}

	public boolean hasParm(String code) throws Exception {
		return this.findById(code) != null;
	}

	public boolean isModify(String code) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public void saveBranchParm(BranchParm sysParm) throws Exception {

		this.save(sysParm);
	}

	public void saveBranchParmInTransaction(BranchParm sysParm) throws Exception {
		Transaction beginTransaction = super.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.beginTransaction();
		super.save(sysParm);
		beginTransaction.commit();
	}

	public String updateNextCapitalAllocMbsExportSeq() {
		BranchParm sysParm = (BranchParm) this.findById("9007");
		String paraVal = sysParm.getParvalue();

		String date = paraVal.substring(0, 8);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate = simpleDateFormat.format(this.getDbTime());

		String seq;
		if (!currentDate.equals(currentDate)) {
			date = currentDate;
			seq = StringUtils.leftPad("1", 6, "0");
		} else {
			String str = paraVal.substring(8);
			Long num = 1l;
			if (!StringUtils.isEmpty(str)) {
				num = Long.parseLong(str) + 1;
			}
			seq = String.valueOf(num);
			seq = StringUtils.leftPad(seq, 6, "0");
		}

		StringBuffer sb = new StringBuffer();
		sb.append(date).append(seq);

		sysParm.setParvalue(sb.toString());
		update(sysParm);

		return sb.toString();

	}

	public void updateBranchParm(BranchParm sysParm) throws Exception {

		this.update(sysParm);
	}

	public void updBranchParm(BranchParm sysParm) {

		update(sysParm);
	}

}
