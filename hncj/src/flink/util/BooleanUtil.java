package flink.util;

/**
 * boolean型和数字转化类 @
 * 
 */
public class BooleanUtil {
	public static final String STR_TRUE = "Y";
	public static final String STR_FALSE = "N";

	public static String booleanToString(boolean v) {
		return v ? STR_TRUE : STR_FALSE;
	}

	public static String BooleanToString(Boolean v) {
		if (v == null) {
			v = false;
		}
		return booleanToString(v);
	}

	public static boolean strToBoolean(String v) {
		return STR_TRUE.equals(v) ? true : false;
	}

}
