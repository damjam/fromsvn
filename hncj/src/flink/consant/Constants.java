package flink.consant;

public abstract class Constants {
	// 用户全部权限点.
	public static final String USER_PRIVILEGE = "USER_PRIVILEGE";

	// 权限资源.
	public static final String USER_PRIVILEGE_RES = "USER_PRIVILEGE_RES";

	// 用户权限树：包含所有的叶子节点
	public static final String USER_MENU = "USER_MENU";

	// 用户菜单列表：显示为左边菜单使用，不包含叶子节点。
	public static final String USER_MENU_LIST = "USER_MENU_LIST";
	public static final String SESSION_USER = "SESSION_USER";
	public static final String PAGE_SIZE = "pageSize";
	public static final int DEFAULT_PAGE_SIZE = 20;

	// 客户id
	public static final String SESSION_CUSTOM_ID = "SESSION_CUSTOM_ID";

	public static final String SESSION_CUSTOM = "SESSION_CUSTOM";

	public static final String INVEST_ACCT_NOS = "INVEST_ACCT_NOS";

	public static final String BRANCH_TYPE = "BRANCH_TYPE";

	public static final String BRANCH_TAG = "tag";
	
	public static final String BRANCH_NO = "BRANCH_NO";
	
	// ********************************************************************
	public static final String OPER_INFO = "msg";
	public static final String SAVE_SUCCESS = "保存成功";
	public static final String SAVE_FAIL = "保存失败";
	public static final String UPDATE_SUCCESS = "修改成功";
	public static final String UPDATE_FAIL = "修改失败";
	public static final String DELETE_SUCCESS = "删除成功";
	public static final String DELETE_FAIL = "删除失败";
	// ********************************************************************

	// ******************************************************************
	// 系统日志类型
	public static final String LOG_SYS_INFO = "I"; // 信息
	public static final String LOG_SYS_WARNING = "W"; // 警告
	public static final String LOG_SYS_ERROR = "E"; // 错误
	public static final String LOG_SYS_TYPE = "I,W,E";

	// 系统日志类别
	public static final String LOG_SYS_S = "S"; // 管理员操作产生
	public static final String LOG_SYS_U = "U"; // 业务人员产生
	public static final String LOG_SYS_CLASS = "S,U";

	// 系统日志状态
	public static final String LOG_SYS_STAT_VIEW = "00";
	public static final String LOG_SYS_STAT_NO_VIEW = "11";

	// 用户日志类型
	public static final String LOG_USER_S = "S"; // 查询
	public static final String LOG_USER_A = "A"; // 新增
	public static final String LOG_USER_U = "U"; // 修改
	public static final String LOG_USER_D = "D"; // 删除
	public static final String LOG_USER_C = "C"; // 审核
	public static final String LOG_USER_O = "O"; // 其他
	public static final String LOG_USER_TYPE = "S,A,U,D,C,O";

	public static final String PRIVILEGE_RESOURCE_ID = "PRIVILEGE_RESOURCE_ID";
	public static final String ROLE_INFO_ID = "ROLE_INFO_ID";
	public static final String USER_LOG_ID = "USER_LOG_ID";
	public static final String NOTICE_MSG_ID = "NOTICE_MSG_ID";
	public static final String NOTICE_MSG_RECORD_ID = "NOTICE_MSG_RECORD_ID";
	public static final String PUSH_PLAN_ID = "PUSH_PLAN_ID";
	public static final String PUSH_RECORD_ID = "PUSH_RECORD_ID";
	public static final String CUST_INFO_ID = "CUST_INFO_ID";
	public static final String USER_INFO_ID = "USER_INFO_ID";
	public static final String ACCOUNT_JOURNAL_ID = "ACCOUNT_JOURNAL_ID";
	public static final String WATER_RECORD_ID = "WATER_RECORD_ID";
	public static final String OWNER_INFO_ID = "OWNER_INFO_ID";
	public static final String HOUSE_INFO_ID = "HOUSE_INFO_ID";
	public static final String BILL_ID = "BILL_ID";
	public static final String LIMIT_GROUP_INFO_ID = "LIMIT_GROUP_INFO_ID";
	public static final String ACCOUNT_DETAIL_ID = "ACCOUNT_DETAIL_ID";
	public static final String CONTACT_ID = "CONTACT_ID";
	public static final String MERCHANT_INFO_ID = "MERCHANT_INFO_ID";
	public static final String PARKING_INFO_ID = "PARKING_INFO_ID";
	public static final String CAR_INFO_ID = "CAR_INFO_ID";
	public static final String TIMER_ID = "TIMER_ID";
	public static final String BILL_TRACK_ID = "BILL_TRACK_ID";
	public static final String TIMER_DO_ID = "TIMER_DO_ID";
	public static final String CHARGE_PARAM_ID = "CHARGE_PARAM_ID";
	public static final String CHARGE_ITEM_ID = "CHARGE_ITEM_ID";
	public static final String SYSUSER = "system";
	public static final String[] RENT_BUILDINGS = { "" };
	public static final String INNER_ACCTID = "00000000";
	public static final String COM_NAME = "漯河昌建物业管理有限公司东外滩客服中心";
	// 根节点权限编号.
	public static final String ROOT_PRIVILEGE_CODE = "00";

	public static final String YES = "Y";

	public static final String NO = "N";

	public static final String LEFT = "l";

	public static final String RIGHT = "r";

	public static final String CENTER = "c";
	/** 开户成功的短信内容 */
	public static final String OPEN_ACC_MOBILE_MSG = "尊敬的客户，您已成功办理由深圳金融电子结算中心推出的黄金定投业务，定投账号为{?}。您可以通过网上银行和深圳金融电子结算中心官网管理您的定投计划，查询详细的账户信息。";
	/** 销户成功的短信内容 */
	public static final String CANCEL_ACC_MOBILE_MSG = "尊敬的客户，您已成功取消由深圳金融电子结算中心推出的黄金定投业务。";
	/** 积存计划完成的短信内容 */
	public static final String DEPOSIT_MOBILE_MSG = "尊敬的客户，您于{?}年{?}月{?}日成功积存{?}克黄金，扣款金额{?}元。目前，您的黄金账户共有黄金{?}克，资金账户余额{?}元。";
	/** 实物赎回完成的短信内容 */
	public static final String TAKEOUT_MOBILE_MSG = "尊敬的客户，您于{?}年{?}月{?}日成功赎回{?}克黄金（实物赎回），扣款金额{?}元。目前，您的黄金账户共有黄金{?}克，资金账户余额{?}元。请您于约定的提货日携带您的身份证件到指定网点办理提货手续。";
	/** 现金赎回完成的短信内容 */
	public static final String SOLD_MOBILE_MSG = "尊敬的客户，您于{?}年{?}月{?}日成功赎回{?}克黄金（现金赎回），扣款手续费后的返还金额为{?}元。目前，您的黄金账户共有黄金{?}克，资金账户余额{?}元。现金赎回的资金将于五个工作日内返还至您的银行卡。";
	/** 物业费催款通知 */
	public static final String SERVICE_BILL_DUE_MSG = "尊敬的业主，你的物业费将于{?}到期，为了更好的为您服务，请您于近期缴纳物业费";
}
