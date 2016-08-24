package flink.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * logger 缓存类
 * 
 *
 * @param <T>
 */
public class LoggerCacheUtils {

	private static Map<String, Logger> logCache = new HashMap<String, Logger>();

	public static Logger getLogger(Class<?> cls) {

		Logger logger = logCache.get(cls.getName());
		if (null == logger) {
			logger = Logger.getLogger(cls);
			logCache.put(cls.getName(), logger);
		}

		return logger;
	}
}
