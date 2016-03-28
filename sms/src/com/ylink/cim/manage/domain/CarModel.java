package com.ylink.cim.manage.domain;

import java.io.Serializable;

public class CarModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Integer id;
	
	private String name;
	
	private String brand;
	
	private String firstLetters;
	
	
	
	

	public String getFirstLetters() {
		return firstLetters;
	}

	public void setFirstLetters(String firstLetters) {
		this.firstLetters = firstLetters;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
