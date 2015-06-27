package com.ylink.client.view;

import java.util.Date;

import com.ylink.cim.common.state.PlanState;
import com.ylink.cim.common.type.AipMode;
import com.ylink.cim.common.type.AipPeriod;

public class AipPlanForm {
	private String branch_id; // �������
	private Date start_date; // *��ʼ����
	private Date end_date; // *��������
	private String grade_id; // �ͻ�����
	private String acct_no; // �ͻ���
	private String cust_name; // �ͻ�����
	private String account_no; // �����˺�
	private String order_no; // ������ˮ��
	private PlanState plan_stat; // �ƻ�״̬
	private AipMode aip_mode; // ��Ͷģʽ
	private AipPeriod aip_periods; // ��Ͷ����

	public AipPlanForm() {
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(String grade_id) {
		this.grade_id = grade_id;
	}

	public String getAcct_no() {
		return acct_no;
	}

	public void setAcct_no(String acct_no) {
		this.acct_no = acct_no;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public PlanState getPlan_stat() {
		return plan_stat;
	}

	public void setPlan_stat(PlanState plan_stat) {
		this.plan_stat = plan_stat;
	}

	public AipMode getAip_mode() {
		return aip_mode;
	}

	public void setAip_mode(AipMode aip_mode) {
		this.aip_mode = aip_mode;
	}

	public AipPeriod getAip_periods() {
		return aip_periods;
	}

	public void setAip_periods(AipPeriod aip_periods) {
		this.aip_periods = aip_periods;
	}

}
