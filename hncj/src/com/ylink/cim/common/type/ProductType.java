package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * 产品类型
 *
 */
public class ProductType extends AbstractState {
	public static Map<String, ProductType> ALL = new LinkedHashMap<String, ProductType>();
	protected ProductType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static final ProductType TYPE_00 = new ProductType("装饰", "00");
	public static final ProductType TYPE_01 = new ProductType("瓷砖", "01");
	public static final ProductType TYPE_02 = new ProductType("墙漆涂料", "02");
	public static final ProductType TYPE_03 = new ProductType("卫浴", "03");
	public static final ProductType TYPE_04 = new ProductType("集成吊顶", "04");
	public static final ProductType TYPE_05 = new ProductType("墙纸", "05");
	public static final ProductType TYPE_06 = new ProductType("衣柜", "06");
	public static final ProductType TYPE_07 = new ProductType("橱柜", "07");
	public static final ProductType TYPE_08 = new ProductType("木门", "08");
	public static final ProductType TYPE_09 = new ProductType("阳台门窗", "09");
	public static final ProductType TYPE_10 = new ProductType("空调", "10");
	public static final ProductType TYPE_11 = new ProductType("采暖", "11");
	public static final ProductType TYPE_12 = new ProductType("家具", "12");
	public static final ProductType TYPE_13 = new ProductType("灯饰", "13");
	public static final ProductType TYPE_14 = new ProductType("窗帘", "14");
	public static final ProductType TYPE_15 = new ProductType("晾衣架", "15");
	public static final ProductType TYPE_16 = new ProductType("其他", "16");
	public static ProductType valueOf(String value) throws Exception {
		ProductType flag = ALL.get(value);
		if(null == flag){
			ExceptionUtils.logException(ProductType.class, "标识错误");
		}
		return flag;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("yesOrNos", ProductType.ALL.values());
	}
}
