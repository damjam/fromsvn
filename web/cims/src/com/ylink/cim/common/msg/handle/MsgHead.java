package com.ylink.cim.common.msg.handle;

public class MsgHead {

	public static MsgField[] REQ_HEAD = 
		{ 
			MsgField.h_gateway_header,
			MsgField.h_exch_code, 
			MsgField.h_bk_tx_date, 
			MsgField.h_bk_tx_time,
			MsgField.h_bk_serial_no, 
			MsgField.h_exch_date, 
			MsgField.h_proxy,
			MsgField.h_bank_no, 
			MsgField.h_branch_id, 
			MsgField.h_teller_id,
			MsgField.h_cashier_id, 
			MsgField.h_teller_id1,
			MsgField.h_teller_id2, 
			MsgField.h_channel, 
			MsgField.h_term_id,
			MsgField.h_rsp_type, 
			MsgField.h_req_type, 
			MsgField.h_auth_lvl,
			MsgField.h_start_num, 
			MsgField.h_query_num, 
			MsgField.back1,
			MsgField.back2, 
			MsgField.back3, 
			MsgField.back4 
		};

	public static MsgField[] RES_HEAD = 
		{ 
			MsgField.h_gateway_header,
			MsgField.h_exch_code, 
			MsgField.h_rsp_code, 
			MsgField.h_rsp_msg,
			MsgField.h_bk_tx_date, 
			MsgField.h_bk_tx_time,
			MsgField.h_bk_serial_no, 
			MsgField.h_exch_date, 
			MsgField.h_proxy,
			MsgField.h_bank_no, 
			MsgField.h_branch_id, 
			MsgField.h_teller_id,
			MsgField.h_teller_id1, 
			MsgField.h_teller_id2, 
			MsgField.h_channel,
			MsgField.h_term_id, 
			MsgField.h_rsp_type, 
			MsgField.h_req_type,
			MsgField.h_auth_lvl, 
			MsgField.h_start_num, 
			MsgField.h_query_num,
			MsgField.h_sum_num, 
			MsgField.h_rsp_num 
		};
}
