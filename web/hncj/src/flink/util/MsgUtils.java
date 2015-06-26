package flink.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 消息工具.
 * 
 * 
 */
public abstract class MsgUtils {
	private static final String REGEX = "\\{\\?\\}";

	/**
	 * 替换日志内容中的代字符,格式为{?}....
	 * 
	 * @param log
	 * @param param
	 * @return
	 */
	public static String r(String msg, Object... params) {
		if (StringUtils.isBlank(msg) || ArrayUtils.isEmpty(params)) {
			return msg;
		}

		for (int i = 0, n = params.length; i < n; i++) {
			msg = msg.replaceFirst(REGEX, ObjectUtils.toString(params[i]));
		}

		return msg;
	}
}
