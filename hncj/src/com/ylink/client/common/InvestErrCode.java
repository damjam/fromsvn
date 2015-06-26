package com.ylink.client.common;

public class InvestErrCode {
	// 基本错误码
	public static String DT0000 = "DT0000"; // 交易成功
	public static String DT0002 = "DT0002"; // 处理异常或超时，请稍后再试
	public static String DT0003 = "DT0003"; // 记录不存在
	public static String DT0004 = "DT0004"; // 查询结果的数据量太大，请缩短查询的时间段！
	public static String DT0016 = "DT0016"; // 获取系统状态失败
	public static String DT0017 = "DT0017"; // 无法取得交易日系统状态信息
	public static String DT0018 = "DT0018"; // 获取交易日期失败
	public static String DT9997 = "DT9997"; // 接收主机报文超时
	public static String DT9999 = "DT9999"; // 处理异常或超时，请稍后再试

	// 账户相关错误码 DT1XXXX
	public static String DT1001 = "DT1001"; // 银行账号未签约！
	public static String DT1004 = "DT1004"; // 代理机构不能为空！
	public static String DT1005 = "DT1005"; // 客户级别不能为空！
	public static String DT1008 = "DT1008"; // 客户经理不能为空！
	public static String DT1014 = "DT1014"; // 银行卡号不能为空
	public static String DT1015 = "DT1015"; // 定投模式不能为空
	public static String DT1016 = "DT1016"; // 银行账户已存在
	public static String DT1017 = "DT1017"; // 定投模式不正确
	public static String DT1018 = "DT1018"; // 单号不存在
	public static String DT1019 = "DT1019"; // 此银行账号在定投系统存在多个用户！
	public static String DT1022 = "DT1022"; // 客户简称不能为空!
	public static String DT1024 = "DT1024"; // 证件号码不能为空!
	public static String DT1028 = "DT1028"; // 该账户已冻结
	public static String DT1030 = "DT1030"; // 该账户已挂失
	public static String DT1032 = "DT1032"; // 该账户已解约
	public static String DT1035 = "DT1035"; // 当日有交易委托,不允许预销户!
	public static String DT1036 = "DT1036"; // 有库存，不允许解约
	public static String DT1039 = "DT1039"; // 有未清算的仓储费，不允许解约
	public static String DT1046 = "DT1046"; // 无效的代理机构!
	public static String DT1050 = "DT1050"; // 该账户状态不正常!
	public static String DT1056 = "DT1056"; // 新卡号与原卡号证件类型不符
	public static String DT1057 = "DT1057"; // 无效的客户
	public static String DT1059 = "DT1059"; // 客户未复核
	public static String DT1062 = "DT1062"; // 是否顺延不能为空
	public static String DT1063 = "DT1063"; // 是否扣款至一元不能为空
	public static String DT1064 = "DT1064"; // 客户风险类型不能为空
	public static String DT1065 = "DT1065"; // 银行账号状态不正常!
	public static String DT1066 = "DT1066"; // 未配置贵宾卡客户级别
	public static String DT1067 = "DT1067"; // 未配置普通卡客户级别
	public static String DT1068 = "DT1068"; // 当前客户状态不允许修改信息
	public static String DT1069 = "DT1069"; // 有欠款不能解约
	public static String DT1070 = "DT1070"; // 有定投计划不能解约
	public static String DT1071 = "DT1071"; // 根据主机客户级别查不到对应的客户级别
	public static String DT1072 = "DT1072"; // 开户手续费没有配置
	public static String DT1073 = "DT1073"; // 无效的帐户类型
	public static String DT1074 = "DT1074"; // 重开户原证件号码不正确
	public static String DT1075 = "DT1075"; // 重开户原证件类型不正确
	public static String DT1076 = "DT1076"; // 重开户原帐户类型不正确
	public static String DT1077 = "DT1077"; // 该机构不能直接开户
	public static String DT1078 = "DT1078"; // 个人证件类型不正确
	public static String DT1079 = "DT1079"; // 开户手续费不正确
	public static String DT1080 = "DT1080"; // 法人证件类型不正确
	public static String DT1084 = "DT1084"; // 手机号不能为空
	public static String DT1086 = "DT1086"; // 证件号码不正确
	public static String DT1111 = "DT1111"; // 当日签约客户不允许变更卡号
	public static String DT1117 = "DT1117"; // 地区代码不能为空
	public static String DT1122 = "DT1122"; // 取得分段计息数据时发现日期设置错误
	public static String DT1130 = "DT1130"; // 证件类型与主机不符
	public static String DT1131 = "DT1131"; // 证件号码与主机不符
	public static String DT1132 = "DT1132"; // 用户名与主机不符
	public static String DT1133 = "DT1133"; // 新账号证件类型与旧账号不符
	public static String DT1134 = "DT1134"; // 新账号证件号码与旧账号不符
	public static String DT1135 = "DT1135"; // 新账号用户名与旧账号不符
	public static String DT1136 = "DT1136"; // 客户账户不是正常状态，不允许开户
	public static String DT1138 = "DT1138"; // 客户经理号不属于支行所在分行
	public static String DT1139 = "DT1139"; // 新账号证件类型与主机不符
	public static String DT1140 = "DT1140"; // 新账号证件号码与主机不符
	public static String DT1141 = "DT1141"; // 无效的证件类型
	public static String DT1142 = "DT1142"; // 提货人姓名不能重复
	public static String DT1143 = "DT1143"; // 新客户级别的账户类型与客户的账户类型不一致
	public static String DT1144 = "DT1144"; // SASB帐户不允许用于个人开户
	public static String DT1145 = "DT1145"; // 非对公账户不允许用于法人开户
	public static String DT1146 = "DT1146"; // 法人客户必须在开户行所开户
	public static String DT1147 = "DT1147"; // 清算时间不允许本交易
	public static String DT1148 = "DT1148"; // 结息出金已成功，但交易所解绑定失败，请重新尝试或联系系统管理员
	public static String DT1149 = "DT1149"; // 客户级别升级失败
	public static String DT1150 = "DT1150"; // 删除旧银行账号信息失败
	public static String DT1151 = "DT1151"; // 旧银行账号信息插入历史表失败
	public static String DT1152 = "DT1152"; // 定投计划不能重复设定
	public static String DT1153 = "DT1153"; // 定投类别不能为空
	public static String DT1154 = "DT1154"; // 定投模式不能为空
	public static String DT1155 = "DT1155"; // 定投金额/重量必须大于0
	public static String DT1156 = "DT1156"; // 定投周期不能为空
	public static String DT1157 = "DT1157"; // 期满条件不能为空
	public static String DT1158 = "DT1158"; // 期满条件错误
	public static String DT1159 = "DT1159"; // 期满值不能为空
	public static String DT1160 = "DT1160"; // 错误的定投周期
	public static String DT1161 = "DT1161"; // 输入日期非法
	public static String DT1162 = "DT1162"; // 交易日期不存在
	public static String DT1163 = "DT1163"; // 期满日期不能小于首次投资日期
	public static String DT1164 = "DT1164"; // 该账户状态为冻结状态
	public static String DT1165 = "DT1165"; // 错误的定投周期
	public static String DT1166 = "DT1166"; // 错误的定投模式
	public static String DT1167 = "DT1167"; // 错误的首次投资日期
	public static String DT1168 = "DT1168"; // 错误的期满日期
	public static String DT1169 = "DT1169"; // 不能修改终止的定投计划
	public static String DT1170 = "DT1170"; // 申请流水号不能为空
	public static String DT1171 = "DT1171"; // 该定投计划已撤销
	public static String DT1172 = "DT1172"; // 新银行帐号不存在
	public static String DT1173 = "DT1173"; // 新银行帐号状态不正常
	public static String DT1174 = "DT1174"; // 定投数额必须大于最小投资额!
	public static String DT1175 = "DT1175"; // 交易数额必须大于最小投资额!
	public static String DT1176 = "DT1176"; // 没有设置定投最小投资额!
	public static String DT1177 = "DT1177"; // 没有设置交易最小投资额!
	public static String DT1178 = "DT1178"; // 没有设置定投投资额步长!
	public static String DT1179 = "DT1179"; // 没有设置交易投资额步长!
	public static String DT1180 = "DT1180"; // 定投数额不满足投资额步长要求!
	public static String DT1181 = "DT1181"; // 交易数额不满足投资额步长要求!
	public static String DT1182 = "DT1182"; // 该账户已开通黄金定投业务，请对该业务解约后再销户!
	public static String DT1183 = "DT1183"; // 该账户无开通黄金定投业务!
	public static String DT1184 = "DT1184"; // 新证件号码与旧证件号码一样！

	// 资金相关错误码 DT2XXXX
	public static String DT2167 = "DT2167"; // 帐号类型不能为空
	public static String DT2168 = "DT2168"; // 帐号密码不能为空
	public static String DT2169 = "DT2169"; // 不正确的账号类型
	public static String DT2170 = "DT2170"; // 此账号不是有效的手输账号
	public static String DT2171 = "DT2171"; // 主机处理超时，请确认资金状况，如有疑问，请联系我行营业网点

	// 仓储相关错误码 DT3XXXX
	public static String DT3002 = "DT3002"; // 可提实物库存不足
	public static String DT3015 = "DT3015"; // 处理异常或超时，请稍后再试
	public static String DT3016 = "DT3016"; // 交易所处理异常
	public static String DT3101 = "DT3101"; // 提金品种不能为空
	public static String DT3107 = "DT3107"; // 提货人证件号码不能为空!
	public static String DT3108 = "DT3108"; // 提货密码不能为空!
	public static String DT3109 = "DT3109"; // 该品种不支持提货!
	public static String DT3110 = "DT3110"; // 提货城市不存在!
	public static String DT3111 = "DT3111"; // 提货网点不存在!
	public static String DT3112 = "DT3112"; // 未配置该品种的定投类别!
	public static String DT3113 = "DT3113"; // 库存信息不存在!
	public static String DT3115 = "DT3115"; // 本地提货流水号不存在!
	public static String DT3126 = "DT3126"; // 当前系统状态不允许撤销提货!
	public static String DT3135 = "DT3135"; // 提货重量不符合起重和步长要求!

	// 交易相关错误码 DT4XXXX
	public static String DT4005 = "DT4005"; // 委托单号不能为空
	public static String DT4011 = "DT4011"; // 委托数量非法!
	public static String DT4012 = "DT4012"; // 暂未开通该交易品种
	public static String DT4015 = "DT4015"; // 报单号不存在!
	public static String DT4111 = "DT4111"; // 可用库存不足
	public static String DT4112 = "DT4112"; // 更新库存表失败
	public static String DT4125 = "DT4125"; // 错误的买卖标志
	public static String DT4126 = "DT4126"; // 只能按重量卖出
	public static String DT4127 = "DT4127"; // 买卖标志不能为空
	public static String DT4128 = "DT4128"; // 无法取得交易日系统状态信息
	public static String DT4129 = "DT4129"; // 资金归集中或者资金归集已完成，不能撤单！
	public static String DT4130 = "DT4130"; // 提货业务暂未开通！
	public static String DT4131 = "DT4131"; // 计划提货日不能为空！
	public static String DT4132 = "DT4132"; // 提货城市不能为空！
	public static String DT4133 = "DT4133"; // 提货网点不能为空！
	public static String DT4134 = "DT4134"; // 是否二次提货不能为空!
	public static String DT4135 = "DT4135"; // 提货日期错误
	public static String DT4136 = "DT4136"; // 申请日期不能为空
	public static String DT4137 = "DT4137"; // 资金归集中或者资金归集已完成，不能撤销提货申请
	public static String DT4138 = "DT4138"; // 计划提货日不能为空
	public static String DT4139 = "DT4139"; // 原提货流水号不能为空
	public static String DT4140 = "DT4140"; // 原申请日期不能为空
	public static String DT4141 = "DT4141"; // 交易日期不存在
	public static String DT4142 = "DT4142"; // 定投业务暂未开通
	public static String DT4143 = "DT4143"; // 买卖委托业务暂未开通
	public static String DT4144 = "DT4144"; // 委托买业务暂未开通
	public static String DT4145 = "DT4145"; // 委托卖业务暂未开通
	public static String DT4146 = "DT4146"; // 本期提货总量已达上限
	public static String DT4147 = "DT4147"; // 本期买入额度已达上限
	public static String DT4148 = "DT4148"; // 本期卖出额度已达上限

	// 系统相关错误码 DT5XXXX
	public static String DT5001 = "DT5001"; // 系统正在启动过程中不允许操作
	public static String DT5014 = "DT5014"; // 银行行号不存在

	// 数据库相关错误码 DT6XXXX
	public static String DT6002 = "DT6002"; // 数据库处理错!

	// 程序异常相关错误码 DT7XXXX
	public static String DT7001 = "DT7001"; // 通讯异常

	// 查询相关错误码 DT8XXXX
	public static String DT8001 = "DT8001"; // 开始日期不能大于结束日期
	public static String DT8006 = "DT8006"; // 当前页码不能为空
	public static String DT8007 = "DT8007"; // 每页记录数不能为空
	public static String DT8008 = "DT8008"; // 查询条件错误
	public static String DT8009 = "DT8009"; // 城市代码不能为空
	public static String DT8010 = "DT8010"; // 提货月份不能为空
	public static String DT8011 = "DT8011"; // 流水号不能为空
	public static String DT8012 = "DT8012"; // 委托类型不能为空
}
