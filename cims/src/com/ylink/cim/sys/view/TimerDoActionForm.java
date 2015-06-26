package com.ylink.cim.sys.view;

import org.apache.struts.action.ActionForm;

public class TimerDoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String beanName;

	private String beanNameCh;

	private String groupNo;
	private String workdate;

	private String remark;

	private String state;

	private Long para1;
	private String para2;

	private String triggerDate;
	private String triggerTime;

	public String getGroupNo() {
		return this.groupNo;
	}

	public Long getId() {
		return id;
	}

	public Long getPara1() {
		return para1;
	}

	public String getPara2() {
		return para2;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getState() {
		return this.state;
	}

	public String getTriggerDate() {
		return triggerDate;
	}

	public String getTriggerTime() {
		return triggerTime;
	}

	public String getWorkdate() {
		return workdate;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setTriggerDate(String triggerDate) {
		this.triggerDate = triggerDate;
	}

	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
	}

	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}

}
