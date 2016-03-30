package com.ylink.cim.manage.domain;

import java.io.Serializable;

import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;

public class CarBrand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String brand;
	
	private String firstLetters;
	
	private String country;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	

	

	public String getFirstLetters() {
		return firstLetters;
	}

	public void setFirstLetters(String firstLetters) {
		this.firstLetters = firstLetters;
	}

	public String getCountry() {
		return country;
	}

	public String getCountryName() {
		return ParaManager.getSysDictName(SysDictType.CoutryType.getValue(), country);
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
}
