package com.ylink.cim.invest.domain;
// default package

/**
 * PayChannel entity. @author MyEclipse Persistence Tools
 */

public class PayChannel implements java.io.Serializable {

	// Fields

	private String id;
	private String channelType;
	private String acctType;
	private String acctNo;
	private String acctState;
	private String bindState;
	private String signState;
	private String createTime;
	private String remark;

	// Constructors

	/** default constructor */
	public PayChannel() {
	}

	/** minimal constructor */
	public PayChannel(String channelType, String acctType, String acctNo, String acctState, String bindState,
			String signState, String createTime) {
		this.channelType = channelType;
		this.acctType = acctType;
		this.acctNo = acctNo;
		this.acctState = acctState;
		this.bindState = bindState;
		this.signState = signState;
		this.createTime = createTime;
	}

	/** full constructor */
	public PayChannel(String channelType, String acctType, String acctNo, String acctState, String bindState,
			String signState, String createTime, String remark) {
		this.channelType = channelType;
		this.acctType = acctType;
		this.acctNo = acctNo;
		this.acctState = acctState;
		this.bindState = bindState;
		this.signState = signState;
		this.createTime = createTime;
		this.remark = remark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelType() {
		return this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getAcctType() {
		return this.acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getAcctNo() {
		return this.acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAcctState() {
		return this.acctState;
	}

	public void setAcctState(String acctState) {
		this.acctState = acctState;
	}

	public String getBindState() {
		return this.bindState;
	}

	public void setBindState(String bindState) {
		this.bindState = bindState;
	}

	public String getSignState() {
		return this.signState;
	}

	public void setSignState(String signState) {
		this.signState = signState;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}