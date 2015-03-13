package com.ylink.cim.admin.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class UserLogActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String userId;
	private String branchNo;
	private String merchantNo;
	private String limitId;
	private String loginIp;
	private String logType;
	private String content;
	private String approach;
	private Date createTime;
	private String userName;
	private String branchName;
	private String merchantName;
	private String limitName;
	
	private String userLogId;

	public UserLogActionForm() {
	}

	public String getApproach() {
		return this.approach;
	}

	public String getBranchName() {
		return branchName;
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

	public Long getId() {
		return this.id;
	}

	public String getLimitId() {
		return this.limitId;
	}

	public String getLimitName() {
		return limitName;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public String getLogType() {
		return this.logType;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public String getMerchantNo() {
		return this.merchantNo;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getUserName() {
		return userName;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
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

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLogId() {
		return userLogId;
	}

	public void setUserLogId(String userLogId) {
		this.userLogId = userLogId;
	}

}
