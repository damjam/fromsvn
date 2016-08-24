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

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("sysParmDao")
public class SysParmDaoImpl extends BaseDaoImpl implements SysParmDao {

	@Override
	public void deleteSysParmById(String id) throws Exception {

		this.deleteById(id);

	}

	@Override
	public SysParm findSysParmById(String id) throws Exception {

		return (SysParm) this.findById(id);

	}

	@Override
	public String generateFeeSettingIndex() {
		SysParm sysParm = findById("7211");
		int index = Integer.parseInt(sysParm.getParvalue()) + 1;
		sysParm.setParvalue(Integer.toString(index));
		update(sysParm);
		return Integer.toString(index);
	}

	@Override
	protected Class getModelClass() {
		return SysParm.class;
	}

	@Override
	public Paginater getPageList(Pager pager, SysParm sysParm) throws Exception {

		QueryHelper helper = new QueryHelper();
		helper.append("from SysParm where 1=1");
		helper.append("and code like ?", sysParm.getCode(), MatchMode.ANYWHERE);
		helper.append("and parname like ?", sysParm.getParname(), MatchMode.ANYWHERE);
		return this.getPageData(helper, pager);
	}

	@Override
	public SysParm getSysParmInfo(String code) {

		return (SysParm) findById(SysParm.class, code);
	}

	@Override
	public boolean hasParm(String code) throws Exception {
		return this.findById(code) != null;
	}

	public boolean isModify(String code) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveSysParm(SysParm sysParm) throws Exception {

		this.save(sysParm);
	}

	@Override
	public void saveSysParmInTransaction(SysParm sysParm) throws Exception {
		Transaction beginTransaction = super.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.beginTransaction();
		super.save(sysParm);
		beginTransaction.commit();
	}


	@Override
	public void updateSysParm(SysParm sysParm) throws Exception {

		this.update(sysParm);
	}

	@Override
	public void updSysParm(SysParm sysParm) {

		update(sysParm);
	}

}
