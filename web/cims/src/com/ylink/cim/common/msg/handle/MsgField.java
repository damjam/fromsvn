package com.ylink.cim.common.msg.handle;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


public class MsgField {

	private String fieldName;
	private String fieldCode;
	private String fieldType;
	private String value;
	private int length;
	private boolean notNull;
	private int deciLength;
	private Double numVal;
	
	public Double getNumVal() {
		return numVal;
	}
	public void setNumVal(Double numVal) {
		this.numVal = numVal;
	}
	public int getDeciLength() {
		return this.deciLength;
	}
	public void setDeciLength(int length) {
		this.deciLength = length;
	}
	public static Map<String, MsgField> ALL = new LinkedHashMap<String, MsgField>();
	public MsgField(String fieldName, String fieldCode, String fieldType, int length){
		this.fieldCode = fieldCode;
		this.fieldType = fieldType;
		this.length = length;
		ALL.put(fieldCode, this);
	}
	public MsgField(String fieldName, String fieldCode, String fieldType, int length, int deciLength){
		this.fieldCode = fieldCode;
		this.fieldType = fieldType;
		this.length = length;
		this.deciLength = deciLength;
		ALL.put(fieldCode, this);
	}
	public MsgField(){
	}
	
	//������ͷ
	public static MsgField h_gateway_header = new MsgField("���ر���ͷ", "h_gateway_header", MsgUtil.FIELD_CHAR, 76);
	public static MsgField h_exch_code = new MsgField("������", "h_exch_code", MsgUtil.FIELD_CHAR, 5);
	public static MsgField h_cashier_id = new MsgField("����Ա��", "h_cashier_id", MsgUtil.FIELD_CHAR, 4);
	
	public static MsgField h_bk_tx_date = new MsgField("���׷�������", "h_bk_tx_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField h_bk_tx_time = new MsgField("���׷���ʱ��", "h_bk_tx_time", MsgUtil.FIELD_CHAR, 8);
	public static MsgField h_bk_serial_no = new MsgField("������ˮ��", "h_bk_serial_no", MsgUtil.FIELD_CHAR, 22);
	public static MsgField h_exch_date = new MsgField("������", "h_exch_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField h_proxy = new MsgField("�����־", "h_proxy", MsgUtil.FIELD_CHAR, 1);
	public static MsgField h_bank_no = new MsgField("�����б��", "h_bank_no", MsgUtil.FIELD_CHAR, 4);
	public static MsgField h_branch_id = new MsgField("��������", "h_branch_id", MsgUtil.FIELD_CHAR, 10);
	public static MsgField h_teller_id = new MsgField("��Ա", "h_teller_id", MsgUtil.FIELD_CHAR, 10);
	public static MsgField h_teller_id1 = new MsgField("��Ȩ��Ա1", "h_teller_id1", MsgUtil.FIELD_CHAR, 10);
	public static MsgField h_teller_id2 = new MsgField("��Ȩ��Ա2", "h_teller_id2", MsgUtil.FIELD_CHAR, 10);
	public static MsgField h_channel = new MsgField("��������", "h_channel", MsgUtil.FIELD_CHAR, 1);
	public static MsgField h_term_id = new MsgField("�ն˺�", "h_term_id", MsgUtil.FIELD_CHAR, 10);
	public static MsgField h_rsp_type = new MsgField("����������", "h_rsp_type", MsgUtil.FIELD_CHAR, 1);
	public static MsgField h_req_type = new MsgField("��������", "h_req_type", MsgUtil.FIELD_CHAR, 1);
	public static MsgField h_auth_lvl = new MsgField("��Ȩ����", "h_auth_lvl", MsgUtil.FIELD_CHAR, 2);
	public static MsgField h_start_num = new MsgField("��ǰ�ڼ�ҳ", "h_start_num", MsgUtil.FIELD_CHAR, 8);
	public static MsgField h_query_num = new MsgField("ÿҳ��¼��", "h_query_num", MsgUtil.FIELD_CHAR, 8);
	public static MsgField back1 = new MsgField("����1", "back1", MsgUtil.FIELD_CHAR, 20);
	public static MsgField back2 = new MsgField("����2", "back2", MsgUtil.FIELD_CHAR, 20);
	public static MsgField back3 = new MsgField("����3", "back3", MsgUtil.FIELD_CHAR, 20);
	public static MsgField back4 = new MsgField("����4", "back4", MsgUtil.FIELD_CHAR, 20);
	//���ر���ͷ
	public static MsgField h_rsp_code = new MsgField("������", "h_rsp_code", MsgUtil.FIELD_CHAR, 7);
	public static MsgField h_rsp_msg = new MsgField("������Ϣ", "h_rsp_msg", MsgUtil.FIELD_CHAR, 100);
	public static MsgField h_sum_num = new MsgField("��ҳ��", "h_sum_num", MsgUtil.FIELD_CHAR, 8);
	public static MsgField h_rsp_num = new MsgField("���ؼ�¼��", "h_rsp_num", MsgUtil.FIELD_CHAR, 8);
	
	public static MsgField account_no = new MsgField("�����ʺ�", "account_no", MsgUtil.FIELD_CHAR, 32);
	public static MsgField is_sign = new MsgField("�Ƿ�ǩԼ", "is_sign", MsgUtil.FIELD_CHAR, 2);
	public static MsgField access_way = new MsgField("��ת����", "access_way", MsgUtil.FIELD_CHAR, 2);
	public static MsgField start_app_date = new MsgField("��ʼ����", "start_app_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField end_app_date = new MsgField("��������", "end_app_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField cust_abbr = new MsgField("�ͻ����", "cust_abbr", MsgUtil.FIELD_CHAR, 31);
	public static MsgField cert_type = new MsgField("֤������", "cert_type", MsgUtil.FIELD_CHAR, 2);
	public static MsgField cert_num = new MsgField("֤������", "cert_num", MsgUtil.FIELD_CHAR, 50);
	public static MsgField acct_stat = new MsgField("�˻�״̬", "acct_stat", MsgUtil.FIELD_CHAR, 20);
	public static MsgField broker_id = new MsgField("�ͻ�����", "broker_id", MsgUtil.FIELD_CHAR, 12);
	public static MsgField grade_id = new MsgField("�ͻ�����", "grade_id", MsgUtil.FIELD_CHAR, 8);
	public static MsgField grade_name = new MsgField("�ͻ���������", "grade_name", MsgUtil.FIELD_CHAR, 30);
	public static MsgField area_code = new MsgField("��������", "area_code", MsgUtil.FIELD_CHAR, 10);
	public static MsgField mobile_phone = new MsgField("�ֻ�", "mobile_phone", MsgUtil.FIELD_CHAR, 30);
	public static MsgField tel = new MsgField("�绰����", "tel", MsgUtil.FIELD_CHAR, 31);
	public static MsgField fax = new MsgField("����", "fax", MsgUtil.FIELD_CHAR, 31);
	public static MsgField addr = new MsgField("�ʼĵ�ַ", "addr", MsgUtil.FIELD_CHAR, 255);
	public static MsgField zipcode = new MsgField("�ʱ�", "zipcode", MsgUtil.FIELD_CHAR, 6);
	public static MsgField email = new MsgField("��������", "email", MsgUtil.FIELD_CHAR, 255);
	public static MsgField o_term_type = new MsgField("������Դ", "o_term_type", MsgUtil.FIELD_CHAR, 20);
	public static MsgField o_teller_id = new MsgField("��������Ա��", "o_teller_id", MsgUtil.FIELD_CHAR, 10);
	public static MsgField defer_flag = new MsgField("�Ƿ�˳��", "defer_flag", MsgUtil.FIELD_CHAR, 2);
	public static MsgField charge_flag = new MsgField("�Ƿ�ۿ���һԪ", "charge_flag", MsgUtil.FIELD_CHAR, 2);
	public static MsgField risk_type = new MsgField("�ͻ���������", "risk_type", MsgUtil.FIELD_CHAR, 2);
	public static MsgField memo = new MsgField("��ע", "memo", MsgUtil.FIELD_CHAR, 30);
	public static MsgField old_account_no = new MsgField("ԭ�����˺�", "old_account_no", MsgUtil.FIELD_CHAR, 32);
	public static MsgField new_account_no = new MsgField("�������˺�", "new_account_no", MsgUtil.FIELD_CHAR, 32);
	
	public static MsgField branch_id = new MsgField("�������", "branch_id", MsgUtil.FIELD_CHAR, 32);
	public static MsgField take_month = new MsgField("����·�", "take_month", MsgUtil.FIELD_CHAR, 6);
	public static MsgField take_date = new MsgField("�����", "take_date", MsgUtil.FIELD_CHAR,8);
	public static MsgField branch_name = new MsgField("��������", "branch_name", MsgUtil.FIELD_CHAR, 50);
	public static MsgField broker_name = new MsgField("�ͻ���������", "broker_name", MsgUtil.FIELD_CHAR, 30);
	public static MsgField aip_mode = new MsgField("��Ͷģʽ", "aip_mode", MsgUtil.FIELD_CHAR, 2);
	public static MsgField aip_type = new MsgField("��Ͷ���", "aip_type", MsgUtil.FIELD_CHAR, 2);
	public static MsgField type_name = new MsgField("�������", "type_name", MsgUtil.FIELD_CHAR, 31);
	public static MsgField plan_stat = new MsgField("�ƻ�״̬", "plan_stat", MsgUtil.FIELD_CHAR, 2);
	public static MsgField start_date = new MsgField("��ʼʱ��", "start_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField end_date = new MsgField("����ʱ��", "end_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField exch_time = new MsgField("��������ʱ��", "exch_time", MsgUtil.FIELD_CHAR, 16);
	public static MsgField exch_dire = new MsgField("���׷���", "exch_dire", MsgUtil.FIELD_CHAR, 31);
	public static MsgField deduct_type = new MsgField("��������", "deduct_type", MsgUtil.FIELD_CHAR, 31);
	public static MsgField prod_code = new MsgField("����Ʒ��", "prod_code", MsgUtil.FIELD_CHAR, 10);
	public static MsgField prod_name = new MsgField("Ʒ������", "prod_name", MsgUtil.FIELD_CHAR, 31);
	public static MsgField entr_source = new MsgField("ί����Դ", "entr_source", MsgUtil.FIELD_CHAR, 2);
	public static MsgField charge_stat = new MsgField("����״̬", "charge_stat", MsgUtil.FIELD_CHAR, 20);
	public static MsgField origin_order_no = new MsgField("ԭί�е���", "origin_order_no", MsgUtil.FIELD_CHAR, 20);
	public static MsgField deal_stat = new MsgField("�ɽ�״̬", "deal_stat", MsgUtil.FIELD_CHAR, 2);
	public static MsgField e_term_type = new MsgField("ί����Դ", "e_term_type", MsgUtil.FIELD_CHAR, 20);
	public static MsgField entr_stat = new MsgField("ί��״̬", "entr_stat", MsgUtil.FIELD_CHAR, 2);
	public static MsgField e_branch_id = new MsgField("ί�д������", "e_branch_id", MsgUtil.FIELD_CHAR, 50);
	public static MsgField e_teller_id = new MsgField("ί�в���Ա", "e_teller_id", MsgUtil.FIELD_CHAR, 10);
	public static MsgField e_exch_time = new MsgField("ί��ʱ��", "e_exch_time", MsgUtil.FIELD_CHAR, 20);
	public static MsgField u_term_type = new MsgField("������Դ", "u_term_type", MsgUtil.FIELD_CHAR, 20);
	public static MsgField u_teller_id = new MsgField("���²���Ա", "u_teller_id", MsgUtil.FIELD_CHAR, 10);
	public static MsgField u_oper = new MsgField("���²���", "u_oper", MsgUtil.FIELD_CHAR, 20);
	public static MsgField u_time = new MsgField("����ʱ��", "u_time", MsgUtil.FIELD_CHAR, 20);
	public static MsgField c_term_type = new MsgField("������Դ", "c_term_type", MsgUtil.FIELD_CHAR, 20);
	public static MsgField c_teller_id = new MsgField("��������Ա", "c_teller_id", MsgUtil.FIELD_CHAR, 10);
	public static MsgField c_exch_time = new MsgField("����ʱ��", "c_exch_time", MsgUtil.FIELD_CHAR, 8);
	public static MsgField aip_amount = new MsgField("��Ͷ���/����", "aip_amount", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField aip_periods = new MsgField("��Ͷ����", "aip_periods", MsgUtil.FIELD_CHAR,10);
	public static MsgField aip_period = new MsgField("��Ͷ����", "aip_period", MsgUtil.FIELD_CHAR, 2);
	public static MsgField chn_aip_date = new MsgField("ָ������", "chn_aip_date", MsgUtil.FIELD_CHAR, 100);
	public static MsgField is_delay = new MsgField("�Ƿ���������", "is_delay", MsgUtil.FIELD_CHAR, 2);
	public static MsgField is_effect = new MsgField("�Ƿ�������Ч", "is_effect", MsgUtil.FIELD_CHAR, 2);
	public static MsgField first_aip_date = new MsgField("�״�Ͷ������", "first_aip_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField invest_time = new MsgField("��������", "invest_time", MsgUtil.FIELD_CHAR, 2);
	public static MsgField invest_time_value = new MsgField("����ֵ", "invest_time_value", MsgUtil.FIELD_CHAR, 18);
	public static MsgField order_no = new MsgField("������ˮ��", "order_no", MsgUtil.FIELD_CHAR, 20);
	public static MsgField entr_date = new MsgField("ί������", "entr_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField exch_date = new MsgField("��������", "exch_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField prod_type = new MsgField("����Ʒ��", "prod_type", MsgUtil.FIELD_CHAR, 31);
	public static MsgField b_contacter = new MsgField("������ϵ��", "b_contacter", MsgUtil.FIELD_CHAR, 20);
	public static MsgField b_addr = new MsgField("�����ַ", "b_addr", MsgUtil.FIELD_CHAR, 200);
	public static MsgField is_stop = new MsgField("�Ƿ���ͣ", "is_stop", MsgUtil.FIELD_CHAR, 2);
	public static MsgField affect_time = new MsgField("�ۿ�����", "affect_time", MsgUtil.FIELD_CHAR, 20);
	public static MsgField multiple  = new MsgField("����", "multiple", MsgUtil.FIELD_CHAR, 10);
	public static MsgField rate  = new MsgField("���ʽ����", "rate", MsgUtil.FIELD_CHAR, 20);
	public static MsgField liqu_time  = new MsgField("��������", "liqu_time", MsgUtil.FIELD_CHAR, 8);
	public static MsgField entr_amount  = new MsgField("ί������", "entr_amount", MsgUtil.FIELD_CHAR, 18);
	public static MsgField bs_flag  = new MsgField("������־", "bs_flag", MsgUtil.FIELD_CHAR, 2);
	public static MsgField app_date = new MsgField("��������", "app_date", MsgUtil.FIELD_CHAR, 4);
	public static MsgField acct_no = new MsgField("�ͻ���", "acct_no", MsgUtil.FIELD_CHAR, 15);
	public static MsgField o_date = new MsgField("��������", "o_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField m_date = new MsgField("�޸�����", "m_date", MsgUtil.FIELD_CHAR, 20);
	public static MsgField m_term_type = new MsgField("�޸���Դ", "m_term_type", MsgUtil.FIELD_CHAR, 20);
	public static MsgField m_teller_id = new MsgField("�޸Ĳ���Ա��", "m_teller_id", MsgUtil.FIELD_CHAR, 10);
	public static MsgField plan_exch_date  = new MsgField("�ƻ������", "plan_exch_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField variety_id  = new MsgField("Ʒ�ִ���", "variety_id", MsgUtil.FIELD_CHAR, 5);
	public static MsgField take_weight  = new MsgField("�������", "take_weight", MsgUtil.FIELD_INTEGER, 15);
	public static MsgField city_code  = new MsgField("�������", "city_code", MsgUtil.FIELD_CHAR, 50);
	public static MsgField city_name  = new MsgField("��������", "city_name", MsgUtil.FIELD_CHAR, 50);
	public static MsgField stor_id  = new MsgField("����ֿ�", "stor_id", MsgUtil.FIELD_CHAR, 60);
	public static MsgField contacter  = new MsgField("�����ϵ��", "contacter", MsgUtil.FIELD_CHAR,20);
	public static MsgField local_serial_no  = new MsgField("�����ˮ��", "local_serial_no", MsgUtil.FIELD_CHAR, 16);
	public static MsgField take_address  = new MsgField("�����ַ", "take_address", MsgUtil.FIELD_CHAR, 200);
	public static MsgField b_contactor  = new MsgField("�����ϵ��", "b_contactor", MsgUtil.FIELD_CHAR, 20);
	public static MsgField serial_no = new MsgField("������ˮ��", "serial_no", MsgUtil.FIELD_CHAR, 16);
	public static MsgField sheet_no = new MsgField("�������", "sheet_no", MsgUtil.FIELD_CHAR, 14);
	public static MsgField variety_name = new MsgField("Ʒ������", "variety_name", MsgUtil.FIELD_CHAR, 40);
	public static MsgField min_pickup = new MsgField("��С�������", "min_pickup", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField pickup_base = new MsgField("�������", "pickup_base", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField cust_name = new MsgField("�ͻ�����", "cust_name", MsgUtil.FIELD_CHAR, 31);
	public static MsgField stat = new MsgField("���״̬", "stat", MsgUtil.FIELD_CHAR, 20);
	public static MsgField trade_type = new MsgField("���׷�ʽ", "trade_type", MsgUtil.FIELD_CHAR, 20);
	public static MsgField entr_flow_serial_no = new MsgField("ί�н�����ˮ��", "entr_flow_serial_no", MsgUtil.FIELD_CHAR, 20);
	public static MsgField out_account_no = new MsgField("���������˺�", "out_account_no", MsgUtil.FIELD_CHAR,32);
	public static MsgField in_account_no = new MsgField("��������˺�", "in_account_no", MsgUtil.FIELD_CHAR, 32);
	public static MsgField send_stat = new MsgField("����״̬", "send_stat", MsgUtil.FIELD_CHAR, 10);
	public static MsgField in_account_flag = new MsgField("�Ƿ�������", "in_account_flag", MsgUtil.FIELD_CHAR, 20);
	public static MsgField remark = new MsgField("��ע", "remark", MsgUtil.FIELD_CHAR, 255);
	public static MsgField bk_plat_date = new MsgField("��������", "bk_plat_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField bk_seq_no = new MsgField("������ˮ��", "bk_seq_no", MsgUtil.FIELD_CHAR, 30);
	public static MsgField bk_rsp_code = new MsgField("������Ӧ����", "bk_rsp_code", MsgUtil.FIELD_CHAR, 20);
	public static MsgField bk_rsp_msg = new MsgField("������Ӧ��Ϣ", "bk_rsp_msg", MsgUtil.FIELD_CHAR, 200);
	public static MsgField bk_check_stat = new MsgField("���ж���״̬", "bk_check_stat", MsgUtil.FIELD_CHAR, 10);
	public static MsgField take_man = new MsgField("���������", "take_man", MsgUtil.FIELD_CHAR, 60);
	public static MsgField take_end_date = new MsgField("�����������", "take_end_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField rsp_msg = new MsgField("������Ϣ", "rsp_msg", MsgUtil.FIELD_CHAR, 60);
	public static MsgField aip_no = new MsgField("��Ͷ�˺�", "aip_no", MsgUtil.FIELD_CHAR, 15);
	public static MsgField change_type = new MsgField("�䶯����", "change_type", MsgUtil.FIELD_CHAR, 20);
	public static MsgField exch_bal = new MsgField("�������", "exch_bal", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField aip_bal = new MsgField("��Ͷ��� ", "aip_bal", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField exch_weight = new MsgField("�仯ֵ ", "exch_weight", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField aip_amt = new MsgField("��Ͷ����", "aip_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField match_bal = new MsgField("�ɽ����", "match_bal", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField match_amt = new MsgField("�ɽ�����", "match_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField real_exch_bal = new MsgField("ʵ�۽��", "real_exch_bal", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField pro_fee = new MsgField("����������", "pro_fee", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField pub_price = new MsgField("��Ͷ�����", "pub_price", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField deal_rate = new MsgField("�ɽ���(%)", "deal_rate", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField trans_amt = new MsgField("���ÿ��", "trans_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField avg_price = new MsgField("���ÿ�����", "avg_price", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField phy_amt = new MsgField("��������", "phy_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField dedt = new MsgField("Ƿ��", "dedt", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField frozen_fund = new MsgField("�����ʽ�", "frozen_fund", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField debt_bal = new MsgField("Ƿ��", "debt_bal", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField frozen_amt = new MsgField("���ն���������", "frozen_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField buy_amt = new MsgField("��������", "buy_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField sell_amt = new MsgField("��������", "sell_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_plan_weight = new MsgField("�ۼƻ�������", "curr_plan_weight", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_buy_weight = new MsgField("�ۼ�������Ͷ����", "curr_buy_weight", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_sell_weight = new MsgField("�ۼ��ֽ��������", "curr_sell_weight", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_take_weight = new MsgField("�ۼ�ʵ���������", "curr_take_weight", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_amt = new MsgField("��ǰ���", "curr_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_bal = new MsgField("��ǰ���", "curr_bal", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField curr_can_use = new MsgField("�������", "curr_can_use", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField sell_froz_amt = new MsgField("������������", "sell_froz_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField trade_app_froz = new MsgField("�����������", "trade_app_froz", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField spot_amt = new MsgField("������", "spot_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField spot_can_get = new MsgField("������", "spot_can_get", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField spot_app_froz = new MsgField("�����������", "spot_app_froz", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField total_amt = new MsgField("�ܿ��", "total_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField account_surplus = new MsgField("�˻�ӯ��", "account_surplus", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField cur_year_acct_surplus = new MsgField("�����˻�ӯ��", "cur_year_acct_surplus", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField settle_price = new MsgField("���ն�Ͷ�����", "settle_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField gold_virtual_bal = new MsgField("�ƽ��˻������ֵ", "gold_virtual_bal", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField gold_real_bal = new MsgField("ʵ�ʶ�Ͷ���", "gold_real_bal", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField au100_take_fare = new MsgField("Au100g��ȡ��", "au100_take_fare", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField au9999_take_fare = new MsgField("Au99.99��ȡ��", "au9999_take_fare", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField take_margin = new MsgField("���׼����", "take_margin", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField take_fare = new MsgField("ʵ�����������", "take_fare", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField re_margin = new MsgField("�����ͻ�׼����", "re_margin", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField open_price = new MsgField("���̼�", "open_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField high_price = new MsgField("��߼�", "high_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField low_price = new MsgField("��ͼ�", "low_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField aip_settle_price = new MsgField("��Ͷ�����", "aip_settle_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField last_close_price = new MsgField("��һ���������̼�", "last_close_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField new_price = new MsgField("���¼�", "new_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField max_price = new MsgField("��߼�", "max_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField min_price = new MsgField("��ͼ�", "min_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField close_price = new MsgField("���̼�", "close_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField exch_fare = new MsgField("�ͻ�����������", "exch_fare", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField acct_manage_fee = new MsgField("�˻������", "acct_manage_fee", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField trans_fee = new MsgField("�˱���", "trans_fee", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField punish_interest = new MsgField("��Ϣ", "punish_interest", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField last_settle_price = new MsgField("���ն�Ͷ�����", "last_settle_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField diff_bal = new MsgField("��̷�", "diff_bal", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField process_cost = new MsgField("Ʒ�ƽ����ӹ���", "process_cost", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField deal_time = new MsgField("�������", "deal_time", MsgUtil.FIELD_INTEGER,6);
	public static int getStartIndex(MsgField[] fields, MsgField field){
		int index = 0;
		for (int i = 0; i < fields.length; i++) {
			if (!StringUtils.equals(field.getFieldCode(), fields[i].getFieldCode())) {
				index += fields[i].length;
			}
		}
		return index;
	}
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}
	
}
