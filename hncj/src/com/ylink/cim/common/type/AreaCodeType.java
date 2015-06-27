package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class AreaCodeType extends AbstractType {

	public static Map<String, AreaCodeType> ALL = new LinkedHashMap<String, AreaCodeType>();

	public static final AreaCodeType BEIJING = new AreaCodeType("������", "110000");
	public static final AreaCodeType TIANJING = new AreaCodeType("�����",
			"120000");
	public static final AreaCodeType HEIBEI = new AreaCodeType("�ӱ�ʡ", "130000");
	public static final AreaCodeType SHANXI = new AreaCodeType("ɽ��ʡ", "140000");
	public static final AreaCodeType INNERMONGOLIA = new AreaCodeType("���ɹ�������",
			"150000");
	public static final AreaCodeType LIAONING = new AreaCodeType("����ʡ",
			"210000");
	public static final AreaCodeType JILIN = new AreaCodeType("����ʡ", "220000");
	public static final AreaCodeType HEILONGJIANG = new AreaCodeType("������ʡ",
			"230000");
	public static final AreaCodeType SHANGHAI = new AreaCodeType("�Ϻ���",
			"310000");
	public static final AreaCodeType JIANGSU = new AreaCodeType("����ʡ", "320000");
	public static final AreaCodeType ZHEJIANG = new AreaCodeType("�㽭ʡ",
			"330000");
	public static final AreaCodeType ANHUI = new AreaCodeType("����ʡ", "340000");
	public static final AreaCodeType FUJIAN = new AreaCodeType("����ʡ", "360000");
	public static final AreaCodeType JIANGXI = new AreaCodeType("����ʡ", "350000");
	public static final AreaCodeType SHANDONG = new AreaCodeType("ɽ��ʡ",
			"370000");
	public static final AreaCodeType HENAN = new AreaCodeType("����ʡ", "410000");
	public static final AreaCodeType HUBEI = new AreaCodeType("����ʡ", "420000");
	public static final AreaCodeType HUNAN = new AreaCodeType("����ʡ", "430000");
	public static final AreaCodeType GUANGDONG = new AreaCodeType("�㶫ʡ",
			"440000");
	public static final AreaCodeType SHENZHEN = new AreaCodeType("����", "440300");
	public static final AreaCodeType GUANGXI = new AreaCodeType("����׳��������",
			"450000");
	public static final AreaCodeType HAINAN = new AreaCodeType("����ʡ", "460000");
	public static final AreaCodeType CHONGQING = new AreaCodeType("������",
			"500000");
	public static final AreaCodeType SICHUANG = new AreaCodeType("�Ĵ�ʡ",
			"510000");
	public static final AreaCodeType GUIZHOU = new AreaCodeType("����ʡ", "520000");
	public static final AreaCodeType YUNNAN = new AreaCodeType("����ʡ", "530000");
	public static final AreaCodeType TIBET = new AreaCodeType("����������", "540000");
	public static final AreaCodeType SHAANXI = new AreaCodeType("����ʡ", "610000");
	public static final AreaCodeType GANSU = new AreaCodeType("����ʡ", "620000");
	public static final AreaCodeType QINGHAI = new AreaCodeType("�ຣʡ", "630000");
	public static final AreaCodeType NINGXIA = new AreaCodeType("���Ļ���������",
			"640000");
	public static final AreaCodeType XINJIANG = new AreaCodeType("�½�ά���������",
			"650000");
	public static final AreaCodeType TAIWAN = new AreaCodeType("̨��ʡ", "710000");
	public static final AreaCodeType HONGKONG = new AreaCodeType("����ر�������",
			"810000");

	protected AreaCodeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AreaCodeType valueOf(String value) throws Exception {

		AreaCodeType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AreaCodeType.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("areaCodeTypes", AreaCodeType.ALL.values());
	}
}
