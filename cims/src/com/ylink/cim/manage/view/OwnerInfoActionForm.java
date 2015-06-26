package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class OwnerInfoActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private String remark;
	private String houseSn;
	private FormFile file;
	
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCreditLevel() {
		return creditLevel;
	}
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	public String getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}
	public String getCheckinState() {
		return checkinState;
	}
	public void setCheckinState(String checkinState) {
		this.checkinState = checkinState;
	}
	public Integer getCheckinNum() {
		return checkinNum;
	}
	public void setCheckinNum(Integer checkinNum) {
		this.checkinNum = checkinNum;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Integer getCarNum() {
		return carNum;
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
