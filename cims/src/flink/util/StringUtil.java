package flink.util;

import org.apache.commons.lang.StringUtils;



public abstract class StringUtil {
	
	public static String quote(String s) {
		return new StringBuffer("'").append(s).append("'").toString();
	}
	
	/**
	 * @param s
	 * @param maskChar
	 * @param beginIndex
	 * @param len
	 * @return
	 * 
	 */
	public static String mask(String s, char maskChar, int beginIndex, int len) {
		if (StringUtils.isEmpty(s)) {
			return s;
		}
		
		int endIndex = beginIndex + len;
		
		if (beginIndex < 0 || endIndex > s.length() || beginIndex > endIndex) {
			return s;
		}
		
		String maskString = StringUtils.leftPad(StringUtils.EMPTY, endIndex - beginIndex, maskChar);
		
		return new StringBuffer(s).replace(beginIndex, endIndex, maskString).toString();
	}
}
