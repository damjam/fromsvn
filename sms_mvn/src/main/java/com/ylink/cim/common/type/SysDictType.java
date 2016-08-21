package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class SysDictType extends AbstractType {

	public static Map<String, SysDictType> ALL = new LinkedHashMap<String, SysDictType>();

	public static final SysDictType UserType = new SysDictType("�û�����", "UserType");
	public static final SysDictType BranchType = new SysDictType("��������", "BranchType");
	public static final SysDictType EconomicalType = new SysDictType("�������÷�¥��", "EconomicalType");
	public static final SysDictType RentType = new SysDictType("���ⷿ¥��", "RentType");
	public static final SysDictType HouseType = new SysDictType("סլ��¥��", "HouseType");
	public static final SysDictType FlatType = new SysDictType("��Ԣ��¥��", "FlatType");
	public static final SysDictType BranchPostType = new SysDictType("����ְλ����", "BranchPostType");
	public static final SysDictType CenterPostType = new SysDictType("�ܲ�ְλ����", "CenterPostType");
	public static final SysDictType ProductType = new SysDictType("��Ʒ", "ProductType");
	public static final SysDictType CoutryType = new SysDictType("����", "CountryType");
	public static final SysDictType CarType = new SysDictType("��������", "CarType");
	public static final SysDictType FMaterialType = new SysDictType("�ŵ����", "FMaterialType");
	public static final SysDictType BMaterialType = new SysDictType("���������", "BMaterialType");
	public static final SysDictType FColorType = new SysDictType("�ŵ���ɫ����", "FColorType");
	public static final SysDictType BColorType = new SysDictType("�������ɫ����", "BColorType");
	protected SysDictType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static SysDictType valueOf(String value) throws Exception {

		SysDictType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(SysDictType.class, "���ʹ���");
		}
		return sysDictType;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("dictTypes", ALL.values());
	}

}
