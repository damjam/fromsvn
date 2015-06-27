/**
 * @since Dec 3, 2009
 */
package flink.util;

import java.math.BigDecimal;

/**
 * 金额工具.以两位小数为基础.
 * 
 *
 */
public abstract class AmountUtils {
	private static final double ZERO = 0.0001;
	private static final int SCALE = 2;

	/**
	 * 判断金额是否相等, 两数之差小于ZERO 视为相等.
	 * 
	 * <pre>
	 * AmountUtils.equals(null, null)	= false
	 * AmountUtils.equals(1, null)      = false
	 * AmountUtils.equals(null, 1)      = false
	 * AmountUtils.equals(2, 2)      	= true
	 * AmountUtils.equals(2, 2.001)     = false
	 * </pre>
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean equals(Double d1, Double d2) {
		if (d1 == null || d2 == null) {
			return false;
		}

		return Math.abs(d1 - d2) < ZERO;
	}

	/**
	 * 四舍五入.
	 * 
	 * @param value
	 * @return
	 */
	public static double format(double value) {
		return new BigDecimal(Double.toString(value)).setScale(SCALE,
				BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 比较.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compare(double d1, double d2) {
		return new BigDecimal(Double.toString(d1)).setScale(SCALE,
				BigDecimal.ROUND_HALF_UP).compareTo(
				new BigDecimal(Double.toString(d2)).setScale(SCALE,
						BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * 大于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean gt(double d1, double d2) {
		return compare(d1, d2) > 0;
	}

	/**
	 * 等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean et(double d1, double d2) {
		return compare(d1, d2) == 0;
	}

	/**
	 * 不等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean ne(double d1, double d2) {
		return compare(d1, d2) != 0;
	}

	/**
	 * 小于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean lt(double d1, double d2) {
		return compare(d1, d2) < 0;
	}

	/**
	 * 大于等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean ge(double d1, double d2) {
		return compare(d1, d2) >= 0;
	}

	/**
	 * 小于等于.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean le(double d1, double d2) {
		return compare(d1, d2) <= 0;
	}

	/**
	 * 加法运算, 结果四舍五入.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double add(Double d1, Double d2) {
		return Double.valueOf(AmountUtils.format(d1.doubleValue()
				+ d2.doubleValue()));
	}

	/**
	 * 精确加法运算, 结果四舍五入.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double eadd(Double d1, Double d2) {
		BigDecimal bd1 = new BigDecimal(String.valueOf(d1));
		BigDecimal bd2 = new BigDecimal(String.valueOf(d2));

		return Double.valueOf(bd1.add(bd2).doubleValue());
	}

	/**
	 * 减法运算, 结果四舍五入.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double subtract(Double d1, Double d2) {
		return Double.valueOf(AmountUtils.format(d1.doubleValue()
				- d2.doubleValue()));
	}

	/**
	 * 精确减法运算, 结果四舍五入.
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double esubtract(Double d1, Double d2) {
		BigDecimal bd1 = new BigDecimal(String.valueOf(d1));
		BigDecimal bd2 = new BigDecimal(String.valueOf(d2));

		return Double.valueOf(bd1.subtract(bd2).doubleValue());
	}
}
