package com.ylink.cim.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

public class FieldUtils {

	/**
	 * 通过反射获取对象的值并组成字符串返回
	 */
	public static String toString(Object obj) {
		try {
			Class clazz = obj.getClass();
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			Field[] fields = clazz.getDeclaredFields();
			int len = fields.length;
			for (int i = 0; i < len - 1; i++) {
				Field f = fields[i];
				PropertyDescriptor pd = null;
				try {
					// 当属性为常量时获取不到PropertyDescriptor，故此种情况要做异常处理
					pd = new PropertyDescriptor(f.getName(), clazz);
				} catch (Exception e) {
					pd = null;
				}
				if (pd != null) {
					Method getMethod = pd.getReadMethod();// 获得get方法
					Object o = getMethod.invoke(obj);// 执行get方法返回一个Object
					if (o != null) {
						if (StringUtils.isNotEmpty(o.toString())) {
							sb.append(f.getName());
							sb.append("=");
							sb.append(o.toString());
							sb.append(",");
						}
					}
				}
			}
			Field f = fields[len - 1];
			PropertyDescriptor pd = null;
			try {
				// 当属性为常量时获取不到PropertyDescriptor，故此种情况要做异常处理
				pd = new PropertyDescriptor(f.getName(), clazz);
			} catch (Exception e) {
				pd = null;
			}
			if (pd != null) {
				Method getMethod = pd.getReadMethod();// 获得get方法
				Object o = getMethod.invoke(obj);// 执行get方法返回一个Object
				if (o != null) {
					if (StringUtils.isNotEmpty(o.toString())) {
						sb.append(f.getName());
						sb.append("=");
						if (o instanceof String[]) {
							String[] oo = (String[]) o;
							sb.append(arrayToString(oo));
						} else {
							sb.append(o.toString());
						}
						// sb.append(",");
					}
				}
			}
			sb.append("]");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String arrayToString(String[] arr) {
		StringBuffer sb = new StringBuffer();
		for (String str : arr) {
			sb.append(str);
			sb.append(",");
		}
		int len = sb.length();
		return sb.substring(0, len - 1).toString();
	}
}
