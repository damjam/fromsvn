package com.ylink.cim.common.msg.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.ylink.cim.common.msg.SSLConstant;

/** 对称加密算法
 * 3重des加密
 * @author aps-cjp,aps-csl
 */
public final class DESede 
{
	
	/** 加解密使用的字符集 */
	private static final String DESEDE_CHARSET_NAME = "UTF-8";
	
	/** 加密算法 */
	private static final String DESEDE = "DESede";
	
	/** 算法的转换名称 */
	private static final String DES_CBC = "DESede/CBC/PKCS5Padding";
	
	
	
	/**
	 * 使用3Des算法进行明文加密
	 * @param key    24个字符的密钥(3个des密钥)
	 * @param ivByte 8字符的随机向量
	 * @param value  需要进行加密的原明文字节数组
	 * @return 加密后的密文字节数组，如果加密失败，返回null
	 */
	public static byte[] encrypt(byte[] key, byte[] ivByte, byte[] value)
	{	
		try
		{
			
			SecureRandom sr = new SecureRandom();
			SecretKey securekey = new SecretKeySpec(key, DESEDE);
			IvParameterSpec iv = new IvParameterSpec(ivByte);
			Cipher cipher = Cipher.getInstance(DES_CBC);
			cipher.init(Cipher.ENCRYPT_MODE, securekey, iv, sr);
			
			return  cipher.doFinal(value);
			
		}catch(Exception e)
		{
		//	CommUtil.WriteLog(Constant.BADLY_ERROR, e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 使用3Des算法进行密文解密
	 * @param key      24个字符的密钥(3个des密钥)
	 * @param ivByte   8字符的随机向量
	 * @param value    需要进行解密的密文字节数组
	 * @return 返回解密后的明文字节数组,如果解密失败，返回null
	 */
	public static byte[] decrypt(byte[] key, byte[] ivByte, byte[] value) 
	{		
		try 
		{
			
			SecureRandom sr = new SecureRandom();
			SecretKey securekey = new SecretKeySpec(key, DESEDE);
			IvParameterSpec iv = new IvParameterSpec(ivByte);
			Cipher cipher = Cipher.getInstance(DES_CBC);
			cipher.init(Cipher.DECRYPT_MODE, securekey, iv, sr);
			
			return cipher.doFinal(value);
			
		} catch (Exception e) 
		{
		//	CommUtil.WriteLog(Constant.BADLY_ERROR, e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 进行Base64编码
	 * @param src 原字符串
	 * @return 返回经过Base64编码后的字符串
	 */
	public static String base64Encode(String src) throws Exception
	{
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	    return encoder.encode(src.getBytes(DESEDE_CHARSET_NAME));
	}
	
	/**
	 * 进行Base64编码
	 * @param src 原字符串
	 * @return 返回经过Base64编码后的字符串
	 */
	public static String base64Encode(byte[] buff) throws Exception
	{
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	    return encoder.encode(buff);
	}
	
	/**
	 * 进行Base64解码
	 * @param src 经Base64编码后的字符串
	 * @return 返回解码后的字符串
	 * @throws Exception
	 */
	public static String base64Decode(String src ) throws Exception
	{
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		return new String(decoder.decodeBuffer(src),DESEDE_CHARSET_NAME);
	}
	
	/**
	 * 进行Base64解码
	 * @param src 经Base64编码后的字符串
	 * @return 返回解码后的字符串
	 * @throws Exception
	 */
	public static byte[] base64DecodeToBytes(String src ) throws Exception
	{
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		return decoder.decodeBuffer(src) ;
	}
	

	/**
	 * 对原始报文进行加密
	 * @param iEncryptMode   加密模式
	 * @param sSessionId   客户端SessionId
	 * @param src          需要进行加密的原始完整报文，
	 * @return 返回加密后的报文
	 */
	public static byte[] encryptSrcMsg(int iHeadLen,byte iEncryptMode,String sSessionId,byte[] src)
		throws Exception
	{
		byte[] cryptBuff = null;
		
		if ( SSLConstant.ENCRYPT_MODE_DEFAULT == iEncryptMode //默认密钥加密
				|| SSLConstant.ENCRYPT_MODE_SESSION == iEncryptMode //根据会话对应的临时密钥加密
				|| SSLConstant.ENCRYPT_MODE_DEFAULT_ZIP == iEncryptMode )  //默认密钥加密且压缩 add by csl 2012.4.13
		{
			
			//System.out.println("加密前：" + new String(src));
			
			//加密密钥
			String sKey = SSLConstant.DEFAULT_KEY;
			
			if (SSLConstant.ENCRYPT_MODE_SESSION == iEncryptMode)
				sKey = SecretKeyMgr.getSecretKey(sSessionId);
			
			if ( sKey == null )
				throw new Exception("没有找到会话ID[" + sSessionId + "]对应的密钥.");
			
			//对原报文进行加密
			cryptBuff = DESede.encrypt(sKey.getBytes(), SSLConstant.DEFAULT_DESEDE_IV.getBytes(), src);
			
			//存储加密后的完整报文
			byte[] buff = new byte[iHeadLen + SSLConstant.ENCRYPT_MODE_LEN + SSLConstant.SESSION_ID_LEN + cryptBuff.length];
			
			//生成报文长度
			String sMsgLen = CommUtil.FILL("" + ( buff.length - iHeadLen ) , '0', iHeadLen, 'L');
			
			//报文长度
			int destPos = 0;
			System.arraycopy(sMsgLen.getBytes(), 0, buff, destPos, iHeadLen);
			
			//加密模式
			destPos += iHeadLen;
			buff[destPos + SSLConstant.ENCRYPT_MODE_LEN - 1 ] = iEncryptMode;
			
			//SessionId
			destPos += SSLConstant.ENCRYPT_MODE_LEN;
			System.arraycopy(sSessionId.getBytes(), 0, buff, destPos, sSessionId.length());
			
			//原报文
			destPos += SSLConstant.SESSION_ID_LEN;
			System.arraycopy(cryptBuff, 0, buff, destPos, cryptBuff.length);
			
			//System.out.println("加密后：" + new String(buff));
			
			//返回加密后的报文
			return buff;
			
		}else{
			
			//不加密，直接返回原报文
			return src;
		}
		
	}
	
	/**
	 * 对原始报文进行解密
	 * @param src          需要进行解密的原始完整报文，
	 * @return 返回解密后的报文
	 */
	public static byte[] decryptSrcMsg(int iHeadLen,byte[] src)
		throws Exception
	{
		//获取加密模式
		int index = iHeadLen;		
		byte iEncryptMode    = src[index + SSLConstant.ENCRYPT_MODE_LEN -1 ];
				
		if ( SSLConstant.ENCRYPT_MODE_DEFAULT == iEncryptMode  //默认密钥加密
				|| SSLConstant.ENCRYPT_MODE_SESSION == iEncryptMode 
				|| SSLConstant.ENCRYPT_MODE_DEFAULT_ZIP == iEncryptMode  ) //根据会话对应的临时密钥加密 
		{

			//System.out.println("解密前:" + new String(src));
			
			//获取会话ID
			index += SSLConstant.ENCRYPT_MODE_LEN;
			String sSessionId = new String(src,index,SSLConstant.SESSION_ID_LEN);
			
			//获取密文
			index += SSLConstant.SESSION_ID_LEN;
			byte[] cryptBuff = new byte[src.length - index];
			System.arraycopy(src, index, cryptBuff, 0, cryptBuff.length);
			
			//获取加密密钥
			String sKey = "";
			if ( SSLConstant.ENCRYPT_MODE_DEFAULT == iEncryptMode || SSLConstant.ENCRYPT_MODE_DEFAULT_ZIP == iEncryptMode )
				sKey = SSLConstant.DEFAULT_KEY;
			else
				sKey = SecretKeyMgr.getSecretKey(sSessionId);
			
			if ( sKey == null )
				throw new Exception("没有找到会话ID[" + sSessionId + "]对应的密钥.");
			
			//解密，并返回解密后的报文			
			byte[] buff = DESede.decrypt(sKey.getBytes(), SSLConstant.DEFAULT_DESEDE_IV.getBytes(), cryptBuff);
			
			//System.out.println("解密后:" + new String(buff));
			
			return buff;
			
		}else{
			//不解密时，直接返回原文
			return src;
		}
		
	}
	
	/**
	 * 加密小工具
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("###############################################");
		System.out.println("##########  数据库密码加密小工具   ###############");
		System.out.print  ("### 请输入密码原文：");

		String sSrcPwd = "";
		while ( true )
		{
			sSrcPwd = br.readLine();
			if ( sSrcPwd != null && sSrcPwd .length() > 0 )
				break;
			else
				System.out.print  ("### 请输入密码原文：");
		}
		
		byte[] encrypt = DESede.encrypt(SSLConstant.DEFAULT_KEY.getBytes(), SSLConstant.DEFAULT_DESEDE_IV.getBytes(), sSrcPwd.getBytes()) ;
		String sPwd = DESede.base64Encode(encrypt);
		System.out.println("### 加密后密文：{" + sPwd + "}");
		System.out.println("###############################################");
		
	}
}
