package com.ylink.client.msg;

import com.ylink.client.common.MsgUtil;

/**
 * 
 * 请求报文 包括请求报文头 请求报文体 sbMsgBody
 * 
 */
public class ReqMsg {
	public static final int H_EXCH_CODE_LEN = 5;
	public static final int H_BK_TX_DATE_LEN = 8;
	public static final int H_BK_TX_TIME_LEN = 8;
	public static final int H_BK_SERIAL_NO_LEN = 22;
	public static final int H_EXCH_DATE_LEN = 8;
	public static final int H_PROXY_LEN = 1;
	public static final int H_BANK_NO_LEN = 4;
	public static final int H_BRANCH_ID_LEN = 10;
	public static final int H_TELLER_ID_LEN = 10;
	public static final int H_CASHIER_NO_UNDEF_LEN = 4;
	public static final int H_TELLER_ID1_LEN = 10;
	public static final int H_TELLER_ID2_LEN = 10;
	public static final int H_CHANNEL_LEN = 1;
	public static final int H_TERM_ID_LEN = 10;
	public static final int H_RSP_TYPE_LEN = 1;
	public static final int H_REQ_TYPE_LEN = 1;
	public static final int H_AUTH_LVL_LEN = 2;
	public static final int H_START_NUM_LEN = 8;
	public static final int H_QUERY_NUM_LEN = 8;
	public static final int H_SPARE_01_LEN = 20;
	public static final int H_SPARE_02_LEN = 20;
	public static final int H_SPARE_03_LEN = 20;
	public static final int H_SPARE_04_LEN = 20;

	private String h_exch_code = "";
	private String h_bk_tx_date = "";
	private String h_bk_tx_time = "";
	private String h_bk_serial_no = "";
	private String h_exch_date = "";
	private String h_proxy = "";
	private String h_bank_no = "";
	private String h_branch_id = "";
	private String h_teller_id = "";
	private String h_cashier_no_undef = "";
	private String h_teller_id1 = "";
	private String h_teller_id2 = "";
	private String h_channel = "";
	private String h_term_id = "";
	private String h_rsp_type = "";
	private String h_req_type = "";
	private String h_auth_lvl = "";
	private String h_start_num = "";
	private String h_query_num = "";
	private String h_spare_01 = "";
	private String h_spare_02 = "";
	private String h_spare_03 = "";
	private String h_spare_04 = "";

	private StringBuffer sbMsgBody = new StringBuffer();

	protected long sStartTime = System.currentTimeMillis();

	public ReqMsg() {
		
	}

	public void parse(String sReqMsg) {
		int index = 0;
		this.h_exch_code = sReqMsg.substring(index, index + H_EXCH_CODE_LEN).trim();
		index += H_EXCH_CODE_LEN;
		
		this.h_bk_tx_date = sReqMsg.substring(index, index + H_BK_TX_DATE_LEN).trim();
		index += H_BK_TX_DATE_LEN;
		
		this.h_bk_tx_time = sReqMsg.substring(index, index + H_BK_TX_TIME_LEN).trim();
		index += H_BK_TX_TIME_LEN;
		
		this.h_bk_serial_no = sReqMsg.substring(index, index + H_BK_SERIAL_NO_LEN).trim();
		index += H_BK_SERIAL_NO_LEN;
		
		this.h_exch_date = sReqMsg.substring(index, index + H_EXCH_DATE_LEN).trim();
		index += H_EXCH_DATE_LEN;
		
		this.h_proxy = sReqMsg.substring(index, index + H_PROXY_LEN).trim();
		index += H_PROXY_LEN;
		
		this.h_bank_no = sReqMsg.substring(index, index + H_BANK_NO_LEN).trim();
		index += H_BANK_NO_LEN;
		
		this.h_branch_id = sReqMsg.substring(index, index + H_BRANCH_ID_LEN).trim();
		index += H_BRANCH_ID_LEN;
		
		this.h_teller_id = sReqMsg.substring(index, index + H_TELLER_ID_LEN).trim();
		index += H_TELLER_ID_LEN;
		
		this.h_cashier_no_undef = sReqMsg.substring(index, index + H_CASHIER_NO_UNDEF_LEN).trim();
		index += H_CASHIER_NO_UNDEF_LEN;
		
		this.h_teller_id1 = sReqMsg.substring(index, index + H_TELLER_ID1_LEN).trim();
		index += H_TELLER_ID1_LEN;
		
		this.h_teller_id2 = sReqMsg.substring(index, index + H_TELLER_ID2_LEN).trim();
		index += H_TELLER_ID2_LEN;
		
		this.h_channel = sReqMsg.substring(index, index + H_CHANNEL_LEN).trim();
		index += H_CHANNEL_LEN;
		
		this.h_term_id = sReqMsg.substring(index, index + H_TERM_ID_LEN).trim();
		index += H_TERM_ID_LEN;
		
		this.h_rsp_type = sReqMsg.substring(index, index + H_RSP_TYPE_LEN).trim();
		index += H_RSP_TYPE_LEN;
		
		this.h_req_type = sReqMsg.substring(index, index + H_REQ_TYPE_LEN).trim();
		index += H_REQ_TYPE_LEN;
		
		this.h_auth_lvl = sReqMsg.substring(index, index + H_AUTH_LVL_LEN).trim();
		index += H_AUTH_LVL_LEN;
		
		this.h_start_num = sReqMsg.substring(index, index + H_START_NUM_LEN).trim();
		index += H_START_NUM_LEN;
		
		this.h_query_num = sReqMsg.substring(index, index + H_QUERY_NUM_LEN).trim();
		index += H_QUERY_NUM_LEN;
		
		this.h_spare_01 = sReqMsg.substring(index, index + H_SPARE_01_LEN).trim();
		index += H_SPARE_01_LEN;
		
		this.h_spare_02 = sReqMsg.substring(index, index + H_SPARE_02_LEN).trim();
		index += H_SPARE_02_LEN;
		
		this.h_spare_03 = sReqMsg.substring(index, index + H_SPARE_03_LEN).trim();
		index += H_SPARE_03_LEN;
		
		this.h_spare_04 = sReqMsg.substring(index, index + H_SPARE_04_LEN).trim();
		index += H_SPARE_04_LEN;
		
		this.sbMsgBody = sbMsgBody.append(sReqMsg.substring(index).trim());
	}

	
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(MsgUtil.addSpaceForString(this.h_exch_code,
				ReqMsg.H_EXCH_CODE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_bk_tx_date,
				ReqMsg.H_BK_TX_DATE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_bk_tx_time,
				ReqMsg.H_BK_TX_TIME_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_bk_serial_no,
				ReqMsg.H_BK_SERIAL_NO_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_exch_date,
				ReqMsg.H_EXCH_DATE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_proxy,
				ReqMsg.H_PROXY_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_bank_no,
				ReqMsg.H_BANK_NO_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_branch_id,
				ReqMsg.H_BRANCH_ID_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_teller_id,
				ReqMsg.H_TELLER_ID_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_cashier_no_undef,
				ReqMsg.H_CASHIER_NO_UNDEF_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_teller_id1,
				ReqMsg.H_TELLER_ID1_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_teller_id2,
				ReqMsg.H_TELLER_ID2_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_channel,
				ReqMsg.H_CHANNEL_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_term_id,
				ReqMsg.H_TERM_ID_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_rsp_type,
				ReqMsg.H_RSP_TYPE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_req_type,
				ReqMsg.H_REQ_TYPE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_auth_lvl,
				ReqMsg.H_AUTH_LVL_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_start_num,
				ReqMsg.H_START_NUM_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_query_num,
				ReqMsg.H_QUERY_NUM_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_spare_01,
				ReqMsg.H_SPARE_01_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_spare_02,
				ReqMsg.H_SPARE_02_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_spare_03,
				ReqMsg.H_SPARE_03_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_spare_04,
				ReqMsg.H_SPARE_04_LEN));

		sb.append(this.sbMsgBody.toString());
		return sb.toString();
	}

	public String getH_exch_code() {
		return h_exch_code;
	}

	public void setH_exch_code(String h_exch_code) {
		this.h_exch_code = h_exch_code;
	}

	public String getH_bk_tx_date() {
		return h_bk_tx_date;
	}

	public void setH_bk_tx_date(String h_bk_tx_date) {
		this.h_bk_tx_date = h_bk_tx_date;
	}

	public String getH_bk_tx_time() {
		return h_bk_tx_time;
	}

	public void setH_bk_tx_time(String h_bk_tx_time) {
		this.h_bk_tx_time = h_bk_tx_time;
	}

	public String getH_bk_serial_no() {
		return h_bk_serial_no;
	}

	public void setH_bk_serial_no(String h_bk_serial_no) {
		this.h_bk_serial_no = h_bk_serial_no;
	}

	public String getH_exch_date() {
		return h_exch_date;
	}

	public void setH_exch_date(String h_exch_date) {
		this.h_exch_date = h_exch_date;
	}

	public String getH_proxy() {
		return h_proxy;
	}

	public void setH_proxy(String h_proxy) {
		this.h_proxy = h_proxy;
	}

	public String getH_bank_no() {
		return h_bank_no;
	}

	public void setH_bank_no(String h_bank_no) {
		this.h_bank_no = h_bank_no;
	}

	public String getH_branch_id() {
		return h_branch_id;
	}

	public void setH_branch_id(String h_branch_id) {
		this.h_branch_id = h_branch_id;
	}

	public String getH_teller_id() {
		return h_teller_id;
	}

	public void setH_teller_id(String h_teller_id) {
		this.h_teller_id = h_teller_id;
	}

	public String getH_cashier_no_undef() {
		return h_cashier_no_undef;
	}

	public void setH_cashier_no_undef(String h_cashier_no_undef) {
		this.h_cashier_no_undef = h_cashier_no_undef;
	}

	public String getH_teller_id1() {
		return h_teller_id1;
	}

	public void setH_teller_id1(String h_teller_id1) {
		this.h_teller_id1 = h_teller_id1;
	}

	public String getH_teller_id2() {
		return h_teller_id2;
	}

	public void setH_teller_id2(String h_teller_id2) {
		this.h_teller_id2 = h_teller_id2;
	}

	public String getH_channel() {
		return h_channel;
	}

	public void setH_channel(String h_channel) {
		this.h_channel = h_channel;
	}

	public String getH_term_id() {
		return h_term_id;
	}

	public void setH_term_id(String h_term_id) {
		this.h_term_id = h_term_id;
	}

	public String getH_rsp_type() {
		return h_rsp_type;
	}

	public void setH_rsp_type(String h_rsp_type) {
		this.h_rsp_type = h_rsp_type;
	}

	public String getH_req_type() {
		return h_req_type;
	}

	public void setH_req_type(String h_req_type) {
		this.h_req_type = h_req_type;
	}

	public String getH_auth_lvl() {
		return h_auth_lvl;
	}

	public void setH_auth_lvl(String h_auth_lvl) {
		this.h_auth_lvl = h_auth_lvl;
	}

	public String getH_start_num() {
		return h_start_num;
	}

	public void setH_start_num(String h_start_num) {
		this.h_start_num = h_start_num;
	}

	public String getH_query_num() {
		return h_query_num;
	}

	public void setH_query_num(String h_query_num) {
		this.h_query_num = h_query_num;
	}

	public String getH_spare_01() {
		return h_spare_01;
	}

	public void setH_spare_01(String h_spare_01) {
		this.h_spare_01 = h_spare_01;
	}

	public String getH_spare_02() {
		return h_spare_02;
	}

	public void setH_spare_02(String h_spare_02) {
		this.h_spare_02 = h_spare_02;
	}

	public String getH_spare_03() {
		return h_spare_03;
	}

	public void setH_spare_03(String h_spare_03) {
		this.h_spare_03 = h_spare_03;
	}

	public String getH_spare_04() {
		return h_spare_04;
	}

	public void setH_spare_04(String h_spare_04) {
		this.h_spare_04 = h_spare_04;
	}

	public long getDealTime() {
		return (System.currentTimeMillis() - this.sStartTime);
	}
}
