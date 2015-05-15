package com.ylink.cim.common.msg.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.ylink.cim.common.msg.ArrayListMsg;
import com.ylink.cim.common.msg.Constant;
import com.ylink.cim.common.msg.HashtableMsg;
import com.ylink.cim.common.msg.MsgBase;
import com.ylink.gess.common.msg.TransReqMsg;
import com.ylink.gess.common.msg.TransRspMsg;

public class MsgUtil {
	public static String addSpaceForString(String str, int len) {
		if (str.length() <= len) {
			return FILL(str, ' ', len, 'R');
		} else {
			throw new IllegalArgumentException("�������ȹ���");
		}
	}

	public static String addZeroForNum(int num, int len) {
		return FormatNum(num, len);
	}

	public static String FormatNum(int num, int len) {
		String s = String.valueOf(num);
		if (s.length() < len) {
			s = FILL("0", '0', len - s.length(), 'L') + s;
		} else if (s.length() == len) {
			return s;
		} else {
			throw new IllegalArgumentException("�������ȹ���");
		}
		return s;
	}

	public static String blank(int n) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; ++i) {
			sb.append(' ');
		}
		return sb.toString();
	}

	public static String FILL(String s, char c, int n, char f) {
		int iByteLen = MsgUtil.StringToBytes(s).length;
		if (iByteLen >= n) {
			return s;
		}
		byte[] fillChars = new byte[n - iByteLen];
		for (int i = 0; i < fillChars.length; ++i) {
			fillChars[i] = (byte) c;
		}
		if (f == 'L') {
			return new String(fillChars) + s;
		}
		return s + new String(fillChars);
	}

	public static byte[] StringToBytes(String str) {
		try {
			if ((str == null) || (str.length() <= 0)) {
				return new byte[0];
			}
			return str.getBytes();
		} catch (Exception e) {
		}
		return null;
	}

	public static String FIELD_NUBER = "N";
	public static String FIELD_CHAR = "C";

	public static String format(String field, String fieldType, int length) {
		if (field == null) {
			field = "";
		}
		// ��δָ�����ͻ�ָ���������ͣ����ַ�����
		if (field.length() == length) {
			return field;
		} else if (field.length() > length) {
			// ������ȡǰlengthλ
			return StringUtils.abbreviate(field, length);
		} else {
			StringBuilder builder = new StringBuilder();
			if (FIELD_NUBER.equals(fieldType)) {
				for (int i = 0; i < length - field.length(); i++) {
					builder.append("0");
				}
				builder.append(field);
			} else {
				builder.append(field);
				for (int i = 0; i < length - field.length(); i++) {
					builder.append(" ");
				}
			}
			return builder.toString();
		}
	}

	public static void appendField(StringBuilder builder, String fieldType, String field, int length) {
		builder.append(format(field, fieldType, length));
	}

	public static ArrayList<String> splitString(String sSrcMsg, String sSplitStr) {
		if (sSrcMsg != null && sSrcMsg.length() > 0 && sSplitStr != null && sSplitStr.length() > 0) {
			ArrayList<String> alSplit = new ArrayList<String>();

			int iStartIdx = 0;
			int index = sSrcMsg.indexOf(sSplitStr);
			while (iStartIdx < sSrcMsg.length()) {
				if (index <= -1) {
					index = sSrcMsg.length();
				}
				alSplit.add(sSrcMsg.substring(iStartIdx, index));

				iStartIdx = index + 1;
				index = sSrcMsg.indexOf(sSplitStr, iStartIdx);
			}

			return alSplit;
		} else {
			return null;
		}
	}

	/**
	 * �滻�������ַ��ķ��������õ�ͬ��String.replaceAll�����ǿ���֧���ַ����а���������ʽ�ķ���
	 * 
	 * @param mainString
	 *            ��Ҫ�����滻���ַ���
	 * @param oldString
	 *            ���滻��ԭ�ַ���
	 * @param newString
	 *            ���滻������ַ���
	 * @return
	 */
	public static String replaceString(String mainString, String oldString, String newString) {
		if (mainString == null) {
			return null;
		}
		if (newString == null) {
			return mainString;
		}
		int i = mainString.lastIndexOf(oldString);
		if (i < 0) {
			return mainString;
		}
		StringBuilder mainSb = new StringBuilder(mainString);
		while (i >= 0) {
			mainSb.replace(i, i + oldString.length(), newString);
			i = mainString.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}

	/**
	 * ���ַ����л�ȡ������ΪsKey��ֵ
	 * 
	 * @param sMsg
	 *            �����ַ���
	 * @param sKey
	 *            ������
	 * @param cSeparator
	 *            �ָ���
	 * @return ������Ӧ���ַ���ֵ
	 */
	public static String getMsgFieldValue(String sMsg, String sKey, char cSeparatorField, char cSeparatorValue) {
		if (sMsg == null || sMsg.length() <= 0) {
			return "";
		}
		if (sKey == null || sKey.length() <= 0) {
			return "";
		}
		int iMsgLen = sMsg.length();
		int iKeyLen = sKey.length();
		if (sMsg != null && iMsgLen > 0 && iKeyLen > 0) {
			int iStartIndex = 0;
			while (true) {
				iStartIndex = sMsg.indexOf(sKey, iStartIndex);
				if (iStartIndex <= -1) {
					break;
				}
				int iSeparatorFieldIndex = iStartIndex + iKeyLen;
				if ((iStartIndex == 0 && iMsgLen > iSeparatorFieldIndex && sMsg.charAt(iSeparatorFieldIndex) == cSeparatorValue)
						|| (iStartIndex > 0 && iMsgLen > iSeparatorFieldIndex
								&& sMsg.charAt(iStartIndex - 1) == cSeparatorField && sMsg.charAt(iSeparatorFieldIndex) == cSeparatorValue)) {
					iStartIndex += iKeyLen + 1;
					int iEndIndex = sMsg.indexOf(cSeparatorField, iStartIndex);
					iEndIndex = iEndIndex == -1 ? iMsgLen : iEndIndex;
					return sMsg.substring(iStartIndex, iEndIndex);
				} else {
					iStartIndex += iKeyLen;
				}
			}

		}
		return "";
	}

	/**
	 * ����Ĭ�ϵĶηָ��������ַ����л�ȡ������ΪsKey��ֵ
	 * 
	 * @param sMsg
	 *            �����ַ���
	 * @param sKey
	 *            ������
	 * @return ������Ӧ���ַ���ֵ
	 */
	public static String getMsgFieldValue(String sMsg, String sKey) {
		return MsgUtil.getMsgFieldValue(sMsg, sKey, Constant.MSG_SEPARATOR_FIELD, Constant.MSG_SEPARATOR_VALUE).trim();
	}

	/**
	 * ���ݶ������Եķ�����Ϣ�����ö����ֵ
	 * 
	 * @param obj
	 *            ����
	 * @param field
	 *            ������Ϣ
	 * @param sValue
	 *            �����ַ�����ʽ��ֵ
	 * @throws Exception
	 */
	public static void setFieldValue(Object obj, Field field, String sValue) throws Exception {
		String fieldType = field.getType().getName();
		if (fieldType.equals("java.lang.String")) {
			if (sValue == null || sValue.length() <= 0) {
				field.set(obj, "");
			} else {
				field.set(obj, sValue);
			}
		} else if (fieldType.equals("char")) {
			if (sValue == null || sValue.length() <= 0) {
				field.setChar(obj, ' ');
			} else {
				field.setChar(obj, (char) sValue.getBytes()[0]);
			}
		} else if (fieldType.equals("int")) {
			if (sValue == null || sValue.length() <= 0) {
				field.setInt(obj, 0);
			} else {
				field.setInt(obj, new BigDecimal(sValue).intValue());
			}
		} else if (fieldType.equals("double")) {
			if (sValue == null || sValue.length() <= 0) {
				field.setDouble(obj, 0.00);
			} else {
				field.setDouble(obj, Double.parseDouble(sValue));
			}
		} else if (fieldType.equals("long")) {
			if (sValue == null || sValue.length() <= 0) {
				field.setLong(obj, 0);
			} else {
				field.setLong(obj, new BigDecimal(sValue).longValue());
			}
		} else if (fieldType.equals("java.math.BigDecimal")) {
			if (sValue == null || sValue.length() <= 0) {
				field.set(obj, new BigDecimal("0"));
			} else {
				field.set(obj, new BigDecimal(sValue));
			}
		} else if (fieldType.equals("com.ylink.gess.common.msg.ArrayListMsg")) {
			if (sValue == null || sValue.length() <= 0) {
				field.set(obj, new ArrayListMsg());
			} else {
				field.set(obj, new ArrayListMsg(sValue));
			}
		} else if (fieldType.equals("com.ylink.gess.common.msg.HashtableMsg")) {
			if (sValue == null || sValue.length() <= 0) {
				field.set(obj, new HashtableMsg());
			} else {
				field.set(obj, new HashtableMsg(sValue));
			}
		} else if (fieldType.equals("[B")) {// byte[]
			if (sValue == null || sValue.length() <= 0) {
				field.set(obj, "".getBytes());
			} else {
				field.set(obj, sValue.getBytes());
			}
		} else {
			throw new Exception("δ���ö�Ӧ���͵ĸ�ֵ����");
		}
	}

	/**
	 * ���ݶ������Ե�ֵ
	 * 
	 * @param obj
	 *            ����
	 * @param sFieldName
	 *            ���Ե�����
	 * @param sValue
	 *            �����ַ�����ʽ��ֵ
	 * @throws Exception
	 */
	public static void setFieldValue(Object obj, String sFieldName, String sValue) throws Exception {
		Field field = obj.getClass().getField(sFieldName);
		if (field != null) {
			MsgUtil.setFieldValue(obj, field, sValue);
		}
	}

	/**
	 * ���ݷ���Ķ���������Ϣ����ȡ���Ե�ֵ
	 * 
	 * @param obj
	 *            ����
	 * @param field
	 *            ����
	 * @return
	 */
	public static String getFieldValue(Object obj, Field field) throws Exception {
		String fieldType = field.getType().getName();
		if (field.get(obj) != null) {
			if (fieldType.equals("java.lang.String")) {
				return (String) field.get(obj);
			} else if (fieldType.equals("char")) {
				return field.getChar(obj) <= 0 ? "" : "" + field.getChar(obj);
			} else if (fieldType.equals("int")) {
				return "" + field.getInt(obj);
			} else if (fieldType.equals("long")) {
				return "" + field.getLong(obj);
			} else if (fieldType.equals("double")) {
				NumberFormat nf = new DecimalFormat("#");
				nf.setMaximumIntegerDigits(20);
				nf.setMaximumFractionDigits(20);
				return nf.format(field.getDouble(obj));
			} else if (fieldType.equals("java.math.BigDecimal")) {
				return field.get(obj).toString();
			} else if (fieldType.equals("[B")) {
				return new String((byte[]) field.get(obj), Constant.CFG_CHARSET_NAME).trim();
			} else if (fieldType.equals("com.ylink.gess.common.msg.ArrayListMsg")) {
				return field.get(obj).toString();
			} else if (fieldType.equals("com.ylink.gess.common.msg.HashtableMsg")) {
				return field.get(obj).toString();
			} else {
				return field.get(obj).toString();// throw new
													// Exception("δ���ö�Ӧ���͵�ȡֵ����");
			}
		} else {
			return "";
		}

	}

	public static String getFieldValue(Object obj, String sFieldName) throws Exception {
		Field field = obj.getClass().getField(sFieldName);
		return getFieldValue(obj, field);
	}

	/**
	 * �����̶����ȵı���
	 * 
	 * @param sMsg
	 *            ����ԭ��
	 * @param lens
	 *            ��������
	 * @return
	 */
	public static String[] splitFastString(String sMsg, int[] lens) {
		String[] values = new String[lens.length];
		try {
			int offset = 0;
			byte[] bMsg = sMsg.getBytes();
			for (int i = 0; i < lens.length; i++) {
				values[i] = new String(bMsg, offset, lens[i]).trim();
				offset += lens[i];
			}
		} catch (Exception e) {
			// CommUtil.WriteLog(Constant.NORMAL_ERROR,e);
		}
		return values;
	}

	/**
	 * ���ַ���תΪ�ַ���
	 * 
	 * @param str
	 * @return
	 */
	public static char StringToChar(String str) {
		if (str == null || str.length() <= 0) {
			return ' ';
		} else {
			return str.charAt(0);
		}
	}

	/**
	 * ���ֽ���������תΪ�ַ���
	 * 
	 * @param bytes
	 * @return
	 */
	public static String ByteToString(byte[] bytes) throws UnsupportedEncodingException {
		return new String(bytes, Constant.CFG_CHARSET_NAME);
	}

	/**
	 * �ַ�������ת��
	 * 
	 * @param str
	 *            ��ת��������ַ���
	 * @param oldCharset
	 *            ԭ����
	 * @param newCharset
	 *            Ŀ�����
	 * @return
	 */
	public String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
		if (str != null) {
			// �þɵ��ַ���������ַ�����������ܻ�����쳣��
			byte[] bs = str.getBytes(oldCharset);
			// ���µ��ַ����������ַ���
			return new String(bs, newCharset);
		}
		return null;
	}

	/**
	 * ����ͨ��ҵ���������ͨѶ����ͷ
	 * 
	 * @param sSrcMsg
	 *            ԭҵ����
	 * @return ���ͨѶ���ĺ����������
	 */
	public static StringBuffer addNetHeadMsg(String sSrcMsg) throws Exception {
		String sMsgLen = CommUtil.FILL("" + sSrcMsg.getBytes(Constant.CFG_CHARSET_NAME).length, '0',
				Constant.MSG_LEN_LEN, 'L');
		StringBuffer sb = new StringBuffer();
		sb.append(sMsgLen);
		sb.append(sSrcMsg);
		return sb;
	}

	/**
	 * ����ͨ��ҵ���������ͨѶ����ͷ
	 * 
	 * @param vTReqMsg
	 *            ����������
	 */
	public static StringBuffer addNetHeadMsg(MsgBase vMsgBase) throws Exception {
		return addNetHeadMsg(vMsgBase.toString());
	}

	// /**
	// * �ڽ��������������ͨѶ����ͷ
	// * @param vTReqMsg ����������
	// */
	// public static StringBuffer addNetHeadMsg(TransReqMsg vTReqMsg) throws
	// Exception
	// {
	// return addNetHeadMsg(vTReqMsg.toString());
	// }
	//
	// /**
	// * �ڽ�����Ӧ���������ͨѶ����ͷ
	// * @param vTRspMsg ������Ӧ����
	// */
	// public static StringBuffer addNetHeadMsg(TransRspMsg vTRspMsg) throws
	// Exception
	// {
	// return addNetHeadMsg(vTRspMsg.toString());
	// }

	/**
	 * �������ı����ϣ�ȥ��ͨѶ����ͷ
	 * 
	 * @param sbFullMsg
	 *            �����ı���
	 * @return ȥ��ͨѶ����ͷ���ҵ����
	 */
	public static String peekNetHeadMsg(StringBuffer sbFullMsg) throws Exception {
		return sbFullMsg.substring(Constant.MSG_LEN_LEN);
	}

	/**
	 * �������ı����ϣ�ȥ��ͨѶ����ͷ
	 * 
	 * @param bFullMsg
	 *            �����ı���
	 * @return ȥ��ͨѶ����ͷ���ҵ����
	 */
	public static String peekNetHeadMsg(ByteBuffer bFullMsg) throws Exception {
		String sMsg = MsgUtil.ByteToString(bFullMsg.array());
		return sMsg.substring(Constant.MSG_LEN_LEN);
	}

	/**
	 * �ڽ��������������ͨѶ����ͷ
	 * 
	 * @param vTReqMsg
	 *            ����������
	 */
	public static StringBuffer addNetHeadMsg(TransReqMsg vTReqMsg) throws Exception {
		return addNetHeadMsg(vTReqMsg.toString());
	}

	/**
	 * �ڽ�����Ӧ���������ͨѶ����ͷ
	 * 
	 * @param vTRspMsg
	 *            ������Ӧ����
	 */
	public static StringBuffer addNetHeadMsg(TransRspMsg vTRspMsg) throws Exception {
		return addNetHeadMsg(vTRspMsg.toString());
	}
}
