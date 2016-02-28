package com.ylink.cim.manage.domain;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String alias;
	private String hiredate;
	private String position;
	private String idcard;
	private String birthday;
	private String gender;
	private String tel;
	private String nativePlace;
	private String livePlace;
	private String spareTel;
	private String instancyTel;
	private String recruitWay;
	private String review;
	private Date createDate;
	private String createUser;
	private String branchNo;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getHiredate() {
		return hiredate;
	}
	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getLivePlace() {
		return livePlace;
	}
	public void setLivePlace(String livePlace) {
		this.livePlace = livePlace;
	}
	public String getSpareTel() {
		return spareTel;
	}
	public void setSpareTel(String spareTel) {
		this.spareTel = spareTel;
	}
	public String getInstancyTel() {
		return instancyTel;
	}
	public void setInstancyTel(String instancyTel) {
		this.instancyTel = instancyTel;
	}
	public String getRecruitWay() {
		return recruitWay;
	}
	public void setRecruitWay(String recruitWay) {
		this.recruitWay = recruitWay;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	
}
