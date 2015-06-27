package flink.etc;

public abstract class Symbol {
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final String[] YESNO = { YES, NO };

	public static String get(boolean expression) {
		return expression ? YES : NO;
	}

	public static String get(Boolean expression, String defaultValue) {
		if (expression == null) {
			return defaultValue;
		}

		return get(expression);
	}
}
