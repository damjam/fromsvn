package com.ylink.cim.common.util;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;


public class DoubleUtil {

	/**
	 * 将一个定长String类型转成double，（如：定义为double（18，4）则 pointLen=4）
	 * @param pointLen是小数点位数
	 * @return
	 */
	public static double stringToDouble(String d,int pointLen){
		int len=d.length();
		if(len>0){
			return Double.parseDouble(d.substring(0, len-pointLen)+"."+d.substring(len-pointLen));
		}else{
			return 0;
		}
	}

	/**
	 * 将一个double转成定长String（如：定义为double（18，4），则len=18，pointLen=4）
	 * @param len是转成字符串之后的长度，pointLen是转成字符串之后小数的位数
	 * @return
	 */
	public static String doubleToString(double d,int len,int pointLen){
		String num=(new Double(d)).toString();
		int index=num.indexOf(".");
		
		return fillNum(num.substring(0,index),"0", len-pointLen, "L")+fillNum(num.substring(index+1),"0", pointLen, "R");
	}
	
	/**
	 * 字符填充
	 * @param s 要填充的字符串
	 * @param f 用f字符填充
	 * @param len 填充之后的长度
	 * @param r L：左填充，R：右填充
	 * @return
	 */
	public static String fillNum(String s,String f,int len,String r){
		if(r.equalsIgnoreCase("L")){
			if(s.length()<len){
				s=f+s;
				return fillNum(s,f,len,r);
			}else{
				return s;
			}
		}else{
			if(s.length()<len){
				s=s+f;
				return fillNum(s,f,len,r);
			}else{
				return s;
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Double d = stringToDouble("1", 2);
		System.out.println(d);
	}

	public static String formatNumber(String fieldStr, int deciLength) {
		if (StringUtils.isEmpty(fieldStr)) {
			return "";
		}
		while(fieldStr.startsWith("0") && fieldStr.length() > 1){
			fieldStr = fieldStr.substring(1, fieldStr.length());
		}
		Double d = Double.parseDouble(fieldStr);
		if (d == 0) {
			return "0";
		}
		for (int i = 0; i < deciLength; i++) {
			d/=10;
		}
		DecimalFormat format = null;
		if (deciLength == 0) {
			format = new DecimalFormat("#");
		}else if (deciLength == 2) {
			format = new DecimalFormat("#0.00");
		}else if (deciLength == 4) {
			format = new DecimalFormat("#0.0000");
		}
		fieldStr = format.format(d);
		return fieldStr;
	}
	
	/**
	 * 比较2个double类型的数据的大小.如果d1>d2,则返回 true
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareDouble(double d1,double d2){
		return (d1>d2 || Math.abs(d1-d2)<0.000001);
	}

}
