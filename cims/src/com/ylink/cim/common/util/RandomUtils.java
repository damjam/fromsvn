package com.ylink.cim.common.util;

import java.util.Random;


public class RandomUtils {

	/**
	 * 获取随机数
	 * @param bit
	 * @return
	 */
	public static String getRandomNumber(int bit){
		if (bit <= 0) {
			return "";
		}
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		if (bit > 10) {
			for (int i = 0; i < bit; i++) {
				String rand = String.valueOf(random.nextInt(10));
				builder.append(rand);
			}
		} else {
			while (bit > 0) {
				String rand = String.valueOf(random.nextInt(10));
				if (builder.indexOf(rand) != -1) {
					continue;
				}
				builder.append(rand);
				bit--;
			}
		}
		return builder.toString();
	}
	public static void main(String[] args) {
		//
		System.out.println(getRandomNumber(6));
	}
}
