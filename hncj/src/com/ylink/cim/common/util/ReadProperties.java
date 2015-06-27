package com.ylink.cim.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
	private static Properties config = null;

	public ReadProperties(String path) {
		InputStream in = ReadProperties.class.getClassLoader()
				.getResourceAsStream(path);
		config = new Properties();
		try {
			config.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getValByKey(String key) {
		try {
			return config.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
