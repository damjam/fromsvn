package com.ylink.cim.manage.domain;


import java.util.Date;

/**
 * AdRent domain. @author MyEclipse Persistence Tools
 */

public class AdrentBill extends BranchField implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String beginDate;
	private String endDate;
	private Date createDate;
	private Date chargeDate;
	private String createUser;
	private String chargeUser;
	private String position;
	private Integer num;
	private Double totalAmt;
	private Double paidAmt;
	private String state;
	private String merchantName;
	private String payerName;
	private String remark;
	private Double unitPrice;
	private String startChargeDate;
	private String endChargeDate;

	/** default constructor */
	public AdrentBill() {
	}

	public String getBeginDate() {
		return this.beginDate;
	}


	public Date getChargeDate() {
		return chargeDate;
	}

	public String getChargeUser() {
		return this.chargeUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getEndChargeDate() {
		return endChargeDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public String getId() {
		return this.id;
	}

	public String getMerchantName() {
		return this.merchantName;
	}

	public Integer getNum() {
		return this.num;
	}

	public Double getPaidAmt() {
		return paidAmt;
	}

	public String getPayerName() {
		return payerName;
	}

	public String getPosition() {
		return this.position;
	}

	public String getRemark() {
		return remark;
	}

	// Constructors

	public String getStartChargeDate() {
		return startChargeDate;
	}

	// Property accessors

	public String getState() {
		return this.state;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setEndChargeDate(String endChargeDate) {
		this.endChargeDate = endChargeDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStartChargeDate(String startChargeDate) {
		this.startChargeDate = startChargeDate;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

}