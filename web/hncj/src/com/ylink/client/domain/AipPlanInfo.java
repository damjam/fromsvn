package com.ylink.client.domain;

import java.util.Date;

import com.ylink.cim.common.state.PlanState;
import com.ylink.cim.common.type.AipChanel;
import com.ylink.cim.common.type.AipMode;
import com.ylink.cim.common.type.AipPeriod;
import com.ylink.cim.common.type.InvestTime;
import com.ylink.cim.common.type.PlanUpOper;

public class AipPlanInfo {
	private String order_no; // ������ˮ��
	private String acct_no; // �ͻ���
	private String account_no; // �����ʺ�
	private String cust_name; // �ͻ�����
	private String aip_type; // ��Ͷ���
	private AipMode aip_mode; // ��Ͷģʽ
	private String aip_bal; // ��Ͷ���(Ԫ)
	private Double aip_amt; // ��Ͷ����(��)
	private AipPeriod aip_periods; // ��Ͷ����
	private String aip_date; // ��Ͷ����
	private String first_aip_date; // �״�Ͷ������
	private String next_invest_date; // �¸�Ͷ������
	private InvestTime invest_time; // ��������
	private Date invest_time_value; // ����ֵ
	private PlanState plan_stat; // �ƻ�״̬
	private String fail_times; // �����ۿ�ʧ������
	private AipChanel e_term_type; // ί����Դ
	private String e_branch_id; // ί�д������
	private String e_teller_id; // ί�в���Ա
	private Date e_exch_time; // ί��ʱ��
	private AipChanel u_term_type; // ������Դ
	private String u_teller_id; // ���²���
	private PlanUpOper u_oper; // ���²���
	private Date u_time; // ����ʱ��
	private String memo; // ��ע

	public AipPlanInfo() {
	}

	public String getAccount_no() {
		return account_no;
	}

	public String getAcct_no() {
		return acct_no;
	}

	public Double getAip_amt() {
		return aip_amt;
	}

	public String getAip_bal() {
		return aip_bal;
	}

	public String getAip_date() {
		return aip_date;
	}

	public AipMode getAip_mode() {
		return aip_mode;
	}

	public AipPeriod getAip_periods() {
		return aip_periods;
	}

	public String getAip_type() {
		return aip_type;
	}

	public String getCust_name() {
		return cust_name;
	}

	public String getE_branch_id() {
		return e_branch_id;
	}

	public Date getE_exch_time() {
		return e_exch_time;
	}

	public String getE_teller_id() {
		return e_teller_id;
	}

	public AipChanel getE_term_type() {
		return e_term_type;
	}

	public String getFail_times() {
		return fail_times;
	}

	public String getFirst_aip_date() {
		return first_aip_date;
	}

	public InvestTime getInvest_time() {
		return invest_time;
	}

	public Date getInvest_time_value() {
		return invest_time_value;
	}

	public String getMemo() {
		return memo;
	}

	public String getNext_invest_date() {
		return next_invest_date;
	}

	public String getOrder_no() {
		return order_no;
	}

	public PlanState getPlan_stat() {
		return plan_stat;
	}

	public PlanUpOper getU_oper() {
		return u_oper;
	}

	public String getU_teller_id() {
		return u_teller_id;
	}

	public AipChanel getU_term_type() {
		return u_term_type;
	}

	public Date getU_time() {
		return u_time;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public void setAcct_no(String acct_no) {
		this.acct_no = acct_no;
	}

	public void setAip_amt(Double aip_amt) {
		this.aip_amt = aip_amt;
	}

	public void setAip_bal(String aip_bal) {
		this.aip_bal = aip_bal;
	}

	public void setAip_date(String aip_date) {
		this.aip_date = aip_date;
	}

	public void setAip_mode(AipMode aip_mode) {
		this.aip_mode = aip_mode;
	}

	public void setAip_periods(AipPeriod aip_periods) {
		this.aip_periods = aip_periods;
	}

	public void setAip_type(String aip_type) {
		this.aip_type = aip_type;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public void setE_branch_id(String e_branch_id) {
		this.e_branch_id = e_branch_id;
	}

	public void setE_exch_time(Date e_exch_time) {
		this.e_exch_time = e_exch_time;
	}

	public void setE_teller_id(String e_teller_id) {
		this.e_teller_id = e_teller_id;
	}

	public void setE_term_type(AipChanel e_term_type) {
		this.e_term_type = e_term_type;
	}

	public void setFail_times(String fail_times) {
		this.fail_times = fail_times;
	}

	public void setFirst_aip_date(String first_aip_date) {
		this.first_aip_date = first_aip_date;
	}

	public void setInvest_time(InvestTime invest_time) {
		this.invest_time = invest_time;
	}

	public void setInvest_time_value(Date invest_time_value) {
		this.invest_time_value = invest_time_value;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setNext_invest_date(String next_invest_date) {
		this.next_invest_date = next_invest_date;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public void setPlan_stat(PlanState plan_stat) {
		this.plan_stat = plan_stat;
	}

	public void setU_oper(PlanUpOper u_oper) {
		this.u_oper = u_oper;
	}

	public void setU_teller_id(String u_teller_id) {
		this.u_teller_id = u_teller_id;
	}

	public void setU_term_type(AipChanel u_term_type) {
		this.u_term_type = u_term_type;
	}

	public void setU_time(Date u_time) {
		this.u_time = u_time;
	}

}
