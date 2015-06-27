package com.ylink.cim.common.msg;

import java.util.ArrayList;

public class ArrayListMsg extends ArrayList<Object> {
	private static final long serialVersionUID = 1L;

	public ArrayListMsg() {

	}

	public ArrayListMsg(String v_strMsg) {
		Parse(v_strMsg);
	}

	/**
	 * �����ַ���
	 * 
	 * @param v_strMsg
	 */
	public void Parse(String v_strMsg) {
		this.Parse(v_strMsg, 0, true);
	}

	/**
	 * ���ݷָ�����������ַ���
	 * 
	 * @param v_strMsg
	 *            �����ַ���
	 * @param v_level
	 *            �ָ�������
	 * @param v_bIsFirst
	 *            �Ƿ��ǵ�һ��
	 */
	private void Parse(String v_strMsg, int v_level, boolean v_bIsFirst) {
		if (isHaveSperatorChar(v_strMsg, v_level) == false) {
			// ����ַ�����û�зָ����ģ���ֱ�����ַ�����ʽ��ӵ�������
			this.add(v_strMsg);
		} else {
			// ����ַ���û���������ļ���ָ�������ֱ�����¼��ָ��������ٽ���
			if (LastIndexOfByte(v_strMsg, Constant.SEPARATOR_RECORD[v_level]) < 0) {
				this.Parse(v_strMsg, v_level + 1, v_bIsFirst);
			} else {
				ArrayListMsg alSubMsgList = null;
				if (v_bIsFirst) {
					alSubMsgList = this;
				} else {
					alSubMsgList = new ArrayListMsg();
					this.add(alSubMsgList);
				}

				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < v_strMsg.length(); i++) {
					boolean bSepChar = (v_strMsg.substring(i, i + 1).equals(""
							+ Constant.SEPARATOR_RECORD[v_level]));
					boolean bLast = (i == v_strMsg.length() - 1);
					if (bSepChar || bLast) {
						if (!bSepChar && bLast)
							sb.append(v_strMsg.substring(i, i + 1));
						alSubMsgList.Parse(sb.toString(), v_level + 1, false);
						sb.delete(0, sb.length());
					} else {
						sb.append(v_strMsg.substring(i, i + 1));
					}
				}
			}

		}
	}

	/**
	 * �ַ������Ƿ�����ж༶�ָ���
	 * 
	 * @param v_strMsg
	 *            �ַ���
	 * @param v_level
	 *            �ָ�����ʼ����
	 * @return
	 */
	private boolean isHaveSperatorChar(String v_strMsg, int v_level) {
		for (; v_level < Constant.SEPARATOR_RECORD.length; v_level++) {
			for (int i = v_strMsg.length() - 1; i >= 0; i--) {
				if (v_strMsg.substring(i, i + 1).equals(
						"" + Constant.SEPARATOR_RECORD[v_level]))
					return true;
			}
		}
		return false;
	}

	/**
	 * �ж��ֽ�ֵB���ַ���ת��Ϊ�ֽ��������ֵ�λ��
	 * 
	 * @param str
	 *            �ַ���
	 * @param b
	 *            ��Ҫ���ҵ�byteֵ
	 * @return
	 */
	private int LastIndexOfByte(String str, char b) {
		if (str == null || str.length() <= 0)
			return -1;

		for (int i = str.length() - 1; i >= 0; i--) {
			if (str.substring(i, i + 1).equals("" + b))
				return i;
		}
		return -1;
	}

	/**
	 * �������е�����ת��Ϊ�ַ�����
	 * 
	 * @return
	 */
	public String[] toStringArray() {
		if (this.size() > 0 && this.get(0) instanceof String) {
			String[] values = new String[this.size()];
			for (int i = 0; i < this.size(); i++) {
				values[i] = (String) this.get(i);
			}
			return values;
		} else {
			return null;
		}
	}

	/**
	 * ������ƽ����ϵͳ1.0�ӿڹ淶һ�µı����ַ���
	 */
	@Override
	public String toString() {
		return this.toString(0);
	}

	/**
	 * ���ݱ��ķָ������������ַ���
	 * 
	 * @param v_level
	 * @return
	 */
	private String toString(int v_level) {
		StringBuffer sbMsgBuff = new StringBuffer();
		if (this.size() > 0) {
			StringBuffer sbSubMsgBuff = new StringBuffer();
			if (this.get(0) instanceof String) {
				for (int i = 0; i < this.size(); i++) {
					sbSubMsgBuff.append((String) this.get(i));
					sbSubMsgBuff.append(Constant.SEPARATOR_RECORD[v_level]);
				}
				sbMsgBuff.append(sbSubMsgBuff);
			} else {
				for (int i = 0; i < this.size(); i++) {
					ArrayListMsg alSubMsgList = (ArrayListMsg) this.get(i);
					sbSubMsgBuff.append(alSubMsgList.toString(v_level + 1));
					sbSubMsgBuff.append(Constant.SEPARATOR_RECORD[v_level]);
				}
				sbMsgBuff.append(sbSubMsgBuff);
			}
		}

		return sbMsgBuff.toString();
	}

}