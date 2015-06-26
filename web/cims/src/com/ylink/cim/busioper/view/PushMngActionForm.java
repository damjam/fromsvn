package com.ylink.cim.busioper.view;

import org.apache.struts.action.ActionForm;

public class PushMngActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	public String getSubsState() {
		return subsState;
	}
	public void setSubsState(String subsState) {
		this.subsState = subsState;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPushAdd() {
		return pushAdd;
	}
	public void setPushAdd(String pushAdd) {
		this.pushAdd = pushAdd;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
}
