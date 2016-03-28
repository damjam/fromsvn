package com.ylink.cim.admin.domain;

import java.io.Serializable;

public class SysDictId implements Serializable{

	private String dictValue;
	
	private String dictType;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysDictId other = (SysDictId) obj;
		if (dictType == null) {
			if (other.dictType != null)
				return false;
		} else if (!dictType.equals(other.dictType))
			return false;
		if (dictValue == null) {
			if (other.dictValue != null)
				return false;
		} else if (!dictValue.equals(other.dictValue))
			return false;
		return true;
	}

	public String getDictType() {
		return dictType;
	}

	public String getDictValue() {
		return dictValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dictType == null) ? 0 : dictType.hashCode());
		result = prime * result
				+ ((dictValue == null) ? 0 : dictValue.hashCode());
		return result;
	}

	
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	
	
	
}
