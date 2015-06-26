package com.ylink.cim.busioper.domain;

import java.util.Date;

/**
 * PushInfo entity. @author MyEclipse Persistence Tools
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

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getSubsState() {
		return subsState;
	}

	public void setSubsState(String subsState) {
		this.subsState = subsState;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getExpPushTime() {
		return expPushTime;
	}

	public void setExpPushTime(Date expPushTime) {
		this.expPushTime = expPushTime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPushType() {
		return this.pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

}