package com.ylink.cim.manage.domain;

import java.io.Serializable;

public class ChargeParamItemId implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2704050631431907353L;
	private String paramId;
	private String itemId;
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
}
