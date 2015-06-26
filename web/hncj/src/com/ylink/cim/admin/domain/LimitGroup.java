package com.ylink.cim.admin.domain;

public class LimitGroup implements java.io.Serializable {

	private static final long serialVersionUID = -442303206776904059L;

	private LimitGroupId id;

	private String limitGroupName;

	private String limitName;

	/**
	 * 权限点的父节点
	 */
	private String pid;

	public LimitGroup() {
	}

	public LimitGroup(final LimitGroupId id) {
		this.id = id;
	}

	public LimitGroupId getId() {
		return this.id;
	}

	public void setId(final LimitGroupId id) {
		this.id = id;
	}

	public String getLimitGroupName() {
		return limitGroupName;
	}

	public void setLimitGroupName(String limitGroupName) {
		this.limitGroupName = limitGroupName;
	}

	public String getLimitName() {
		return limitName;
	}

	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}