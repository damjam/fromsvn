package com.ylink.cim.common.msg.util;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public final class SecretKeyMgr {

	/** 用于生成密钥的随机数 */
	private static Random sRandom = new Random(System.nanoTime());

	/** 密钥容器 */
	private static ConcurrentHashMap<String, String> sSecretkeyMap = new ConcurrentHashMap<String, String>();

	/**
	 * 添加密钥
	 */
	public static void addSecretKey(String sSessionId, String sSecretKey) {
		sSecretkeyMap.put(sSessionId, sSecretKey);
	}

	/**
	 * 移除密钥
	 * 
	 * @param sSessionId
	 */
	public static void removeSecretKey(String sSessionId) {
		sSecretkeyMap.remove(sSessionId);
	}

	/**
	 * 根据SessionId获取密钥
	 * 
	 * @return 返回SessionID对应的密钥，如果密钥不存在，则返回null
	 */
	public static String getSecretKey(String sSessionId) {
		return sSecretkeyMap.get(sSessionId);
	}

	/**
	 * 创建一个新的密钥
	 * 
	 * @return 返回24位长度的密钥
	 */
	public synchronized static String createNewSecretKey() throws Exception {
		String key = "";

		key += CommUtil.FILL(String.valueOf((long) (sRandom.nextDouble() * 1000000)), '0', 6, 'L');
		key += CommUtil.FILL(String.valueOf((long) (sRandom.nextDouble() * 1000000)), '0', 6, 'L');
		key += CommUtil.FILL(String.valueOf((long) (sRandom.nextDouble() * 1000000)), '0', 6, 'L');
		key += CommUtil.FILL(String.valueOf((long) (sRandom.nextDouble() * 1000000)), '0', 6, 'L');

		return key;
	}

}