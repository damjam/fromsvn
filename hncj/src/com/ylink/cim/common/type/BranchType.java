package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class BranchType extends AbstractType {

	public static Map<String, BranchType> ALL = new LinkedHashMap<String, BranchType>();
	public static Map<String, BranchType> ALL_ = new LinkedHashMap<String, BranchType>();
	private String tag;
	public static final BranchType HQ_0000 = new BranchType("总部", "0000", "hq");
	public static final BranchType BR_0001 = new BranchType("东外滩", "0001",
			"dwt");
	public static final BranchType BR_0002 = new BranchType("双汇国际花园 ", "0002",
			"gjhy");
	public static final BranchType BR_0003 = new BranchType("外滩 ", "0003", "wt");
	public static final BranchType BR_0004 = new BranchType("西班牙玫瑰 ", "0004",
			"xbymg");
	public static final BranchType BR_0005 = new BranchType("欧洲故事", "0005",
			"ozgs");
	public static final BranchType BR_0006 = new BranchType("MOCO新世界 ", "0006",
			"moco");
	public static final BranchType BR_0007 = new BranchType("昌建广场", "0007",
			"cjgc");
	public static final BranchType BR_0008 = new BranchType("格林印象", "0008",
			"glyx");
	public static final BranchType BR_0009 = new BranchType("新旺角", "0009",
			"xwj");
	public static final BranchType BR_0010 = new BranchType("昌建国际", "0010",
			"cjgj");
	public static final BranchType BR_0011 = new BranchType("生态水城 ", "0011",
			"stsc");

	protected BranchType(String name, String value, String tag) {
		super(name, value);
		this.tag = tag;
		ALL.put(value, this);
		ALL_.put(tag, this);
	}

	public String getTag() {
		return tag;
	}

	public static BranchType valueOf(String value) {

		BranchType type = ALL.get(value);
		if (null == type) {
			type = HQ_0000;
		}

		return type;
	}

	public static BranchType getByTag(String value) {
		BranchType type = ALL_.get(value);
		if (type == null) {
			type = HQ_0000;
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("branchTypes", BranchType.ALL.values());
	}

}
