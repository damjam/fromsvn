package com.ylink.cim.admin.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.PrivilegeDao;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.domain.PrivilegeTreeNode;

import flink.etc.BizException;
import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("privilegeDao")
public class PrivilegeDaoImpl extends BaseDaoHibernateImpl implements PrivilegeDao {

	/**
	 * @see flink.hibernate.BaseDaoHibernateImpl#getModelClass()
	 */

	protected Class<Privilege> getModelClass() {
		return Privilege.class;
	}

	public Map<String, List<PrivilegeResource>> getPrivResource(String[] privs) {
		Map<String, List<PrivilegeResource>> result = new HashMap<String, List<PrivilegeResource>>();
		if (privs == null || privs.length == 0) {
			return result;
		}

		String condtion = "";
		for (String p : privs) {
			condtion = condtion + ",'" + p + "'";
		}
		condtion = condtion.substring(1);
		String hql = "from PrivilegeResource r where r.limitId in(" + condtion + ")";
		List<PrivilegeResource> allResource = this.getList(hql);

		for (PrivilegeResource r : allResource) {
			List<PrivilegeResource> subRes = result.get(r.getLimitId());
			if (subRes == null) {
				subRes = new ArrayList<PrivilegeResource>();
				result.put(r.getLimitId(), subRes);
			}
			subRes.add(r);
		}
		return result;
	}

	public Paginater getPrivilegePageList(Privilege privilege, Pager pager) {
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.append("from Privilege p");
		queryHelper.append("where 1=1");
		queryHelper.append(" and p.ifAudit = ?", privilege.getIfAudit());
		queryHelper.append("and p.limitId=?", privilege.getLimitId());
		queryHelper.append("and p.limitName like ?", privilege.getLimitName(), MatchMode.ANYWHERE);

		return super.getPageData(queryHelper, pager);
	}

	public List<PrivilegeTreeNode> getRoleTreeByRole(String roleId) throws BizException {
		List<PrivilegeTreeNode> result = new ArrayList<PrivilegeTreeNode>();
		String hql = "select p from Privilege p, RolePrivilege rp where p.limitId=rp.id.limitId and rp.id.roleId='"
				+ roleId + "'";
		List<Privilege> prs = getList(hql);
		for (Privilege p : prs) {
			PrivilegeTreeNode node = new PrivilegeTreeNode();
			node.setCode(p.getLimitId());
			node.setName(p.getLimitName());
			String parent = p.getParent();
			node.setParentCode(parent == null ? PrivilegeTreeNode.ROOT_PARENT : parent);
			result.add(node);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gnete.dsf.biz.dao.PrivilegeDao#getRoleTree(java.util.List)
	 */
	public List<PrivilegeTreeNode> getRoleTree(String limitGroupId) {
		List<PrivilegeTreeNode> treeList = new ArrayList<PrivilegeTreeNode>();
		List<Map> prList = getLimitGroupPrivilege(limitGroupId);
		for (int i = 0; i < prList.size(); i++) {
			Map map = (Map) prList.get(i);
			PrivilegeTreeNode node = new PrivilegeTreeNode();
			node.setCode((String) map.get("limitId"));
			node.setName((String) map.get("limitName"));
			String parent = (String) map.get("parent");
			// node.setParentCode(parent == null?PrivilegeTreeNode.ROOT:parent);
			node.setParentCode(parent == null ? PrivilegeTreeNode.ROOT_PARENT : parent);
			treeList.add(node);
		}
		return treeList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gnete.dsf.biz.dao.PrivilegeDao#getLimitGroupPrivilege(java.lang.String)
	 */
	public List<Map> getLimitGroupPrivilege(String limitGroupId) {

		QueryHelper sql = new QueryHelper();
		sql.append(" select new map(p.limitId as limitId,p.limitName as limitName,");
		sql.append(" p.parent as parent)");
		sql.append(" from Privilege p,LimitGroup lg");
		sql.append(" where p.limitId = lg.id.limitId");
		sql.append(" and lg.id.limitGroupId =?", limitGroupId);
		sql.append(" order by p.menuOrder");
		return getList(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gnete.dsf.biz.dao.PrivilegeDao#getRoleTree(java.util.List)
	 */
	public List getRoleTree() {
		List<PrivilegeTreeNode> treeList = new ArrayList<PrivilegeTreeNode>();
		List<Privilege> prList = getLimitGroupPrivilege();
		for (int i = 0; i < prList.size(); i++) {
			Privilege pg = (Privilege) prList.get(i);
			PrivilegeTreeNode node = new PrivilegeTreeNode();
			node.setCode(pg.getLimitId());
			node.setName(pg.getLimitName());
			String parent = pg.getParent();
			node.setParentCode(parent == null ? PrivilegeTreeNode.ROOT_PARENT : parent);
			treeList.add(node);
		}
		return treeList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gnete.dsf.biz.dao.PrivilegeDao#getLimitGroupPrivilege(java.lang.String)
	 */
	public List<Privilege> getLimitGroupPrivilege() {

		QueryHelper sql = new QueryHelper();
		sql.append(" from Privilege p");
		sql.append(" order by p.menuOrder");
		return getList(sql);
	}

	public List<Privilege> getPrivilegeList(Privilege privilege) {

		QueryHelper helper = new QueryHelper();
		helper.append("from Privilege p");
		helper.append("where 1=1");
		helper.append("and p.limitId=?", privilege.getLimitId());
		helper.append("and p.limitName like ? ", privilege.getLimitName(), MatchMode.ANYWHERE);
		helper.append(" order by p.menuOrder ");

		return super.getList(helper);
	}

	public boolean hasSubPris(String limitId) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(p.limitId) as subNum) from Privilege p where p.parent = ?", limitId);
		Map<String, Object> map = (Map<String, Object>) super.getUniqueResult(helper);
		return (Long) map.get("subNum") > 0 ? true : false;
	}

	public Paginater findPrivs(Privilege Privilege, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from Privilege where 1=1");
		helper.append("and limitId like ?", Privilege.getLimitId(), MatchMode.ANYWHERE);
		helper.append("and limitName like ?", Privilege.getLimitName(), MatchMode.ANYWHERE);
		helper.append("and parent = ?", Privilege.getParent());
		helper.append("and isMenu = ?", Privilege.getIsMenu());
		helper.append("and ifAudit = ?", Privilege.getIfAudit());
		helper.append("order by limitId");
		return super.getPageData(helper, pager);
	}

	public Integer getNextIndex(String parent) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(max(menuOrder) as index) from Privilege where 1=1");
		helper.append("and parent = ?", parent);
		Map<String, Object> map = (Map<String, Object>) super.getUniqueResult(helper);
		Integer index = (Integer) map.get("index");
		if (index == null) {
			return 0;
		}
		return index + 1;
	}
}
