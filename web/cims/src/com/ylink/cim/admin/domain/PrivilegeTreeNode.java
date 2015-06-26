package com.ylink.cim.admin.domain;

import java.io.Serializable;

public class PrivilegeTreeNode implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String ROOT_PARENT = "-1";
	private String code;
	private String parentCode;
	private String name;
	private String tip = "";
	private String isMenu;
	private String uri = "javascript:;";
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getParentCode() {
		return parentCode;
	}
	
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTip() {
		return tip;
	}
	
	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}

}