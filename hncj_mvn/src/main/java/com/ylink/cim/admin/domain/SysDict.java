package com.ylink.cim.admin.domain;

public class SysDict {

	private SysDictId id = new SysDictId();

	private String dictName;

	private String remark;

	private String display;
	
	private Integer sort;
	

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public SysDictId getId() {
		return id;
	}

	public void setId(SysDictId id) {
		this.id = id;
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
