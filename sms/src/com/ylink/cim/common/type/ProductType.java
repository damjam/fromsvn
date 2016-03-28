package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * ��Ʒ����
 *
 */
public class ProductType extends AbstractState {
	public static Map<String, ProductType> ALL = new LinkedHashMap<String, ProductType>();

	protected ProductType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final ProductType TYPE_00 = new ProductType("װ��", "00");
	public static final ProductType TYPE_01 = new ProductType("��ש", "01");
	public static final ProductType TYPE_02 = new ProductType("ǽ��Ϳ��", "02");
	public static final ProductType TYPE_03 = new ProductType("��ԡ", "03");
	public static final ProductType TYPE_04 = new ProductType("���ɵ���", "04");
	public static final ProductType TYPE_05 = new ProductType("ǽֽ", "05");
	public static final ProductType TYPE_06 = new ProductType("�¹�", "06");
	public static final ProductType TYPE_07 = new ProductType("����", "07");
	public static final ProductType TYPE_08 = new ProductType("ľ��", "08");
	public static final ProductType TYPE_09 = new ProductType("��̨�Ŵ�", "09");
	public static final ProductType TYPE_10 = new ProductType("�յ�", "10");
	public static final ProductType TYPE_11 = new ProductType("��ů", "11");
	public static final ProductType TYPE_12 = new ProductType("�Ҿ�", "12");
	public static final ProductType TYPE_13 = new ProductType("����", "13");
	public static final ProductType TYPE_14 = new ProductType("����", "14");
	public static final ProductType TYPE_15 = new ProductType("���¼�", "15");
	public static final ProductType TYPE_16 = new ProductType("����", "16");

	public static ProductType valueOf(String value) throws Exception {
		ProductType flag = ALL.get(value);
		if (null == flag) {
			ExceptionUtils.logException(ProductType.class, "��ʶ����");
		}
		return flag;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("yesOrNos", ProductType.ALL.values());
	}
}
