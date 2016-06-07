package com.ylink.cim.manage.domain;


import java.util.Date;

/**
 * DecorateServiceBillId domain. @author MyEclipse Persistence Tools
 */

public class DecorateServiceBill extends BranchField implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String id;
	private String houseSn;
	private Double area;
	private Double cleanPrice;
	private Double cleanAmount;
	private Double liftFee;
	private Double amount;
	private Double paidAmount;
	private Date createDate;
	private String chargeUser;
	private Date chargeDate;
	private String createUser;
	private String state;
	private String remark;
	private String ownerName;
	private Double supFee;
	private String csBillId;

	private String startChargeDate;
	private String endChargeDate;
	private String year;
	private String createType;
	
	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStartChargeDate() {
		return startChargeDate;
	}

	public void setStartChargeDate(String startChargeDate) {
		this.startChargeDate = startChargeDate;
	}

	public String getEndChargeDate() {
		return endChargeDate;
	}

	public void setEndChargeDate(String endChargeDate) {
		this.endChargeDate = endChargeDate;
	}

	// Constructors

	public String getCsBillId() {
		return csBillId;
	}

	public void setCsBillId(String csBillId) {
		this.csBillId = csBillId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public Double getSupFee() {
		return supFee;
	}

	public void setSupFee(Double supFee) {
		this.supFee = supFee;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getState() {
		return state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getChargeUser() {
		return chargeUser;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public void setState(String state) {
		this.state = state;
	}

	/** default constructor */
	public DecorateServiceBill() {
	}

	/** minimal constructor */
	public DecorateServiceBill(String id) {
		this.id = id;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHouseSn() {
		return this.houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public Double getArea() {
		return this.area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getCleanPrice() {
		return this.cleanPrice;
	}

	public void setCleanPrice(Double cleanPrice) {
		this.cleanPrice = cleanPrice;
	}

	public Double getCleanAmount() {
		return this.cleanAmount;
	}

	public void setCleanAmount(Double cleanAmount) {
		this.cleanAmount = cleanAmount;
	}

	public Double getLiftFee() {
		return this.liftFee;
	}

	public void setLiftFee(Double liftFee) {
		this.liftFee = liftFee;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}