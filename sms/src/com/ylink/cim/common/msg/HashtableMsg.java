package com.ylink.cim.common.msg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.ylink.cim.common.msg.util.MsgUtil;

public class HashtableMsg {
	// 存放元素的ArrayList
	private ArrayList<Hashtable<String, String>> m_alValue = new ArrayList<Hashtable<String, String>>();

	public HashtableMsg() {

	}

	public HashtableMsg(String v_sMsg) {
		Parse(v_sMsg);
	}

	/**
	 * 根据黄金二级系统1.0接口规范，解析相应的字符串，规则： 1、第一级分隔符的第一段代表字段名，字段名称用二级分隔符分隔
	 * 2、第一级分隔符的第二段代表实际的值，
	 * 如：FieldNameA|FieldNameB|FieldNameC|^11,12,13,|21,22,23,|^
	 * 
	 * @param v_sMsg
	 *            字符串
	 */
	public void Parse(String v_sMsg) {
		ArrayListMsg almValue = new ArrayListMsg();
		almValue.Parse(v_sMsg);

		if (almValue.size() >= 2) {
			// 字段名称
			ArrayListMsg almFieldName = (ArrayListMsg) almValue.get(0);
			// 多条记录值
			ArrayListMsg almFieldValues = (ArrayListMsg) almValue.get(1);

			if (almFieldValues.size() > 0) {
				// 循环遍历每条记录
				for (int i = 0; i < almFieldValues.size(); i++) {
					// 一条记录
					ArrayListMsg almFieldValue = (ArrayListMsg) almFieldValues
							.get(i);

					// 字段名称数目与字段实际值的数目是否相等
					if (almFieldName.size() == almFieldValue.size()) {
						Hashtable<String, String> htValue = new Hashtable<String, String>();
						for (int j = 0; j < almFieldValue.size(); j++) {
							htValue.put((String) almFieldName.get(j),
									(String) almFieldValue.get(j));
						}
						this.add(htValue);
					}
				}
			}
		}
	}

	/**
	 * 按一定的排序规则添加Hashtable数据
	 * 
	 * @param v_htValue
	 * @param v_sOrderInfoMsg
	 *            排序格式，格式：
	 *            "字段名1 数据类型[string/number] 排序方式[asc/desc],字段名1 数据类型[string/number] 排序方式[asc/desc]"
	 */
	public void add(Hashtable<String, String> v_htValue, String v_sOrderInfoMsg) {
		String[][] arrOrderInfo = new String[10][3];
		int index = 0;
		// 解析排序字段
		if (v_sOrderInfoMsg != null && v_sOrderInfoMsg.length() > 0) {
			String splitA[] = v_sOrderInfoMsg.split(",");
			for (int i = 0; i < splitA.length; i++) {
				if (splitA[i].trim().length() > 0) {
					boolean bIsFindName = false;
					boolean bIsFindType = false;
					String splitB[] = splitA[i].trim().split(" ");
					for (int j = 0; j < splitB.length; j++) {
						String tmpValue = splitB[j].trim();
						if (tmpValue.length() > 0) {
							if (bIsFindName == false) {
								arrOrderInfo[index][0] = tmpValue;
								bIsFindName = true;
							} else if (bIsFindType == false) {
								arrOrderInfo[index][1] = tmpValue;
								bIsFindType = true;
							} else {
								arrOrderInfo[index][2] = tmpValue;
								break;
							}
						}
					}
					index++;
				}
			}
		}

		// csl 2009.3.29 时间紧急，暂只支持一个排序字段
		index = -1;
		if (arrOrderInfo[0][0] != null && arrOrderInfo[0][0].length() > 0) {
			String sFieldName = arrOrderInfo[0][0];
			String sFieldType = arrOrderInfo[0][1];
			String sOrderType = arrOrderInfo[0][2];

			if (sFieldType == null || sFieldType.length() <= 0)
				sFieldType = "string";
			if (sOrderType == null || sOrderType.length() <= 0)
				sOrderType = "asc";

			String sNewValue = v_htValue.get(sFieldName);

			for (int i = 0; i < m_alValue.size(); i++) {
				int comp = 0;
				String sItemValue = m_alValue.get(i).get(sFieldName);

				if (sFieldType.toLowerCase().equals("number")) {
					if (sItemValue == null || sItemValue.length() <= 0)
						sItemValue = "0";
					if (sNewValue == null || sNewValue.length() <= 0)
						sNewValue = "0";
					comp = (new BigDecimal(sItemValue)
							.compareTo(new BigDecimal(sNewValue)));
				} else {
					if (sItemValue == null)
						sItemValue = "";
					if (sNewValue == null)
						sNewValue = "";
					comp = sItemValue.compareTo(sNewValue);
				}
				if (sOrderType.toLowerCase().equals("desc") == true) // 降序
				{
					if (comp < 0) {
						index = i;
						break;
					}
				} else // 升序
				{
					if (comp > 0) {
						index = i;
						break;
					}
				}

			}

			if (index >= 0)
				m_alValue.add(index, v_htValue);
			else
				m_alValue.add(v_htValue);

		} else {
			m_alValue.add(v_htValue);
		}

	}

	/**
	 * 添加使用Hashtable存放的元素值
	 * 
	 * @param v_htValue
	 */
	public void add(Hashtable<String, String> v_htValue) {
		m_alValue.add(v_htValue);
	}

	/**
	 * 获得子Hashtable
	 * 
	 * @param v_iIndex
	 * @return
	 */
	public Hashtable<String, String> get(int v_iIndex) {
		if (v_iIndex < m_alValue.size() && v_iIndex >= 0) {
			return m_alValue.get(v_iIndex);
		} else {
			return null;
		}

	}

	/**
	 * 删除元素
	 * 
	 * @param v_iIndex
	 */
	public void remove(int v_iIndex) {
		if (v_iIndex < m_alValue.size())
			m_alValue.remove(v_iIndex);
	}

	/**
	 * 删除元素
	 * 
	 * @param htKey
	 */
	public void remove(Hashtable<String, String> htKey) {
		m_alValue.remove(htKey);
	}

	/**
	 * 元素个数
	 * 
	 * @return
	 */
	public int size() {
		return this.m_alValue.size();
	}

	/**
	 * 重写toString()方法，生成符合黄金二级系统1.0接口规范的报文字符串
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		ArrayListMsg alm = new ArrayListMsg();

		if (m_alValue.size() > 0) {
			// 生成字段名
			ArrayListMsg almFieldName = new ArrayListMsg();
			Hashtable<String, String> htFieldName = m_alValue.get(0);

			Enumeration<String> enumKeys = htFieldName.keys();
			while (enumKeys.hasMoreElements()) {
				String key = enumKeys.nextElement();
				almFieldName.add(key);
			}

			// 生成元素值
			ArrayListMsg almFieldValues = new ArrayListMsg();
			for (int i = 0; i < this.m_alValue.size(); i++) {
				ArrayListMsg alFieldValue = new ArrayListMsg();
				Hashtable<String, String> ht = this.m_alValue.get(i);
				for (int j = 0; j < almFieldName.size(); j++) {
					alFieldValue.add(j, ht.get(almFieldName.get(j)));
				}
				almFieldValues.add(alFieldValue);
			}

			alm.add(almFieldName);
			alm.add(almFieldValues);
		}
		return alm.toString();
	}

	/**
	 * 转为对象数组
	 * 
	 * @param objClass
	 * @return
	 */
	public Object[] toObjects(Class<?> objClass) {
		try {
			Object[] objs = new Object[this.size()];
			for (int i = 0; i < this.size(); i++) {
				Object obj = objClass.newInstance();
				Hashtable<String, String> htValue = this.m_alValue.get(i);

				Enumeration<String> enumKeys = htValue.keys();
				while (enumKeys.hasMoreElements()) {
					String key = enumKeys.nextElement();
					MsgUtil.setFieldValue(obj, key, htValue.get(key));
				}
			}
			return objs;
		} catch (Exception e) {

		}
		return null;
	}
}