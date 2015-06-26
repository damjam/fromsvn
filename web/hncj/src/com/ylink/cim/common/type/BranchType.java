package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class BranchType extends AbstractType {

	
	public static Map<String,BranchType> ALL=new LinkedHashMap<String,BranchType>();
	public static Map<String,BranchType> ALL_=new LinkedHashMap<String,BranchType>();
	private String tag;
	public static final BranchType HQ_0000 = new BranchType("�ܲ�", "0000", "hq");
	public static final BranchType BR_0001 = new BranchType("����̲", "0001", "dwt");
	public static final BranchType BR_0002 = new BranchType("˫����ʻ�԰ ", "0002", "gjhy");
	public static final BranchType BR_0003 = new BranchType("��̲ ", "0003", "wt");
	public static final BranchType BR_0004 = new BranchType("������õ�� ", "0003", "xbymg");
	public static final BranchType BR_0005 = new BranchType("ŷ�޹���", "0004", "ozgs");
	public static final BranchType BR_0006 = new BranchType("MOCO������ ", "0005", "moco");
	public static final BranchType BR_0007 = new BranchType("�����㳡", "0006", "cjgc");
	public static final BranchType BR_0008 = new BranchType("����ӡ��", "0007", "glyx");
	public static final BranchType BR_0009 = new BranchType("������", "0008", "xwj");
	public static final BranchType BR_0010 = new BranchType("��������", "0009", "cjgj");
	public static final BranchType BR_0011 = new BranchType("��̬ˮ�� ", "0010", "stsc");
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
		if(null==type){
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
