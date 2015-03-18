package com.ylink.cim.common.msg.handle;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.InvestErrCodeType;
import com.ylink.cim.common.util.DoubleUtil;
import com.ylink.cim.common.util.SocketUtil;

import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;

public class MsgUtil {

	public static String FIELD_INTEGER = "I";//整形
	public static String FIELD_DOUBLE = "D";
	public static String FIELD_CHAR = "C";
	
	public static int REQ_HEAD_LENGTH = 287;
	public static int RES_HEAD_LENGTH = 326;
	
	public static String CHARSET = "GBK";
	private static Logger logger = Logger.getLogger(MsgUtil.class);
	public static void appendField(StringBuilder builder, String fieldValue, MsgField msgField) {
		try {
			builder.append(format(fieldValue, msgField));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static StringBuilder buildMsgBody(StringBuilder builder, Map<String, String> map, MsgField[] msgFields) {
		StringBuilder sb=new StringBuilder();
//		sb.append("请求报文内容[");
		sb.append("\n请求报文体解析内容：\n");
		for (int i = 0; i < msgFields.length; i++) {
			MsgField msgField = msgFields[i];
			MsgUtil.appendField(builder, map.get(msgField.getFieldCode()), msgField);
			sb.append(msgField.getFieldCode()+"="+map.get(msgField.getFieldCode()));
//			sb.append(",");
			sb.append("\n");
		}
//		int len=sb.length()-1;
//		logger.debug(sb.substring(0, len).toString()+"]");
		logger.debug(sb.toString());
		return builder;
	}
	private static StringBuilder buildReqHead(StringBuilder builder, Map<String, String> map) throws Exception{
		MsgField[] REQ_HEAD = MsgHead.REQ_HEAD;
		String gateway=fill(new StringBuilder("G"), 'G', 36, 'R').toString();
		map.put(MsgField.h_gateway_header.getFieldCode(), fill(gateway,' ',76,'L'));
		map.put(MsgField.h_bk_tx_date.getFieldCode(), DateUtil.getCurrentDate());
		map.put(MsgField.h_bk_tx_time.getFieldCode(), DateUtil.getCurrentPrettyTime());
		map.put(MsgField.h_exch_date.getFieldCode(), DateUtil.getCurrentDate());
		map.put(MsgField.h_channel.getFieldCode(),"5");
		map.put(MsgField.h_term_id.getFieldCode(),"0000");
		if (StringUtils.isEmpty(map.get("h_bank_no"))) {
			map.put(MsgField.h_bank_no.getFieldCode(), "0000");
		}else {
			map.put(MsgField.h_bank_no.getFieldCode(), map.get("h_bank_no"));
		}
		
		if(!map.get("h_bank_no").equals(BranchType.HQ_0000.getValue())){
			map.put(MsgField.h_proxy.getFieldCode(), "1");//代理行
		}else{
			map.put(MsgField.h_proxy.getFieldCode(), "0");//深圳金融电子结算中心
		}
		if (StringUtils.isEmpty(map.get("h_term_id"))) {
			map.put(MsgField.h_term_id.getFieldCode(), "");
		}
		if (StringUtils.isEmpty(map.get("h_req_type"))){
			map.put(MsgField.h_req_type.getFieldCode(), "T");
		}
		
		if (StringUtils.isEmpty(map.get("h_auth_lvl"))){
			map.put(MsgField.h_auth_lvl.getFieldCode(), "00");
		}
		if (StringUtils.isEmpty(map.get("h_start_num"))) {
			map.put(MsgField.h_start_num.getFieldCode(), "1");
		}
		if (StringUtils.isEmpty(map.get("h_query_num"))){
			map.put(MsgField.h_query_num.getFieldCode(), "20");
		}
		StringBuilder sb = new StringBuilder();
//		sb.append("请求报文头:[");
		sb.append("\n请求报文头解析内容:\n");
		for (int i = 0; i < REQ_HEAD.length; i++) {
			MsgField msgField = REQ_HEAD[i];
			MsgUtil.appendField(builder, map.get(msgField.getFieldCode()), msgField);
			sb.append(msgField.getFieldCode()+"="+map.get(msgField.getFieldCode()));
//			sb.append(",");
			sb.append("\n");
		}
//		int len=sb.length()-1;
//		logger.debug(sb.substring(0, len).toString()+"]");
		logger.debug(sb.toString());
		return builder;
	}
	
	public static String buildReqMsg(Map<String, String> map, MsgField[] msgFields) {
		StringBuilder builder = new StringBuilder();
		//添加请求报文头
		
		try {
			buildReqHead(builder, map);
			buildMsgBody(builder, map, msgFields);
			String msg = builder.toString();
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		//return prefix.append(builder).toString();
	}
	private static StringBuilder buildResHead(StringBuilder builder, Map<String, String> map) {
		
		return null;
	}
	public static String buildResMsg(Map<String, String> map, MsgField[] msgFields) {
		StringBuilder builder = new StringBuilder();
		//添加返回报文头
		buildResHead(builder, map);
		buildMsgBody(builder, map, msgFields);
		return builder.toString();
	}
	public static String fill(String s, char c, int n, char f) {
		int iByteLen = MsgUtil.StringToBytes(s).length;
		if (iByteLen >= n) {
			return s;
		}
		byte[] fillChars = new byte[n - iByteLen];
		for (int i = 0; i < fillChars.length; ++i) {
			fillChars[i] = (byte) c;
		}
		if (f == 'L') {
			try {
				return new String(fillChars, CHARSET) + s;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return s;
			}
		}else {
			try {
				return s + new String(fillChars, CHARSET);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return s;
			}
		}
		
	}
	
	/*public static byte[] stringToBytes(String str) {
		try {
			if ((str == null) || (str.length() <= 0)) {
				return new byte[0];
			}
			return str.getBytes();
		} catch (Exception e) {
		}
		return null;
	}
	public static byte[] stringToBytes(StringBuilder str) {
		try {
			if ((str == null) || (str.length() <= 0)) {
				return new byte[0];
			}
			return str.toString().getBytes();
		} catch (Exception e) {
		}
		return null;
	}*/

	public static StringBuilder fill(StringBuilder s, char c, int n, char f) throws Exception{
		int iByteLen = MsgUtil.StringToBytes(s.toString()).length;
		if (iByteLen >= n) {
			return s;
		}
		byte[] fillChars = new byte[n - iByteLen];
		for (int i = 0; i < fillChars.length; ++i) {
			fillChars[i] = (byte) c;
		}
		if (f == 'L') {
			return new StringBuilder(new String(fillChars, CHARSET)).append(s);
		}
		return s.append(new String(fillChars, CHARSET));
	}
	
	public static String format(String field, MsgField msgField) throws Exception{
		if (field == null) {
			field = "";
		}
		int length = msgField.getLength();
		String fieldType = msgField.getFieldType();
		byte[] byteField = field.getBytes(CHARSET);
		//如未指定类型或指定错误类型，换字符处理
		if (byteField.length == length) {
			return field;
		} else if(byteField.length > length){
			//超长截取前length位
			byteField = ArrayUtils.subarray(byteField, 0, length);
			try {
				return new String(byteField, CHARSET);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			}
		} else {
			if (FIELD_DOUBLE.equals(fieldType) || FIELD_INTEGER.equals(fieldType)) {
				return fill(field, '0', length, 'L');
			} else {
				return fill(field, ' ', length, 'R');
			}
		}
	}
	public static String getListContent(List<Map<String, MsgField>> list) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			Map<String, MsgField> map = list.get(i);
			builder.append(getMapContent(map));
		}
		return builder.toString();
	}
	public static String getMapContent(Map<String, MsgField> map) {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, MsgField> entry : map.entrySet()) {
			builder.append(entry.getKey()+"="+entry.getValue().getValue());
			builder.append("\n");
		}
		return builder.toString();
	}
	public static String getMapStr(Map<String, String> map) {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			builder.append(entry.getKey()+"="+entry.getValue());
			builder.append("\n");
		}
		return builder.toString();
	}

	
	private static int getMsgBodyLength(MsgField[] msgFields) {
		int num = 0;
		for (int i = 0; i < msgFields.length; i++) {
			num += msgFields[i].getLength();
		}
		return num;
	}
	public static int getReqMsgLength(MsgField[] msgFields) {
		return REQ_HEAD_LENGTH + getMsgBodyLength(msgFields);
	}
	public static Map<String, MsgField> getResBody(Map<String, String> map, MsgField[] reqFields, MsgField[] resFields) throws BizException{		
		String reqMsg = buildReqMsg(map, reqFields);
		String resMsg = SocketUtil.sendRec(reqMsg);
		logger.debug("\n响应报文:"+resMsg);
		Map<String, MsgField> resHeadMap = MsgUtil.parseResHead(resMsg);
		String resCode = resHeadMap.get(MsgField.h_rsp_code.getFieldCode()).getValue();
		if (!InvestErrCodeType.DT0000.getValue().equals(resCode)) {
			//String resErrMsg = resHeadMap.get(MsgField.h_rsp_msg.getFieldCode()).getValue();
			throw new BizException(InvestErrCodeType.valueOf(resCode).getName());
		}
		Map<String, MsgField> resMp = MsgUtil.parseResBody(resMsg, resFields);
		logger.debug(getMapContent(resMp));
		return resMp;
	}
	public static MsgField getResBodyField(String msg, String fieldCode, MsgField[] msgFields) throws Exception{
		byte[] byteMsg = msg.getBytes(CHARSET);
		byte[] byteBody = ArrayUtils.subarray(byteMsg, RES_HEAD_LENGTH, byteMsg.length);
		int startIndex = 0;
		MsgField msgField = null;
		for (int i = 0; i < msgFields.length; i++) {
			if (!StringUtils.equals(fieldCode, msgFields[i].getFieldCode())) {
				startIndex += msgFields[i].getLength();
			} else {
				msgField = msgFields[i];
				break;
			}
		}
		byte[] fieldMsg = ArrayUtils.subarray(byteBody, startIndex, startIndex+msgField.getLength());
		String str = "";
		try {
			str = new String(fieldMsg, CHARSET);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MsgField field = new MsgField();
		field.setValue(MsgUtil.trim(str, msgField));
		if (FIELD_INTEGER.equals(msgField.getFieldType()) || FIELD_DOUBLE.equals(msgField.getFieldType())) {
			field.setNumVal(Double.parseDouble(str));
		}
		field.setFieldCode(fieldCode);
		field.setFieldType(msgField.getFieldType());
		field.setLength(msgField.getLength());
		field.setFieldName(msgField.getFieldName());
		return field;
	}
	public static List<Map<String, MsgField>> getResBodyList(Map<String, String> map, MsgField[] reqFields, MsgField[] resFields) throws BizException{
		String reqMsg = buildReqMsg(map, reqFields);
		String resMsg = SocketUtil.sendRec(reqMsg);
		logger.debug("\n响应报文:"+resMsg);
		Map<String, MsgField> resHeadMap = MsgUtil.parseResHead(resMsg);
		String resCode = resHeadMap.get(MsgField.h_rsp_code.getFieldCode()).getValue();
		if (!InvestErrCodeType.DT0000.getValue().equals(resCode)) {
			if (InvestErrCodeType.DT0003.getValue().equals(resCode)) {
				return new ArrayList<Map<String,MsgField>>();
			}else {
				String resErrMsg = resHeadMap.get(MsgField.h_rsp_msg.getFieldCode()).getValue();
				throw new BizException(resErrMsg);
			}
		}
		List<Map<String, MsgField>> resMpList = MsgUtil.parseResBodyList(resMsg, resFields);
		logger.debug(getListContent(resMpList));
		return MsgUtil.parseResBodyList(resMsg, resFields);
	}
	
	
	public static Paginater getResBodyPager(Map<String, String> map, MsgField[] reqFields, MsgField[] resFields, Pager pager) throws BizException{
		map.put(MsgField.h_start_num.getFieldCode(), String.valueOf(pager.getPageNumber()));
		map.put(MsgField.h_query_num.getFieldCode(), String.valueOf(pager.getPageSize()));
		String reqMsg = buildReqMsg(map, reqFields);
		String resMsg = SocketUtil.sendRec(reqMsg);
		logger.debug("\n响应报文:"+resMsg);
		Map<String, MsgField> resHeadMap = MsgUtil.parseResHead(resMsg);
		String resCode = resHeadMap.get(MsgField.h_rsp_code.getFieldCode()).getValue();
		List<Map<String,MsgField>> list = null;
		if (!InvestErrCodeType.DT0000.getValue().equals(resCode)) {
			if (InvestErrCodeType.DT0003.getValue().equals(resCode)) {
				list = new ArrayList<Map<String,MsgField>>();
			}else {
				String resErrMsg = resHeadMap.get(MsgField.h_rsp_msg.getFieldCode()).getValue();
				throw new BizException(resErrMsg);
			}
		}else {
			List<Map<String, MsgField>> resMpList = MsgUtil.parseResBodyList(resMsg, resFields);
			logger.debug(getListContent(resMpList));
		}
		list = MsgUtil.parseResBodyList(resMsg, resFields);
		list = MsgUtil.getResBodyList(map, reqFields, resFields);
		long maxRowCount = Long.parseLong(resHeadMap.get(MsgField.h_rsp_num.getFieldCode()).getValue());
		int currentPage = Integer.parseInt(resHeadMap.get(MsgField.h_start_num.getFieldCode()).getValue());
		int pageSize = Integer.parseInt(resHeadMap.get(MsgField.h_query_num.getFieldCode()).getValue());
		Paginater paginater = new Paginater(maxRowCount, currentPage, pageSize);
		paginater.setData(list);
		return paginater;
	}

	
	public static MsgField getResHeadField(String msg, String fieldCode) throws Exception{
		int startIndex = 0;
		MsgField msgField = null;
		for (int i = 0; i < MsgHead.RES_HEAD.length; i++) {
			if (!StringUtils.equals(fieldCode, MsgHead.RES_HEAD[i].getFieldCode())) {
				startIndex += MsgHead.RES_HEAD[i].getLength();
			} else {
				msgField = MsgHead.RES_HEAD[i];
				break;
			}
		}
		byte[] byteMsg = msg.getBytes(CHARSET);
		byte[] byteField = ArrayUtils.subarray(byteMsg, startIndex, startIndex+msgField.getLength());
		String str = new String(byteField, CHARSET);
		MsgField field = new MsgField();
		field.setValue(MsgUtil.trim(str, msgField));
		if (FIELD_INTEGER.equals(msgField.getFieldType()) || FIELD_DOUBLE.equals(msgField.getFieldType())) {
			field.setNumVal(Double.parseDouble(str));
		}
		field.setFieldCode(fieldCode);
		field.setFieldType(msgField.getFieldType());
		field.setLength(msgField.getLength());
		field.setFieldName(msgField.getFieldName());
		return field;
	}
	/**
	 * 仅适用于返回报文不为多条的情况
	 * @param msgFields
	 * @return
	 */
	public static int getResMsgLength(MsgField[] msgFields) {
		return RES_HEAD_LENGTH + getMsgBodyLength(msgFields);
	}
	public static void main(String[] args) {
		int num = 0;
		for (int i = 0; i < MsgHead.REQ_HEAD.length; i++) {
			num+=MsgHead.REQ_HEAD[i].getLength();
		}
		System.out.println(num);
		
			
	}
	private static List<Map<String, MsgField>> parseBodyList(byte[] bodyMsg, MsgField[] msgFields) {
		int startIndex = 0;
		List<Map<String, MsgField>> list = new ArrayList<Map<String,MsgField>>();
		while (startIndex < bodyMsg.length) {
			Map<String, MsgField> map = new LinkedHashMap<String, MsgField>();
			for (int i = 0; i < msgFields.length; i++) {
				MsgField msgField = msgFields[i];
				byte[] byteField = ArrayUtils.subarray(bodyMsg, startIndex, startIndex+msgField.getLength());
				String str = "";
				try {
					str = new String(byteField, CHARSET);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				str = trim(str, msgField);
				MsgField field = new MsgField();
				field.setValue(str);
				field.setFieldCode(msgField.getFieldCode());
				field.setFieldType(msgField.getFieldType());
				field.setValue(str);
				if (FIELD_INTEGER.equals(msgField.getFieldType()) || FIELD_DOUBLE.equals(msgField.getFieldType())) {
					field.setNumVal(Double.parseDouble(str));
				}
				map.put(msgField.getFieldCode(), field);
				startIndex += msgField.getLength();
			}
			if (map.isEmpty()) {
				continue;
			}
			list.add(map);
		}
		return list;
	}
	private static Map<String, MsgField> parseMsgField(byte[] byteMsg, MsgField[] msgFields){
		Map<String, MsgField> map = new LinkedHashMap<String, MsgField>();
		int startIndex = 0;
		for (int i = 0; i < msgFields.length; i++) {
			MsgField msgField = msgFields[i];
			byte[] byteFiled = ArrayUtils.subarray(byteMsg, startIndex, startIndex+msgField.getLength());
			startIndex+= msgField.getLength();
			String str = "";
			try {
				str = new String(byteFiled, CHARSET);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (msgField.getFieldCode().equals("curr_can_use")) {
				System.out.println();
			}
			str = trim(str, msgField);
			MsgField field = new MsgField();
			field.setValue(str);
			if (FIELD_INTEGER.equals(msgField.getFieldType()) || FIELD_DOUBLE.equals(msgField.getFieldType())) {
				field.setNumVal(Double.parseDouble(str));
			}
			field.setFieldCode(msgField.getFieldCode());
			field.setFieldType(msgField.getFieldType());
			map.put(msgField.getFieldCode(), field);
		}
		return map;
	}
	@Deprecated
	public static Map<String, MsgField> parseReqBody(String msg, MsgField[] msgFields) throws Exception{
		byte[] byteMsg = msg.getBytes(CHARSET);
		byte[] byteBody = ArrayUtils.subarray(byteMsg, REQ_HEAD_LENGTH, byteMsg.length);
		Map<String, MsgField> map = parseMsgField(byteBody, msgFields);
		logger.debug("\n请求报文体单条22222内容\n"+getMapContent(map));
		return map;
	}
	@Deprecated
	public static Map<String, MsgField> parseReqHead(String msg) {
		return parseMsgField(msg.getBytes(), MsgHead.REQ_HEAD);
	}
	public static Map<String, MsgField> parseResBody(String msg, MsgField[] msgFields) {
		try {
			Map<String, MsgField> resHeadMap = MsgUtil.parseResHead(msg);
			String resCode = resHeadMap.get(MsgField.h_rsp_code.getFieldCode()).getValue();
			if (!InvestErrCodeType.DT0000.getValue().equals(resCode)) {
				if (InvestErrCodeType.DT0003.getValue().equals(resCode)) {
					return new HashMap<String, MsgField>();
				}else {
					String resErrMsg = resHeadMap.get(MsgField.h_rsp_msg.getFieldCode()).getValue();
					throw new BizException(resErrMsg);
				}
			}
			byte[] byteMsg = msg.getBytes(CHARSET);
			byte[] byteBody = ArrayUtils.subarray(byteMsg, RES_HEAD_LENGTH, byteMsg.length);
			Map<String, MsgField> map = parseMsgField(byteBody, msgFields);
			logger.debug("\n响应报文体内容(单条)\n"+getMapContent(map));
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static List<Map<String, MsgField>> parseResBodyList(String msg, MsgField[] msgFields) {
		try {
			byte[] bodyMsg = msg.getBytes(CHARSET);
			byte[] byteBodyMsg = ArrayUtils.subarray(bodyMsg, RES_HEAD_LENGTH, bodyMsg.length);
			List<Map<String, MsgField>> list = parseBodyList(byteBodyMsg, msgFields);
			System.out.println(getListContent(list));
			logger.debug("\n响应报文体多条内容\n"+getListContent(list));
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			//throw new Exception("解释报文出错"+e.getMessage());
		}
	}
	public static Map<String, MsgField> parseResHead(String msg) {
		Map<String, MsgField> map = null;
		try {
			map = parseMsgField(msg.getBytes(CHARSET), MsgHead.RES_HEAD);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("\n响应报文头解析内容\n"+getMapContent(map));
		return map;
	}
	public static byte[] StringToBytes(String str) {
		try {
			if ((str == null) || (str.length() <= 0)) {
				return new byte[0];
			}
			return str.getBytes(CHARSET);
		} catch (Exception e) {
		}
		return null;
	}
	public static String trim(String fieldStr, MsgField msgField){
		if (StringUtils.isEmpty(fieldStr)) {
			return "";
		}
		fieldStr = fieldStr.trim();
		//数字,去零
		if (FIELD_INTEGER.equals(msgField.getFieldType()) || FIELD_DOUBLE.equals(msgField.getFieldType())) {
			fieldStr = DoubleUtil.formatNumber(fieldStr, msgField.getDeciLength());
		}
		return fieldStr;
	}
}
