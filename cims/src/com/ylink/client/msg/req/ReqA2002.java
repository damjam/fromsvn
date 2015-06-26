package com.ylink.client.msg.req;

import com.ylink.gess.product.msg.ReqBase;

/**
 * 
 * 客户定投计划查询 请求报文
 * 
 */
public class ReqA2002 extends ReqBase {
	/**
	 * *银行账号
	 */
	public String account_no = "test";
	/**
	 * 申请流水号
	 */
	public String order_no = "aaa";
	/**
	 * 定投类别
	 */
	public String aip_type = "";
	/**
	 * 计划状态
	 */
	public String plan_stat = "";
	/**
	 * 开始日期
	 */
	public String start_date = "";
	/**
	 * 结束日期
	 */
	public String end_date = "";
}
