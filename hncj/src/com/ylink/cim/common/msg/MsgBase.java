package com.ylink.cim.common.msg;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import com.ylink.cim.common.msg.util.MsgUtil;

/**
 * ��ֵ��Ӧģʽ�ı��Ļ���
 */
public class MsgBase {
	/** ����ֶ��б��漯�� */
	private static ConcurrentHashMap<String, Field[]> sFieldMap = new ConcurrentHashMap<String, Field[]>();

	/**
	 * ���캯��
	 */
	public MsgBase() {

	}

	/**
	 * <p>
	 * �������ģ���ʽ��Key=Value#Key=Value
	 * </p>
	 * <p>
	 * 
	 * @param vMsg
	 *            �����ַ���
	 *            </p>
	 */
	public void Parse(String sMsg) throws Exception {
		Field[] fields = this.getFields(this.getClass());
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			String value = MsgUtil.getMsgFieldValue(sMsg, fieldName);
			if (value != null && value.length() > 0) {
				MsgUtil.setFieldValue(this, fields[i], value);
			}
		}
	}

	// ����
	public void Parse(StringBuffer sbMsg) throws Exception {
		Parse(sbMsg.toString());
	}

	/**
	 * ���ɷ��ϻƽ����ϵͳ1.0�ӿ�Ҫ��ı���
	 */
	public String toString() {
		return toStringBuffer().toString();
	}

	/**
	 * ���ɷ��ϻƽ����ϵͳ1.0�ӿ�Ҫ��ı���
	 */
	public String toString(String sFilterFields) {
		return toStringBuffer(sFilterFields).toString();
	}

	/**
	 * ���ɷ��ϻƽ����ϵͳ1.0�ӿ�Ҫ��ı���
	 */
	public StringBuffer toStringBuffer() {
		return toStringBuffer(null);
	}

	/**
	 * ���ɷ��ϻƽ����ϵͳ1.0�ӿ�Ҫ��ı���
	 * 
	 * @param sFilterFields
	 *            ����ʱ���˵��ֶ��б�ʹ��","�ŷָ� modify by csl 2011.5.12 ���ӹ����ֶεĹ���
	 */
	public StringBuffer toStringBuffer(String sFilterFields) {
		StringBuffer sb = new StringBuffer(500);
		sb.append(Constant.MSG_SEPARATOR_FIELD);
		try {
			Field[] fields = this.getFields(this.getClass());
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				if (sFilterFields != null && sFilterFields.indexOf("," + fieldName + ",") != -1)
					continue;
				String fieldValue = MsgUtil.getFieldValue(this, fields[i]);
				MsgBase.addMsgFieldValue(sb, fieldName, fieldValue);
			}
		} catch (Exception e) {
			// CommUtil.WriteLog(Constant.NORMAL_ERROR,e);
		}
		return sb;
	}

	/** ͨ�������ȡ����ֶ��б� */
	protected Field[] getFields(Class vClass) {
		Field[] fields = sFieldMap.get(vClass.getName());
		if (fields == null) {
			fields = vClass.getFields();
			sFieldMap.put(vClass.getName(), fields);
		}
		return fields;
	}

	/**
	 * ׷���ַ�����
	 */
	public static StringBuffer addMsgFieldValue(StringBuffer sbSrc, String sFieldName, String sFieldValue) {

		if (sFieldName != null && sFieldValue.length() > 0) {
			// ��������
			sbSrc.append(sFieldName);
			sbSrc.append(Constant.MSG_SEPARATOR_VALUE);
			// ��������������Ե�ֵ
			sbSrc.append(sFieldValue);
			// �ָ���
			sbSrc.append(Constant.MSG_SEPARATOR_FIELD);
		}
		return sbSrc;
	}
}