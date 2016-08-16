package flink.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
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

		String maskString = StringUtils.leftPad(StringUtils.EMPTY, endIndex
				- beginIndex, maskChar);

		return new StringBuffer(s).replace(beginIndex, endIndex, maskString)
				.toString();
	}
	
	public static String class2Table(String className) {
		if (className.indexOf(".") != -1) {
			className = className.substring(className.lastIndexOf(".")+1, className.length());
		}
		List<String> array = new ArrayList<String>();
		for(int i=1; i<className.length(); i++){
			char c = className.charAt(i);
			if (c > 'A' && c < 'Z') {//
				System.out.println(1);
				array.add(className.substring(0, i));
				className = className.substring(i, className.length());
			}
		}
		array.add(className);
		StringBuilder tableName = new StringBuilder();
		for(int i=0; i<array.size(); i++){
			if(i != 0){
				tableName.append("_");
			}
			tableName.append(array.get(i));
		}
		return tableName.toString().toUpperCase();
	}
	public static String class2Object(String className) {
		if (className.indexOf(".") != -1) {
			className = className.substring(className.lastIndexOf(".")+1, className.length());
		}
		return StringUtils.uncapitalize(className);
	}
	public static String subStringByByte(String str, int len) {
		String result = null;
		try {
			if (str != null) {
				byte[] a = str.getBytes("gbk");
				if (a.length <= len) {
					result = str;
				} else if (len > 0) {
					result = new String(a, 0, len, "gbk");
					System.out.println(result);
					int length = result.length();
					if (str.charAt(length - 1) != result.charAt(length - 1)) {
						if (length < 2) {
							result = null;
						} else {
							result = result.substring(0, length - 1);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	public static String array2String(String[] array) {
		if (ArrayUtils.isEmpty(array)) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0,size = array.length; i < array.length; i++) {
			builder.append(array[i]);
			if (i < size-1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
}
