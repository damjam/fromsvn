package flink.util;

import flink.etc.BizException;

/**
 * 异常输出类
 * 
 */
public class ExceptionUtils {

	public static void logBizException(Class cls, String msg)
			throws BizException {

		LoggerCacheUtils.getLogger(cls).debug(msg);
		throw new BizException(msg);

	}

	public static void logException(Class cls, String msg) throws Exception {

		LoggerCacheUtils.getLogger(cls).debug(msg);
		throw new Exception(msg);

	}

}
