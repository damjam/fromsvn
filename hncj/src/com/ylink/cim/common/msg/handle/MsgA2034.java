package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;

public class MsgA2034 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */

	public static MsgField memo = new MsgField();
	public static MsgField aip_type = new MsgField();
	public static MsgField bs_flag = new MsgField();
	public static MsgField aip_mode = new MsgField();
	public static MsgField deal_stat = new MsgField();
	public static MsgField entr_source = new MsgField();
	public static MsgField exch_bal = new MsgField();
	static {
		try {
			BeanUtils.copyProperties(memo, MsgField.memo);
			memo.setLength(60);
			BeanUtils.copyProperties(aip_type, MsgField.aip_type);
			aip_type.setLength(31);
			BeanUtils.copyProperties(bs_flag, MsgField.bs_flag);
			bs_flag.setLength(2);
			BeanUtils.copyProperties(aip_mode, MsgField.aip_mode);
			aip_mode.setLength(20);
			BeanUtils.copyProperties(deal_stat, MsgField.deal_stat);
			deal_stat.setLength(20);
			BeanUtils.copyProperties(entr_source, MsgField.entr_source);
			entr_source.setLength(20);
			BeanUtils.copyProperties(exch_bal, MsgField.exch_bal);
			exch_bal.setLength(18);
			exch_bal.setDeciLength(4);
		} catch (Exception e) {
		}

	}

	public static MsgField[] A2034REQ = { MsgField.aip_no,
			MsgField.local_serial_no, MsgField.aip_type, MsgField.start_date,
			MsgField.end_date, MsgField.entr_source, MsgField.deal_stat,
			MsgField.bs_flag };

	public static MsgField[] A2034RES = { MsgField.entr_date,
			MsgField.exch_date, MsgField.order_no, MsgField.aip_no,
			MsgField.account_no, MsgField.cust_name, aip_type, aip_mode,
			bs_flag, MsgField.aip_bal, MsgField.aip_amt, MsgField.match_bal,
			MsgField.match_amt, exch_bal, MsgField.real_exch_bal,
			MsgField.pro_fee, MsgField.pub_price, MsgField.deal_time,
			deal_stat, MsgField.deal_rate, entr_source, MsgField.charge_stat,
			memo, MsgField.origin_order_no };

}
