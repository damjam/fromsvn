package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
/**
 * 
 *	接口错误码
 */
public class InvestErrCodeType extends AbstractType {
	public static Map<String, InvestErrCodeType> ALL = new LinkedHashMap<String, InvestErrCodeType>();

	//基本错误码
	public static final InvestErrCodeType DT0000 = new InvestErrCodeType("交易成功", "DT0000");
	public static final InvestErrCodeType DT0002 = new InvestErrCodeType("处理异常或超时，请稍后再试", "DT0002");
	public static final InvestErrCodeType DT0003 = new InvestErrCodeType("记录不存在", "DT0003");
	public static final InvestErrCodeType DT0004 = new InvestErrCodeType("查询结果的数据量太大，请缩短查询的时间段", "DT0004");
	public static final InvestErrCodeType DT0016 = new InvestErrCodeType("获取系统状态失败", "DT0016");
	public static final InvestErrCodeType DT0017 = new InvestErrCodeType("无法取得交易日系统状态信息", "DT0017");
	public static final InvestErrCodeType DT0018 = new InvestErrCodeType("获取交易日期失败", "DT0018");
	public static final InvestErrCodeType DT9997 = new InvestErrCodeType("接收主机报文超时", "DT9997");
	public static final InvestErrCodeType DT9999 = new InvestErrCodeType("处理异常或超时，请稍后再试", "DT9999");
	
	//账户相关错误码 DT1XXXX
	public static final InvestErrCodeType DT1001 = new InvestErrCodeType("银行账号未签约", "DT1001");
	public static final InvestErrCodeType DT1004 = new InvestErrCodeType("代理机构不能为空", "DT1004");
	public static final InvestErrCodeType DT1005 = new InvestErrCodeType("客户级别不能为空", "DT1005");
	public static final InvestErrCodeType DT1008 = new InvestErrCodeType("客户经理不能为空", "DT1008");
	public static final InvestErrCodeType DT1014 = new InvestErrCodeType("银行卡号不能为空", "DT1014");
	public static final InvestErrCodeType DT1015 = new InvestErrCodeType("定投模式不能为空", "DT1015");
	public static final InvestErrCodeType DT1016 = new InvestErrCodeType("银行账户已存在", "DT1016");
	public static final InvestErrCodeType DT1017 = new InvestErrCodeType("定投模式不正确", "DT1017");
	public static final InvestErrCodeType DT1018 = new InvestErrCodeType("单号不存在", "DT1018");
	public static final InvestErrCodeType DT1019 = new InvestErrCodeType("此银行账号在定投系统存在多个用户", "DT1019");
	public static final InvestErrCodeType DT1022 = new InvestErrCodeType("客户简称不能为空", "DT1022");
	public static final InvestErrCodeType DT1024 = new InvestErrCodeType("证件号码不能为空", "DT1024");
	public static final InvestErrCodeType DT1028 = new InvestErrCodeType("该账户已冻结", "DT1028");
	public static final InvestErrCodeType DT1030 = new InvestErrCodeType("该账户已挂失", "DT1030");
	public static final InvestErrCodeType DT1032 = new InvestErrCodeType("该账户已解约", "DT1032");
	public static final InvestErrCodeType DT1035 = new InvestErrCodeType("当日有交易委托,不允许预销户", "DT1035");
	public static final InvestErrCodeType DT1036 = new InvestErrCodeType("有库存，不允许解约", "DT1036");
	public static final InvestErrCodeType DT1039 = new InvestErrCodeType("有未清算的仓储费，不允许解约", "DT1039");
	public static final InvestErrCodeType DT1046 = new InvestErrCodeType("无效的代理机构", "DT1046");
	public static final InvestErrCodeType DT1050 = new InvestErrCodeType("该账户状态不正常", "DT1050");
	public static final InvestErrCodeType DT1056 = new InvestErrCodeType("新卡号与原卡号证件类型不符", "DT1056");
	public static final InvestErrCodeType DT1057 = new InvestErrCodeType("无效的客户", "DT1057");
	public static final InvestErrCodeType DT1059 = new InvestErrCodeType("客户未复核", "DT1059");
	public static final InvestErrCodeType DT1062 = new InvestErrCodeType("是否顺延不能为空", "DT1062");
	public static final InvestErrCodeType DT1063 = new InvestErrCodeType("是否扣款至一元不能为空", "DT1063");
	public static final InvestErrCodeType DT1064 = new InvestErrCodeType("客户风险类型不能为空", "DT1064");
	public static final InvestErrCodeType DT1065 = new InvestErrCodeType("银行账号状态不正常", "DT1065");
	public static final InvestErrCodeType DT1066 = new InvestErrCodeType("未配置贵宾卡客户级别", "DT1066");
	public static final InvestErrCodeType DT1067 = new InvestErrCodeType("未配置普通卡客户级别", "DT1067");
	public static final InvestErrCodeType DT1068 = new InvestErrCodeType("当前客户状态不允许修改信息", "DT1068");
	public static final InvestErrCodeType DT1069 = new InvestErrCodeType("有欠款不能解约", "DT1069");
	public static final InvestErrCodeType DT1070 = new InvestErrCodeType("有定投计划不能解约", "DT1070");
	public static final InvestErrCodeType DT1071 = new InvestErrCodeType("根据主机客户级别查不到对应的客户级别", "DT1071");
	public static final InvestErrCodeType DT1072 = new InvestErrCodeType("开户手续费没有配置", "DT1072");
	public static final InvestErrCodeType DT1073 = new InvestErrCodeType("无效的帐户类型", "DT1073");
	public static final InvestErrCodeType DT1074 = new InvestErrCodeType("重开户原证件号码不正确", "DT1074");
	public static final InvestErrCodeType DT1075 = new InvestErrCodeType("重开户原证件类型不正确", "DT1075");
	public static final InvestErrCodeType DT1076 = new InvestErrCodeType("重开户原帐户类型不正确", "DT1076");
	public static final InvestErrCodeType DT1077 = new InvestErrCodeType("该机构不能直接开户", "DT1077");
	public static final InvestErrCodeType DT1078 = new InvestErrCodeType("个人证件类型不正确", "DT1078");
	public static final InvestErrCodeType DT1079 = new InvestErrCodeType("开户手续费不正确", "DT1079");
	public static final InvestErrCodeType DT1080 = new InvestErrCodeType("法人证件类型不正确", "DT1080");
	public static final InvestErrCodeType DT1084 = new InvestErrCodeType("手机号不能为空", "DT1084");
	public static final InvestErrCodeType DT1086 = new InvestErrCodeType("证件号码不正确", "DT1086");
	public static final InvestErrCodeType DT1111 = new InvestErrCodeType("当日签约客户不允许变更卡号", "DT1111");
	public static final InvestErrCodeType DT1117 = new InvestErrCodeType("地区代码不能为空", "DT1117");
	public static final InvestErrCodeType DT1122 = new InvestErrCodeType("取得分段计息数据时发现日期设置错误", "DT1122");
	public static final InvestErrCodeType DT1130 = new InvestErrCodeType("证件类型与主机不符", "DT1130");
	public static final InvestErrCodeType DT1131 = new InvestErrCodeType("证件号码与主机不符", "DT1131");
	public static final InvestErrCodeType DT1132 = new InvestErrCodeType("用户名与主机不符", "DT1132");
	public static final InvestErrCodeType DT1133 = new InvestErrCodeType("新账号证件类型与旧账号不符", "DT1133");
	public static final InvestErrCodeType DT1134 = new InvestErrCodeType("新账号证件号码与旧账号不符", "DT1134");
	public static final InvestErrCodeType DT1135 = new InvestErrCodeType("新账号用户名与旧账号不符", "DT1135");
	public static final InvestErrCodeType DT1136 = new InvestErrCodeType("客户账户不是正常状态，不允许开户", "DT1136");
	public static final InvestErrCodeType DT1138 = new InvestErrCodeType("客户经理号不属于支行所在分行", "DT1138");
	public static final InvestErrCodeType DT1139 = new InvestErrCodeType("新账号证件类型与主机不符", "DT1139");
	public static final InvestErrCodeType DT1140 = new InvestErrCodeType("新账号证件号码与主机不符", "DT1140");
	public static final InvestErrCodeType DT1141 = new InvestErrCodeType("无效的证件类型", "DT1141");
	public static final InvestErrCodeType DT1142 = new InvestErrCodeType("提货人姓名不能重复", "DT1142");
	public static final InvestErrCodeType DT1143 = new InvestErrCodeType("新客户级别的账户类型与客户的账户类型不一致", "DT1143");
	public static final InvestErrCodeType DT1144 = new InvestErrCodeType("SASB帐户不允许用于个人开户", "DT1144");
	public static final InvestErrCodeType DT1145 = new InvestErrCodeType("非对公账户不允许用于法人开户", "DT1145");
	public static final InvestErrCodeType DT1146 = new InvestErrCodeType("法人客户必须在开户行所开户", "DT1146");
	public static final InvestErrCodeType DT1147 = new InvestErrCodeType("清算时间不允许本交易", "DT1147");
	public static final InvestErrCodeType DT1148 = new InvestErrCodeType("结息出金已成功，但交易所解绑定失败，请重新尝试或联系系统管理员", "DT1148");
	public static final InvestErrCodeType DT1149 = new InvestErrCodeType("客户级别升级失败", "DT1149");
	public static final InvestErrCodeType DT1150 = new InvestErrCodeType("删除旧银行账号信息失败", "DT1150");
	public static final InvestErrCodeType DT1151 = new InvestErrCodeType("旧银行账号信息插入历史表失败", "DT1151");
	public static final InvestErrCodeType DT1152 = new InvestErrCodeType("定投计划不能重复设定", "DT1152");
	public static final InvestErrCodeType DT1153 = new InvestErrCodeType("定投类别不能为空", "DT1153");
	public static final InvestErrCodeType DT1154 = new InvestErrCodeType("定投模式不能为空", "DT1154");
	public static final InvestErrCodeType DT1155 = new InvestErrCodeType("定投金额/重量必须大于0", "DT1155");
	public static final InvestErrCodeType DT1156 = new InvestErrCodeType("定投周期不能为空", "DT1156");
	public static final InvestErrCodeType DT1157 = new InvestErrCodeType("期满条件不能为空", "DT1157");
	public static final InvestErrCodeType DT1158 = new InvestErrCodeType("期满条件错误", "DT1158");
	public static final InvestErrCodeType DT1159 = new InvestErrCodeType("期满值不能为空", "DT1159");
	public static final InvestErrCodeType DT1160 = new InvestErrCodeType("错误的定投周期", "DT1160");
	public static final InvestErrCodeType DT1161 = new InvestErrCodeType("输入日期非法", "DT1161");
	public static final InvestErrCodeType DT1162 = new InvestErrCodeType("交易日期不存在", "DT1162");
	public static final InvestErrCodeType DT1163 = new InvestErrCodeType("期满日期不能小于首次投资日期", "DT1163");
	public static final InvestErrCodeType DT1164 = new InvestErrCodeType("该账户状态为冻结状态", "DT1164");
	public static final InvestErrCodeType DT1165 = new InvestErrCodeType("错误的定投周期", "DT1165");
	public static final InvestErrCodeType DT1166 = new InvestErrCodeType("错误的定投模式", "DT1166");
	public static final InvestErrCodeType DT1167 = new InvestErrCodeType("错误的首次投资日期", "DT1167");
	public static final InvestErrCodeType DT1168 = new InvestErrCodeType("错误的期满日期", "DT1168");
	public static final InvestErrCodeType DT1169 = new InvestErrCodeType("不能修改终止的定投计划", "DT1169");
	public static final InvestErrCodeType DT1170 = new InvestErrCodeType("申请流水号不能为空", "DT1170");
	public static final InvestErrCodeType DT1171 = new InvestErrCodeType("该定投计划已撤销", "DT1171");
	public static final InvestErrCodeType DT1172 = new InvestErrCodeType("新银行帐号不存在", "DT1172");
	public static final InvestErrCodeType DT1173 = new InvestErrCodeType("新银行帐号状态不正常", "DT1173");
	public static final InvestErrCodeType DT1174 = new InvestErrCodeType("定投数额必须大于最小投资额", "DT1174");
	public static final InvestErrCodeType DT1175 = new InvestErrCodeType("交易数额必须大于最小投资额", "DT1175");
	public static final InvestErrCodeType DT1176 = new InvestErrCodeType("没有设置定投最小投资额", "DT1176");
	public static final InvestErrCodeType DT1177 = new InvestErrCodeType("没有设置交易最小投资额", "DT1177");
	public static final InvestErrCodeType DT1178 = new InvestErrCodeType("没有设置定投投资额步长", "DT1178");
	public static final InvestErrCodeType DT1179 = new InvestErrCodeType("没有设置交易投资额步长", "DT1179");
	public static final InvestErrCodeType DT1180 = new InvestErrCodeType("定投数额不满足投资额步长要求", "DT1180");
	public static final InvestErrCodeType DT1181 = new InvestErrCodeType("交易数额不满足投资额步长要求", "DT1181");
	public static final InvestErrCodeType DT1182 = new InvestErrCodeType("该账户已开通黄金定投业务，请对该业务解约后再销户", "DT1182");
	public static final InvestErrCodeType DT1183 = new InvestErrCodeType("该账户无开通黄金定投业务", "DT1183");
	public static final InvestErrCodeType DT1184 = new InvestErrCodeType("新证件号码与旧证件号码一样", "DT1184");
	
	//资金相关错误码 DT2XXXX
	public static final InvestErrCodeType DT2167 = new InvestErrCodeType("帐号类型不能为空", "DT2167");
	public static final InvestErrCodeType DT2168 = new InvestErrCodeType("帐号密码不能为空", "DT2168");
	public static final InvestErrCodeType DT2169 = new InvestErrCodeType("不正确的账号类型", "DT2169");
	public static final InvestErrCodeType DT2170 = new InvestErrCodeType("此账号不是有效的手输账号", "DT2170");
	public static final InvestErrCodeType DT2171 = new InvestErrCodeType("主机处理超时，请确认资金状况，如有疑问，请联系我行营业网点", "DT2171");
	
	//仓储相关错误码 DT3XXXX
	public static final InvestErrCodeType DT3002 = new InvestErrCodeType("可提实物库存不足", "DT3002");
	public static final InvestErrCodeType DT3015 = new InvestErrCodeType("处理异常或超时，请稍后再试", "DT3015");
	public static final InvestErrCodeType DT3016 = new InvestErrCodeType("交易所处理异常", "DT3016");
	public static final InvestErrCodeType DT3101 = new InvestErrCodeType("提金品种不能为空", "DT3101");
	public static final InvestErrCodeType DT3107 = new InvestErrCodeType("提货人证件号码不能为空", "DT3107");
	public static final InvestErrCodeType DT3108 = new InvestErrCodeType("提货密码不能为空", "DT3108");
	public static final InvestErrCodeType DT3109 = new InvestErrCodeType("该品种不支持提货", "DT3109");
	public static final InvestErrCodeType DT3110 = new InvestErrCodeType("提货城市不存在", "DT3110");
	public static final InvestErrCodeType DT3111 = new InvestErrCodeType("提货网点不存在", "DT3111");
	public static final InvestErrCodeType DT3112 = new InvestErrCodeType("未配置该品种的定投类别", "DT3112");
	public static final InvestErrCodeType DT3113 = new InvestErrCodeType("库存信息不存在", "DT3113");
	public static final InvestErrCodeType DT3115 = new InvestErrCodeType("本地提货流水号不存在", "DT3115");
	public static final InvestErrCodeType DT3126 = new InvestErrCodeType("当前系统状态不允许撤销提货", "DT3126");
	public static final InvestErrCodeType DT3135 = new InvestErrCodeType("提货重量不符合起重和步长要求", "DT3135");
	
	
	//交易相关错误码 DT4XXXX
	public static final InvestErrCodeType DT4005 = new InvestErrCodeType("委托单号不能为空", "DT4005");
	public static final InvestErrCodeType DT4011 = new InvestErrCodeType("委托数量非法", "DT4011");
	public static final InvestErrCodeType DT4012 = new InvestErrCodeType("暂未开通该交易品种", "DT4012");
	public static final InvestErrCodeType DT4015 = new InvestErrCodeType("报单号不存在", "DT4015");
	public static final InvestErrCodeType DT4111 = new InvestErrCodeType("可用库存不足", "DT4111");
	public static final InvestErrCodeType DT4112 = new InvestErrCodeType("更新库存表失败", "DT4112");
	public static final InvestErrCodeType DT4125 = new InvestErrCodeType("错误的买卖标志", "DT4125");
	public static final InvestErrCodeType DT4126 = new InvestErrCodeType("只能按重量卖出", "DT4126");
	public static final InvestErrCodeType DT4127 = new InvestErrCodeType("买卖标志不能为空", "DT4127");
	public static final InvestErrCodeType DT4128 = new InvestErrCodeType("无法取得交易日系统状态信息", "DT4128");
	public static final InvestErrCodeType DT4129 = new InvestErrCodeType("资金归集中或者资金归集已完成，不能撤单", "DT4129");
	public static final InvestErrCodeType DT4130 = new InvestErrCodeType("提货业务暂未开通", "DT4130");
	public static final InvestErrCodeType DT4131 = new InvestErrCodeType("计划提货日不能为空", "DT4131");
	public static final InvestErrCodeType DT4132 = new InvestErrCodeType("提货城市不能为空", "DT4132");
	public static final InvestErrCodeType DT4133 = new InvestErrCodeType("提货网点不能为空", "DT4133");
	public static final InvestErrCodeType DT4134 = new InvestErrCodeType("是否二次提货不能为空", "DT4134");
	public static final InvestErrCodeType DT4135 = new InvestErrCodeType("提货日期错误", "DT4135");
	public static final InvestErrCodeType DT4136 = new InvestErrCodeType("申请日期不能为空", "DT4136");
	public static final InvestErrCodeType DT4137 = new InvestErrCodeType("资金归集中或者资金归集已完成，不能撤销提货申请", "DT4137");
	public static final InvestErrCodeType DT4138 = new InvestErrCodeType("计划提货日不能为空", "DT4138");
	public static final InvestErrCodeType DT4139 = new InvestErrCodeType("原提货流水号不能为空", "DT4139");
	public static final InvestErrCodeType DT4140 = new InvestErrCodeType("原申请日期不能为空", "DT4140");
	public static final InvestErrCodeType DT4141 = new InvestErrCodeType("交易日期不存在", "DT4141");
	public static final InvestErrCodeType DT4142 = new InvestErrCodeType("定投业务暂未开通", "DT4142");
	public static final InvestErrCodeType DT4143 = new InvestErrCodeType("买卖委托业务暂未开通", "DT4143");
	public static final InvestErrCodeType DT4144 = new InvestErrCodeType("委托买业务暂未开通", "DT4144");
	public static final InvestErrCodeType DT4145 = new InvestErrCodeType("委托卖业务暂未开通", "DT4145");
	public static final InvestErrCodeType DT4146 = new InvestErrCodeType("本期提货总量已达上限", "DT4146");
	public static final InvestErrCodeType DT4147 = new InvestErrCodeType("本期买入额度已达上限", "DT4147");
	public static final InvestErrCodeType DT4148 = new InvestErrCodeType("本期卖出额度已达上限", "DT4148");
	
	
	//系统相关错误码 DT5XXXX
	public static final InvestErrCodeType DT5001 = new InvestErrCodeType("系统正在启动过程中不允许操作", "DT5001");
	public static final InvestErrCodeType DT5014 = new InvestErrCodeType("银行行号不存在", "DT5014");
	
	//数据库相关错误码 DT6XXXX
	public static final InvestErrCodeType DT6002 = new InvestErrCodeType("数据库处理错", "DT6002");
	
	//程序异常相关错误码 DT7XXXX
	public static final InvestErrCodeType DT7001 = new InvestErrCodeType("通讯异常", "DT7001");
	
	
	//查询相关错误码 DT8XXXX
	public static final InvestErrCodeType DT8001 = new InvestErrCodeType("开始日期不能大于结束日期", "DT8001");
	public static final InvestErrCodeType DT8006 = new InvestErrCodeType("当前页码不能为空", "DT8006");
	public static final InvestErrCodeType DT8007 = new InvestErrCodeType("每页记录数不能为空", "DT8007");
	public static final InvestErrCodeType DT8008 = new InvestErrCodeType("查询条件错误", "DT8008");
	public static final InvestErrCodeType DT8009 = new InvestErrCodeType("城市代码不能为空", "DT8009");
	public static final InvestErrCodeType DT8010 = new InvestErrCodeType("提货月份不能为空", "DT8010");
	public static final InvestErrCodeType DT8011 = new InvestErrCodeType("流水号不能为空", "DT8011");
	public static final InvestErrCodeType DT8012 = new InvestErrCodeType("委托类型不能为空", "DT8012");
	

	protected InvestErrCodeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static InvestErrCodeType valueOf(String value) {

		InvestErrCodeType type = ALL.get(value);
		
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("investErrCodes", InvestErrCodeType.ALL.values());
	}
}
