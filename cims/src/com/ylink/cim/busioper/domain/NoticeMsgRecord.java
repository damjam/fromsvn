package com.ylink.cim.busioper.domain;

// default package

import java.util.Date;

/**
 * NoticeMsgRecord entity. @author MyEclipse Persistence Tools
 */

public class NoticeMsgRecord implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String noticeMsgId;
	private String custId;
	private String read;
	private Date createTime;
	private Date readTime;

	// Constructors

	/** default constructor */
	public NoticeMsgRecord() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getRead() {
		return this.read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getReadTime() {
		return this.readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public String getNoticeMsgId() {
		return noticeMsgId;
	}

	public void setNoticeMsgId(String noticeMsgId) {
		this.noticeMsgId = noticeMsgId;
	}

}