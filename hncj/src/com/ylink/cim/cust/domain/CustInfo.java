package com.ylink.cim.cust.domain;

import java.util.Date;

// default package

/**
 * CustInfo domain. @author MyEclipse Persistence Tools
 */

public class CustInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String sex;
	private String birthday;
	private String cardType;
	private String idCard;
	private String mobile;
	private String addr;
	private String email;
	private String custType;
	private String subsPhone;
	private String subsEmail;
	private Date createTime;
	private String userId;
	private String fromType;
	private Date vSendTime;
	private Date activeMailTime;
	private String vMailStr;
	private String state;
	private String activeState;
	// 所属机构
	private String branchNo;
	private String extend;
	private String column1;
	private String column2;
	private String column3;
	// 非数据库表字段
	private String loginPwd;

	private String payChnlType;

	private String payChnlNo;
	private String acctNo;

	private String signState;

	private String busiType;
	private String areaCode;

	public String getActiveState() {
		return activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getPayChnlType() {
		return payChnlType;
	}

	public void setPayChnlType(String payChnlType) {
		this.payChnlType = payChnlType;
	}

	public String getPayChnlNo() {
		return payChnlNo;
	}

	public void setPayChnlNo(String payChnlNo) {
		this.payChnlNo = payChnlNo;
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

	public String getvMailStr() {
		return vMailStr;
	}

	public void setvMailStr(String vMailStr) {
		this.vMailStr = vMailStr;
	}

	public Date getvSendTime() {
		return vSendTime;
	}

	public void setvSendTime(Date vSendTime) {
		this.vSendTime = vSendTime;
	}

	public Date getActiveMailTime() {
		return activeMailTime;
	}

	public void setActiveMailTime(Date activeMailTime) {
		this.activeMailTime = activeMailTime;
	}

	public String getSignState() {
		return signState;
	}

	public void setSignState(String signState) {
		this.signState = signState;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	/** default constructor */
	public CustInfo() {
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getSubsPhone() {
		return this.subsPhone;
	}

	public void setSubsPhone(String subsPhone) {
		this.subsPhone = subsPhone;
	}

	public String getSubsEmail() {
		return this.subsEmail;
	}

	public void setSubsEmail(String subsEmail) {
		this.subsEmail = subsEmail;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getColumn3() {
		return column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}

}