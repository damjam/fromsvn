package com.ylink.client.common;

public class MsgUtil {
	public static String addSpaceForString(String str, int len) {
		if (str.length() <= len) {
			return FILL(str, ' ', len, 'R');
		} else {
			throw new IllegalArgumentException("参数长度过长");
		}
	}

	public static String addZeroForNum(int num, int len) {
		return FormatNum(num, len);
	}

	public static String FormatNum(int num, int len) {
		String s = String.valueOf(num);
		if (s.length() < len) {
			s = FILL("0", '0', len - s.length(), 'L') + s;
		} else if (s.length() == len) {
			return s;
		} else {
			throw new IllegalArgumentException("参数长度过长");
		}
		return s;
	}

	public static String blank(int n) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; ++i)
			sb.append(' ');
		return sb.toString();
	}

	public static String FILL(String s, char c, int n, char f) {
		int iByteLen = MsgUtil.StringToBytes(s).length;
		if (iByteLen >= n) {
			return s;
		}
		byte[] fillChars = new byte[n - iByteLen];
		for (int i = 0; i < fillChars.length; ++i) {
			fillChars[i] = (byte) c;
		}
		if (f == 'L') {
			return new String(fillChars) + s;
		}
		return s + new String(fillChars);
	}

	public static byte[] StringToBytes(String str) {
		try {
			if ((str == null) || (str.length() <= 0)) {
				return new byte[0];
			}
			return str.getBytes();
		} catch (Exception e) {
		}
		return null;
	}
}
