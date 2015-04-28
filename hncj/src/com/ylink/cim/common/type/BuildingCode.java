package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class BuildingCode extends AbstractType {

	
	public static Map<String,BuildingCode> ALL=new LinkedHashMap<String,BuildingCode>();
	public static Map<String,BuildingCode> ALL_=new LinkedHashMap<String,BuildingCode>();
	private String tag;
	//����̲סլһ��.��ҵ.��Ԣ
	public static final BuildingCode DWT_04 = new BuildingCode("����̲4#", "0104", "01");
	public static final BuildingCode DWT_05 = new BuildingCode("����̲5#", "0105", "01");
	public static final BuildingCode DWT_06 = new BuildingCode("����̲6#", "0106", "01");
	public static final BuildingCode DWT_A1 = new BuildingCode("����̲A1", "01A1", "01");
	public static final BuildingCode DWT_A2 = new BuildingCode("����̲A2", "01A2", "01");
	public static final BuildingCode DWT_A3 = new BuildingCode("����̲A3", "01A3", "01");
	public static final BuildingCode DWT_A4 = new BuildingCode("����̲A4", "01A4", "01");
	public static final BuildingCode DWT_A5 = new BuildingCode("����̲A5", "01A5", "01");
	public static final BuildingCode DWT_A6 = new BuildingCode("����̲A6", "01A6", "01");
	public static final BuildingCode DWT_A7 = new BuildingCode("����̲A7", "01A7", "01");
	//����̲סլ����
	public static final BuildingCode DWT_01 = new BuildingCode("����̲1#", "0101", "01");
	public static final BuildingCode DWT_02 = new BuildingCode("����̲2#", "0102", "01");
	public static final BuildingCode DWT_03 = new BuildingCode("����̲3#", "0103", "01");
	//����̲סլ����
	public static final BuildingCode DWT_07 = new BuildingCode("����̲7#", "0107", "01");
	public static final BuildingCode DWT_08 = new BuildingCode("����̲8#", "0108", "01");
	public static final BuildingCode DWT_09 = new BuildingCode("����̲9#", "0109", "01");
	
	protected BuildingCode(String name, String value, String tag) {
		super(name, value);
		this.tag = tag;
		ALL.put(value, this);
		ALL_.put(tag, this);
	}
	public String getTag() {
		return tag;
	}

	public static BuildingCode valueOf(String value) {
		
		BuildingCode type = ALL.get(value);
			
		return type; 
	}
	public static BuildingCode getByTag(String value) {
		BuildingCode type = ALL_.get(value);
		
		return type; 
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("buildingCodes", BuildingCode.ALL.values());
	}
	
	
}
