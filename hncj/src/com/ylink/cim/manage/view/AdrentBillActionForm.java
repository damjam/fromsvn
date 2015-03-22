package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class AdrentBillActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String chargeUser;
	private String createUser;
	private Date createDate;
	private Date chargeDate;
	private String tradeType;
	private Double unitPrice;
	private Integer num;
	private Double totalAmt;
	private Double paidAmt;
	private String state;
	private String remark;
	private String startCreateDate;
	private String endCreateDate;
	private String startChargeDate;
	private String endChargeDate;
	private String payerName;
	private String year;
	private String merchantNo;
	private String merchantName;
	private String beginDate;
	private String endDate;
	private String position;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Double getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public String getChargeUser() {
		return chargeUser;
	}

	public String getEndChargeDate() {
		return endChargeDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public String getId() {
		return id;
	}

	public Integer getNum() {
		return num;
	}

	public String getPayerName() {
		return payerName;
	}

	public String getRemark() {
		return remark;
	}

	public String getStartChargeDate() {
		return startChargeDate;
	}

	public String getStartCreateDate() {
		return startCreateDate;
	}

	public String getState() {
		return state;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public String getTradeType() {
		return tradeType;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public void setEndChargeDate(String endChargeDate) {
		this.endChargeDate = endChargeDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStartChargeDate(String startChargeDate) {
		this.startChargeDate = startChargeDate;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

}
