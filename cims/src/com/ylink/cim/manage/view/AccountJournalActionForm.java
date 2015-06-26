package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class AccountJournalActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String tradeType;
	private Double amount;
	private Double preamount;
	private Double balance;
	private String inoutType;
	private Date createDate;
	private String createUser;
	private String remark;
	private String billId;
	private String startCreateDate;
	private String endCreateDate;
	private String year;
	private String gatherPeriod;
	private String gatherWay;
	
	
	public String getGatherPeriod() {
		return gatherPeriod;
	}
	public void setGatherPeriod(String gatherPeriod) {
		this.gatherPeriod = gatherPeriod;
	}
	public String getGatherWay() {
		return gatherWay;
	}
	public void setGatherWay(String gatherWay) {
		this.gatherWay = gatherWay;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPreamount() {
		return preamount;
	}
	public void setPreamount(Double preamount) {
		this.preamount = preamount;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getInoutType() {
		return inoutType;
	}
	public void setInoutType(String inoutType) {
		this.inoutType = inoutType;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	
}
