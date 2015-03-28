package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * ParkingBillId domain. @author MyEclipse Persistence Tools
 */

public class ParkingBill implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String ownerId;
	private Date createDate;
	private String createUser;
	private Double amount;
	private String beginDate;
	private String endDate;
	private String parkingSn;
	private String carSn;
	private String ownerName;
	private String houseSn;
	private String state;
	private String isInternal;
	private Date chargeDate;
	private String chargeUser;
	private String remark;
	
	// Constructors
	
	private String branchNo;
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
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

	public String getIsInternal() {
		return isInternal;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getCarSn() {
		return carSn;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setCarSn(String carSn) {
		this.carSn = carSn;
	}

	/** default constructor */
	public ParkingBill() {
	}

	/** minimal constructor */
	public ParkingBill(String id) {
		this.id = id;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getParkingSn() {
		return this.parkingSn;
	}

	public void setParkingSn(String parkingSn) {
		this.parkingSn = parkingSn;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ParkingBill))
			return false;
		ParkingBill castOther = (ParkingBill) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null && castOther.getId() != null && this
				.getId().equals(castOther.getId())))
				&& ((this.getOwnerId() == castOther.getOwnerId()) || (this.getOwnerId() != null
						&& castOther.getOwnerId() != null && this.getOwnerId().equals(castOther.getOwnerId())))
				&& ((this.getCreateDate() == castOther.getCreateDate()) || (this.getCreateDate() != null
						&& castOther.getCreateDate() != null && this.getCreateDate().equals(castOther.getCreateDate())))
				&& ((this.getCreateUser() == castOther.getCreateUser()) || (this.getCreateUser() != null
						&& castOther.getCreateUser() != null && this.getCreateUser().equals(castOther.getCreateUser())))
				&& ((this.getAmount() == castOther.getAmount()) || (this.getAmount() != null
						&& castOther.getAmount() != null && this.getAmount().equals(castOther.getAmount())))
				&& ((this.getBeginDate() == castOther.getBeginDate()) || (this.getBeginDate() != null
						&& castOther.getBeginDate() != null && this.getBeginDate().equals(castOther.getBeginDate())))
				&& ((this.getEndDate() == castOther.getEndDate()) || (this.getEndDate() != null
						&& castOther.getEndDate() != null && this.getEndDate().equals(castOther.getEndDate())))
				&& ((this.getParkingSn() == castOther.getParkingSn()) || (this.getParkingSn() != null
						&& castOther.getParkingSn() != null && this.getParkingSn().equals(castOther.getParkingSn())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result + (getOwnerId() == null ? 0 : this.getOwnerId().hashCode());
		result = 37 * result + (getCreateDate() == null ? 0 : this.getCreateDate().hashCode());
		result = 37 * result + (getCreateUser() == null ? 0 : this.getCreateUser().hashCode());
		result = 37 * result + (getAmount() == null ? 0 : this.getAmount().hashCode());
		result = 37 * result + (getBeginDate() == null ? 0 : this.getBeginDate().hashCode());
		result = 37 * result + (getEndDate() == null ? 0 : this.getEndDate().hashCode());
		result = 37 * result + (getParkingSn() == null ? 0 : this.getParkingSn().hashCode());
		return result;
	}

}