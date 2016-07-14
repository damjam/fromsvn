package com.ylink.cim.manage.domain;
import java.util.Date;

/**
 * Renter entity. @author MyEclipse Persistence Tools
 */

public class RenterInfo extends BranchField implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String id;
	private String renterName;
	private String mobile;
	private String instancyTel;
	private String instancyPerson;
	private String checkinDate;
	private String checkoutDate;
	private Double depositAmt;
	private Double returnAmt;
	private Date createDate;
	private String payMode;
	private String createUser;
	private String state;
	private Integer livingNum;
	private String houseSn;
	
	
	// Constructors

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	/** default constructor */
	public RenterInfo() {
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRenterName() {
		return this.renterName;
	}

	public void setRenterName(String renterName) {
		this.renterName = renterName;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getInstancyTel() {
		return this.instancyTel;
	}

	public void setInstancyTel(String instancyTel) {
		this.instancyTel = instancyTel;
	}

	public String getInstancyPerson() {
		return this.instancyPerson;
	}

	public void setInstancyPerson(String instancyPerson) {
		this.instancyPerson = instancyPerson;
	}

	public String getCheckinDate() {
		return this.checkinDate;
	}

	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}

	public String getCheckoutDate() {
		return this.checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public Double getDepositAmt() {
		return this.depositAmt;
	}

	public void setDepositAmt(Double depositAmt) {
		this.depositAmt = depositAmt;
	}

	public Double getReturnAmt() {
		return this.returnAmt;
	}

	public void setReturnAmt(Double returnAmt) {
		this.returnAmt = returnAmt;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPayMode() {
		return this.payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getLivingNum() {
		return this.livingNum;
	}

	public void setLivingNum(Integer livingNum) {
		this.livingNum = livingNum;
	}

}