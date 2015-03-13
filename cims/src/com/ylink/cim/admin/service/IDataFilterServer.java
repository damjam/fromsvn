package com.ylink.cim.admin.service;


import java.util.List;

import com.ylink.cim.user.domain.UserInfo;

/**
 * ���ݹ��˷���
 * @
 *
 */
public interface IDataFilterServer {
	/**
	 * ��ȡ�û���Ȩ�޲鿴����������,�û��û������棬�����û����صĻ���Ϊ��
	 * 
	 * @param userInfo
	 * @return ��û�л������򷵻�һ������Ϊ0��list
	 */
	public List<String> getAvailableChnl(UserInfo userInfo);
	/**
	 * ��ȡ�û���Ȩ�޲鿴�����з�֧����,�û��û������棬�����û����صĻ���Ϊ��
	 * 
	 * @param userInfo
	 * @return ��û�л������򷵻�һ������Ϊ0��list
	 */
	public List<String> getAvailableBranch(UserInfo userInfo);
	
	/**
	 * ��ȡ�û���Ȩ�޲鿴�����е��̻�
	 * @param userInfo
	 * @return ��û���̻����򷵻�һ������Ϊ0��list
	 */
	public List<String> getAllAvailableMrcht(UserInfo userInfo);
	
	/**
	 * ҵ�������Ĳ鿴���õĻ����������û�ʹ���ܲ�������Ȩ��
	 * @param userInfo
	 * @return
	 */
	public List<String> getAvailableBranchForBus(UserInfo userInfo);

}
