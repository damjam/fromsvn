package com.ylink.cim.admin.view;

import org.apache.struts.action.ActionForm;

public class SysDictActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String dictValue;

	private String dictType;

	private String dictName;

	private String remark;

	private String display;

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
