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



	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCustId() {
		return this.custId;
	}


	public String getId() {
		return this.id;
	}

	public String getNoticeMsgId() {
		return noticeMsgId;
	}

	public String getRead() {
		return this.read;
	}

	public Date getReadTime() {
		return this.readTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNoticeMsgId(String noticeMsgId) {
		this.noticeMsgId = noticeMsgId;
	}



	public void setRead(String read) {
		this.read = read;
	}



	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

}