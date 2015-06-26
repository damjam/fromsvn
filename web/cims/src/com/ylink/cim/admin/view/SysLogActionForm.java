package com.ylink.cim.admin.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class SysLogActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String userId;
	private String userName;
	private String branchNo;
	private String branchName;
	private String merchantNo;
	private String merchantName;
	private String limitId;
	private String limitName;
	private String errorCode;
	private String logType;
	private String logClass;
	private String content;
	private String approach;
	private String viewUser;
	private Date viewDate;
	private String state;
	private Date createTime;

	public SysLogActionForm() {
	}

	public String getApproach() {
		return this.approach;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public String getBranchNo() {
		return this.branchNo;
	}

	public String getContent() {
		return this.content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public Long getId() {
		return this.id;
	}

	public String getLimitId() {
		return this.limitId;
	}

	public String getLimitName() {
		return limitName;
	}

	public String getLogClass() {
		return this.logClass;
	}

	public String getLogType() {
		return this.logType;
	}

	public String getMerchantName() {
		return this.merchantName;
	}

	public String getMerchantNo() {
		return this.merchantNo;
	}

	public String getState() {
		return this.state;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public Date getViewDate() {
		return this.viewDate;
	}

	public String getViewUser() {
		return this.viewUser;
	}

	public void setApproach(String approach) {
		this.approach = approach;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}

	public void setLogClass(String logClass) {
		this.logClass = logClass;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public void setViewUser(String viewUser) {
		this.viewUser = viewUser;
	}
}
