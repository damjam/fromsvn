package com.ylink.cim.manage.domain;

import java.io.Serializable;
import java.util.Date;

public class EmpTransfer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String empId;
	private String transferType;
	private String transferDetail;
	private Date createDate;
	private String createUser;
	private String transferDate;
	private String reason;
	
	private String transBranchNo;
	private String transPosition;
	
	private String beginDate;
	private String endDate;
	
	
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTransBranchNo() {
		return transBranchNo;
	}
	public void setTransBranchNo(String transBranchNo) {
		this.transBranchNo = transBranchNo;
	}
	public String getTransPosition() {
		return transPosition;
	}
	public void setTransPosition(String transPosition) {
		this.transPosition = transPosition;
	}
	
	public String getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getTransferDetail() {
		return transferDetail;
	}
	public void setTransferDetail(String transferDetail) {
		this.transferDetail = transferDetail;
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
	
	
}
