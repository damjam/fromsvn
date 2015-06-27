package flink.etc;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import flink.util.Type;

/**
 * Assertion utility.
 * 
 * 
 *
 */
public abstract class Assert {
	private final static Logger logger = Logger.getLogger(Assert.class);

	private static void throwException(BizException e) throws BizException {
		throw e;
	}

	private static void throwException(BizException e, String log)
			throws BizException {
		logger.debug(e + ";" + log, e);
		throw e;
	}

	private static BizException getBizException(String errorCode) {
		return new BizException(errorCode);
	}

	private static BizException getBizException(String errorCode, boolean roll) {
		return roll ? new BizException(errorCode) : new NoRollbackBizException(
				errorCode);
	}

	private static BizException getBizException(String errorCode, String message) {
		return new BizException(errorCode, message);
	}

	private static BizException getBizException(String errorCode,
			String message, boolean roll) {
		return roll ? new BizException(errorCode, message)
				: new NoRollbackBizException(errorCode, message);
	}

	private static BizException getBizException(Type type, String message) {
		return new BizException(type, message);
	}

	private static BizException getBizException(Type type, String message,
			boolean roll) {
		return roll ? new BizException(type, message)
				: new NoRollbackBizException(type, message);
	}

	public static void isTrue(boolean expression, String message)
			throws BizException {
		isTrue(expression, (String) null, message);
	}

	public static void isTrue(boolean expression, String message, boolean roll)
			throws BizException {
		isTrue(expression, (String) null, message, roll);
	}

	public static void isTrue(boolean expression, String errorCode,
			String message) throws BizException {
		if (!expression) {
			throwException(getBizException(errorCode, message));
		}
	}

	public static void isTrue(boolean expression, String errorCode,
			String message, boolean roll) throws BizException {
		if (!expression) {
			throwException(getBizException(errorCode, message, roll));
		}
	}

	public static void isTrue(boolean expression, Type type, String message)
			throws BizException {
		if (!expression) {
			throwException(getBizException(type, message));
		}
	}

	public static void isTrue(boolean expression, Type type, String message,
			boolean roll) throws BizException {
		if (!expression) {
			throwException(getBizException(type, message, roll));
		}
	}

	public static void isTrue(boolean expression, String errorCode,
			String message, String log) throws BizException {
		if (!expression) {
			throwException(getBizException(errorCode, message), log);
		}
	}

	public static void isTrue(boolean expression, String errorCode,
			String message, String log, boolean roll) throws BizException {
		if (!expression) {
			throwException(getBizException(errorCode, message, roll), log);
		}
	}

	public static void isTrue(boolean expression, Type type, String message,
			String log) throws BizException {
		if (!expression) {
			throwException(getBizException(type, message), log);
		}
	}

	public static void isTrue(boolean expression, Type type, String message,
			String log, boolean roll) throws BizException {
		if (!expression) {
			throwException(getBizException(type, message, roll), log);
		}
	}

	public static void notTrue(boolean expression, String message)
			throws BizException {
		notTrue(expression, (String) null, message);
	}

	public static void notTrue(boolean expression, String message, boolean roll)
			throws BizException {
		notTrue(expression, (String) null, message, roll);
	}

	public static void notTrue(boolean expression, String errorCode,
			String message) throws BizException {
		if (expression) {
			throwException(getBizException(errorCode, message));
		}
	}

	public static void notTrue(boolean expression, String errorCode,
			String message, boolean roll) throws BizException {
		if (expression) {
			throwException(getBizException(errorCode, message, roll));
		}
	}

	public static void notTrue(boolean expression, String errorCode,
			String message, String log) throws BizException {
		if (expression) {
			throwException(getBizException(errorCode, message), log);
		}
	}

	public static void notTrue(boolean expression, String errorCode,
			String message, String log, boolean roll) throws BizException {
		if (expression) {
			throwException(getBizException(errorCode, message, roll), log);
		}
	}

	public static void notTrue(boolean expression, Type type, String message)
			throws BizException {
		if (expression) {
			throwException(getBizException(type, message));
		}
	}

	public static void notTrue(boolean expression, Type type, String message,
			boolean roll) throws BizException {
		if (expression) {
			throwException(getBizException(type, message, roll));
		}
	}

	public static void isNull(Object object, String message)
			throws BizException {
		if (object != null) {
			throwException(getBizException(message));
		}
	}

	public static void isNull(Object object, String message, boolean roll)
			throws BizException {
		if (object != null) {
			throwException(getBizException(message, roll));
		}
	}

	public static void notNull(Object object, String message)
			throws BizException {
		if (object == null) {
			throwException(getBizException(message));
		}
	}

	public static void notNull(Object object, String message, boolean roll)
			throws BizException {
		if (object == null) {
			throwException(getBizException(message, roll));
		}
	}

	public static void notNull(Object object, String errorCode, String message)
			throws BizException {
		if (object == null) {
			throwException(getBizException(errorCode, message));
		}
	}

	public static void notNull(Object object, String errorCode, String message,
			boolean roll) throws BizException {
		if (object == null) {
			throwException(getBizException(errorCode, message, roll));
		}
	}

	public static void notNull(Object object, String errorCode, String message,
			String log) throws BizException {
		if (object == null) {
			throwException(getBizException(errorCode, message), log);
		}
	}

	public static void notNull(Object object, String errorCode, String message,
			String log, boolean roll) throws BizException {
		if (object == null) {
			throwException(getBizException(errorCode, message, roll), log);
		}
	}

	public static void notNull(Object object, Type type, String log)
			throws BizException {
		if (object == null) {
			throwException(getBizException(type, type.getName()), log);
		}
	}

	public static void notNull(Object object, Type type, String log,
			boolean roll) throws BizException {
		if (object == null) {
			throwException(getBizException(type, type.getName(), roll), log);
		}
	}

	public static void isEmpty(String data, String message) throws BizException {
		if (!StringUtils.isEmpty(data)) {
			throwException(getBizException(message));
		}
	}

	public static void isEmpty(String data, String message, boolean roll)
			throws BizException {
		if (!StringUtils.isEmpty(data)) {
			throwException(getBizException(message, roll));
		}
	}

	public static void notEmpty(String data, String message)
			throws BizException {
		if (StringUtils.isEmpty(data)) {
			throwException(getBizException(message));
		}
	}

	public static void notEmpty(String data, String message, boolean roll)
			throws BizException {
		if (StringUtils.isEmpty(data)) {
			throwException(getBizException(message, roll));
		}
	}

	public static void notEmpty(String data, String errorCode, String message)
			throws BizException {
		if (StringUtils.isEmpty(data)) {
			throwException(getBizException(errorCode, message));
		}
	}

	public static void notEmpty(String data, String errorCode, String message,
			boolean roll) throws BizException {
		if (StringUtils.isEmpty(data)) {
			throwException(getBizException(errorCode, message, roll));
		}
	}

	public static void notEmpty(String data, Type type, String message)
			throws BizException {
		if (StringUtils.isEmpty(data)) {
			throwException(getBizException(type, message));
		}
	}

	public static void notEmpty(String data, Type type, String message,
			boolean roll) throws BizException {
		if (StringUtils.isEmpty(data)) {
			throwException(getBizException(type, message, roll));
		}
	}

	public static void isEmpty(Collection data, String message)
			throws BizException {
		if (!CollectionUtils.isEmpty(data)) {
			throwException(getBizException(message));
		}
	}

	public static void isEmpty(Collection data, String message, boolean roll)
			throws BizException {
		if (!CollectionUtils.isEmpty(data)) {
			throwException(getBizException(message, roll));
		}
	}

	public static void notEmpty(Collection data, String message)
			throws BizException {
		if (CollectionUtils.isEmpty(data)) {
			throwException(getBizException(message));
		}
	}

	public static void notEmpty(Collection data, String message, boolean roll)
			throws BizException {
		if (CollectionUtils.isEmpty(data)) {
			throwException(getBizException(message, roll));
		}
	}

	public static void notEmpty(Collection data, String errorCode,
			String message) throws BizException {
		if (CollectionUtils.isEmpty(data)) {
			throwException(getBizException(errorCode, message));
		}
	}

	public static void notEmpty(Collection data, String errorCode,
			String message, boolean roll) throws BizException {
		if (CollectionUtils.isEmpty(data)) {
			throwException(getBizException(errorCode, message, roll));
		}
	}

	public static void notEmpty(Collection data, Type type, String message)
			throws BizException {
		if (CollectionUtils.isEmpty(data)) {
			throwException(getBizException(type, message));
		}
	}

	public static void notEmpty(Collection data, Type type, String message,
			boolean roll) throws BizException {
		if (CollectionUtils.isEmpty(data)) {
			throwException(getBizException(type, message, roll));
		}
	}

	public static void notEmpty(Object[] data, String message)
			throws BizException {
		if (ArrayUtils.isEmpty(data)) {
			throwException(getBizException(message));
		}
	}

	public static void notEmpty(Object[] data, String message, boolean roll)
			throws BizException {
		if (ArrayUtils.isEmpty(data)) {
			throwException(getBizException(message, roll));
		}
	}

	public static void notEmpty(Object[] data, String errorCode, String message)
			throws BizException {
		if (ArrayUtils.isEmpty(data)) {
			throwException(getBizException(errorCode, message));
		}
	}

	public static void notEmpty(Object[] data, String errorCode,
			String message, boolean roll) throws BizException {
		if (ArrayUtils.isEmpty(data)) {
			throwException(getBizException(errorCode, message, roll));
		}
	}

	public static void notEmpty(Object[] data, Type type, String message)
			throws BizException {
		if (ArrayUtils.isEmpty(data)) {
			throwException(getBizException(type, message));
		}
	}

	public static void notEmpty(Object[] data, Type type, String message,
			boolean roll) throws BizException {
		if (ArrayUtils.isEmpty(data)) {
			throwException(getBizException(type, message, roll));
		}
	}

	public static void equals(String s1, String s2, String message)
			throws BizException {
		if (!StringUtils.equals(s1, s2)) {
			throwException(getBizException(message));
		}
	}

	public static void equals(String s1, String s2, String message, boolean roll)
			throws BizException {
		if (!StringUtils.equals(s1, s2)) {
			throwException(getBizException(message, roll));
		}
	}

	public static void equals(String s1, String s2, Type type, String message)
			throws BizException {
		if (!StringUtils.equals(s1, s2)) {
			throwException(getBizException(type, message));
		}
	}

	public static void equals(String s1, String s2, Type type, String message,
			boolean roll) throws BizException {
		if (!StringUtils.equals(s1, s2)) {
			throwException(getBizException(type, message, roll));
		}
	}

	public static void equalsIgnoreCase(String s1, String s2, String message)
			throws BizException {
		if (!StringUtils.equalsIgnoreCase(s1, s2)) {
			throwException(getBizException(message));
		}
	}

	public static void equalsIgnoreCase(String s1, String s2, String message,
			boolean roll) throws BizException {
		if (!StringUtils.equalsIgnoreCase(s1, s2)) {
			throwException(getBizException(message, roll));
		}
	}

	public static void equals(String s1, String s2, String message, String log)
			throws BizException {
		if (!StringUtils.equals(s1, s2)) {
			throwException(getBizException(message), log);
		}
	}

	public static void equals(String s1, String s2, String message, String log,
			boolean roll) throws BizException {
		if (!StringUtils.equals(s1, s2)) {
			throwException(getBizException(message, roll), log);
		}
	}

	public static void notEquals(String s1, String s2, String message)
			throws BizException {
		if (StringUtils.equals(s1, s2)) {
			throwException(getBizException(message));
		}
	}

	public static void notEquals(String s1, String s2, String message,
			boolean roll) throws BizException {
		if (StringUtils.equals(s1, s2)) {
			throwException(getBizException(message, roll));
		}
	}

	public static void notEquals(String s1, String s2, Type type, String message)
			throws BizException {
		if (StringUtils.equals(s1, s2)) {
			throwException(getBizException(type, message));
		}
	}

	public static void notEquals(String s1, String s2, Type type,
			String message, boolean roll) throws BizException {
		if (StringUtils.equals(s1, s2)) {
			throwException(getBizException(type, message, roll));
		}
	}

	public static void contains(Object[] arr, Object o, String message)
			throws BizException {
		if (!ArrayUtils.contains(arr, o)) {
			throwException(getBizException(message));
		}
	}

	public static void contains(Object[] arr, Object o, String message,
			boolean roll) throws BizException {
		if (!ArrayUtils.contains(arr, o)) {
			throwException(getBizException(message, roll));
		}
	}

	public static void contains(Object[] arr, Object o, Type type,
			String message) throws BizException {
		if (!ArrayUtils.contains(arr, o)) {
			throwException(getBizException(type, message));
		}
	}

	public static void contains(Object[] arr, Object o, Type type,
			String message, boolean roll) throws BizException {
		if (!ArrayUtils.contains(arr, o)) {
			throwException(getBizException(type, message, roll));
		}
	}
}
