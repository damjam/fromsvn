package com.ylink.cim.admin.view;

import org.apache.struts.action.ActionForm;

public class SysParmManageActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 参数代码
	 */
	private String code;

	/**
	 * 参数名称
	 */
	private String parname;

	/**
	 * 参数值
	 */
	private String parvalue;

	/**
	 * 是否允许修改
	 */
	private String ifmodify;

	/**
	 * 用户编码
	 */
	private String usercode;

	/**
	 * 更新日期
	 */
	private String updatedate;

	/**
	 * 备注
	 */
	private String remark;

	public SysParmManageActionForm() {
	}

	/**
	 * 
	 * @param code
	 *            获取参数代码
	 * @param parname
	 *            参数名称
	 * @param parvalue
	 *            参数值
	 * @param ifmodify
	 *            是否允许修改 Y 允许，N 不允许
	 * @param usercode
	 *            修改人
	 * @param updatedate
	 *            更新日期
	 * @param remark
	 *            备注
	 */
	public SysParmManageActionForm(String code, String parname, String parvalue, String ifmodify, String usercode,
			String updatedate, String remark) {
		this.code = code;
		this.parname = parname;
		this.parvalue = parvalue;
		this.ifmodify = ifmodify;
		this.usercode = usercode;
		this.updatedate = updatedate;
		this.remark = remark;
	}

	/**
	 * 获取参数编码
	 * 
	 * @return
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * 获取是否允许修改标志 N 不允许，Y 允许
	 * 
	 * @return
	 */
	public String getIfmodify() {
		return this.ifmodify;
	}

	/**
	 * 获取参数名称
	 * 
	 * @return
	 */
	public String getParname() {
		return this.parname;
	}

	/**
	 * 获取参数值
	 * 
	 * @return
	 */
	public String getParvalue() {
		return this.parvalue;
	}

	/**
	 * 获取备注
	 * 
	 * @return
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 获取修改日期
	 * 
	 * @return
	 */
	public String getUpdatedate() {
		return this.updatedate;
	}

	/**
	 * 获取用户编码
	 * 
	 * @return
	 */
	public String getUsercode() {
		return this.usercode;
	}

	/**
	 * 设置参数编码
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 设置是否允许修改标志
	 * 
	 * @param ifmodify
	 */
	public void setIfmodify(String ifmodify) {
		this.ifmodify = ifmodify;
	}

	/**
	 * 设置参数名称
	 * 
	 * @param parname
	 */
	public void setParname(String parname) {
		this.parname = parname;
	}

	/**
	 * 设置参数值
	 * 
	 * @param parvalue
	 */
	public void setParvalue(String parvalue) {
		this.parvalue = parvalue;
	}

	/**
	 * 设置备注
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 设置修改日期
	 * 
	 * @param updatedate
	 */
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	/**
	 * 设置修改人
	 * 
	 * @return
	 */
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

}
