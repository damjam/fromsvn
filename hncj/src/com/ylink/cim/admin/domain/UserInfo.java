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
	
	private String[] ids;
	
	


	private String oldLoginPwd;

	private String confirmPwd;

	public String getBranchName() {
		return branchName;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getErrorTime() {
		return errorTime;
	}


	public String[] getIds() {
		return ids;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public String getOldLoginPwd() {
		return oldLoginPwd;
	}

	public Integer getPwdErrorTimes() {
		return pwdErrorTimes;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserType() {
		return userType;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setErrorTime(Date errorTime) {
		//this.errorTime = errorTime;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public void setOldLoginPwd(String oldLoginPwd) {
		this.oldLoginPwd = oldLoginPwd;
	}

	public void setPwdErrorTimes(Integer pwdErrorTimes) {
		this.pwdErrorTimes = pwdErrorTimes;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
}
