package flink.util;

import java.util.Collection;

/**
 * Ȩ�޽ӿ�.
 * 
 * 
 */
public interface IPrivilege {
	/**
	 * Ȩ�ޱ��.
	 * @return
	 */
	String getCode();
	
	void setCode(String code);
	
	/**
	 * Ȩ������.
	 * @return
	 */
	String getName();
	
	/**
	 * Ȩ�����.
	 * @return
	 */
	String getEntry();
	
	/**
	 * ����Ȩ�ޱ��.
	 * @return
	 */
	String getParent();
	
	/**
	 * �Ƿ��ǲ˵�����Ŀ¼����true������Ҷ�ӣ�����false��
	 * @return
	 */
	public boolean isMenu();
	
	/**
	 * ��Ȩ�޼���.
	 * @param children
	 */
	void setChildren(Collection<IPrivilege> children);
	
	
	/**
	 * ��ȡ���е��ӽڵ�
	 * @return
	 */
	public Collection<IPrivilege> getAllChildren();
	
}
