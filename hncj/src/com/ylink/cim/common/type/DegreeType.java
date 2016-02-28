package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class DegreeType extends AbstractType {
	public static Map<String, DegreeType> ALL = new LinkedHashMap<String, DegreeType>();

	public static final DegreeType MIDDLE_SCHOOL = new DegreeType("����", "00");
	public static final DegreeType HIGH_SCHOOL = new DegreeType("����/��ר", "01");
	public static final DegreeType JUNIOR_COLLEGE = new DegreeType("��ר", "02");
	public static final DegreeType BACHELOR = new DegreeType("����", "03");
	public static final DegreeType MASTER = new DegreeType("˶ʿ", "04");
	public static final DegreeType DOCTOR = new DegreeType("��ʿ", "05");
	public static final DegreeType OTHER = new DegreeType("����", "06");
	protected DegreeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DegreeType valueOf(String value) {
		DegreeType type = ALL.get(value);
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("degreeTypes", DegreeType.ALL.values());
	}
}
