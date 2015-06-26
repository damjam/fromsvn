package com.ylink.cim.admin.domain;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {

	/**
	 * 
	 */
	public static final String INIT_PWD = "111111";
	private static final long serialVersionUID = 1L;

	private String userId;

	private String userName;

	private String userType;

	private String loginId;

	private String loginPwd;

	private Date createTime;

	private String updateUser;

	private Date updateTime;

	private Integer pwdErrorTimes;
	
	private Date errorTime;
	
	private String branchNo;
	private String branchName;
	private String userTypeName;
	private String oldLoginPwd;
	private String confirmPwd;
	private String[] ids;
	
	public String getOldLoginPwd() {
		return oldLoginPwd;
	}

	public void setOldLoginPwd(String oldLoginPwd) {
		this.oldLoginPwd = oldLoginPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public Date getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(Date errorTime) {
		//this.errorTime = errorTime;
	}

	public Integer getPwdErrorTimes() {
		return pwdErrorTimes;
	}

	public void setPwdErrorTimes(Integer pwdErrorTimes) {
		this.pwdErrorTimes = pwdErrorTimes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
