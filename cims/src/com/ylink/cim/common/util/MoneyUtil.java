package com.ylink.cim.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;

/**
 * <p>
 * Title: 金额处理
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 雁联
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class MoneyUtil {
	private static final Logger logger = Logger.getLogger(MoneyUtil.class);
	/**
	 * 获取对应于amount的以元为单位的字符串，截尾数采用四舍五入
	 * 
	 * @param amount
	 *            以元为单位
	 * @return
	 */
	private static String HanDigiStr[] = new String[] { "零", "壹", "贰", "叁",
			"肆", "伍", "陆", "柒", "捌", "玖" };

	private static String HanDiviStr[] = new String[] { "", "拾", "佰", "仟", "万",
			"拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾",
			"佰", "仟", "万", "拾", "佰", "仟" };

	public static String getYuanString(BigDecimal amount) {
		return amount.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
	}

	/**
	 * 获取对应于amount的以分为单位的字符串，截尾数采用四舍五入
	 * 
	 * @param amount
	 *            以元为单位
	 * @return
	 */
	public static String getFenString(BigDecimal amount) {
		String yuan = getYuanString(amount);
		StringBuffer sb = new StringBuffer(getYuanString(amount));
		int dotIndex = yuan.indexOf(".");
		sb.replace(dotIndex, dotIndex + 1, "");
		return sb.toString();
	}

	/**
	 * 根据以分为单位的字符串，构造以元为单位的BigDecimal
	 * 
	 * @param fen
	 * @return
	 */
	public static BigDecimal getBigDecimalFromFen(String fen) {
		return new BigDecimal(fen).divide(new BigDecimal(100),
				BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 获取金额的格式化字符串，格式为：xx,xxx,xxx.xx
	 * 
	 * @param d
	 * @return
	 */
	public static String getFormatStr(Double d) {
		return NumberFormat.getCurrencyInstance().format(d.doubleValue()).replaceAll("￥", "").replace("$","");
	}

	
	/**
	 * 获取金额的格式化字符串，格式为：xx,xxx,xxx.xx
	 * 
	 * @param d
	 * @return
	 */
	public static String getFormatStr2(Double d) {
		//return NumberFormat.getCurrencyInstance().format(d).replaceAll("$", "").replaceAll("￥", "");
		DecimalFormat df = new DecimalFormat("######.##");
		String money = df.format(d);
		if(!money.contains(".")){
			money = money+".00";
		}
		if(org.apache.commons.lang.ObjectUtils.toString(money.charAt(money.length()-2)).equals(".")){
			money = money+"0";
		}
		return money;
	}

	
	
	/**
	 * 获取金额的格式化字符串，格式为：xxxxxxxx.xx
	 * 
	 * @param d
	 * @return
	 */
	public static String getPlainStr(Double d) {
		return getFormatStr(d).replaceAll(",", "");
	}

	private static String PositiveIntegerToHanStr(String NumStr) { // 输入字符串必须正整数，只允许前导空格(必须右对齐)，不宜有前导零
		String RMBStr = "";
		boolean lastzero = false;
		boolean hasvalue = false; // 亿、万进位前有数值标记
		int len, n;
		len = NumStr.length();
		if (len > 15)
			return "数值过大!";
		for (int i = len - 1; i >= 0; i--) {
			if (NumStr.charAt(len - i - 1) == ' ')
				continue;
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9)
				return "输入含非数字字符!";

			if (n != 0) {
				if (lastzero)
					RMBStr += HanDigiStr[0]; // 若干零后若跟非零值，只显示一个零
				// 除了亿万前的零不带到后面
				// if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) ) //
				// 如十进位前有零也不发壹音用此行
				if (!(n == 1 && (i % 4) == 1 && i == len - 1)) // 十进位处于第一位不发壹音
					RMBStr += HanDigiStr[n];
				RMBStr += HanDiviStr[i]; // 非零值后加进位，个位为空
				hasvalue = true; // 置万进位前有值标记

			} else {
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) // 亿万之间必须有非零值方显示万
					RMBStr += HanDiviStr[i]; // “亿”或“万”
			}
			if (i % 8 == 0)
				hasvalue = false; // 万进位前有值标记逢亿复位
			lastzero = (n == 0) && (i % 4 != 0);
		}

		if (RMBStr.length() == 0)
			return HanDigiStr[0]; // 输入空字符或"0"，返回"零"
		return RMBStr;
	}

	public static String numToRMBStr(double val) {
		String SignStr = "";
		String TailStr = "";
		long fraction, inte;
		int yuan, jiao, fen;

		if (val < 0) {
			val = -val;
			SignStr = "负";
		}
		if (val > 99999999999999.999 || val < -99999999999999.999)
			return "数值位数过大!";
		// 四舍五入到分
		long temp = Math.round(val * 100);
		inte = temp / 100;
		fraction = temp % 100;
		yuan = (int) inte;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;
		if (yuan == 10 || yuan == 11 || yuan == 12 || yuan == 13 || yuan == 14
				|| yuan == 15 || yuan == 16 || yuan == 17 || yuan == 18
				|| yuan == 19) {
			SignStr = "壹";
		}
		if (jiao == 0 && fen == 0) {
			TailStr = "整";
		} else {
			TailStr = HanDigiStr[jiao];
			if (jiao != 0)
				TailStr += "角";
			if (inte == 0 && jiao == 0) // 零元后不写零几分
				TailStr = "";
			if (fen != 0)
				TailStr += HanDigiStr[fen] + "分";
		}

		// 下一行可用于非正规金融场合，0.03只显示“叁分”而不是“零元叁分”
		if (inte == 0)
			return SignStr + TailStr;

		return SignStr + PositiveIntegerToHanStr(org.apache.commons.lang.ObjectUtils.toString(inte)) + "元"
				+ TailStr;
	}

	// 将金额转换成逗号分隔的
	public static String NumToCommaRMB(Double NumStr) {
		return getFormatStr(NumStr);
	}

	// 将小数大于等于三位的四舍五入转成两位
	public static String ChangeMoney(String money) {
		String[] moneyarr = money.replace(".", ",").split(",");
		String temp = moneyarr[1].substring(2, 3);
		long zhengshu = Long.parseLong(moneyarr[0]);
		long xiaoshu = Long.parseLong(moneyarr[1].substring(0, 2));
		String A = "";
		if (Integer.parseInt(temp) >= 5)
			xiaoshu = xiaoshu + 1;
		if (xiaoshu < 10)
			A = "0" + org.apache.commons.lang.ObjectUtils.toString(xiaoshu);
		else
			A = org.apache.commons.lang.ObjectUtils.toString(xiaoshu);
		if (logger.isDebugEnabled()) {logger.debug("========" + A);}
		if (xiaoshu >= 100) {
			zhengshu = zhengshu + 1;
			money = org.apache.commons.lang.ObjectUtils.toString(zhengshu) + "." + "00";
		} else
			money = org.apache.commons.lang.ObjectUtils.toString(zhengshu) + "." + org.apache.commons.lang.ObjectUtils.toString(A);
		return money;

	}

	// 转换金额中的长度问题，将其转成2位小数
	public static String CheckMoney(String money) {
		//if (logger.isDebugEnabled()) {logger.debug("======>oldmoney" + money);}
		String newmoney = money + "";
		if (newmoney.indexOf("E") >= 0 && newmoney.indexOf(".") >= 0)// 处理带小数的科学记数法
		{
			String[] moneyarr = newmoney.split("E");
			String xiaoshu = moneyarr[0].substring(
					moneyarr[0].indexOf(".") + 1, moneyarr[0].length());
			String checkmoney = moneyarr[0].replace(".", "");
			int cifang = Integer.parseInt(moneyarr[1]);
			
			if(Integer.valueOf(moneyarr[1]) < 0){//次方数小于0
				int len = checkmoney.length();
				String Zero = "";
				while(checkmoney.substring(len - 1, len).equals("0")){
					len = len -1;
				}
				
				checkmoney = checkmoney.substring(0, len);
				
				cifang = cifang * -1;
				
				for(int i = 0; i < cifang - 1; i++){
					Zero += "0";
				}
				
				return "0" + "." + Zero + checkmoney;
			}
			
			if (xiaoshu.length() > cifang)// 如果科学记数的结果是一个小数
			{
				if (xiaoshu.length() - cifang == 1) {
					return checkmoney.substring(0, checkmoney.length() - 1)
							+ "."
							+ checkmoney.substring(checkmoney.length() - 1,
									checkmoney.length()) + "0";
				} else if (xiaoshu.length() - cifang == 2) {
					return checkmoney.substring(0, checkmoney.length() - 2)
							+ "."
							+ checkmoney.substring(checkmoney.length() - 2,
									checkmoney.length());
				} else {
					int x = xiaoshu.length() - cifang;
					String newmoney1 = checkmoney.substring(0, checkmoney
							.length()
							- x)
							+ "."
							+ checkmoney.substring(checkmoney.length() - x,
									checkmoney.length());
					if (logger.isDebugEnabled()) {logger.debug("+++++++++++++++++++"
							+ newmoney1);}
					newmoney = ChangeMoney(newmoney1);
					return newmoney;
				}

			}

			else // 如果科学记数的结果是一个整数
			{

				for (int i = xiaoshu.length(); i < cifang; i++) {
					checkmoney = checkmoney + "0";
				}

				return checkmoney + ".00";
			}
		} else if (newmoney.indexOf("E") >= 0 && newmoney.indexOf(".") < 0)// 处理不带小数的科学记数法
		{
			String[] moneyarr = newmoney.split("E");
			String checkmoney = moneyarr[0].replace(".", "");
			int cifang = Integer.parseInt(moneyarr[1]);
			for (int i = 0; i < cifang; i++) {
				checkmoney = checkmoney + "0";
			}

			return checkmoney + ".00";
		} else// 处理非科学记数法
		{
			if (newmoney.indexOf(".") < 0)
				return newmoney + ".00";
			String xiaoshu = newmoney.substring(newmoney.indexOf(".") + 1,
					newmoney.length());
			if (logger.isDebugEnabled()) {logger.debug("+++++++++++++++++++" + xiaoshu.length()
					+ "=====" + xiaoshu);}
			if (xiaoshu.length() == 1) {
				newmoney = newmoney + "0";
				return newmoney;
			} else if (xiaoshu.length() == 2)
				return newmoney;
			else {
				newmoney = ChangeMoney(newmoney);
				return newmoney;
			}
		}

	}

	public static String moneyFormat(String s)
	{
		DecimalFormat df=(DecimalFormat) NumberFormat.getCurrencyInstance();
		df.applyPattern("#,###,##0.00");
		if(s.equals("0")||s.equals("0.0")||s.equals("0.00")){
			df.applyPattern("0.00");	
		}
		String ns=df.format(Double.parseDouble(s));
		return ns;
	}
	
	public static String throwZero(String s)
	{
		if(s==null || s.equals("")){
			s = "0";
		}
		
		DecimalFormat df=(DecimalFormat) NumberFormat.getCurrencyInstance();
		df.applyPattern("#,###,###,###,##0.00");
		if(Long.parseLong(s)==0){
			df.applyPattern("0.00");	
		}
		String ns=df.format(Double.valueOf(s));
		return ns;
	}
	
	public static String throwZeroInt(String s)
	{
		if(s==null || s.equals("")){
			s = "0";
		}
		DecimalFormat df=(DecimalFormat) NumberFormat.getCurrencyInstance();
		df.applyPattern("#####0");
		if(Long.parseLong(s)==0){
			df.applyPattern("0");	
		}
		String ns=df.format(Double.valueOf(s));
		return ns;
	}
	
	public static String formatMoney (String s) {
		 NumberFormat nf1 = NumberFormat.getInstance(); 
		 Object obj1 = null; 
		 String money = "";
         // 基于格式的解析 
         try { 
             obj1 = nf1.parse(s); 
             money = obj1.toString();
         } 
         catch (ParseException e1) { 
             if (logger.isDebugEnabled()) {logger.debug(e1); }
         } 
         return money;
	}
	
	//将金额小数点去掉,然后前面补"0"
	public static String getAmount(String amount){
		DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance();
		df.applyPattern("0000000000000.00");
		String ns=df.format(Double.valueOf(amount));
		String[] str = ns.split("\\.");
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < str.length; i++){
			sb.append(str[i]);
		}
		return sb.toString();
	}
	/**
	 * /将金额小数点去掉,然后前面补"0" 总共15位
	 * @param money
	 * @return
	 */
	public static String switchMoney(double money){
		DecimalFormat df = new DecimalFormat("0.00");
		String str = df.format(money*100);
		str = str.substring(0,str.indexOf("."));
		return str;
	}

	/**
	 * 从报文中取出的金额以科学计数法显示在页面
	 * 如:RMB000000040132453 转换为:RMB 4,013,324.53
	 * @param money 从报文中取出的金额(包含货币符号,如:RMB)
	 * @return 
	 */
	public static String showMoney (String money) {
		if(money != null && money.length() > 2){
			String huobiType = money.substring(0, 3); //取货币符号
		
			int num = 0;
			for (int i = 3; i < money.length(); i++) {
				if (money.charAt(i) > '9' || money.charAt(i) < '1') {
					num++;
				} else {
					break;
				}
			}
			String oldMoney = money.substring(3 + num);
			if (!"".equals(oldMoney) && oldMoney.length() > 2) {
				
				StringBuffer sb = new StringBuffer(huobiType);
				sb.append(" ");
				for (int i = 0; i < oldMoney.length(); i++) {
					if ((oldMoney.length() - i) % 3 == 2 && i != oldMoney.length() - 2 && i != 0){
						sb.append(",");
					}
					sb.append(oldMoney.charAt(i));
					if (oldMoney.length() - i == 3){
						sb.append(".");
					}
				}
				return sb.toString();
			} else if (oldMoney.length() == 2) {
				return huobiType + " 0." + oldMoney;
			} else if (oldMoney.length() == 1) {
				return huobiType + " 0.0" + oldMoney;
			} else {
				return huobiType + " 0.00";
			}
		} else {
			return "";
		}
	}
	
	/**
	 * 从报文中取出的金额以科学计数法显示在页面
	 * 如:RMB000000040132453 转换为:RMB 4,013,324.53
	 * @param money 从报文中取出的金额(不包含包含货币符号,如:RMB)
	 * @return 
	 */
	public static String _showMoney (String money) {
		if(money != null && money.length() > 2){
			//String huobiType = money.substring(0, 3); //取货币符号
		
			int num = 0;
			for (int i = 0; i < money.length(); i++) {
				if (money.charAt(i) > '9' || money.charAt(i) < '1') {
					num++;
				} else {
					break;
				}
			}
			String oldMoney = money.substring(num);
			if (!"".equals(oldMoney) && oldMoney.length() > 2) {
				
				StringBuffer sb = new StringBuffer("");
				sb.append(" ");
				for (int i = 0; i < oldMoney.length(); i++) {
					if ((oldMoney.length() - i) % 3 == 2 && i != oldMoney.length() - 2 && i != 0){
						sb.append(",");
					}
					sb.append(oldMoney.charAt(i));
					if (oldMoney.length() - i == 3){
						sb.append(".");
					}
				}
				return sb.toString();
			} else if (oldMoney.length() == 2) {
				return  " 0." + oldMoney;
			} else if (oldMoney.length() == 1) {
				return " 0.0" + oldMoney;
			} else {
				return " 0.00";
			}
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
	 if (logger.isDebugEnabled()) {logger.debug(ChangeMoneyToTwoxiaoshu("30.0"));}
	}
	
	
	
	/*
	 * 一下方法是解决JAVA中DOUBLE 相加的精度问题
	 */
	private static final int DEF_DIV_SCALE = 10;
    
    /**
      * 两个Double数相加
      * @param v1
      * @param v2
      * @return Double
     */
    public static Double add(Double v1,Double v2){
         BigDecimal b1 = new BigDecimal(v1.toString());
         BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
     }
    
    /**
      * 两个Double数相减
      * @param v1
      * @param v2
      * @return Double
     */
    public static Double sub(Double v1,Double v2){
         BigDecimal b1 = new BigDecimal(v1.toString());
         BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
     }
    
    /**
      * 两个Double数相乘
      * @param v1
      * @param v2
      * @return Double
     */
    public static Double mul(Double v1,Double v2){
         BigDecimal b1 = new BigDecimal(v1.toString());
         BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
     }
    
    /**
      * 两个Double数相除
      * @param v1
      * @param v2
      * @return Double
     */
    public static Double div(Double v1,Double v2){
         BigDecimal b1 = new BigDecimal(v1.toString());
         BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP).doubleValue();
     }
    
    /**
      * 两个Double数相除，并保留scale位小数
      * @param v1
      * @param v2
      * @param scale
      * @return Double
     */
    public static Double div(Double v1,Double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
            "The scale must be a positive integer or zero");
         }
         BigDecimal b1 = new BigDecimal(v1.toString());
         BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
     }
//	 将小数大于等于三位的四舍五入转成两位
	public static String ChangeMoneyToTwoxiaoshu(String money) {
		
		String str[] = money.replace(".", ",").split(",");
		if(str[1].length()>=3){
			String[] moneyarr = money.replace(".", ",").split(",");
			String temp = moneyarr[1].substring(2, 3);
			long zhengshu = Long.parseLong(moneyarr[0]);
			long xiaoshu = Long.parseLong(moneyarr[1].substring(0, 2));
			String A = "";
			if (Integer.parseInt(temp) >= 5)
				xiaoshu = xiaoshu + 1;
			if (xiaoshu < 10)
				A = "0" + org.apache.commons.lang.ObjectUtils.toString(xiaoshu);
			else
				A = org.apache.commons.lang.ObjectUtils.toString(xiaoshu);
			if (logger.isDebugEnabled()) {logger.debug("========" + A);}
			if (xiaoshu >= 100) {
				zhengshu = zhengshu + 1;
				money = org.apache.commons.lang.ObjectUtils.toString(zhengshu) + "." + "00";
			} else
				money = org.apache.commons.lang.ObjectUtils.toString(zhengshu) + "." + org.apache.commons.lang.ObjectUtils.toString(A);
			return money;
		}else{
			return money;	
		}
	}
	public static double replaceFormat(String money){
		String m = money.replace(",", "").trim();
		System.out.println(m);
		return Double.parseDouble(money);
	}
}
