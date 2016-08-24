package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class DegreeType extends AbstractType {
	public static Map<String, DegreeType> ALL = new LinkedHashMap<String, DegreeType>();

	public static final DegreeType MIDDLE_SCHOOL = new DegreeType("初中", "00");
	public static final DegreeType HIGH_SCHOOL = new DegreeType("高中/中专", "01");
	public static final DegreeType JUNIOR_COLLEGE = new DegreeType("大专", "02");
	public static final DegreeType BACHELOR = new DegreeType("本科", "03");
	public static final DegreeType MASTER = new DegreeType("硕士", "04");
	public static final DegreeType DOCTOR = new DegreeType("博士", "05");
	public static final DegreeType OTHER = new DegreeType("其他", "06");
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
