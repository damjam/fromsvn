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
 * 权限辅助类.
 * 
 * 
 *
 */
public abstract class PrivilegeHelper {
	
	private static Log log =LogFactory.getLog(PrivilegeHelper.class);
	/**
	 * 生成权限目录树（若是叶子节点，将被排除）
	 * @param root
	 * @param privileges  当前所有的所有权限
	 * @param map 权限和权限资源之间的映射
	 * @return
	 */
	public static IPrivilege getPrivilegeTree(IPrivilege root, Collection<IPrivilege> privileges,Map<String,List<PrivilegeResource>> map) {
		Collection<Privilege> ps = new ArrayList<Privilege>();
		for(IPrivilege ip: privileges){
			Privilege subP = (Privilege)ip;
			subP.setUsedToTree(false);
			ps.add(subP);
		}
		
		long first = System.currentTimeMillis();
		List<IPrivilege> children = getChildren(root, ps);
		long next = System.currentTimeMillis();
		log.debug("权限树构造时间："+(next-first));
		
		Privilege pRoot = (Privilege)root;
		pRoot.setUsedToTree(true);
		pRoot.setChildren(children);
		return pRoot;
	}

	/**
	 * 获取权限的子权限.
	 * 
	 * @param parent
	 * @param privileges 当前所有的所有权限
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<IPrivilege> getChildren(IPrivilege parent, Collection<Privilege> privileges) {
		List<IPrivilege> result = new ArrayList();
		
		for (Iterator<Privilege>  i = privileges.iterator(); i.hasNext();) {
			Privilege privilege = i.next();
			if(privilege.isUsedToTree()){//已经使用了的不参与循环
				continue;
			}
			if (isChild(privilege, parent)) {
				privilege.setChildren(getChildren(privilege, privileges));
				if(privilege.isMenu()){//是目录才加入到目录树中
					result.add(privilege);
				}
				privilege.setUsedToTree(true);
			}
		}
		
		return result;
	}
	
	/**
	 * 前者是否为后者的子权限.
	 * 
	 * @param privilege
	 * @param parent
	 * @return
	 */
	private static boolean isChild(IPrivilege privilege, IPrivilege parent) {
		return StringUtils.equals(privilege.getParent(), parent.getCode());
	}
	
	/**
	 * 查找父节点.
	 * @param privilege
	 * @param privileges
	 * @return
	 */
	public static IPrivilege getParent(IPrivilege privilege, Collection privileges) {
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
	 * 根据编号查找权限对象.
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
}
