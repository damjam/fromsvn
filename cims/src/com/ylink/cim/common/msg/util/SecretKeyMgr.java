package com.ylink.cim.common.msg.util;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


public final class SecretKeyMgr
{
	
	/** ����������Կ������� */
	private static Random sRandom = new Random(System.nanoTime());
	
	/** ��Կ���� */
	private static ConcurrentHashMap<String,String> sSecretkeyMap = new ConcurrentHashMap<String,String>();

	
	/** 
	 * �����Կ
	 */
	public static void addSecretKey(String sSessionId,String sSecretKey)
	{
		sSecretkeyMap.put(sSessionId, sSecretKey);
	}
	
	/**
	 * �Ƴ���Կ
	 * @param sSessionId
	 */
	public static void removeSecretKey(String sSessionId)
	{
		sSecretkeyMap.remove(sSessionId);
	}
	
	/** 
	 * ����SessionId��ȡ��Կ 
	 * @return ����SessionID��Ӧ����Կ�������Կ�����ڣ��򷵻�null
	 */
	public static String getSecretKey(String sSessionId)
	{
		return sSecretkeyMap.get(sSessionId);
	}
	
	/**
	 * ����һ���µ���Կ 
	 * @return ����24λ���ȵ���Կ
	 */
	public synchronized static String createNewSecretKey() throws Exception
	{
		String key = "";

		key += CommUtil.FILL(String.valueOf((long)(sRandom.nextDouble()*1000000)), '0', 6, 'L');
		key += CommUtil.FILL(String.valueOf((long)(sRandom.nextDouble()*1000000)), '0', 6, 'L');
		key += CommUtil.FILL(String.valueOf((long)(sRandom.nextDouble()*1000000)), '0', 6, 'L');
		key += CommUtil.FILL(String.valueOf((long)(sRandom.nextDouble()*1000000)), '0', 6, 'L');
		
		return key;
	}
	
}