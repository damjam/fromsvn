package com.ylink.cim.admin.domain;

public class BranchDict {

	private BranchDictId id = new BranchDictId();

	private String dictName;

	private String remark;

	private String display;

	private String branchNo;
	
	private String branchName;
	
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public BranchDictId getId() {
		return id;
	}

	public void setId(BranchDictId id) {
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
