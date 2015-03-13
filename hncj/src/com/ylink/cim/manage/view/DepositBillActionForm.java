package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class DepositBillActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String houseSn;
	private Double amount;
	private String state;
	private String payerName;
	private Date depositDate;
	private String depositUser;
	private Date refundDate;
	private String refundUser;
	private String startDepositDate;
	private String endDepositDate;
	private String startRefundDate;
	private String endRefundDate;
	private String purpose;;
	private String remark;
	private String isInternal;
	private String year;
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getIsInternal() {
		return isInternal;
	}
	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getStartRefundDate() {
		return startRefundDate;
	}
	public void setStartRefundDate(String startRefundDate) {
		this.startRefundDate = startRefundDate;
	}
	public String getEndRefundDate() {
		return endRefundDate;
	}
	public void setEndRefundDate(String endRefundDate) {
		this.endRefundDate = endRefundDate;
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
	public String getHouseSn() {
		return houseSn;
	}
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public Date getDepositDate() {
		return depositDate;
	}
	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}
	public String getDepositUser() {
		return depositUser;
	}
	public void setDepositUser(String depositUser) {
		this.depositUser = depositUser;
	}
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	public String getRefundUser() {
		return refundUser;
	}
	public void setRefundUser(String refundUser) {
		this.refundUser = refundUser;
	}
	
	
}
