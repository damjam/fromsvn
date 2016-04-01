package com.ylink.cim.manage.domain;

import java.io.Serializable;
import java.util.Date;

public class OrderRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String clientName;

	private String clientTel;

	private String orderDate;

	private String address;

	private Double amount;

	private Date createDate;

	private String createUser;

	private String[] carModels;

	private String[] productNames;

	private String[] materials;

	private String[] colors;
	private Double[] prices;
	private Integer[] nums;
	private Double[] amounts;
	private String[] deliTypes;
	
	public String[] getCarModels() {
		return carModels;
	}

	public void setCarModels(String[] carModels) {
		this.carModels = carModels;
	}

	public String[] getProductNames() {
		return productNames;
	}

	public void setProductNames(String[] productNames) {
		this.productNames = productNames;
	}

	public String[] getMaterials() {
		return materials;
	}

	public void setMaterials(String[] materials) {
		this.materials = materials;
	}

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	public Double[] getPrices() {
		return prices;
	}

	public void setPrices(Double[] prices) {
		this.prices = prices;
	}

	public Integer[] getNums() {
		return nums;
	}

	public void setNums(Integer[] nums) {
		this.nums = nums;
	}

	

	public String[] getDeliTypes() {
		return deliTypes;
	}

	public void setDeliTypes(String[] deliTypes) {
		this.deliTypes = deliTypes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientTel() {
		return clientTel;
	}

	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Double[] getAmounts() {
		return amounts;
	}

	public void setAmounts(Double[] amounts) {
		this.amounts = amounts;
	}

	
}
