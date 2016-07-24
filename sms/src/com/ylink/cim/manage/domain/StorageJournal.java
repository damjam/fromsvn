package com.ylink.cim.manage.domain;

import java.util.Date;

public class StorageJournal extends BranchField {

	private String id;
	
	private String carModel;
	
	private String color;
	
	private String productType;
	
	private String product;
	
	private String shelf;
	
	private String code;
	
	private Integer num;

	private String material;
	
	private String pattern;
	
	private String stockDate;
	
	private Date createDate;
	
	private String inoutType;
	
	private String orderId;
	
	private String remark;
	
	private Integer inoutNum;
	
	
	public Integer getInoutNum() {
		return inoutNum;
	}

	public void setInoutNum(Integer inoutNum) {
		this.inoutNum = inoutNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getInoutType() {
		return inoutType;
	}

	public void setInoutType(String inoutType) {
		this.inoutType = inoutType;
	}

	
	public String getStockDate() {
		return stockDate;
	}

	public void setStockDate(String stockDate) {
		this.stockDate = stockDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
