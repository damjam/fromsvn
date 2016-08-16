package flink.util;

import java.math.BigDecimal;

/**
 * 数字帮助类
 * 
 * @
 * 
 */
public class NumberHelper {
	/**
	 * 计算一个数的比例金额
	 * 
	 * @param partQuatity
	 * @param totalQuatity
	 * @param total
	 * @return 保留两位小数
	 */
	public static Double getPart(Double partQuatity, Double totalQuatity, Double total) {
		BigDecimal result = BigDecimal.valueOf(total).multiply(BigDecimal.valueOf(partQuatity))
				.divide(BigDecimal.valueOf(totalQuatity));
		return result.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 相加，其中任意参数可以为null，为null则取默认值0
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Long add(Long o1, Long o2) {
		if (o1 == null) {
			o1 = 0L;
		}
		if (o2 == null) {
			o2 = 0L;
		}
		return BigDecimal.valueOf(o1).add(BigDecimal.valueOf(o2)).longValue();
	}

	/**
	 * 相加，其中任意参数可以为null，为null则取默认值0
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Double add(Double o1, Double o2) {
		if (o1 == null) {
			o1 = 0.0;
		}
		if (o2 == null) {
			o2 = 0.0;
		}
		return BigDecimal.valueOf(o1).add(BigDecimal.valueOf(o2)).doubleValue();
	}
	
	/**
	 * 多个数字相加
	 * @param numbers
	 * @return
	 */
	public static Double add(Double[] numbers){
		Double result = 0d;
		for(Double d:numbers){
			result = add(result,d);
		}
		return result;
	}

	/**
	 * 相减，其中任意参数可以为null，为null则取默认值0
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Long sub(Long o1, Long o2) {
		if (o1 == null) {
			o1 = 0L;
		}
		if (o2 == null) {
			o2 = 0L;
		}

		return BigDecimal.valueOf(o1).subtract(BigDecimal.valueOf(o2)).longValue();
	}

	/**
	 * 相减，其中任意参数可以为null，为null则取默认值0
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static Double sub(Double o1, Double o2) {
		if (o1 == null) {
			o1 = 0.0;
		}
		if (o2 == null) {
			o2 = 0.0;
		}

		return BigDecimal.valueOf(o1).subtract(BigDecimal.valueOf(o2)).doubleValue();
	}

}
