package com.ylink.cim.busioper.domain;

import java.util.Date;

// default package

/**
 * PushRecord entity. @author MyEclipse Persistence Tools
 */

public class PushRecord implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String pushAdd;
	//N:Î´ÍÆËÍY:ÒÑÍÆËÍ
	private String state;
	private Date pushTime;
	private String planId;
	
	
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPushAdd() {
		return this.pushAdd;
	}

	public void setPushAdd(String pushAdd) {
		this.pushAdd = pushAdd;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getPushTime() {
		return this.pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

}