package com.ylink.client.msg;

import com.ylink.client.common.MsgUtil;

/**
 * 
 * 响应报文 包括响应报文头 以及响应报文体 sbMsgBody
 * 
 */
public class RspMsg {
	public static final int H_EXCH_CODE_LEN = PacketLenConstant.LEN_5;
	public static final int H_RSP_CODE_LEN = PacketLenConstant.LEN_7;
	public static final int H_RSP_MSG_LEN = PacketLenConstant.LEN_100;
	public static final int H_BK_TX_DATE_LEN = PacketLenConstant.LEN_8;
	public static final int H_BK_TX_TIME = PacketLenConstant.LEN_8;
	public static final int H_BK_SERIAL_NO_LEN = PacketLenConstant.LEN_22;
	public static final int H_EXCH_DATE_LEN = PacketLenConstant.LEN_8;
	public static final int H_PROXY_LEN = PacketLenConstant.LEN_1;
	public static final int H_BANK_NO_LEN = PacketLenConstant.LEN_4;
	public static final int H_BRANCH_ID_LEN = PacketLenConstant.LEN_10;
	public static final int H_TELLER_ID_LEN = PacketLenConstant.LEN_10;
	public static final int H_TELLER_ID1_LEN = PacketLenConstant.LEN_10;
	public static final int H_TELLER_ID2_LEN = PacketLenConstant.LEN_10;
	public static final int H_CHANNEL_LEN = PacketLenConstant.LEN_1;
	public static final int H_TERM_ID_LEN = PacketLenConstant.LEN_10;
	public static final int H_RSP_TYPE_LEN = PacketLenConstant.LEN_1;
	public static final int H_REQ_TYPE_LEN = PacketLenConstant.LEN_1;
	public static final int H_AUTH_LVL_LEN = PacketLenConstant.LEN_8;
	public static final int H_START_NUM_LEN = PacketLenConstant.LEN_8;
	public static final int H_QUERY_NUM_LEN = PacketLenConstant.LEN_8;
	public static final int H_SUM_NUM_LEN = PacketLenConstant.LEN_8;
	public static final int H_RSP_NUM_LEN = PacketLenConstant.LEN_8;

	/**
	 * 交易码
	 */
	private String h_exch_code = "";
	/**
	 * 返回码
	 */
	private String h_rsp_code = "";
	/**
	 * 返回信息
	 */
	private String h_rsp_msg = "";
	/**
	 * 交易发生日期
	 */
	private String h_bk_tx_date = "";
	/**
	 * 交易发生时间
	 */
	private String h_bk_tx_time = "";
	/**
	 * 交易流水号
	 */
	private String h_bk_serial_no = "";
	/**
	 * 交易日
	 */
	private String h_exch_date = "";
	/**
	 * 代理标志
	 */
	private String h_proxy = "";
	/**
	 * 联网行编号
	 */
	private String h_bank_no = "";
	/**
	 * 交易网点
	 */
	private String h_branch_id = "";
	/**
	 * 柜员
	 */
	private String h_teller_id = "";
	/**
	 * 授权柜员1
	 */
	private String h_teller_id1 = "";
	/**
	 * 授权柜员2
	 */
	private String h_teller_id2 = "";
	/**
	 * 交易渠道
	 */
	private String h_channel = "";
	/**
	 * 终端号
	 */
	private String h_term_id = "";
	/**
	 * 返回码类型
	 */
	private String h_rsp_type = "";
	/**
	 * 请求类型
	 */
	private String h_req_type = "";
	/**
	 * 授权级别
	 */
	private String h_auth_lvl = "";
	/**
	 * 当前第几页
	 */
	private String h_start_num = "";
	/**
	 * 查询记录数
	 */
	private String h_query_num = "";
	/**
	 * 总页数
	 */
	private String h_sum_num = "";
	/**
	 * 返回记录数
	 */
	private String h_rsp_num = "";

	private StringBuffer sbMsgBody = new StringBuffer();

	protected long sStartTime = System.currentTimeMillis();

	public RspMsg() {

	}

	public long getDealTime() {
		return (System.currentTimeMillis() - this.sStartTime);
	}

	public String getH_auth_lvl() {
		return h_auth_lvl;
	}

	public String getH_bank_no() {
		return h_bank_no;
	}

	public String getH_bk_serial_no() {
		return h_bk_serial_no;
	}

	public String getH_bk_tx_date() {
		return h_bk_tx_date;
	}

	public String getH_bk_tx_time() {
		return h_bk_tx_time;
	}

	public String getH_branch_id() {
		return h_branch_id;
	}

	public String getH_channel() {
		return h_channel;
	}

	public String getH_exch_code() {
		return h_exch_code;
	}

	public String getH_exch_date() {
		return h_exch_date;
	}

	public String getH_proxy() {
		return h_proxy;
	}

	public String getH_query_num() {
		return h_query_num;
	}

	public String getH_req_type() {
		return h_req_type;
	}

	public String getH_rsp_code() {
		return h_rsp_code;
	}

	public String getH_rsp_msg() {
		return h_rsp_msg;
	}

	public String getH_rsp_num() {
		return h_rsp_num;
	}

	public String getH_rsp_type() {
		return h_rsp_type;
	}

	public String getH_start_num() {
		return h_start_num;
	}

	public String getH_sum_num() {
		return h_sum_num;
	}

	public String getH_teller_id() {
		return h_teller_id;
	}

	public String getH_teller_id1() {
		return h_teller_id1;
	}

	public String getH_teller_id2() {
		return h_teller_id2;
	}

	public String getH_term_id() {
		return h_term_id;
	}

	public StringBuffer getSbMsgBody() {
		return sbMsgBody;
	}

	public long getsStartTime() {
		return sStartTime;
	}

	public void parse(String sReqMsg) {

	}

	public void setH_auth_lvl(String h_auth_lvl) {
		this.h_auth_lvl = h_auth_lvl;
	}

	public void setH_bank_no(String h_bank_no) {
		this.h_bank_no = h_bank_no;
	}

	public void setH_bk_serial_no(String h_bk_serial_no) {
		this.h_bk_serial_no = h_bk_serial_no;
	}

	public void setH_bk_tx_date(String h_bk_tx_date) {
		this.h_bk_tx_date = h_bk_tx_date;
	}

	public void setH_bk_tx_time(String h_bk_tx_time) {
		this.h_bk_tx_time = h_bk_tx_time;
	}

	public void setH_branch_id(String h_branch_id) {
		this.h_branch_id = h_branch_id;
	}

	public void setH_channel(String h_channel) {
		this.h_channel = h_channel;
	}

	public void setH_exch_code(String h_exch_code) {
		this.h_exch_code = h_exch_code;
	}

	public void setH_exch_date(String h_exch_date) {
		this.h_exch_date = h_exch_date;
	}

	public void setH_proxy(String h_proxy) {
		this.h_proxy = h_proxy;
	}

	public void setH_query_num(String h_query_num) {
		this.h_query_num = h_query_num;
	}

	public void setH_req_type(String h_req_type) {
		this.h_req_type = h_req_type;
	}

	public void setH_rsp_code(String h_rsp_code) {
		this.h_rsp_code = h_rsp_code;
	}

	public void setH_rsp_msg(String h_rsp_msg) {
		this.h_rsp_msg = h_rsp_msg;
	}

	public void setH_rsp_num(String h_rsp_num) {
		this.h_rsp_num = h_rsp_num;
	}

	public void setH_rsp_type(String h_rsp_type) {
		this.h_rsp_type = h_rsp_type;
	}

	public void setH_start_num(String h_start_num) {
		this.h_start_num = h_start_num;
	}

	public void setH_sum_num(String h_sum_num) {
		this.h_sum_num = h_sum_num;
	}

	public void setH_teller_id(String h_teller_id) {
		this.h_teller_id = h_teller_id;
	}

	public void setH_teller_id1(String h_teller_id1) {
		this.h_teller_id1 = h_teller_id1;
	}

	public void setH_teller_id2(String h_teller_id2) {
		this.h_teller_id2 = h_teller_id2;
	}

	public void setH_term_id(String h_term_id) {
		this.h_term_id = h_term_id;
	}

	public void setSbMsgBody(StringBuffer sbMsgBody) {
		this.sbMsgBody = sbMsgBody;
	}

	public void setsStartTime(long sStartTime) {
		this.sStartTime = sStartTime;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(MsgUtil.addSpaceForString(this.h_exch_code, RspMsg.H_EXCH_CODE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_rsp_code, RspMsg.H_RSP_CODE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_rsp_msg, RspMsg.H_RSP_MSG_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_bk_tx_date, RspMsg.H_BK_TX_DATE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_bk_tx_time, RspMsg.H_BK_TX_TIME));
		sb.append(MsgUtil.addSpaceForString(this.h_bk_serial_no, RspMsg.H_BK_SERIAL_NO_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_exch_date, RspMsg.H_EXCH_DATE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_proxy, RspMsg.H_PROXY_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_bank_no, RspMsg.H_BANK_NO_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_branch_id, RspMsg.H_BRANCH_ID_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_teller_id, RspMsg.H_TELLER_ID_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_teller_id1, RspMsg.H_TELLER_ID1_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_teller_id2, RspMsg.H_TELLER_ID2_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_channel, RspMsg.H_CHANNEL_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_term_id, RspMsg.H_TERM_ID_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_rsp_type, RspMsg.H_RSP_TYPE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_req_type, RspMsg.H_REQ_TYPE_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_auth_lvl, RspMsg.H_AUTH_LVL_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_start_num, RspMsg.H_START_NUM_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_query_num, RspMsg.H_QUERY_NUM_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_sum_num, RspMsg.H_SUM_NUM_LEN));
		sb.append(MsgUtil.addSpaceForString(this.h_rsp_num, RspMsg.H_RSP_NUM_LEN));

		sb.append(this.sbMsgBody.toString());
		return sb.toString();
	}

}
