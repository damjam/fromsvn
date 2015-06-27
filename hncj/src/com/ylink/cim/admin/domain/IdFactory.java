package com.ylink.cim.admin.domain;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class IdFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String seqIdName;

	private String initValue;

	private String currentValue;

	private String stepValue;

	private String fillValue;

	private String maxLength;

	private String dateLength;

	private String remark;

	private String direction;

	public String getSeqIdName() {
		return seqIdName;
	}

	public void setSeqIdName(String seqIdName) {
		this.seqIdName = seqIdName;
	}

	public String getInitValue() {
		return initValue;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getStepValue() {
		return stepValue;
	}

	public void setStepValue(String stepValue) {
		this.stepValue = stepValue;
	}

	public String getFillValue() {
		return fillValue;
	}

	public void setFillValue(String fillValue) {
		this.fillValue = fillValue;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getDateLength() {
		if (StringUtils.isEmpty(dateLength)) {
			dateLength = "8";
		}
		return dateLength;
	}

	public void setDateLength(String dateLength) {
		this.dateLength = dateLength;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
