package com.ylink.cim.admin.domain;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

// default package

/**
 * UserLog entity. @author MyEclipse Persistence Tools
 */

public class UserLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String userId;
	private String limitId;
	private String loginIp;
	private String loginType;
	private String content;
	private String approach;
	private Date createTime;
	private String logType;
	private String branchNo;
	// Constructors
	private String limitName;
	private String userName;

	public String getContentAbbr() {
		return StringUtils.abbreviate(content, 30);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLimitName() {
		return limitName;
	}

	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}

	/** default constructor */
	public UserLog() {
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

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginType() {
		return this.loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getApproach() {
		return this.approach;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

}