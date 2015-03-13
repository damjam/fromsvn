package com.ylink.cim.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.LimitGroupInfoDao;
import com.ylink.cim.admin.domain.LimitGroupInfo;
import com.ylink.cim.admin.domain.SysDict;
import com.ylink.cim.common.type.SysDictType;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("limitGroupInfoDao")
public class LimitGroupInfoDaoImpl extends BaseDaoHibernateImpl implements LimitGroupInfoDao {

	
	public Paginater getLimitGroupInfoPageList(
			LimitGroupInfo limitGroupInfo, Pager pager) {
	
		QueryHelper helper=new QueryHelper();
		helper.append("from LimitGroupInfo lgi,SysDict sd ");
		helper.append("where 1=1");
		helper.append("and lgi.userType=sd.id.dictValue ");
		helper.append("and sd.id.dictType=?",SysDictType.UserType.getValue());
		helper.append("and lgi.limitGroupName like ? ",limitGroupInfo.getLimitGroupName(),MatchMode.ANYWHERE);
	
		Paginater pageData = super.getPageData(helper, pager);
		List<LimitGroupInfo> limitGroupInfos=convertToGroupInfo((List)pageData.getData());
		pageData.setData(limitGroupInfos);
		
		return pageData;
	}

	
	private List<LimitGroupInfo> convertToGroupInfo(List data) {
	
		List<LimitGroupInfo> ls=new ArrayList<LimitGroupInfo>();
		for(int i=0;i<data.size();i++){
			Object[] objects =(Object[])data.get(i);
			LimitGroupInfo limitGroupInfo=(LimitGroupInfo)objects[0];
			SysDict sysDict=(SysDict)objects[1];
			
			limitGroupInfo.setUserTypeName(sysDict.getDictName());
			
			ls.add(limitGroupInfo);
		}
		return ls;
	}


	
	protected Class getModelClass() {
		
		return LimitGroupInfo.class;
	}


	public List<SysDict> getSysDictNoLimitGroup() {
		
		QueryHelper helper=new QueryHelper();
		helper.append("from SysDict sd ");
		helper.append("where 1=1");
		helper.append("and sd.id.dictType = ?",SysDictType.UserType.getValue());
		helper.append("and sd.id.dictValue not in ( ");
		helper.append("select distinct lgi.userType from LimitGroupInfo lgi");
		helper.append(")");
		
		return super.getList(helper);
	}


	public List<LimitGroupInfo> getAll() {
		
		QueryHelper helper=new QueryHelper();
		helper.append("from LimitGroupInfo lgi");
		helper.append("where 1=1");

		return super.getList(helper);
	}

	
	
}


