package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;

public class MsgA2036 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */

	public static MsgField memo = new MsgField();
	public static MsgField aip_type = new MsgField();
	public static MsgField bs_flag = new MsgField();
	public static MsgField aip_mode = new MsgField();
	public static MsgField deal_stat = new MsgField();
	public static MsgField entr_source = new MsgField();
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
		} catch (Exception e) {
		}

	}

	public static MsgField[] A2036REQ = { MsgField.aip_no, MsgField.start_date, MsgField.end_date };

	public static MsgField[] A2036RES = { MsgField.exch_date, MsgField.aip_no, MsgField.account_no, MsgField.cust_name,
			aip_type, MsgField.trans_amt, MsgField.avg_price, MsgField.dedt, MsgField.frozen_fund, MsgField.frozen_amt,
			MsgField.buy_amt, MsgField.sell_amt, MsgField.account_surplus, MsgField.cur_year_acct_surplus,
			MsgField.settle_price, MsgField.gold_virtual_bal, MsgField.gold_real_bal };

}
