package com.ylink.cim.admin.view;

import java.util.List;

public class LoginedResource {
	
	private List<String> reses ;

	public List<String> getReses() {
		return reses;
	}

	public void setReses(List<String> reses) {
		this.reses = reses;
	}
	
	/**
	 * 获取所有的公用资源
	 * @return
	 */
	public String[] getAllResource(){
		return this.getReses().toArray(new String[]{});
	}

}
