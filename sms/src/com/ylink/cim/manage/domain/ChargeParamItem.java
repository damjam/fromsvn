package com.ylink.cim.manage.domain;

import java.io.Serializable;

/**
 * ChargeParamItem entity. @author MyEclipse Persistence Tools
 */

public class ChargeParamItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4475310292512606921L;

	private ChargeParamItemId id;

	public ChargeParamItemId getId() {
		return id;
	}

	public void setId(ChargeParamItemId id) {
		this.id = id;
	}

}