package com.ylink.cim.sys.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class TimerActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String beanName;
	private String beanNameCh;
	private String remark;
	private String state;
	private Long para1;
	private String para2;
	private String triggertime;
	private String groupno;
	private Date createTime;
	private String createUser;
	private Date auditTime;
	private String auditUser;
	private String noPass;
	
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanNameCh() {
		return beanNameCh;
	}

	public void setBeanNameCh(String beanNameCh) {
		this.beanNameCh = beanNameCh;
	}

	public TimerActionForm() {
		super();
	}

	public Date getAuditTime() {
		return this.auditTime;
	}

	public String getAuditUser() {
		return this.auditUser;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public String getGroupno() {
		return this.groupno;
	}


	public String getNoPass() {
		return this.noPass;
	}

	public Long getPara1() {
		return this.para1;
	}

	public String getPara2() {
		return this.para2;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getState() {
		return this.state;
	}

	public String getTriggertime() {
		return this.triggertime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNoPass(String noPass) {
		this.noPass = noPass;
	}

	public void setPara1(Long para1) {
		this.para1 = para1;
	}

	public void setPara2(String para2) {
		this.para2 = para2;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(String state) {
		this.state = state;
	}


	public void setTriggertime(String triggertime) {
		this.triggertime = triggertime;
	}
}
