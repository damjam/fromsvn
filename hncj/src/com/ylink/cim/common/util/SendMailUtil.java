package com.ylink.cim.common.util;

import java.net.InetAddress;

import javax.mail.internet.MimeUtility;

import com.flink.mail.MailConfig;
import com.flink.mail.MailConstant;
import com.flink.mail.MailHelper;
import com.flink.mail.cmp.FlinkMailComponent;
import com.flink.mail.cmp.IBaseFlinkMailComponent;
import com.flink.mail.info.SimpleHtmlMailInfo;
import com.flink.mail.info.SimpleTextMailInfo;

import flink.util.SpringContext;

public class SendMailUtil {
	static MailConfig config = (MailConfig) SpringContext
			.getService("mailServerConfig");

	private static void sendTextMail(SimpleTextMailInfo info) throws Exception {
		IBaseFlinkMailComponent baseFlinkMailComponent = new FlinkMailComponent();
		MailHelper helper = new MailHelper();
		helper.setMailConfig(config);
		baseFlinkMailComponent.setHelper(helper);
		baseFlinkMailComponent.sendTextMail(info);
	}

	private static void sendHtmlMail(SimpleHtmlMailInfo info) throws Exception {
		IBaseFlinkMailComponent baseFlinkMailComponent = new FlinkMailComponent();
		MailHelper helper = new MailHelper();
		helper.setMailConfig(config);
		baseFlinkMailComponent.setHelper(helper);
		baseFlinkMailComponent.sendHtmlMail(info);
	}

	public static void main(String[] args) throws Exception {
		// SimpleTextMailInfo info = new SimpleTextMailInfo();
		// info.setSubject("邮件主题");
		// info.setEncoding(MailConstant.CHARSET_GBK);
		// info.setTextContent("邮件内容");
		// info.setTo(new String[] { "libaozhu_mail@163.com" });
		// info.setFrom(new String[] { "test@HQ_0000.com.cn" });
		// sendTextMail(info);
		System.out.println(InetAddress.getLocalHost());
	}

	public static boolean sendTextMail(String[] receivers, String subject,
			String content) {
		try {
			SimpleTextMailInfo info = new SimpleTextMailInfo();
			info.setSubject(subject);
			info.setEncoding(MailConstant.CHARSET_GBK);
			info.setTextContent(content);
			info.setFrom(new String[] { config.getSendUserName() });
			info.setTo(receivers);
			sendTextMail(info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean sendTextMail(String receiver, String subject,
			String content) {
		try {
			SimpleTextMailInfo info = new SimpleTextMailInfo();
			info.setSubject(subject);
			info.setEncoding(MailConstant.CHARSET_GBK);
			info.setTextContent(content);
			info.setFrom(new String[] { config.getSendUserName() });
			info.setTo(new String[] { receiver });

			sendTextMail(info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean sendHtmlMail(String receiver, String subject,
			String content) {
		try {
			SimpleHtmlMailInfo info = new SimpleHtmlMailInfo();
			info.setSubject(subject);
			info.setSubject(MimeUtility.encodeText(info.getSubject(),
					MailConstant.CHARSET_GBK, null));
			info.setEncoding(MailConstant.CHARSET_GBK);
			info.setHtmlContent(content);
			info.setFrom(new String[] { config.getSendUserName() });
			info.setTo(new String[] { receiver });
			sendHtmlMail(info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void sendActiveMail(String contextPath, String receiver,
			String custId, String v) {
		try {
			String subject = "激活订阅功能";
			InetAddress addr = InetAddress.getLocalHost();
			String localIp = addr.getHostAddress();
			String port = "";
			if (localIp.endsWith("82")) {
				port = ":8443";
			}
			String content = "请<a href=\"https://" + localIp + contextPath
					+ port + "/custInfoAction.do?action=verifyEmail&c="
					+ custId + "&v=" + v + "\">点击此处</a>激活";
			sendHtmlMail(receiver, subject, content);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
