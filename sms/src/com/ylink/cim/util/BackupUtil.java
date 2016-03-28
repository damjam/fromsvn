package com.ylink.cim.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import flink.util.DateUtil;

public class BackupUtil {

	public static void backup() {
		try {
			String today = DateUtil.getCurrentDate();
			Runtime rt = Runtime.getRuntime();
			String backDir = "d:\\data_back";
			File dir = new File(backDir);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String changeDir = "cd C:\\Program Files\\MySQL\\MySQL Server 5.0\\bin\\";
			Process process1 = rt.exec("cmd /c " + changeDir);
			InputStreamReader isr1 = new InputStreamReader(
					process1.getErrorStream());
			LineNumberReader input1 = new LineNumberReader(isr1);
			String line;
			while ((line = input1.readLine()) != null) {
				System.out.println("错误信息11" + line + "~~~~~~~~~~");
			}
			// String cmd =
			// "C:/Program Files/MySQL/MySQL Server 5.0/bin/mysqldump.exe -ucims -pcims cims> "+backDir+"\\cims"+today+".sql";
			String cmd = "mysqldump -h localhost -ucims -pcims cims> "
					+ backDir + "\\cims" + today + ".sql"; // 一定要加
			// -h
			// localhost(或是服务器IP地址)
			Process process = rt.exec("cmd /c " + cmd);
			InputStreamReader isr = new InputStreamReader(
					process.getErrorStream());
			LineNumberReader input = new LineNumberReader(isr);
			while ((line = input.readLine()) != null) {
				System.out.println("错误信息" + line + "~~~~~~~~~~");
			}
			System.out.println("备份成功!");
			// 删除以往文件
			String yesterday = DateUtil.formatDate("yyyyMMdd",
					DateUtil.addDays(DateUtil.getCurrent(), -1));

			File file = new File("d:\\" + backDir + "\\cims" + yesterday
					+ ".sql");
			file.deleteOnExit();
			process.destroy();
		} catch (IOException e) {
			System.out.println("备份失败!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		backup();
	}
}