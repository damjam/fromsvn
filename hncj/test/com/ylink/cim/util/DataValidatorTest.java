package com.ylink.cim.util;

import org.junit.Test;

public class DataValidatorTest {
	@Test
	public void testIsMobileNo() throws Exception {
		String mobile= "13203994512";
		System.out.println(DataValidator.isMobileNO(mobile));
	}
	
	@Test
	public void testIsEmail() {
		String email = "UNHoMO@W3Cfuns.com";
		System.out.println(DataValidator.isEmail(email));
	}
	
	@Test
	public void testIsIDNumber() {
		String idNumber = "320323197710235419";
		System.out.println(DataValidator.isIDNumber(idNumber));
	}
}
