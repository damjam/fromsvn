package com.ylink.cim.common.util;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import flink.etc.BizException;

public class SendMsgUtilAlidayu {

	public static String sendNotice(String template, String msg, String clientTel) throws BizException{
		String url = ParaManager.getProperty("url");
		String appKey = ParaManager.getProperty("appKey");
		String secretKey = ParaManager.getProperty("secretKey");
		String signName = ParaManager.getProperty("signName");
		TaobaoClient client = new DefaultTaobaoClient(url, appKey, secretKey);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("shipment_notice");
		req.setSmsType("normal");
		req.setSmsFreeSignName(signName);
		req.setSmsParamString(msg);
		req.setRecNum(clientTel);
		req.setSmsTemplateCode("SMS_585014");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			String rspMsg = rsp.getBody();
			System.out.println(rspMsg);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizException(msg);
		}
		return null;
	}
	public static String sendRandomCode(String phone) {
		
		return null;
	}
	public static String query() {
		return null;
	}
}
