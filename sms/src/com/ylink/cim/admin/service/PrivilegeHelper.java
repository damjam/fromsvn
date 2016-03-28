package com.ylink.cim.admin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeResource;

import flink.util.IPrivilege;

/**
 * Ȩ�޸�����.
 * 
 * 
 *
 */
public abstract class PrivilegeHelper {

	private static Log log = LogFactory.getLog(PrivilegeHelper.class);

	/**
	 * ��ȡȨ�޵���Ȩ��.
	 * 
	 * @param parent
	 * @param privileges
	 *            ��ǰ���е�����Ȩ��
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<IPrivilege> getChildren(IPrivilege parent,
			Collection<Privilege> privileges) {
		List<IPrivilege> result = new ArrayList();

		for (Iterator<Privilege> i = privileges.iterator(); i.hasNext();) {
			Privilege privilege = i.next();
			if (privilege.isUsedToTree()) {// �Ѿ�ʹ���˵Ĳ�����ѭ��
				continue;
			}
			if (isChild(privilege, parent)) {
				privilege.setChildren(getChildren(privilege, privileges));
				if (privilege.isMenu()) {// ��Ŀ¼�ż��뵽Ŀ¼����
					result.add(privilege);
				}
				privilege.setUsedToTree(true);
			}
		}

		return result;
	}

	/**
	 * ���Ҹ��ڵ�.
	 * 
	 * @param privilege
	 * @param privileges
	 * @return
	 */
	public static IPrivilege getParent(IPrivilege privilege,
			Collection privileges) {
		String code = privilege.getCode();

		for (Iterator i = privileges.iterator(); i.hasNext();) {
			IPrivilege p = (IPrivilege) i.next();

			if (p.getCode().equals(code)) {
				return p;
			}
		}

		return null;
	}

	/**
	 * ���ݱ�Ų���Ȩ�޶���.
	 * 
	 * @param code
	 * @param privileges
	 * @return
	 */
	public static IPrivilege getPrivilege(String code, Collection privileges) {
		for (Iterator i = privileges.iterator(); i.hasNext();) {
			IPrivilege p = (IPrivilege) i.next();
			if (p.getCode().equals(code)) {
				return p;
			}
		}

		return null;
	}

	/**
	 * ����Ȩ��Ŀ¼��������Ҷ�ӽڵ㣬�����ų���
	 * 
	 * @param root
	 * @param privileges
	 *            ��ǰ���е�����Ȩ��
	 * @param map
	 *            Ȩ�޺�Ȩ����Դ֮���ӳ��
	 * @return
	 */
	public static IPrivilege getPrivilegeTree(IPrivilege root,
			Collection<IPrivilege> privileges,
			Map<String, List<PrivilegeResource>> map) {
		Collection<Privilege> ps = new ArrayList<Privilege>();
		for (IPrivilege ip : privileges) {
			Privilege subP = (Privilege) ip;
			subP.setUsedToTree(false);
			ps.add(subP);
		}

		long first = System.currentTimeMillis();
		List<IPrivilege> children = getChildren(root, ps);
		long next = System.currentTimeMillis();
		log.debug("Ȩ��������ʱ�䣺" + (next - first));

		Privilege pRoot = (Privilege) root;
		pRoot.setUsedToTree(true);
		pRoot.setChildren(children);
		return pRoot;
	}

	/**
	 * ǰ���Ƿ�Ϊ���ߵ���Ȩ��.
	 * 
	 * @param privilege
	 * @param parent
	 * @return
	 */
	private static boolean isChild(IPrivilege privilege, IPrivilege parent) {
		return StringUtils.equals(privilege.getParent(), parent.getCode());
	}
}
