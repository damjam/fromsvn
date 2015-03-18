package com.ylink.cim.busioper.view;

import org.apache.struts.action.ActionForm;

public class PushMngActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String id;
	private String pushType;
	private String subject;
	private String content;
	private String startCreateDate;
	private String endCreateDate;
	private String state;
	private String pushAdd;
	private String branchNo;
	private String busiType;
	private String subsState;
	
	private String custType;
	public String getBranchNo() {
		return branchNo;
	}
	public String getBusiType() {
		return busiType;
	}
	public String getContent() {
		return content;
	}
	public String getCustType() {
		return custType;
	}
	public String getEndCreateDate() {
		return endCreateDate;
	}
	public String getId() {
		return id;
	}
	public String getPushAdd() {
		return pushAdd;
	}
	public String getPushType() {
		return pushType;
	}
	public String getStartCreateDate() {
		return startCreateDate;
	}
	public String getState() {
		return state;
	}
	public String getSubject() {
		return subject;
	}
	public String getSubsState() {
		return subsState;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPushAdd(String pushAdd) {
		this.pushAdd = pushAdd;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setSubsState(String subsState) {
		this.subsState = subsState;
	}
	
}
