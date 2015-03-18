package com.ylink.cim.manage.view;

import org.apache.struts.action.ActionForm;

public class AdRentActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String chargeUser;
	private String tradeType;
	private Double unitPrice;
	private Integer num;
	private Double totalAmt;
	private String state;
	private String remark;
	private String startCreateDate;
	private String endCreateDate;
	private String startChargeDate;
	private String endChargeDate;
	private String payerName;
	
	
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
