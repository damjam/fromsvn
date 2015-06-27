package com.ylink.cim.common.msg.handle;

import java.util.HashMap;
import java.util.Map;

public class HeadReqUtils {

	public static Map<String, String> encapsulationHeadReqParam() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(MsgField.h_bk_serial_no.getFieldCode(), "");
		map.put(MsgField.h_proxy.getFieldCode(), "0");
		map.put(MsgField.h_bank_no.getFieldCode(), "0000");
		map.put(MsgField.h_branch_id.getFieldCode(), "");
		map.put(MsgField.h_teller_id.getFieldCode(), "");
		map.put(MsgField.h_cashier_id.getFieldCode(), "");
		map.put(MsgField.h_teller_id1.getFieldCode(), "");
		map.put(MsgField.h_teller_id2.getFieldCode(), "");
		map.put(MsgField.h_channel.getFieldCode(), "5");
		map.put(MsgField.h_term_id.getFieldCode(), "");
		map.put(MsgField.h_rsp_type.getFieldCode(), "");
		map.put(MsgField.h_req_type.getFieldCode(), "T");
		map.put(MsgField.h_auth_lvl.getFieldCode(), "00");
		map.put(MsgField.h_start_num.getFieldCode(), "1");
		map.put(MsgField.h_query_num.getFieldCode(), "20");
		map.put(MsgField.back1.getFieldCode(), "");
		map.put(MsgField.back2.getFieldCode(), "");
		map.put(MsgField.back3.getFieldCode(), "");
		map.put(MsgField.back4.getFieldCode(), "");

		return map;
	}
}
