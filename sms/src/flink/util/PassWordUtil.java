package flink.util;

import java.util.Random;

/**
 * 随机密码生成器
 * 
 * @
 * 
 */
public class PassWordUtil {

	/**
	 * 产生一个6位随机密码
	 * 
	 * @return
	 */
	public static String genernatePassword() {
		return genernatePassword(6);
	}

	/**
	 * 产生随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String genernatePassword(int length) {
		StringBuffer buffer = new StringBuffer(
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(genernatePassword());
	}

}
