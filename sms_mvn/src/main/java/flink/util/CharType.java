package flink.util;

/**
 * 字符类型.
 * 
 * 
 */
public class CharType {
	/**
	 * 数字.
	 */
	public static final int N = 0;
	/**
	 * 字母.
	 */
	public static final int L = 1;
	/**
	 * 标点符号.
	 */
	public static final int I = 2;

	/**
	 * 数字, 字母.
	 */
	public static final int N_L = 3;
	/**
	 * 数字, 标点符号.
	 */
	public static final int N_I = 4;
	/**
	 * 字母, 标点符号.
	 */
	public static final int L_I = 5;
	/**
	 * 数字, 字母, 标点符号.
	 */
	public static final int N_L_I = 6;

	/**
	 * 数字, 字母, 标点符号, 汉字.
	 */
	public static final int ALL = 99;
}
