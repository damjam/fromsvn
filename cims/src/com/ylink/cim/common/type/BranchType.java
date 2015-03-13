package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class BranchType extends AbstractType {

	
	public static Map<String,BranchType> ALL=new LinkedHashMap<String,BranchType>();
	public static Map<String,BranchType> ALL_=new LinkedHashMap<String,BranchType>();
	private String tag;
	public static final BranchType SZGOLD = new BranchType("深圳金融电子结算中心", "0000", "sz");
	public static final BranchType BODG = new BranchType("东莞银行", "2001", "dg");
	public static final BranchType BOGZ = new BranchType("广州银行 ", "2002", "gz");
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
			type = SZGOLD;
		}
			
		return type; 
	}
	public static BranchType getByTag(String value) {
		BranchType type = ALL_.get(value);
		if (type == null) {
			type = SZGOLD;
		}
		return type; 
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("branchTypes", BranchType.ALL.values());
	}
	
	
}
