package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class IcDepositActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Double amount;
	private String houseSn;
	private String ownerName;
	private String payerName;
	private Date chargeDate;
	private String chargeUser;
	private String payType;
	private String state;
	private String startDepositDate;
	private String endDepositDate;
	private String year;
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getStartDepositDate() {
		return startDepositDate;
	}
	public void setStartDepositDate(String startDepositDate) {
		this.startDepositDate = startDepositDate;
	}
	public String getEndDepositDate() {
		return endDepositDate;
	}
	public void setEndDepositDate(String endDepositDate) {
		this.endDepositDate = endDepositDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
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
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public Date getChargeDate() {
		return chargeDate;
	}
	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}
	public String getChargeUser() {
		return chargeUser;
	}
	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
