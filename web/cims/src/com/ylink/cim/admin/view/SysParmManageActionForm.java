package com.ylink.cim.admin.view;

import org.apache.struts.action.ActionForm;

public class SysParmManageActionForm extends ActionForm {

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

	/**
	 * �Ƿ������޸�
	 */
	private String ifmodify;

	/**
	 * �û�����
	 */
	private String usercode;

	/**
	 * ��������
	 */
	private String updatedate;

	/**
	 * ��ע
	 */
	private String remark;

	public SysParmManageActionForm() {
	}

	/**
	 * 
	 * @param code
	 *            ��ȡ��������
	 * @param parname
	 *            ��������
	 * @param parvalue
	 *            ����ֵ
	 * @param ifmodify
	 *            �Ƿ������޸� Y ����N ������
	 * @param usercode
	 *            �޸���
	 * @param updatedate
	 *            ��������
	 * @param remark
	 *            ��ע
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
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * ��ȡ�Ƿ������޸ı�־ N ������Y ����
	 * 
	 * @return
	 */
	public String getIfmodify() {
		return this.ifmodify;
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

	/**
	 * ��ȡ�޸�����
	 * 
	 * @return
	 */
	public String getUpdatedate() {
		return this.updatedate;
	}

	/**
	 * ��ȡ�û�����
	 * 
	 * @return
	 */
	public String getUsercode() {
		return this.usercode;
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
	 * �����Ƿ������޸ı�־
	 * 
	 * @param ifmodify
	 */
	public void setIfmodify(String ifmodify) {
		this.ifmodify = ifmodify;
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

	/**
	 * �����޸�����
	 * 
	 * @param updatedate
	 */
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	/**
	 * �����޸���
	 * 
	 * @return
	 */
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

}
