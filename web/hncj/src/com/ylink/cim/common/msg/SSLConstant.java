package com.ylink.cim.common.msg;

import com.ylink.cim.common.msg.util.CommUtil;

public final class SSLConstant {
	// --------------------------------------------------------------------
	/** Ĭ����Կ */
	public static final String DEFAULT_KEY = "240262447423713749922240";
	/** Ĭ������ֵ */
	public static String DEFAULT_DESEDE_IV = "12345678";

	// --------------------------------------------------------------------
	/** ����ģʽ�������� */
	public static final byte ENCRYPT_MODE_NO = 1;
	/** ����ģʽ��Ĭ����Կ���� */
	public static final byte ENCRYPT_MODE_DEFAULT = 2;
	/** ����ģʽ����֤�õ��ĻỰ��Կ���� */
	public static final byte ENCRYPT_MODE_SESSION = 3;
	/** ����ģʽ��Ĭ����Կ���� */
	public static final byte ENCRYPT_MODE_DEFAULT_ZIP = 4;

	// --------------------------------------------------------------------
	/** ����ģʽ�Ӷ� */
	public static final int ENCRYPT_MODE_LEN = 1;
	/** �ỰID���� */
	public static final int SESSION_ID_LEN = 10;

	// --------------------------------------------------------------------
	/** �洢������֤������ */
	public static final String CFG_SERVER_KEY_STORE_FILE_PATH = CommUtil.getConfigByString(
			"SERVER_KEY_STORE_FILE_PATH", "./ssl/server.pfx");
	/** ������֤���������� */
	public static final String CFG_SERVER_KEY_STORE_PASSWORD = CommUtil.getConfigByString("SERVER_KEY_STORE_PASSWORD",
			"123456");

	/** �ͻ���֤�� */
	public static final String CFG_CLIENT_PUBLIC_KEY_FILE_PATH = CommUtil.getConfigByString(
			"CLIENT_PUBLIC_KEY_FILE_PATH", "./ssl/client.crt");

	// --------------------------------------------------------------------
	/** ��ȫ��֤������ַ */
	public static final int CFG_SECURITY_LISTEN_PORT = CommUtil.getConfigByInt("SECURITY_LISTEN_PORT", 0);
	/** ��ȫ��֤�������̳߳ش�С */
	// public static final int CFG_SECURITY_THREAD_NUM =
	// CommUtil.getConfigByInt("SECURITY_THREAD_NUM",6);

	/** ��ȫ��֤���������ϼ����׷�������ַ */
	public static final String CFG_SECURITY_PARENT_ADDRESS_S = CommUtil.getConfigByString("SECURITY_PARENT_ADDRESS_S",
			"");

}