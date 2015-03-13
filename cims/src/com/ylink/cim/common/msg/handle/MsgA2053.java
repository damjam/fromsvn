package com.ylink.cim.common.msg.handle;




public class MsgA2053 extends MsgField{
	
	
	public static MsgField[] A2053REQ = {
		MsgField.aip_no,
		MsgField.start_date,
		MsgField.end_date
	};
	
	public static MsgField[] A2053RES = {
		MsgField.exch_date,
		MsgField.aip_no,
		MsgField.account_no,
		MsgField.cust_name,
		MsgField.aip_type,
		MsgField.trans_amt,
		MsgField.avg_price,
		MsgField.phy_amt,
		MsgField.dedt,
		MsgField.frozen_fund,
		MsgField.frozen_amt,
		MsgField.buy_amt,
		MsgField.sell_amt,
		MsgField.account_surplus,
		MsgField.cur_year_acct_surplus,
		MsgField.settle_price,
		MsgField.total_amt,
		MsgField.last_settle_price
		
		};
	

}
