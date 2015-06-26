package com.ylink.cim.busioper.domain;
// default package

import java.util.Date;

/**
 * NoticeMsg domain. @author MyEclipse Persistence Tools
 */

public class NoticeMsg implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String content;
	private Date createTime;
	private String subject;
	private String branchNo;
	private String busiType;
	private String custType;
	private String startCreateDate;
	private String endCreateDate;
	
	// Constructors

	public String getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	/** default constructor */
	public NoticeMsg() {
	}
	
	public String getBranchNo() {
		return branchNo;
	}


	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	
}