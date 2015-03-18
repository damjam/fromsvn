package com.ylink.cim.admin.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.SysParmDao;
import com.ylink.cim.admin.domain.SysParm;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("sysParmDao")
public class SysParmDaoHibernateImpl extends BaseDaoHibernateImpl implements SysParmDao {

	public void deleteSysParmById(String id) throws Exception {

		this.deleteById(id);

	}


	public SysParm findSysParmById(String id) throws Exception {

		return (SysParm) this.findById(id);

	}

	public String generateFeeSettingIndex() {
		SysParm sysParm = findById("7211");
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
		return SysParm.class;
	}

	public Paginater getPageList(Pager pager, SysParm sysParm) throws Exception {

		QueryHelper helper = new QueryHelper();
		helper.append("from SysParm where 1=1");
		helper.append("and code like ?", sysParm.getCode(), MatchMode.ANYWHERE);
		helper.append("and parname like ?", sysParm.getParname(), MatchMode.ANYWHERE);
		return this.getPageData(helper, pager);
	}

	public SysParm getSysParmInfo(String code) {

		return (SysParm) findById(SysParm.class, code);
	}

	public boolean hasParm(String code) throws Exception {
		return this.findById(code) != null;
	}

	

	public boolean isModify(String code) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public void saveSysParm(SysParm sysParm) throws Exception {

		this.save(sysParm);
	}

	public void saveSysParmInTransaction(SysParm sysParm) throws Exception {
		Transaction beginTransaction = super.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.beginTransaction();
		super.save(sysParm);
		beginTransaction.commit();
	}

	public String updateNextCapitalAllocMbsExportSeq() {
		SysParm sysParm = (SysParm) this.findById("9007");
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

	public void updateSysParm(SysParm sysParm) throws Exception {

		this.update(sysParm);
	}

	public void updSysParm(SysParm sysParm) {

		update(sysParm);
	}


}
