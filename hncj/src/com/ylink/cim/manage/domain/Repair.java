package com.ylink.cim.manage.domain;

import java.io.Serializable;
import java.util.Date;

public class Repair implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String reporter;
	private String reporterType;
	private String item;
	private String impIndex;
	private String emgIndex;
	private String processor;
	private String state;
	private String remark;
	private Date createDate;
	private String createUser;
	private String branchNo;
	private String startCreateDate;
	private String endCreateDate;
	private String feedback;
	
	
	
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
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
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getReporterType() {
		return reporterType;
	}
	public void setReporterType(String reporterType) {
		this.reporterType = reporterType;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getImpIndex() {
		return impIndex;
	}
	public void setImpIndex(String impIndex) {
		this.impIndex = impIndex;
	}
	public String getEmgIndex() {
		return emgIndex;
	}
	public void setEmgIndex(String emgIndex) {
		this.emgIndex = emgIndex;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	
}
