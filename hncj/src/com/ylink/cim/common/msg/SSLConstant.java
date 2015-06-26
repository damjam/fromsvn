package com.ylink.cim.common.msg;

import com.ylink.cim.common.msg.util.CommUtil;

public final class SSLConstant {
	// --------------------------------------------------------------------
	/** 默认密钥 */
	public static final String DEFAULT_KEY = "240262447423713749922240";
	/** 默认向量值 */
	public static String DEFAULT_DESEDE_IV = "12345678";

	// --------------------------------------------------------------------
	/** 加密模式：不加密 */
	public static final byte ENCRYPT_MODE_NO = 1;
	/** 加密模式：默认密钥加密 */
	public static final byte ENCRYPT_MODE_DEFAULT = 2;
	/** 加密模式：认证得到的会话密钥加密 */
	public static final byte ENCRYPT_MODE_SESSION = 3;
	/** 加密模式：默认密钥加密 */
	public static final byte ENCRYPT_MODE_DEFAULT_ZIP = 4;

	// --------------------------------------------------------------------
	/** 加密模式加度 */
	public static final int ENCRYPT_MODE_LEN = 1;
	/** 会话ID长度 */
	public static final int SESSION_ID_LEN = 10;

	// --------------------------------------------------------------------
	/** 存储服务器证书容器 */
	public static final String CFG_SERVER_KEY_STORE_FILE_PATH = CommUtil.getConfigByString(
			"SERVER_KEY_STORE_FILE_PATH", "./ssl/server.pfx");
	/** 服务器证书容器密码 */
	public static final String CFG_SERVER_KEY_STORE_PASSWORD = CommUtil.getConfigByString("SERVER_KEY_STORE_PASSWORD",
			"123456");

	/** 客户端证书 */
	public static final String CFG_CLIENT_PUBLIC_KEY_FILE_PATH = CommUtil.getConfigByString(
			"CLIENT_PUBLIC_KEY_FILE_PATH", "./ssl/client.crt");

	// --------------------------------------------------------------------
	/** 安全认证监听地址 */
	public static final int CFG_SECURITY_LISTEN_PORT = CommUtil.getConfigByInt("SECURITY_LISTEN_PORT", 0);
	/** 安全认证服务器线程池大小 */
	// public static final int CFG_SECURITY_THREAD_NUM =
	// CommUtil.getConfigByInt("SECURITY_THREAD_NUM",6);

	/** 安全认证服务器的上级交易服务器地址 */
	public static final String CFG_SECURITY_PARENT_ADDRESS_S = CommUtil.getConfigByString("SECURITY_PARENT_ADDRESS_S",
			"");

}