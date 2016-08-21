package com.ylink.cim.admin.domain;
import java.io.Serializable;

import flink.util.WebResource;

/**
 * A class that represents a row in the PRIVILEGE_RESOURCE table. You can
 * customize the behavidor of this class by editing the class, {@link
 * PrivilegeResource()}. WARNING: DO NOT EDIT THIS FILE. This is a generated
 * file that is synchronized by MyEclipse Hibernate tool integration.
 */
public class PrivilegeResource extends WebResource implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * The cached hash code value for this instance. Settting to 0 triggers
	 * re-calculation.
	 */
	private int hashValue = 0;

	/** The composite primary key value. */
	private java.lang.Long id;

	/** The value of the simple limitId property. */
	private java.lang.String limitId;

	/** The value of the simple url property. */
	private java.lang.String url;

	/** The value of the simple param property. */
	private java.lang.String param;

	/** The value of the simple isEntry property. */
	private java.lang.String isEntry;

	/**
	 * Simple constructor of AbstractPrivilegeResource instances.
	 */
	public PrivilegeResource() {
	}

	/**
	 * Constructor of AbstractPrivilegeResource instances given a simple primary
	 * key.
	 * 
	 * @param id
	 */
	public PrivilegeResource(java.lang.Long id) {
		this.setId(id);
	}

	/**
	 * Return the simple primary key value that identifies this object.
	 * 
	 * @return java.lang.Long
	 */
	public java.lang.Long getId() {
		return id;
	}

	/**
	 * Set the simple primary key value that identifies this object.
	 * 
	 * @param id
	 */
	public void setId(java.lang.Long id) {
		this.hashValue = 0;
		this.id = id;
	}

	/**
	 * Return the value of the LIMIT_ID column.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getLimitId() {
		return this.limitId;
	}

	/**
	 * Set the value of the LIMIT_ID column.
	 * 
	 * @param limitId
	 */
	public void setLimitId(java.lang.String limitId) {
		this.limitId = limitId;
	}

	/**
	 * Return the value of the URL column.
	 * 
	 * @return java.lang.String
	 */

	@Override
	public java.lang.String getUrl() {
		return this.url;
	}

	/**
	 * Set the value of the URL column.
	 * 
	 * @param url
	 */
	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	/**
	 * Return the value of the PARAM column.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getParam() {
		return this.param;
	}

	/**
	 * Set the value of the PARAM column.
	 * 
	 * @param param
	 */
	public void setParam(java.lang.String param) {
		this.param = param;
	}

	/**
	 * Return the value of the IS_ENTRY column.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getIsEntry() {
		return this.isEntry;
	}

	/**
	 * Set the value of the IS_ENTRY column.
	 * 
	 * @param isEntry
	 */
	public void setIsEntry(java.lang.String isEntry) {
		this.isEntry = isEntry;
	}

	/**
	 * Implementation of the equals comparison on the basis of equality of the
	 * primary key values.
	 * 
	 * @param rhs
	 * @return boolean
	 */

	@Override
	public boolean equals(Object rhs) {
		if (rhs == null)
			return false;
		if (!(rhs instanceof PrivilegeResource))
			return false;
		PrivilegeResource that = (PrivilegeResource) rhs;
		if (this.getId() == null || that.getId() == null)
			return false;
		return (this.getId().equals(that.getId()));
	}

	/**
	 * Implementation of the hashCode method conforming to the Bloch pattern
	 * with the exception of array properties (these are very unlikely primary
	 * key types).
	 * 
	 * @return int
	 */

	@Override
	public int hashCode() {
		if (this.hashValue == 0) {
			int result = 17;
			int idValue = this.getId() == null ? 0 : this.getId().hashCode();
			result = result * 37 + idValue;
			this.hashValue = result;
		}
		return this.hashValue;
	}

	@Override
	public String getParamValue() {
		return this.param;
	}

}
