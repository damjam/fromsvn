package com.ylink.cim.manage.domain;


import java.util.Date;

/**
 * AccountId domain. @author MyEclipse Persistence Tools
 */

public class InnerAcct extends BranchField implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private Double balance;

	private Date createDate;
	private Date updateDate;
	private Long inTimes;
	private Long outTimes;
	private Long times;

	public Long getInTimes() {
		return inTimes;
	}

	public void setInTimes(Long inTimes) {
		this.inTimes = inTimes;
	}

	public Long getOutTimes() {
		return outTimes;
	}

	public void setOutTimes(Long outTimes) {
		this.outTimes = outTimes;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}