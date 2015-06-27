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

	public MsgField(String fieldName, String fieldCode, String fieldType,
			int length) {
		this.fieldCode = fieldCode;
		this.fieldType = fieldType;
		this.length = length;
		ALL.put(fieldCode, this);
	}

	public MsgField(String fieldName, String fieldCode, String fieldType,
			int length, int deciLength) {
		this.fieldCode = fieldCode;
		this.fieldType = fieldType;
		this.length = length;
		this.deciLength = deciLength;
		ALL.put(fieldCode, this);
	}

	public MsgField() {
	}

	// 请求报文头
	public static MsgField h_gateway_header = new MsgField("网关报文头",
			"h_gateway_header", MsgUtil.FIELD_CHAR, 76);
	public static MsgField h_exch_code = new MsgField("交易码", "h_exch_code",
			MsgUtil.FIELD_CHAR, 5);
	public static MsgField h_cashier_id = new MsgField("出纳员号", "h_cashier_id",
			MsgUtil.FIELD_CHAR, 4);

	public static MsgField h_bk_tx_date = new MsgField("交易发生日期",
			"h_bk_tx_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField h_bk_tx_time = new MsgField("交易发生时间",
			"h_bk_tx_time", MsgUtil.FIELD_CHAR, 8);
	public static MsgField h_bk_serial_no = new MsgField("交易流水号",
			"h_bk_serial_no", MsgUtil.FIELD_CHAR, 22);
	public static MsgField h_exch_date = new MsgField("交易日", "h_exch_date",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField h_proxy = new MsgField("代理标志", "h_proxy",
			MsgUtil.FIELD_CHAR, 1);
	public static MsgField h_bank_no = new MsgField("联网行编号", "h_bank_no",
			MsgUtil.FIELD_CHAR, 4);
	public static MsgField h_branch_id = new MsgField("交易网点", "h_branch_id",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField h_teller_id = new MsgField("柜员", "h_teller_id",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField h_teller_id1 = new MsgField("授权柜员1", "h_teller_id1",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField h_teller_id2 = new MsgField("授权柜员2", "h_teller_id2",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField h_channel = new MsgField("交易渠道", "h_channel",
			MsgUtil.FIELD_CHAR, 1);
	public static MsgField h_term_id = new MsgField("终端号", "h_term_id",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField h_rsp_type = new MsgField("返回码类型", "h_rsp_type",
			MsgUtil.FIELD_CHAR, 1);
	public static MsgField h_req_type = new MsgField("请求类型", "h_req_type",
			MsgUtil.FIELD_CHAR, 1);
	public static MsgField h_auth_lvl = new MsgField("授权级别", "h_auth_lvl",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField h_start_num = new MsgField("当前第几页", "h_start_num",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField h_query_num = new MsgField("每页记录数", "h_query_num",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField back1 = new MsgField("备用1", "back1",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField back2 = new MsgField("备用2", "back2",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField back3 = new MsgField("备用3", "back3",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField back4 = new MsgField("备用4", "back4",
			MsgUtil.FIELD_CHAR, 20);
	// 返回报文头
	public static MsgField h_rsp_code = new MsgField("返回码", "h_rsp_code",
			MsgUtil.FIELD_CHAR, 7);
	public static MsgField h_rsp_msg = new MsgField("返回信息", "h_rsp_msg",
			MsgUtil.FIELD_CHAR, 100);
	public static MsgField h_sum_num = new MsgField("总页数", "h_sum_num",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField h_rsp_num = new MsgField("返回记录数", "h_rsp_num",
			MsgUtil.FIELD_CHAR, 8);

	public static MsgField account_no = new MsgField("银行帐号", "account_no",
			MsgUtil.FIELD_CHAR, 32);
	public static MsgField is_sign = new MsgField("是否签约", "is_sign",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField access_way = new MsgField("划转方向", "access_way",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField start_app_date = new MsgField("开始日期",
			"start_app_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField end_app_date = new MsgField("结束日期", "end_app_date",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField cust_abbr = new MsgField("客户简称", "cust_abbr",
			MsgUtil.FIELD_CHAR, 31);
	public static MsgField cert_type = new MsgField("证件类型", "cert_type",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField cert_num = new MsgField("证件号码", "cert_num",
			MsgUtil.FIELD_CHAR, 50);
	public static MsgField acct_stat = new MsgField("账户状态", "acct_stat",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField broker_id = new MsgField("客户经理", "broker_id",
			MsgUtil.FIELD_CHAR, 12);
	public static MsgField grade_id = new MsgField("客户级别", "grade_id",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField grade_name = new MsgField("客户级别名称", "grade_name",
			MsgUtil.FIELD_CHAR, 30);
	public static MsgField area_code = new MsgField("地区代码", "area_code",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField mobile_phone = new MsgField("手机", "mobile_phone",
			MsgUtil.FIELD_CHAR, 30);
	public static MsgField tel = new MsgField("电话号码", "tel",
			MsgUtil.FIELD_CHAR, 31);
	public static MsgField fax = new MsgField("传真", "fax", MsgUtil.FIELD_CHAR,
			31);
	public static MsgField addr = new MsgField("邮寄地址", "addr",
			MsgUtil.FIELD_CHAR, 255);
	public static MsgField zipcode = new MsgField("邮编", "zipcode",
			MsgUtil.FIELD_CHAR, 6);
	public static MsgField email = new MsgField("电子邮箱", "email",
			MsgUtil.FIELD_CHAR, 255);
	public static MsgField o_term_type = new MsgField("创建来源", "o_term_type",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField o_teller_id = new MsgField("创建操作员号", "o_teller_id",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField defer_flag = new MsgField("是否顺延", "defer_flag",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField charge_flag = new MsgField("是否扣款至一元", "charge_flag",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField risk_type = new MsgField("客户风险类型", "risk_type",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField memo = new MsgField("备注", "memo",
			MsgUtil.FIELD_CHAR, 30);
	public static MsgField old_account_no = new MsgField("原银行账号",
			"old_account_no", MsgUtil.FIELD_CHAR, 32);
	public static MsgField new_account_no = new MsgField("新银行账号",
			"new_account_no", MsgUtil.FIELD_CHAR, 32);

	public static MsgField branch_id = new MsgField("代理机构", "branch_id",
			MsgUtil.FIELD_CHAR, 32);
	public static MsgField take_month = new MsgField("提货月份", "take_month",
			MsgUtil.FIELD_CHAR, 6);
	public static MsgField take_date = new MsgField("提货日", "take_date",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField branch_name = new MsgField("网点名称", "branch_name",
			MsgUtil.FIELD_CHAR, 50);
	public static MsgField broker_name = new MsgField("客户经理名称", "broker_name",
			MsgUtil.FIELD_CHAR, 30);
	public static MsgField aip_mode = new MsgField("定投模式", "aip_mode",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField aip_type = new MsgField("定投类别", "aip_type",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField type_name = new MsgField("类别名称", "type_name",
			MsgUtil.FIELD_CHAR, 31);
	public static MsgField plan_stat = new MsgField("计划状态", "plan_stat",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField start_date = new MsgField("开始时间", "start_date",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField end_date = new MsgField("结束时间", "end_date",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField exch_time = new MsgField("交易日期时间", "exch_time",
			MsgUtil.FIELD_CHAR, 16);
	public static MsgField exch_dire = new MsgField("交易方向", "exch_dire",
			MsgUtil.FIELD_CHAR, 31);
	public static MsgField deduct_type = new MsgField("交易类型", "deduct_type",
			MsgUtil.FIELD_CHAR, 31);
	public static MsgField prod_code = new MsgField("交易品种", "prod_code",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField prod_name = new MsgField("品种名称", "prod_name",
			MsgUtil.FIELD_CHAR, 31);
	public static MsgField entr_source = new MsgField("委托来源", "entr_source",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField charge_stat = new MsgField("处理状态", "charge_stat",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField origin_order_no = new MsgField("原委托单号",
			"origin_order_no", MsgUtil.FIELD_CHAR, 20);
	public static MsgField deal_stat = new MsgField("成交状态", "deal_stat",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField e_term_type = new MsgField("委托来源", "e_term_type",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField entr_stat = new MsgField("委托状态", "entr_stat",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField e_branch_id = new MsgField("委托代理机构", "e_branch_id",
			MsgUtil.FIELD_CHAR, 50);
	public static MsgField e_teller_id = new MsgField("委托操作员", "e_teller_id",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField e_exch_time = new MsgField("委托时间", "e_exch_time",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField u_term_type = new MsgField("更新来源", "u_term_type",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField u_teller_id = new MsgField("更新操作员", "u_teller_id",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField u_oper = new MsgField("更新操作", "u_oper",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField u_time = new MsgField("更新时间", "u_time",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField c_term_type = new MsgField("撤销来源", "c_term_type",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField c_teller_id = new MsgField("撤销操作员", "c_teller_id",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField c_exch_time = new MsgField("撤销时间", "c_exch_time",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField aip_amount = new MsgField("定投金额/重量", "aip_amount",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField aip_periods = new MsgField("定投周期", "aip_periods",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField aip_period = new MsgField("定投周期", "aip_period",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField chn_aip_date = new MsgField("指定日期", "chn_aip_date",
			MsgUtil.FIELD_CHAR, 100);
	public static MsgField is_delay = new MsgField("是否无限延期", "is_delay",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField is_effect = new MsgField("是否立即生效", "is_effect",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField first_aip_date = new MsgField("首次投资日期",
			"first_aip_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField invest_time = new MsgField("期满条件", "invest_time",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField invest_time_value = new MsgField("期满值",
			"invest_time_value", MsgUtil.FIELD_CHAR, 18);
	public static MsgField order_no = new MsgField("申请流水号", "order_no",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField entr_date = new MsgField("委托日期", "entr_date",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField exch_date = new MsgField("交易日期", "exch_date",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField prod_type = new MsgField("交易品种", "prod_type",
			MsgUtil.FIELD_CHAR, 31);
	public static MsgField b_contacter = new MsgField("网点联系人", "b_contacter",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField b_addr = new MsgField("网点地址", "b_addr",
			MsgUtil.FIELD_CHAR, 200);
	public static MsgField is_stop = new MsgField("是否暂停", "is_stop",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField affect_time = new MsgField("扣款日期", "affect_time",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField multiple = new MsgField("倍数", "multiple",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField rate = new MsgField("扣资金比率", "rate",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField liqu_time = new MsgField("清算日期", "liqu_time",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField entr_amount = new MsgField("委托数量", "entr_amount",
			MsgUtil.FIELD_CHAR, 18);
	public static MsgField bs_flag = new MsgField("买卖标志", "bs_flag",
			MsgUtil.FIELD_CHAR, 2);
	public static MsgField app_date = new MsgField("申请日期", "app_date",
			MsgUtil.FIELD_CHAR, 4);
	public static MsgField acct_no = new MsgField("客户号", "acct_no",
			MsgUtil.FIELD_CHAR, 15);
	public static MsgField o_date = new MsgField("开户日期", "o_date",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField m_date = new MsgField("修改日期", "m_date",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField m_term_type = new MsgField("修改来源", "m_term_type",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField m_teller_id = new MsgField("修改操作员号", "m_teller_id",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField plan_exch_date = new MsgField("计划提货日",
			"plan_exch_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField variety_id = new MsgField("品种代码", "variety_id",
			MsgUtil.FIELD_CHAR, 5);
	public static MsgField take_weight = new MsgField("提货重量", "take_weight",
			MsgUtil.FIELD_INTEGER, 15);
	public static MsgField city_code = new MsgField("提货城市", "city_code",
			MsgUtil.FIELD_CHAR, 50);
	public static MsgField city_name = new MsgField("城市名称", "city_name",
			MsgUtil.FIELD_CHAR, 50);
	public static MsgField stor_id = new MsgField("提货仓库", "stor_id",
			MsgUtil.FIELD_CHAR, 60);
	public static MsgField contacter = new MsgField("提货联系人", "contacter",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField local_serial_no = new MsgField("提货流水号",
			"local_serial_no", MsgUtil.FIELD_CHAR, 16);
	public static MsgField take_address = new MsgField("提货地址", "take_address",
			MsgUtil.FIELD_CHAR, 200);
	public static MsgField b_contactor = new MsgField("提货联系人", "b_contactor",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField serial_no = new MsgField("本地流水号", "serial_no",
			MsgUtil.FIELD_CHAR, 16);
	public static MsgField sheet_no = new MsgField("提货单号", "sheet_no",
			MsgUtil.FIELD_CHAR, 14);
	public static MsgField variety_name = new MsgField("品种名称", "variety_name",
			MsgUtil.FIELD_CHAR, 40);
	public static MsgField min_pickup = new MsgField("最小提货数量", "min_pickup",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField pickup_base = new MsgField("提货步长", "pickup_base",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField cust_name = new MsgField("客户名称", "cust_name",
			MsgUtil.FIELD_CHAR, 31);
	public static MsgField stat = new MsgField("提货状态", "stat",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField trade_type = new MsgField("交易方式", "trade_type",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField entr_flow_serial_no = new MsgField("委托交易流水号",
			"entr_flow_serial_no", MsgUtil.FIELD_CHAR, 20);
	public static MsgField out_account_no = new MsgField("出金银行账号",
			"out_account_no", MsgUtil.FIELD_CHAR, 32);
	public static MsgField in_account_no = new MsgField("入金银行账号",
			"in_account_no", MsgUtil.FIELD_CHAR, 32);
	public static MsgField send_stat = new MsgField("发送状态", "send_stat",
			MsgUtil.FIELD_CHAR, 10);
	public static MsgField in_account_flag = new MsgField("是否已入账",
			"in_account_flag", MsgUtil.FIELD_CHAR, 20);
	public static MsgField remark = new MsgField("备注", "remark",
			MsgUtil.FIELD_CHAR, 255);
	public static MsgField bk_plat_date = new MsgField("银行日期", "bk_plat_date",
			MsgUtil.FIELD_CHAR, 8);
	public static MsgField bk_seq_no = new MsgField("银行流水号", "bk_seq_no",
			MsgUtil.FIELD_CHAR, 30);
	public static MsgField bk_rsp_code = new MsgField("银行响应代码", "bk_rsp_code",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField bk_rsp_msg = new MsgField("银行响应消息", "bk_rsp_msg",
			MsgUtil.FIELD_CHAR, 200);
	public static MsgField bk_check_stat = new MsgField("银行对账状态",
			"bk_check_stat", MsgUtil.FIELD_CHAR, 10);
	public static MsgField take_man = new MsgField("提货人姓名", "take_man",
			MsgUtil.FIELD_CHAR, 60);
	public static MsgField take_end_date = new MsgField("提货结束日期",
			"take_end_date", MsgUtil.FIELD_CHAR, 8);
	public static MsgField rsp_msg = new MsgField("处理信息", "rsp_msg",
			MsgUtil.FIELD_CHAR, 60);
	public static MsgField aip_no = new MsgField("定投账号", "aip_no",
			MsgUtil.FIELD_CHAR, 15);
	public static MsgField change_type = new MsgField("变动类型", "change_type",
			MsgUtil.FIELD_CHAR, 20);
	public static MsgField exch_bal = new MsgField("发生金额", "exch_bal",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField aip_bal = new MsgField("定投金额 ", "aip_bal",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField exch_weight = new MsgField("变化值 ", "exch_weight",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField aip_amt = new MsgField("定投重量", "aip_amt",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField match_bal = new MsgField("成交金额", "match_bal",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField match_amt = new MsgField("成交重量", "match_amt",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField real_exch_bal = new MsgField("实扣金额",
			"real_exch_bal", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField pro_fee = new MsgField("交易手续费", "pro_fee",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField pub_price = new MsgField("定投结算价", "pub_price",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField deal_rate = new MsgField("成交率(%)", "deal_rate",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField trans_amt = new MsgField("可用库存", "trans_amt",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField avg_price = new MsgField("可用库存均价", "avg_price",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField phy_amt = new MsgField("可提货库存", "phy_amt",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField dedt = new MsgField("欠费", "dedt",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField frozen_fund = new MsgField("冻结资金", "frozen_fund",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField debt_bal = new MsgField("欠款", "debt_bal",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField frozen_amt = new MsgField("当日冻结库存重量", "frozen_amt",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField buy_amt = new MsgField("买入重量", "buy_amt",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField sell_amt = new MsgField("卖出重量", "sell_amt",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_plan_weight = new MsgField("累计积存重量",
			"curr_plan_weight", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_buy_weight = new MsgField("累计主动定投重量",
			"curr_buy_weight", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_sell_weight = new MsgField("累计现金赎回重量",
			"curr_sell_weight", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_take_weight = new MsgField("累计实物赎回重量",
			"curr_take_weight", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_amt = new MsgField("当前余额", "curr_amt",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField curr_bal = new MsgField("当前余额", "curr_bal",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField curr_can_use = new MsgField("可用余额", "curr_can_use",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField sell_froz_amt = new MsgField("卖出冻结数量",
			"sell_froz_amt", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField trade_app_froz = new MsgField("提货冻结数量",
			"trade_app_froz", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField spot_amt = new MsgField("库存余额", "spot_amt",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField spot_can_get = new MsgField("可提库存", "spot_can_get",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField spot_app_froz = new MsgField("提货冻结数量",
			"spot_app_froz", MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField total_amt = new MsgField("总库存", "total_amt",
			MsgUtil.FIELD_DOUBLE, 18, 4);
	public static MsgField account_surplus = new MsgField("账户盈亏",
			"account_surplus", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField cur_year_acct_surplus = new MsgField("本年账户盈亏",
			"cur_year_acct_surplus", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField settle_price = new MsgField("当日定投结算价",
			"settle_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField gold_virtual_bal = new MsgField("黄金账户账面价值",
			"gold_virtual_bal", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField gold_real_bal = new MsgField("实际定投金额",
			"gold_real_bal", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField au100_take_fare = new MsgField("Au100g提取费",
			"au100_take_fare", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField au9999_take_fare = new MsgField("Au99.99提取费",
			"au9999_take_fare", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField take_margin = new MsgField("提货准备金", "take_margin",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField take_fare = new MsgField("实际提货手续费", "take_fare",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField re_margin = new MsgField("返还客户准备金", "re_margin",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField open_price = new MsgField("开盘价", "open_price",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField high_price = new MsgField("最高价", "high_price",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField low_price = new MsgField("最低价", "low_price",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField aip_settle_price = new MsgField("定投结算价",
			"aip_settle_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField last_close_price = new MsgField("上一交易日收盘价",
			"last_close_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField new_price = new MsgField("最新价", "new_price",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField max_price = new MsgField("最高价", "max_price",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField min_price = new MsgField("最低价", "min_price",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField close_price = new MsgField("收盘价", "close_price",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField exch_fare = new MsgField("客户交易手续费", "exch_fare",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField acct_manage_fee = new MsgField("账户管理费",
			"acct_manage_fee", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField trans_fee = new MsgField("运保费", "trans_fee",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField punish_interest = new MsgField("罚息",
			"punish_interest", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField last_settle_price = new MsgField("上日定投结算价",
			"last_settle_price", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField diff_bal = new MsgField("溢短费", "diff_bal",
			MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField process_cost = new MsgField("品牌金条加工费",
			"process_cost", MsgUtil.FIELD_DOUBLE, 18, 2);
	public static MsgField deal_time = new MsgField("处理次数", "deal_time",
			MsgUtil.FIELD_INTEGER, 6);

	public static int getStartIndex(MsgField[] fields, MsgField field) {
		int index = 0;
		for (int i = 0; i < fields.length; i++) {
			if (!StringUtils.equals(field.getFieldCode(),
					fields[i].getFieldCode())) {
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
