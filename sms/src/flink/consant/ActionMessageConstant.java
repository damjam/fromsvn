package flink.consant;

public class ActionMessageConstant {

	public static final String OPER_SUCCESS = "操作成功";

	public static final String OPER_FAIL = "操作失败";

	public static final String OPER_FAIL_EXIST = "操作失败,记录已经存在";

	public static final String OPER_FAIL_NO_USER = "用户不存在";

	public static final String OPER_FAIL_ERROR_PWD = "密码错误";

	public static final String OPER_FAIL_USER_LOCKED = "密码错误超过三次，半小时内不可登录";

	public static final String OPER_FAIL_HAS_USER = "用户已存在";

	public static final String OPER_FAIL_HAS_LOG_IN_ID = "登录名存在";

	public static final String OPER_FAIL_NO_LIMIT_GROUP = "没有要创建的权限组";

	public static final String OPER_FAIL_NO_LIMIT = "请选择权限点";
}
