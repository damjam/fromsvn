package com.ylink.cim.manage.domain;

import java.io.File;
import java.util.Date;

/**
 * OwnerInfoId domain. @author MyEclipse Persistence Tools
 */

public class OwnerInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String mobile;
	private String ownerName;
	private String email;
	private String idCard;
	private String gender;
	private String creditLevel;
	private String checkinDate;
	private String checkinState;
	private Integer checkinNum;
	private String grade;
	private Integer carNum;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private String remark;
	private String houseSn;
	private Integer oweTimes;
	private Integer oweDays;
	private String state;
	private Date cancelDate;
	private String cancelUser;
	private String hasAcct;
	private Double area;
	private String branchNo;
	private String fileName;
	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	// Constructors

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getHasAcct() {
		return hasAcct;
	}

	public void setHasAcct(String hasAcct) {
		this.hasAcct = hasAcct;
	}

	public String getState() {
		return state;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(String cancelUser) {
		this.cancelUser = cancelUser;
	}

	public void setState(String state) {
		this.state = state;
	}

	/** default constructor */
	public OwnerInfo() {
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getOweTimes() {
		return oweTimes;
	}

	public void setOweTimes(Integer oweTimes) {
		this.oweTimes = oweTimes;
	}

	public Integer getOweDays() {
		return oweDays;
	}

	public void setOweDays(Integer oweDays) {
		this.oweDays = oweDays;
	}

	/** minimal constructor */
	public OwnerInfo(String id) {
		this.id = id;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCreditLevel() {
		return this.creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public String getCheckinDate() {
		return this.checkinDate;
	}

	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}

	public String getCheckinState() {
		if (checkinState == null) {
			checkinState = "";
		}
		return this.checkinState;
	}

	public void setCheckinState(String checkinState) {
		this.checkinState = checkinState;
	}

	public Integer getCheckinNum() {
		return this.checkinNum;
	}

	public void setCheckinNum(Integer checkinNum) {
		this.checkinNum = checkinNum;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getCarNum() {
		return this.carNum;
	}

	public void setCarNum(Integer carNum) {
		this.carNum = carNum;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

}