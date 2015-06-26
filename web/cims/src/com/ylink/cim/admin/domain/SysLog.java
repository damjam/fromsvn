package com.ylink.cim.admin.domain;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

// default package

/**
 * SysLog entity. @author MyEclipse Persistence Tools
 */

public class SysLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String userId;
	private String limitId;
	private String errorCode;
	private String logType;
	private String logClass;
	private String content;
	private String viewUser;
	private Date viewDate;
	private String state;
	private Date createTime;

	private String limitName;

	// Constructors

	public String getContentAbbr() {
		return StringUtils.abbreviate(content, 30);
	}

	public String getLimitName() {
		return limitName;
	}

	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}

	/** default constructor */
	public SysLog() {
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLimitId() {
		return this.limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogClass() {
		return this.logClass;
	}

	public void setLogClass(String logClass) {
		this.logClass = logClass;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getViewUser() {
		return this.viewUser;
	}

	public void setViewUser(String viewUser) {
		this.viewUser = viewUser;
	}

	public Date getViewDate() {
		return this.viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}