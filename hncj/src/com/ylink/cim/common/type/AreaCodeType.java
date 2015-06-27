package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class AreaCodeType extends AbstractType {

	public static Map<String, AreaCodeType> ALL = new LinkedHashMap<String, AreaCodeType>();

	public static final AreaCodeType BEIJING = new AreaCodeType("北京市", "110000");
	public static final AreaCodeType TIANJING = new AreaCodeType("天津市",
			"120000");
	public static final AreaCodeType HEIBEI = new AreaCodeType("河北省", "130000");
	public static final AreaCodeType SHANXI = new AreaCodeType("山西省", "140000");
	public static final AreaCodeType INNERMONGOLIA = new AreaCodeType("内蒙古自治区",
			"150000");
	public static final AreaCodeType LIAONING = new AreaCodeType("辽宁省",
			"210000");
	public static final AreaCodeType JILIN = new AreaCodeType("吉林省", "220000");
	public static final AreaCodeType HEILONGJIANG = new AreaCodeType("黑龙江省",
			"230000");
	public static final AreaCodeType SHANGHAI = new AreaCodeType("上海市",
			"310000");
	public static final AreaCodeType JIANGSU = new AreaCodeType("江苏省", "320000");
	public static final AreaCodeType ZHEJIANG = new AreaCodeType("浙江省",
			"330000");
	public static final AreaCodeType ANHUI = new AreaCodeType("安徽省", "340000");
	public static final AreaCodeType FUJIAN = new AreaCodeType("福建省", "360000");
	public static final AreaCodeType JIANGXI = new AreaCodeType("江西省", "350000");
	public static final AreaCodeType SHANDONG = new AreaCodeType("山东省",
			"370000");
	public static final AreaCodeType HENAN = new AreaCodeType("河南省", "410000");
	public static final AreaCodeType HUBEI = new AreaCodeType("湖北省", "420000");
	public static final AreaCodeType HUNAN = new AreaCodeType("湖南省", "430000");
	public static final AreaCodeType GUANGDONG = new AreaCodeType("广东省",
			"440000");
	public static final AreaCodeType SHENZHEN = new AreaCodeType("深圳", "440300");
	public static final AreaCodeType GUANGXI = new AreaCodeType("广西壮族自治区",
			"450000");
	public static final AreaCodeType HAINAN = new AreaCodeType("海南省", "460000");
	public static final AreaCodeType CHONGQING = new AreaCodeType("重庆市",
			"500000");
	public static final AreaCodeType SICHUANG = new AreaCodeType("四川省",
			"510000");
	public static final AreaCodeType GUIZHOU = new AreaCodeType("贵州省", "520000");
	public static final AreaCodeType YUNNAN = new AreaCodeType("云南省", "530000");
	public static final AreaCodeType TIBET = new AreaCodeType("西藏自治区", "540000");
	public static final AreaCodeType SHAANXI = new AreaCodeType("陕西省", "610000");
	public static final AreaCodeType GANSU = new AreaCodeType("甘肃省", "620000");
	public static final AreaCodeType QINGHAI = new AreaCodeType("青海省", "630000");
	public static final AreaCodeType NINGXIA = new AreaCodeType("宁夏回族自治区",
			"640000");
	public static final AreaCodeType XINJIANG = new AreaCodeType("新疆维吾尔自治区",
			"650000");
	public static final AreaCodeType TAIWAN = new AreaCodeType("台湾省", "710000");
	public static final AreaCodeType HONGKONG = new AreaCodeType("香港特别行政区",
			"810000");

	protected AreaCodeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AreaCodeType valueOf(String value) throws Exception {

		AreaCodeType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AreaCodeType.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("areaCodeTypes", AreaCodeType.ALL.values());
	}
}
