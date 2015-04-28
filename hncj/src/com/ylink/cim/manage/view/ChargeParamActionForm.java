package com.ylink.cim.manage.view;

import org.apache.struts.action.ActionForm;

public class ChargeParamActionForm extends ActionForm{

	private String id;
	private String remark;
	private String branchNo;
	private String rangeCode;
	private String chargeObj;
	private String paramName;
	private String paramId;
	private String itemId;
	private String[] itemIds;
	
	
	public String getChargeObj() {
		return chargeObj;
	}
	public void setChargeObj(String chargeObj) {
		this.chargeObj = chargeObj;
	}
	public String[] getItemIds() {
		return itemIds;
	}
	public void setItemIds(String[] itemIds) {
		this.itemIds = itemIds;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRangeCode() {
		return rangeCode;
	}
	public void setRangeCode(String rangeCode) {
		this.rangeCode = rangeCode;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
