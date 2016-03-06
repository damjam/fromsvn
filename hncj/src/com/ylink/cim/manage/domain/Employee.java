package com.ylink.cim.manage.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ylink.cim.common.util.ParaManager;

public class Employee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String alias;
	private String hireDate;
	private String position;
	private String idCard;
	private String birthday;
	private String gender;
	private String tel;
	private String nativePlace;
	private String livePlace;
	private String spareTel;
	private String instancyPerson;
	private String instancyTel;
	private String recruitWay;
	private String review;
	private Date createDate;
	private String createUser;
	private String branchNo;
	private String transfer;
	private String state;
	private Date updateDate;
	private String email;
	private String weibo;
	private String qq;
	private String school;
	private String major;
	private String hobby;
	private String degree;
	private String weixin;
	private EmpTransfer empTransfer = new EmpTransfer();
	
	
	public String getInstancyPerson() {
		return instancyPerson;
	}
	public void setInstancyPerson(String instancyPerson) {
		this.instancyPerson = instancyPerson;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	private String startCreateDate;
	private String endCreateDate;
	public String getPositionName() {
		if (StringUtils.isNotEmpty(position)) {
			return ParaManager.getAllPositions().get(position);
		}
		return null;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
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
	
	public String getHireDate() {
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
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
	
	public String getTransfer() {
		return transfer;
	}
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeibo() {
		return weibo;
	}
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getStartCreateDate() {
		return startCreateDate;
	}
	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}
	public String getEndCreateDate() {
		return endCreateDate;
	}
	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
	public EmpTransfer getEmpTransfer() {
		return empTransfer;
	}
	public void setEmpTransfer(EmpTransfer empTransfer) {
		this.empTransfer = empTransfer;
	}
	
}
