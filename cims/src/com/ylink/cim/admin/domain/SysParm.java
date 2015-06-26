package com.ylink.cim.admin.domain;

/**
 * 系统参数表
 * 
 * 
 */
public class SysParm implements java.io.Serializable {

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
	 * 备注
	 */
	private String remark;

	public SysParm() {
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

	public int hashCode() {
		int result = 17;
		int code = this.getCode() == null ? 0 : this.getCode().hashCode();
		result = result * 37 + code;

		return result;
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

}
