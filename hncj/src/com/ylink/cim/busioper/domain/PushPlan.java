package com.ylink.cim.busioper.domain;

import java.util.Date;

/**
 * PushInfo domain. @author MyEclipse Persistence Tools
 */

public class PushPlan implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String pushType;
	private Date expPushTime;
	private String subject;
	private String content;
	private Date createTime;
	private String state;
	private String branchNo;
	private String busiType;
	private String subsState;
	private String custType;
	
	public String getBranchNo() {
		return branchNo;
	}

	public String getBusiType() {
		return busiType;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCustType() {
		return custType;
	}

	public Date getExpPushTime() {
		return expPushTime;
	}

	public String getId() {
		return this.id;
	}

	public String getPushType() {
		return this.pushType;
	}

	public String getState() {
		return state;
	}

	public String getSubject() {
		return subject;
	}

	public String getSubsState() {
		return subsState;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public void setExpPushTime(Date expPushTime) {
		this.expPushTime = expPushTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSubsState(String subsState) {
		this.subsState = subsState;
	}

}