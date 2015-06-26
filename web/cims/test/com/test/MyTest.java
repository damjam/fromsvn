package com.test;

import org.junit.Test;

import flink.MD5Util;

public class MyTest {
	@Test
	public void testMD5() {
//		String str = new String("111111");
		String str = "13049839872";
		String str_md5 = MD5Util.MD5(str);
		System.out.println(str_md5);
		if(str_md5.equals("96e79218965eb72c92a549dd5a330112")) {
			System.out.println("true");
		}
	}
	
	@Test
	public void test() {
		String s = "18233848321";
		System.out.println(s.substring(s.length()-6));
	}
}
