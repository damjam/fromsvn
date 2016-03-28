package com.ylink.cim.common.msg.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.ylink.cim.common.msg.Constant;
import com.ylink.cim.common.msg.NioSocketChannel;
import com.ylink.gess.common.msg.TransReqMsg;
import com.ylink.gess.common.msg.TransRspMsg;

public final class CommUtil {

	/**
	 * <p>
	 * 功能描述:将数字格式化为指定长度的字符串格式
	 * </p>
	 * <p>
	 * 输入参数:数值，长度
	 * </p>
	 */
	public static String FormatNum(int num, int len) {
		String s = String.valueOf(num);
		if (s.length() < len) {
			s = CommUtil.FILL("0", '0', len - s.length(), 'L') + s;
		}
		return s;
	}

	/**
	 * 生成长度为n的字符串
	 */
	public static String blank(int n) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; i++)
			sb.append(' ');
		return sb.toString();
	}

	/**
	 * <p>
	 * 方法名称:
	 * </p>
	 * <p>
	 * 功能描述:字符串填充
	 * </p>
	 * <p>
	 * 参数介绍:需要填充的字符串;填充的字符;填充长度;左右标志
	 * </p>
	 * modify by csl 2008.09.19 优化
	 */
	public static String FILL(String s, char c, int n, char f) {
		int iByteLen = MsgUtil.StringToBytes(s).length;
		if (iByteLen >= n) {
			return s;
		} else {
			byte[] fillChars = new byte[n - iByteLen];
			for (int i = 0; i < fillChars.length; i++)
				fillChars[i] = (byte) c;

			if (f == 'L') // 左补
			{
				return new String(fillChars) + s;
			} else // 右补
			{
				return s + new String(fillChars);
			}

		}
	}

	/**
	 * <p>
	 * 功能描述:从指定文件中取得参数值
	 * </p>
	 * <p>
	 * 参数介绍:键值
	 * </p>
	 */
	private static HashMap<String, Object[]> propsMap = new HashMap<String, Object[]>();

	public static String getConfig(String fileName, String keyName) {

		URL url = ClassLoader.getSystemResource(fileName);
		Properties props = null;
		boolean bIsNeedLoadCfg = false;

		File cfgFile = new File(url.getFile());
		if (cfgFile.exists() == false)
			return "";

		Object[] arrs = propsMap.get(url.getFile());

		if (arrs == null) {
			bIsNeedLoadCfg = true;
		} else {
			Long lastModify = (Long) arrs[0];
			if (lastModify.longValue() != cfgFile.lastModified())
				bIsNeedLoadCfg = true;
			else
				props = (Properties) arrs[1];
		}

		if (bIsNeedLoadCfg == true) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(cfgFile);
				props = new Properties();
				props.load(fis);
				propsMap.put(url.getFile(),
						new Object[] { cfgFile.lastModified(), props });
			} catch (Exception e) {
				return "";
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (Exception e) {
					;
				}
			}
		}

		return props.getProperty(keyName, "");
	}

	/**
	 * <p>
	 * 功能描述:从默认配置文件中取得参数值
	 * </p>
	 * <p>
	 * 参数介绍:键值
	 * </p>
	 */
	public static String getConfig(String keyName) {
		return CommUtil.getConfig("gess.properties", keyName);
	}

	/**
	 * <p>
	 * 功能描述:从默认配置文件中取得字符串的值
	 * </p>
	 * <p>
	 * 参数介绍:键值,默认值
	 * </p>
	 */
	public static String getConfigByString(String keyName, String defaultValue) {
		String value = getConfig(keyName);
		if (value != null && value.length() > 0)
			return value.trim();
		else
			return defaultValue;
	}

	/**
	 * <p>
	 * 功能描述:从默认配置文件中取得字符串的值
	 * </p>
	 * <p>
	 * 参数介绍:键值
	 * </p>
	 */
	public static String getConfigByString(String keyName) {
		return getConfig(keyName);
	}

	/**
	 * <p>
	 * 功能描述:从默认配置文件中取得短整型的值
	 * </p>
	 * <p>
	 * 参数介绍:键值,默认值
	 * </p>
	 */
	public static int getConfigByInt(String keyName, int defaultValue) {
		String value = getConfig(keyName);
		if (value != null && value.length() > 0)
			return Integer.parseInt(value.trim());
		else
			return defaultValue;
	}

	/**
	 * <p>
	 * 功能描述:从默认配置文件中取得短整型的值
	 * </p>
	 * <p>
	 * 参数介绍:键值
	 * </p>
	 */
	public static int getConfigByInt(String keyName) {
		return Integer.parseInt(getConfig(keyName).trim());
	}

	/**
	 * <p>
	 * 功能描述:从默认配置文件中取得boolean的值
	 * </p>
	 * <p>
	 * 参数介绍:键值
	 * </p>
	 */
	public static boolean getConfigByBoolean(String keyName) {
		return Boolean.parseBoolean(getConfig(keyName).trim());
	}

	/**
	 * <p>
	 * 功能描述:从默认配置文件中取得boolean的值
	 * </p>
	 * <p>
	 * 参数介绍:键值
	 * </p>
	 */
	public static boolean getConfigByBoolean(String keyName,
			boolean defaultValue) {
		String value = getConfig(keyName);
		if (value != null && value.length() > 0)
			return Boolean.parseBoolean(value);
		else
			return defaultValue;
	}

	/**
	 * <p>
	 * 功能描述:从默认配置文件中取得长整型的值
	 * </p>
	 * <p>
	 * 参数介绍:键值,默认值
	 * </p>
	 */
	public static long getConfigByLong(String keyName, long defaultValue) {
		String value = getConfig(keyName);
		if (value != null && value.length() > 0)
			return Long.parseLong(value.trim());
		else
			return defaultValue;
	}

	/**
	 * <p>
	 * 功能描述:从默认配置文件中取得长整型的值
	 * </p>
	 * <p>
	 * 参数介绍:键值
	 * </p>
	 */
	public static long getConfigByLong(String keyName) {
		return Long.parseLong(getConfig(keyName));
	}

	/**
	 * <p>
	 * 功能描述:从默认配置文件中取得符点型的值
	 * </p>
	 * <p>
	 * 参数介绍:键值,默认值
	 * </p>
	 */
	public static float getConfigByFloat(String keyName, float defaultValue) {
		String value = getConfig(keyName);
		if (value != null && value.length() > 0)
			return Float.parseFloat(value.trim());
		else
			return defaultValue;
	}

	public static float getConfigByFloat(String keyName) {
		return Float.parseFloat(getConfig(keyName));
	}

	/**
	 * <p>
	 * 功能描述:将数字格式化为 "01"格式
	 * </p>
	 * <p>
	 * 输入参数:数值
	 * </p>
	 */
	public static String FormatNum(int num) {
		String s = String.valueOf(num);
		if (num < 10) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 获得分钟格式的交易时间，格式：HH24MI，如：0812或2007
	 * 
	 * @return
	 */
	public static String getExchMinute() {
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		if (_hour < 10)
			sb.append("0");
		sb.append(_hour);

		if (_minute < 10)
			sb.append("0");
		sb.append(_minute);

		return sb.toString();
	}

	/**
	 * 获得分钟格式的交易时间，格式：HH24:MI，如：08:12或20:07 其中":"号是输入参数ch
	 * 
	 * @param ch
	 *            分钟符
	 * @return
	 */
	public static String getExchMinute(char ch) {
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		if (_hour < 10)
			sb.append("0");
		sb.append(_hour);
		sb.append(ch);
		if (_minute < 10)
			sb.append("0");
		sb.append(_minute);

		return sb.toString();
	}

	/**
	 * 获取当前交易时间，如122415，12小时24分15秒
	 */
	public static String getExchTime() {
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		if (_hour < 10)
			sb.append("0");
		sb.append(_hour);
		if (_minute < 10)
			sb.append("0");
		sb.append(_minute);
		if (_second < 10)
			sb.append("0");
		sb.append(_second);

		return sb.toString();
	}

	/**
	 * 获取当前的交易时间，如12:24:15，12小时24分15秒
	 * 
	 * @param ch
	 *            分隔符
	 * @return
	 */
	public static String getExchTime(char ch) {
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		if (_hour < 10)
			sb.append("0");
		sb.append(_hour);
		sb.append(ch);
		if (_minute < 10)
			sb.append("0");
		sb.append(_minute);
		sb.append(ch);
		if (_second < 10)
			sb.append("0");
		sb.append(_second);

		return sb.toString();
	}

	/**
	 * 获得当前的毫秒时间
	 * 
	 * @return
	 */
	public static String getTimeInMs() {
		String TimeStr;
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		long _ms = nowtime.getTimeInMillis() % 1000;// 获取毫秒数

		TimeStr = FormatNum(_hour) + FormatNum(_minute) + FormatNum(_second);
		TimeStr = TimeStr + CommUtil.FILL(String.valueOf(_ms), '0', 3, 'L');
		return TimeStr;
	}

	/**
	 * 使用分隔符分隔当前时间， 如c=':'，则格式如：12:24:32 ,代表12时24分32秒
	 * 
	 * @param c
	 *            分隔符
	 * @return
	 */
	public static String getTime(char c) {
		String TimeStr;
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		TimeStr = FormatNum(_hour) + c + FormatNum(_minute) + c
				+ FormatNum(_second);
		return TimeStr;
	}

	/**
	 * 获取当前时间的6位形式，格式如：122432 ,代表12时24分32秒
	 * 
	 * @return
	 */
	public static String getTime() {
		String TimeStr;
		Calendar nowtime = Calendar.getInstance();
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		TimeStr = FormatNum(_hour) + FormatNum(_minute) + FormatNum(_second);
		return TimeStr;
	}

	public static int getNvlInt(ResultSet rs, int columnIndex)
			throws SQLException {
		int tmpInt = rs.getInt(columnIndex);
		return tmpInt;
	}

	public static int getNvlInt(ResultSet rs, String columnName)
			throws SQLException {
		int tmpInt = rs.getInt(columnName);
		return tmpInt;
	}

	public static long getNvlLong(ResultSet rs, int columnIndex)
			throws SQLException {
		long tmpLong = rs.getLong(columnIndex);
		return tmpLong;
	}

	public static long getNvlLong(ResultSet rs, String columnName)
			throws SQLException {
		long tmpLong = rs.getLong(columnName);
		return tmpLong;
	}

	public static double getNvlDouble(ResultSet rs, int columnIndex)
			throws SQLException {
		return rs.getDouble(columnIndex);
	}

	public static double getNvlDouble(ResultSet rs, String columnName)
			throws SQLException {
		return rs.getDouble(columnName);
	}

	public static String getNvlClob(ResultSet rs, String columnName)
			throws SQLException, IOException {
		StringBuffer sbResult = new StringBuffer();
		Clob clob = rs.getClob(columnName);
		BufferedReader brClob = null;
		try {
			brClob = new BufferedReader(clob.getCharacterStream());

			for (;;) {
				String line = brClob.readLine();
				if (line == null)
					break;
				sbResult.append(line);
			}
		} finally {
			if (brClob != null)
				brClob.close();
		}
		return sbResult.toString();
	}

	public static String getNvlClob(ResultSet rs, int columnIndex)
			throws SQLException, IOException {
		StringBuffer sbResult = new StringBuffer();
		Clob clob = rs.getClob(columnIndex);
		BufferedReader brClob = null;
		try {
			brClob = new BufferedReader(clob.getCharacterStream());

			for (;;) {
				String line = brClob.readLine();
				if (line == null)
					break;
				sbResult.append(line);
			}
		} finally {
			if (brClob != null)
				brClob.close();
		}
		return sbResult.toString();
	}

	public static BigDecimal getNvlMoney(ResultSet rs, int columnIndex)
			throws SQLException {
		BigDecimal bd_money = rs.getBigDecimal(columnIndex);
		if (bd_money == null)
			return BigDecimal.ZERO;
		else
			return bd_money.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getNvlMoney(ResultSet rs, String columnName)
			throws SQLException {
		BigDecimal bd_money = rs.getBigDecimal(columnName);
		if (bd_money == null)
			return BigDecimal.ZERO;
		else
			return bd_money.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static String getNvlString(ResultSet rs, int columnIndex)
			throws SQLException {
		String tmpStr = rs.getString(columnIndex);
		if (tmpStr == null) {
			tmpStr = "";
		}
		return tmpStr.trim();
	}

	public static String getNvlString(ResultSet rs, String columnName)
			throws SQLException {
		String tmpStr = rs.getString(columnName);
		if (tmpStr == null) {
			tmpStr = "";
		}
		return tmpStr.trim();
	}

	public static byte[] getNvlBytes(ResultSet rs, int columnIndex)
			throws SQLException {
		String str = rs.getString(columnIndex);
		if (str == null) {
			return "".getBytes();
		} else
			return str.getBytes();
	}

	public static byte[] getNvlBytes(ResultSet rs, String columnName)
			throws SQLException {
		String str = rs.getString(columnName);
		if (str == null) {
			return "".getBytes();
		} else
			return str.getBytes();
	}

	public static char getNvlChar(ResultSet rs, int columnIndex)
			throws SQLException {
		byte[] bytes = rs.getBytes(columnIndex);
		if (bytes == null || bytes.length <= 0) {
			return '\0';
		}
		return (char) bytes[0];
	}

	public static char getNvlChar(ResultSet rs, String columnName)
			throws SQLException {
		byte[] bytes = rs.getBytes(columnName);
		if (bytes == null || bytes.length <= 0) {
			return '\0';
		}
		return (char) bytes[0];
	}

	public static BigDecimal getNvlBigDecimalByPrecision(ResultSet rs,
			int columnIndex, int pre) throws SQLException {
		return getNvlBigDecimal(rs, columnIndex).setScale(pre,
				BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getNvlBigDecimalByPrecision(ResultSet rs,
			String columnName, int pre) throws SQLException {
		return getNvlBigDecimal(rs, columnName).setScale(pre,
				BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal getNvlBigDecimal(ResultSet rs, int columnIndex)
			throws SQLException {
		BigDecimal tmpBigDecimal = rs.getBigDecimal(columnIndex);
		if (tmpBigDecimal == null) {
			tmpBigDecimal = new BigDecimal(0.00);
		}
		return tmpBigDecimal;
	}

	public static BigDecimal getNvlBigDecimal(ResultSet rs, String columnName)
			throws SQLException {
		BigDecimal tmpBigDecimal = rs.getBigDecimal(columnName);
		if (tmpBigDecimal == null) {
			tmpBigDecimal = new BigDecimal(0.00);
		}
		return tmpBigDecimal;
	}

	public static String getNvlDateString(ResultSet rs, int columnIndex)
			throws SQLException {
		java.sql.Timestamp date = rs.getTimestamp(columnIndex);
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static String getNvlDateString(ResultSet rs, String columnName)
			throws SQLException {
		java.sql.Timestamp date = rs.getTimestamp(columnName);
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 获取完全格式的的日期格式
	 * 
	 * @return 格式如 20008-09-15 10:33:25:12
	 */
	public static String getFullDateTime() {
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _year = nowtime.get(Calendar.YEAR); // 获取年数
		int _month = nowtime.get(Calendar.MONTH) + 1; // 获取月数（Java中默认为0-11）
		int _day = nowtime.get(Calendar.DAY_OF_MONTH); // 获取天数
		int _hour = nowtime.get(Calendar.HOUR_OF_DAY); // 获取小时
		int _minute = nowtime.get(Calendar.MINUTE); // 获取分钟
		int _second = nowtime.get(Calendar.SECOND); // 获取秒数
		int _millisecond = nowtime.get(Calendar.MILLISECOND); // 获取毫秒数

		sb.append(_year);
		sb.append("-");
		if (_month < 10)
			sb.append("0");
		sb.append(_month);
		sb.append("-");
		if (_day < 10)
			sb.append("0");
		sb.append(_day);
		sb.append(" ");

		if (_hour < 10)
			sb.append("0");
		sb.append(_hour);
		sb.append(":");
		if (_minute < 10)
			sb.append("0");
		sb.append(_minute);
		sb.append(":");
		if (_second < 10)
			sb.append("0");
		sb.append(_second);
		sb.append(":");
		if (_millisecond < 10)
			sb.append("00");
		else if (_millisecond < 100)
			sb.append("0");
		sb.append(_millisecond);

		return sb.toString();
	}

	/**
	 * <p>
	 * 函数功能：获得异常的详细信息
	 * </p>
	 * <p>
	 * 输入参数：e 异常对象
	 * </p>
	 * <p>
	 * 返回值：将异常的堆栈信息转为字符串
	 * </p>
	 */
	public static String getExpStack(Exception e) {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bo);
		e.printStackTrace(pw);
		pw.flush();
		pw.close();
		return bo.toString();
	}

	/**
	 * <p>
	 * 函数功能：获取机器日期
	 * </p>
	 * <p>
	 * 输入参数：无
	 * </p>
	 * <p>
	 * 返回值：机器日期，格式：YYYYMMDD
	 * </p>
	 */
	public static String getPCDate() {
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _year = nowtime.get(Calendar.YEAR); // 获取年数
		int _month = nowtime.get(Calendar.MONTH) + 1; // 获取月数（Java中默认为0-11）
		int _day = nowtime.get(Calendar.DAY_OF_MONTH); // 获取天数

		sb.append(_year);
		if (_month < 10)
			sb.append("0");
		sb.append(_month);
		if (_day < 10)
			sb.append("0");
		sb.append(_day);

		return sb.toString();
	}

	/**
	 * 获得中文的机器日期
	 * 
	 * @return
	 */
	public static String getPCDateChinese() {
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _year = nowtime.get(Calendar.YEAR); // 获取年数
		int _month = nowtime.get(Calendar.MONTH) + 1; // 获取月数（Java中默认为0-11）
		int _day = nowtime.get(Calendar.DAY_OF_MONTH); // 获取天数

		sb.append(_year);
		sb.append("年");
		if (_month < 10)
			sb.append("0");
		sb.append(_month);
		sb.append("月");
		if (_day < 10)
			sb.append("0");
		sb.append(_day);
		sb.append("日");

		return sb.toString();
	}

	/**
	 * <p>
	 * 函数功能：获取机器日期
	 * </p>
	 * <p>
	 * 输入参数：c,日期之间的分隔符
	 * </p>
	 * <p>
	 * 返回值：用分隔符c分隔的日期
	 * </p>
	 */
	public static String getPCDate(char c) {
		StringBuffer sb = new StringBuffer(30);
		Calendar nowtime = Calendar.getInstance();
		int _year = nowtime.get(Calendar.YEAR); // 获取年数
		int _month = nowtime.get(Calendar.MONTH) + 1; // 获取月数（Java中默认为0-11）
		int _day = nowtime.get(Calendar.DAY_OF_MONTH); // 获取天数

		sb.append(_year);
		sb.append(c);
		if (_month < 10)
			sb.append("0");
		sb.append(_month);
		sb.append(c);
		if (_day < 10)
			sb.append("0");
		sb.append(_day);

		return sb.toString();
	}

	/**
	 * 获得指定日期的上一天
	 * 
	 * @param exch_date
	 *            当前日期，格式 YYYYMMDD
	 * @return 上一个日期
	 */
	public static String getLastDate(String exch_date) {
		int year = Integer.parseInt(exch_date.substring(0, 4));
		int month = Integer.parseInt(exch_date.substring(4, 6));
		int day = Integer.parseInt(exch_date.substring(6, 8));
		GregorianCalendar gc = new GregorianCalendar(year, month - 1, day);
		gc.add(Calendar.DAY_OF_MONTH, -1);
		year = gc.get(Calendar.YEAR);
		month = gc.get(Calendar.MONTH) + 1;
		day = gc.get(Calendar.DAY_OF_MONTH);
		return FormatNum(year) + FormatNum(month) + FormatNum(day);
	}

	/**
	 * <p>
	 * 函数功能：获得指定日期的下一天
	 * </p>
	 * <p>
	 * 输入参数：exch_date 当前日期，格式 YYYYMMDD
	 * </p>
	 * <p>
	 * 返回值：下一日期
	 * </p>
	 */
	public static String getNextDate(String exch_date) {
		int year = Integer.parseInt(exch_date.substring(0, 4));
		int month = Integer.parseInt(exch_date.substring(4, 6));
		int day = Integer.parseInt(exch_date.substring(6, 8));
		GregorianCalendar gc = new GregorianCalendar(year, month - 1, day);
		gc.add(Calendar.DAY_OF_MONTH, 1);
		year = gc.get(Calendar.YEAR);
		month = gc.get(Calendar.MONTH) + 1;
		day = gc.get(Calendar.DAY_OF_MONTH);
		return FormatNum(year) + FormatNum(month) + FormatNum(day);
	}

	/**
	 * <p>
	 * 函数功能：　获得当前日期的下个月一日
	 * </p>
	 * <p>
	 * 输入参数： 当前日期
	 * </p>
	 * <p>
	 * 返回值：　　下个月的一日
	 * </p>
	 */
	public static String getNextMonthDate(String exch_date) {
		String tmp = "01,03,05,07,08,10,12";
		String year = exch_date.substring(0, 4);
		String month = exch_date.substring(4, 6);
		String day = "30";
		if (month.compareTo("02") == 0) {
			int y = Integer.parseInt(year);
			if ((y % 4 == 0 && y % 100 == 0) || y % 400 == 0)
				day = "29";
			else
				day = "28";
		} else if (tmp.indexOf(month) != -1) {
			day = "31";
		} else {
			day = "30";
		}
		return CommUtil.getNextDate(year + month + day);
	}

	/**
	 * 将一个长整型的数，转换合适的字节数组。
	 * 
	 * @param lValue
	 * @return 返回最少3个字节长度，最大不固定的长度的字节数组
	 */
	public static byte[] toBytesByLong(long lValue) {
		// 最高位0代表正数，1代表负数
		byte[] buff = null;
		boolean bIsNegative = false;
		if (lValue < 0) {
			lValue = -1 * lValue;
			bIsNegative = true;
		}

		String sBinaryString = Long.toBinaryString(lValue);
		int iBinLen = sBinaryString.length() + 1;
		int m = iBinLen % 8;
		if (m > 0)
			buff = new byte[iBinLen / 8 + 1];
		else
			buff = new byte[iBinLen / 8];

		if (buff.length <= 3)
			buff = new byte[3];

		sBinaryString = CommUtil.FILL(sBinaryString, '0', buff.length * 8, 'L');

		if (bIsNegative == true) // 负
			sBinaryString = "1" + sBinaryString.substring(1);

		for (int i = 0; i < buff.length; i++) {
			buff[i] = (byte) Integer.parseInt(
					sBinaryString.substring(i * 8, (i + 1) * 8), 2);
		}

		return buff;
	}

	/**
	 * 将一个字节数组转换为长整型
	 * 
	 * @param bytes
	 *            字节数组,
	 * @return 返回转换后的长整型
	 */
	public static long toLongByBytes(byte[] bytes) {
		String sHexString = "";
		for (int i = 0; i < bytes.length; i++) {
			String tmp = Integer.toBinaryString(bytes[i]);

			if (tmp.length() < 8)
				tmp = CommUtil.FILL(tmp, '0', 8, 'L'); // 补足8位
			else
				tmp = tmp.substring(tmp.length() - 8); // 取有效的后8位

			sHexString += tmp;
		}

		// 最高位0代表正数，1代表负数
		if (sHexString.charAt(0) == '1')// 负数
		{
			sHexString = "0" + sHexString.substring(1);
			return Long.parseLong(sHexString, 2) * -1;
		} else {
			return Long.parseLong(sHexString, 2);
		}

	}

	/**
	 * 压缩整型值
	 * 
	 * @param vHtValues
	 *            key=行情字段的序号,value=乘以100后的整型值
	 * @return
	 */
	public static String zipLongValue(Hashtable<Integer, Long> vHtValues)
			throws Exception {
		ByteBuffer bb = ByteBuffer.allocate(2048);

		// 根据设置的字段，分别压缩
		Enumeration<Integer> enumKey = vHtValues.keys();
		while (enumKey.hasMoreElements()) {
			Integer key = enumKey.nextElement();
			Long value = vHtValues.get(key);

			// 获得压缩后的字节数组
			byte[] buff = CommUtil.toBytesByLong(value);

			// 获得压缩头 前六位代表字段序号 + 后2位代表长度
			String sBinHead = Long.toBinaryString(key.intValue())
					+ CommUtil.FILL(Long.toBinaryString(buff.length - 3), '0',
							2, 'L');

			byte bHead = Integer.valueOf(sBinHead, 2).byteValue();
			bb.put(bHead);
			bb.put(buff);
		}

		byte[] bytes = new byte[bb.position()];
		System.arraycopy(bb.array(), 0, bytes, 0, bytes.length);
		String sZipValue = DESede.base64Encode(bytes);

		;// CommUtil.WriteLog(Constant.DEBUG, "压缩报文[" + bytes.length + "," +
			// sZipValue.length() + "]：" + sZipValue);

		return sZipValue;
	}

	/**
	 * 解压缩长整型数据
	 * 
	 * @param sZipMsg
	 *            压缩后的整型数据
	 * @return
	 * @throws Exception
	 */
	public static Hashtable<Integer, Long> unzipLongValue(String sZipMsg)
			throws Exception {
		Hashtable<Integer, Long> ht = new Hashtable<Integer, Long>();

		byte[] bValues = DESede.base64DecodeToBytes(sZipMsg);

		for (int i = 0; i < bValues.length; i++) {
			// 获得压缩头
			byte bHead = bValues[i];
			String sBinHead = CommUtil.FILL(Integer.toBinaryString(bHead), '0',
					8, 'L');

			if (sBinHead.length() > 8) // 只取有效的后8位
				sBinHead = sBinHead.substring(sBinHead.length() - 8);

			// 获得字段顺序号
			int iSeqNo = Integer.parseInt(sBinHead.substring(0, 6), 2);

			// 获得字段值的字节数
			int iBuffLen = 3 + Integer.parseInt(sBinHead.substring(6, 8), 2);

			if (i >= bValues.length - 1)
				break;

			byte[] buff = new byte[iBuffLen];
			for (int j = 0; j < iBuffLen; j++) {
				buff[j] = bValues[i + j + 1];
			}

			i = i + buff.length;

			long bValue = CommUtil.toLongByBytes(buff);

			ht.put(iSeqNo, bValue);
		}

		return ht;
	}

	/**
	 * 压缩字节数组
	 * 
	 * @param bytes
	 *            原始字符串转换后的字节数组
	 * @return 返回压缩后的字符数组
	 */
	public static byte[] zip(byte[] bytes) throws Exception {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			gzip.write(bytes);
			gzip.close();
			return out.toByteArray();
		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * 解压缩字节数组
	 * 
	 * @param bytes
	 *            压缩后字节数组
	 * @return 返回解压后的字节数组
	 */
	public static byte[] unzip(byte[] bytes) throws Exception {
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(bytes);
			GZIPInputStream gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			return out.toByteArray();
		} finally {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
		}

	}

	/**
	 * 解析字符串形式的IP地址列表
	 * 
	 * @param sAddress
	 *            字符串格式的IP地址列表，格式为： Ip:Port,Ip:Port
	 * @return
	 */
	public static ArrayList<InetSocketAddress> parseStrAddress(String sAddress) {
		ArrayList<InetSocketAddress> alAddress = new ArrayList<InetSocketAddress>();

		String[] splitA = sAddress.split("\\,");
		for (int i = 0; i < splitA.length; i++) {
			String[] splitB = splitA[i].split("\\:");
			if (splitB.length == 2) {
				InetSocketAddress addr = new InetSocketAddress(splitB[0],
						Integer.parseInt(splitB[1]));
				alAddress.add(addr);
			}
		}
		return alAddress;
	}

	/**
	 * 获得SQL中的动态参数列表
	 * 
	 * @param sSql
	 * @return
	 */
	public static ArrayList<String> getSqlDynamicParameters(String sSql,
			char cStartChar, char cEndChar) {
		ArrayList<String> alPara = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		boolean bStart = false;
		for (int i = 0; i < sSql.length(); i++) {
			char ch = sSql.charAt(i);
			if (ch == cStartChar) {
				bStart = true;
				sb.delete(0, sb.length());
			} else if (ch == cEndChar) {
				if (bStart) {
					String sPara = sb.toString();
					if (sPara.length() > 0 && sPara.indexOf(" ") == -1)
						alPara.add(sPara);
					sb.delete(0, sb.length());
					bStart = false;
				}
			} else if (bStart) {
				sb.append(ch);
			}
		}
		return alPara;
	}

	/**
	 * 写文件
	 * 
	 * @param str
	 * @param bIsAppend
	 */
	public synchronized static void writeToFile(String sFileName,
			String sWriteMsg, boolean bIsNewLine, boolean bIsAppend)
			throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(sFileName, bIsAppend);
			fos.write(MsgUtil.StringToBytes(sWriteMsg));

			if (bIsNewLine)
				fos.write("\n".getBytes());

			fos.flush();

		} finally {
			if (fos != null)
				fos.close();
		}
	}

	/**
	 * 检查IP是否合法
	 * 
	 * @param sValidIps
	 *            配置的允许IP地址列表
	 * @param sCheckIp
	 *            当前被检查是否可访问的IP地址
	 * @return
	 */
	public static boolean checkIpAllowAccess(String sValidIps, String sCheckIp) {
		boolean bIsCheckSucceed = false;

		String[] checkIpNode = sCheckIp.split("\\.");
		if (checkIpNode.length != 4)
			return false;

		String[] splitA = sValidIps.split("\\,");

		// 示例：168.33.114.*,168.33.112.200-207 分隔次序：\\, \\. \\-
		// 1. 分解出每个IP的配置
		for (int i = 0; i < splitA.length; i++) {
			String[] splitB = splitA[i].split("\\.");
			if (splitB.length == 4) {
				int j = 0;
				// 2.检查单个IP地址中每一个段的模糊配置
				for (j = 0; j < splitB.length; j++) {
					// 不匹配，直接退出循环
					if (checkIpNode[j].equals(splitB[j]) == false
							&& "*".equals(splitB[j]) == false
							&& splitB[j].indexOf("-") == -1)
						break;

					String[] splitC = splitB[j].split("\\-");
					if (splitC.length == 2) {
						int k = 0;
						int iStartIp = Integer.parseInt(splitC[0]);
						int iEndIp = Integer.parseInt(splitC[1]);
						for (k = iStartIp; k <= iEndIp
								&& bIsCheckSucceed == false; k++) {
							if (checkIpNode[j].equals("" + k) == true)// 匹配上，则退出循环
								break;
						}

						// 如果循环全部走完，说明没有匹配上，退出循环，进行下一段地址检查
						if (k > iEndIp)
							break;
					}
				}

				// 如果每一段都检查通过，则说明地址合法
				if (j >= splitB.length) {
					bIsCheckSucceed = true;
					break;
				}
			}
		}
		return bIsCheckSucceed;
	}

	public static boolean TransProc(TransReqMsg v_reqMsg, TransRspMsg v_rspMsg) {

		NioSocketChannel scLink = null;
		try {
			// 连接查询服务器
			scLink = new NioSocketChannel(Constant.CFG_SOCKET_TIMEOUT_TIME);
			boolean bLinked = scLink.disConnect(Constant.CFG_SERVER_HOST_IP,
					Constant.CFG_SERVER_HOST_PORT);
			if (bLinked) {
				// 发送交易
				return SendTrans(scLink, v_reqMsg, v_rspMsg);
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scLink != null)
				scLink.closeSocket();
		}
		return false;
	}

	/***
	 * 
	 * @param scLink
	 * @param v_reqMsg
	 * @param v_rspMsg
	 * @return
	 */
	private static boolean SendTrans(NioSocketChannel scLink,
			TransReqMsg v_reqMsg, TransRspMsg v_rspMsg) {
		String sSendMsg = "";
		String sRecvMsg = "";

		TransReqMsg reqMsg = v_reqMsg;

		try {
			if (scLink != null && scLink.getIsConnected()) {
				// 发送报文
				scLink.SendStringMsg(reqMsg.toString());

				// 接收报文
				sRecvMsg = scLink.doReadBufferMsg();

				// 关闭socket链接
				if (scLink != null)
					scLink.closeSocket();

				// 解析报文
				v_rspMsg.Parse(sRecvMsg);

				return true;
			}
		} catch (Exception e) {
			// v_rspMsg.setH_rsp_code(Constant.NET_TIMEOUT_ERRCODE);
			// v_rspMsg.setH_rsp_msg(e.getMessage());
		}
		return false;
	}
}