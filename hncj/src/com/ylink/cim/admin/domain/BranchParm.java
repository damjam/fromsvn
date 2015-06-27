package com.ylink.cim.admin.domain;

/**
 * ϵͳ������
 * 
 * 
 */
public class BranchParm implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ��������
	 */
	private String code;

	/**
	 * ��������
	 */
	private String parname;

	/**
	 * ����ֵ
	 */
	private String parvalue;

	private String branchNo;

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	/**
	 * ��ע
	 */
	private String remark;

	public BranchParm() {
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public String getParname() {
		return this.parname;
	}

	/**
	 * ��ȡ����ֵ
	 * 
	 * @return
	 */
	public String getParvalue() {
		return this.parvalue;
	}

	/**
	 * ��ȡ��ע
	 * 
	 * @return
	 */
	public String getRemark() {
		return this.remark;
	}

	@Override
	public int hashCode() {
		int result = 17;
		int code = this.getCode() == null ? 0 : this.getCode().hashCode();
		result = result * 37 + code;

		return result;
	}

	/**
	 * ���ò�������
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ���ò�������
	 * 
	 * @param parname
	 */
	public void setParname(String parname) {
		this.parname = parname;
	}

	/**
	 * ���ò���ֵ
	 * 
	 * @param parvalue
	 */
	public void setParvalue(String parvalue) {
		this.parvalue = parvalue;
	}

	/**
	 * ���ñ�ע
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
