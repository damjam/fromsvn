package flink.util;

import java.util.Collection;

/**
 * 权限接口.
 * 
 * 
 */
public interface IPrivilege {
	/**
	 * 获取所有的子节点
	 * 
	 * @return
	 */
	public Collection<IPrivilege> getAllChildren();

	/**
	 * 权限编号.
	 * 
	 * @return
	 */
	String getCode();

	/**
	 * 权限入口.
	 * 
	 * @return
	 */
	String getEntry();

	/**
	 * 权限名称.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 父级权限编号.
	 * 
	 * @return
	 */
	String getParent();

	/**
	 * 是否是菜单。是目录返回true；若是叶子，返回false。
	 * 
	 * @return
	 */
	public boolean isMenu();

	/**
	 * 子权限集合.
	 * 
	 * @param children
	 */
	void setChildren(Collection<IPrivilege> children);

	void setCode(String code);

}
