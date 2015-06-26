package com.ylink.cim.common.msg.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.ylink.cim.common.msg.SSLConstant;

/**
 * �ԳƼ����㷨 3��des����
 * 
 * @author aps-cjp,aps-csl
 */
public final class DESede {

	/** �ӽ���ʹ�õ��ַ��� */
	private static final String DESEDE_CHARSET_NAME = "UTF-8";

	/** �����㷨 */
	private static final String DESEDE = "DESede";

	/** �㷨��ת������ */
	private static final String DES_CBC = "DESede/CBC/PKCS5Padding";

	/**
	 * ʹ��3Des�㷨�������ļ���
	 * 
	 * @param key
	 *            24���ַ�����Կ(3��des��Կ)
	 * @param ivByte
	 *            8�ַ����������
	 * @param value
	 *            ��Ҫ���м��ܵ�ԭ�����ֽ�����
	 * @return ���ܺ�������ֽ����飬�������ʧ�ܣ�����null
	 */
	public static byte[] encrypt(byte[] key, byte[] ivByte, byte[] value) {
		try {

			SecureRandom sr = new SecureRandom();
			SecretKey securekey = new SecretKeySpec(key, DESEDE);
			IvParameterSpec iv = new IvParameterSpec(ivByte);
			Cipher cipher = Cipher.getInstance(DES_CBC);
			cipher.init(Cipher.ENCRYPT_MODE, securekey, iv, sr);

			return cipher.doFinal(value);

		} catch (Exception e) {
			// CommUtil.WriteLog(Constant.BADLY_ERROR, e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ʹ��3Des�㷨�������Ľ���
	 * 
	 * @param key
	 *            24���ַ�����Կ(3��des��Կ)
	 * @param ivByte
	 *            8�ַ����������
	 * @param value
	 *            ��Ҫ���н��ܵ������ֽ�����
	 * @return ���ؽ��ܺ�������ֽ�����,�������ʧ�ܣ�����null
	 */
	public static byte[] decrypt(byte[] key, byte[] ivByte, byte[] value) {
		try {

			SecureRandom sr = new SecureRandom();
			SecretKey securekey = new SecretKeySpec(key, DESEDE);
			IvParameterSpec iv = new IvParameterSpec(ivByte);
			Cipher cipher = Cipher.getInstance(DES_CBC);
			cipher.init(Cipher.DECRYPT_MODE, securekey, iv, sr);

			return cipher.doFinal(value);

		} catch (Exception e) {
			// CommUtil.WriteLog(Constant.BADLY_ERROR, e);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ����Base64����
	 * 
	 * @param src
	 *            ԭ�ַ���
	 * @return ���ؾ���Base64�������ַ���
	 */
	public static String base64Encode(String src) throws Exception {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encode(src.getBytes(DESEDE_CHARSET_NAME));
	}

	/**
	 * ����Base64����
	 * 
	 * @param src
	 *            ԭ�ַ���
	 * @return ���ؾ���Base64�������ַ���
	 */
	public static String base64Encode(byte[] buff) throws Exception {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encode(buff);
	}

	/**
	 * ����Base64����
	 * 
	 * @param src
	 *            ��Base64�������ַ���
	 * @return ���ؽ������ַ���
	 * @throws Exception
	 */
	public static String base64Decode(String src) throws Exception {
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		return new String(decoder.decodeBuffer(src), DESEDE_CHARSET_NAME);
	}

	/**
	 * ����Base64����
	 * 
	 * @param src
	 *            ��Base64�������ַ���
	 * @return ���ؽ������ַ���
	 * @throws Exception
	 */
	public static byte[] base64DecodeToBytes(String src) throws Exception {
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		return decoder.decodeBuffer(src);
	}

	/**
	 * ��ԭʼ���Ľ��м���
	 * 
	 * @param iEncryptMode
	 *            ����ģʽ
	 * @param sSessionId
	 *            �ͻ���SessionId
	 * @param src
	 *            ��Ҫ���м��ܵ�ԭʼ�������ģ�
	 * @return ���ؼ��ܺ�ı���
	 */
	public static byte[] encryptSrcMsg(int iHeadLen, byte iEncryptMode, String sSessionId, byte[] src) throws Exception {
		byte[] cryptBuff = null;

		if (SSLConstant.ENCRYPT_MODE_DEFAULT == iEncryptMode // Ĭ����Կ����
				|| SSLConstant.ENCRYPT_MODE_SESSION == iEncryptMode // ���ݻỰ��Ӧ����ʱ��Կ����
				|| SSLConstant.ENCRYPT_MODE_DEFAULT_ZIP == iEncryptMode) // Ĭ����Կ������ѹ��
																			// add
																			// by
																			// csl
																			// 2012.4.13
		{

			// System.out.println("����ǰ��" + new String(src));

			// ������Կ
			String sKey = SSLConstant.DEFAULT_KEY;

			if (SSLConstant.ENCRYPT_MODE_SESSION == iEncryptMode) {
				sKey = SecretKeyMgr.getSecretKey(sSessionId);
			}

			if (sKey == null) {
				throw new Exception("û���ҵ��ỰID[" + sSessionId + "]��Ӧ����Կ.");
			}

			// ��ԭ���Ľ��м���
			cryptBuff = DESede.encrypt(sKey.getBytes(), SSLConstant.DEFAULT_DESEDE_IV.getBytes(), src);

			// �洢���ܺ����������
			byte[] buff = new byte[iHeadLen + SSLConstant.ENCRYPT_MODE_LEN + SSLConstant.SESSION_ID_LEN
					+ cryptBuff.length];

			// ���ɱ��ĳ���
			String sMsgLen = CommUtil.FILL("" + (buff.length - iHeadLen), '0', iHeadLen, 'L');

			// ���ĳ���
			int destPos = 0;
			System.arraycopy(sMsgLen.getBytes(), 0, buff, destPos, iHeadLen);

			// ����ģʽ
			destPos += iHeadLen;
			buff[destPos + SSLConstant.ENCRYPT_MODE_LEN - 1] = iEncryptMode;

			// SessionId
			destPos += SSLConstant.ENCRYPT_MODE_LEN;
			System.arraycopy(sSessionId.getBytes(), 0, buff, destPos, sSessionId.length());

			// ԭ����
			destPos += SSLConstant.SESSION_ID_LEN;
			System.arraycopy(cryptBuff, 0, buff, destPos, cryptBuff.length);

			// System.out.println("���ܺ�" + new String(buff));

			// ���ؼ��ܺ�ı���
			return buff;

		} else {

			// �����ܣ�ֱ�ӷ���ԭ����
			return src;
		}

	}

	/**
	 * ��ԭʼ���Ľ��н���
	 * 
	 * @param src
	 *            ��Ҫ���н��ܵ�ԭʼ�������ģ�
	 * @return ���ؽ��ܺ�ı���
	 */
	public static byte[] decryptSrcMsg(int iHeadLen, byte[] src) throws Exception {
		// ��ȡ����ģʽ
		int index = iHeadLen;
		byte iEncryptMode = src[index + SSLConstant.ENCRYPT_MODE_LEN - 1];

		if (SSLConstant.ENCRYPT_MODE_DEFAULT == iEncryptMode // Ĭ����Կ����
				|| SSLConstant.ENCRYPT_MODE_SESSION == iEncryptMode
				|| SSLConstant.ENCRYPT_MODE_DEFAULT_ZIP == iEncryptMode) // ���ݻỰ��Ӧ����ʱ��Կ����
		{

			// System.out.println("����ǰ:" + new String(src));

			// ��ȡ�ỰID
			index += SSLConstant.ENCRYPT_MODE_LEN;
			String sSessionId = new String(src, index, SSLConstant.SESSION_ID_LEN);

			// ��ȡ����
			index += SSLConstant.SESSION_ID_LEN;
			byte[] cryptBuff = new byte[src.length - index];
			System.arraycopy(src, index, cryptBuff, 0, cryptBuff.length);

			// ��ȡ������Կ
			String sKey = "";
			if (SSLConstant.ENCRYPT_MODE_DEFAULT == iEncryptMode
					|| SSLConstant.ENCRYPT_MODE_DEFAULT_ZIP == iEncryptMode) {
				sKey = SSLConstant.DEFAULT_KEY;
			} else {
				sKey = SecretKeyMgr.getSecretKey(sSessionId);
			}

			if (sKey == null) {
				throw new Exception("û���ҵ��ỰID[" + sSessionId + "]��Ӧ����Կ.");
			}

			// ���ܣ������ؽ��ܺ�ı���
			byte[] buff = DESede.decrypt(sKey.getBytes(), SSLConstant.DEFAULT_DESEDE_IV.getBytes(), cryptBuff);

			// System.out.println("���ܺ�:" + new String(buff));

			return buff;

		} else {
			// ������ʱ��ֱ�ӷ���ԭ��
			return src;
		}

	}

	/**
	 * ����С����
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("###############################################");
		System.out.println("##########  ���ݿ��������С����   ###############");
		System.out.print("### ����������ԭ�ģ�");

		String sSrcPwd = "";
		while (true) {
			sSrcPwd = br.readLine();
			if (sSrcPwd != null && sSrcPwd.length() > 0) {
				break;
			} else {
				System.out.print("### ����������ԭ�ģ�");
			}
		}

		byte[] encrypt = DESede.encrypt(SSLConstant.DEFAULT_KEY.getBytes(), SSLConstant.DEFAULT_DESEDE_IV.getBytes(),
				sSrcPwd.getBytes());
		String sPwd = DESede.base64Encode(encrypt);
		System.out.println("### ���ܺ����ģ�{" + sPwd + "}");
		System.out.println("###############################################");

	}
}
