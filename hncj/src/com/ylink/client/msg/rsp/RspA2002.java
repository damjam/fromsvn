package com.ylink.client.msg.rsp;

import com.ylink.gess.product.msg.RspBase;

/**
 * 
 * 客户定投计划查询
 * 返回报文
 *
 */
public class RspA2002 extends RspBase {
	/**
	 * 申请流水号
	 */
	public String order_no = "";
	/**
	 * 客户号
	 */
	public String acct_no = "";
	/**
	 * 银行帐号
	 */
	public String account_no = "";
	/**
	 * 客户名称
	 */
	public String cust_name = "";
	/**
	 * 定投类别
	 */
	public String aip_type = "";
	/**
	 * 定投模式
	 */
	public String aip_mode = "";
	/**
	 * 定投金额\重量
	 */
	public String aip_amount = "";
	/**
	 * 定投周期
	 */
	public String aip_periods = "";
	/**
	 * 首次投资日期
	 */
	public String first_aip_date = "";
	/**
	 * 下个扣款日期
	 */
	public String next_invest_date = "";
	/**
	 * 终止日期
	 */
	public String end_date = "";
	/**
	 * 计划状态
	 */
	public String plan_stat = "";
	/**
	 * 备注
	 */
	public String memo = "";
	/**
	 * 委托来源
	 */
	public String e_term_type = "";
	/**
	 *  委托代理机构
	 */
	public String e_branch_id = "";
	/**
	 * 委托操作员
	 */
	public String e_teller_id = "";
	/**
	 * 委托时间
	 */
	public String e_exch_time = "";
	/**
	 * 更新来源
	 */
	public String u_term_type = "";
	/**
	 * 更新操作员
	 */
	public String u_teller_id = "";
	/**
	 * 更新操作
	 */
	public String u_oper = "";
	/**
	 * 更新时间
	 */
	public String u_time = "";
}
