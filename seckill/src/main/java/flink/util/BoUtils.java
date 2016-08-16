package flink.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

/**
 * bean utils.
 * 
 * 
 */
public abstract class BoUtils {
	/**
	 * 添加属性.
	 * 
	 * @param targetList
	 *            目标列表
	 * @param targetKeyName
	 *            目标属性key 从目标列表中元素取key值(如user.code)
	 * @param targetPropertyName
	 *            目标属性名称 添加的属性名称(如user.name)
	 * 
	 * @param sourceList
	 *            源列表
	 * @param sourceKeyName
	 *            源属性key 从源列表中取key值对应的列表元素
	 * @param sourcePropertyName
	 *            源属性名称 从源列表元素中取属性值作为目标属性名称的值
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void addProperty(List targetList, String targetKeyName,
			String targetPropertyName, List sourceList, String sourceKeyName,
			String sourcePropertyName) {
		if (CollectionUtils.isEmpty(targetList)
				|| CollectionUtils.isEmpty(sourceList)) {
			return;
		}

		try {
			// 将源list转换为map.
			Map sourceMap = new HashMap();
			for (Iterator i = sourceList.iterator(); i.hasNext();) {
				Object element = i.next();
				Object keyValue = PropertyUtils.getProperty(element,
						sourceKeyName);
				Object propertyValue = PropertyUtils.getProperty(element,
						sourcePropertyName);

				sourceMap.put(keyValue, propertyValue);
			}

			// 设置目标属性.
			for (Iterator i = targetList.iterator(); i.hasNext();) {
				Object element = i.next();
				Object keyValue = PropertyUtils.getProperty(element,
						targetKeyName);
				Object propertyValue = sourceMap.get(keyValue);

				PropertyUtils.setProperty(element, targetPropertyName,
						propertyValue);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void addProperty(List targetList, String targetKeyName,
			String targetPropertyName, Map<String, String> sourceMap,
			String sourceKeyName, String sourcePropertyName) {
		if (CollectionUtils.isEmpty(targetList) || MapUtils.isEmpty(sourceMap)) {
			return;
		}

		try {
			// 设置目标属性.
			for (Iterator i = targetList.iterator(); i.hasNext();) {
				Object element = i.next();
				Object keyValue = PropertyUtils.getProperty(element,
						targetKeyName);
				Object propertyValue = sourceMap.get(keyValue);

				PropertyUtils.setProperty(element, targetPropertyName,
						propertyValue);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 取属性.
	 * 
	 * @param list
	 * @param propertyName
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static List getProperties(List list, String propertyName) {
		if (CollectionUtils.isEmpty(list)) {
			return Collections.EMPTY_LIST;
		}

		Set result = new HashSet();
		try {
			for (Iterator i = list.iterator(); i.hasNext();) {
				Object value = PropertyUtils
						.getProperty(i.next(), propertyName);

				if (value != null) {
					result.add(value);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return new ArrayList(result);
	}
}
