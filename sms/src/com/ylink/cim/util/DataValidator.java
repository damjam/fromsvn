package com.ylink.cim.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class DataValidator {
	private static final Logger logger = Logger.getLogger(DataValidator.class);
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			String check = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
			Pattern p = Pattern.compile(check);
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch(Exception e) {
			flag = false;
		}
		logger.info(flag + "---");
		return flag;
	}
	
	public static boolean isEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
			Pattern p = Pattern.compile(check);
			Matcher m = p.matcher(email);
			flag = m.matches();
		} catch(Exception e) {
			flag = false;
		}
		logger.info(flag + "---");
		return flag;
	}
	
	public static boolean isIDNumber(String idNumber) {
		boolean flag = false;
		try {
			String isIDCard= "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";
			Pattern p = Pattern.compile(isIDCard);
			Matcher m = p.matcher(idNumber);
			flag = m.matches();
		} catch(Exception e) {
			flag = false;
		}
		logger.info(flag + "---");
		return flag;
	}
}
