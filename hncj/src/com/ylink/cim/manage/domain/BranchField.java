package com.ylink.cim.manage.domain;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.ylink.cim.common.util.ParaManager;

public class BranchField {

	private String branchNo;

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getBranchName() {
		if (StringUtils.isNotEmpty(branchNo) && MapUtils.isNotEmpty(ParaManager.getBranches(true))) {
			String branchName = ParaManager.getBranches(true).get(branchNo);
			return branchName;
		}
		return null;
	}


}
