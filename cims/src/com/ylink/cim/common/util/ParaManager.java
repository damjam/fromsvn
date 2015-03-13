package com.ylink.cim.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ylink.cim.admin.dao.SysDictDao;
import com.ylink.cim.admin.dao.SysParmDao;
import com.ylink.cim.admin.domain.SysDict;
import com.ylink.cim.admin.domain.SysParm;
import com.ylink.cim.common.type.SysDictType;

import flink.etc.Symbol;
import flink.util.SpringContext;

public abstract class ParaManager {
	private static Log log = LogFactory.getLog(ParaManager.class);

	private static SysParmDao sysParmDao = (SysParmDao) SpringContext.getService("sysParmDao");

	private static SysDictDao sysDictDao = (SysDictDao)SpringContext.getService("sysDictDao");
	
	// 系统参数.
	private static Map<String, String> paraMap = new ConcurrentHashMap<String, String>();
	
	private static Map<String, Map<String, String>> sysDictMap = new ConcurrentHashMap <String, Map<String, String>>();
	
	// 刷新间隔时间
	private static long sleepTime = 10 * 60 * 1000;

	public static int getInt(final String key) {
		return Integer.parseInt(getPara(key));
	}

	private static String getPara(final String key) {
		if (paraMap.size() == 0) {
			init();
		}

		return paraMap.get(key);
	}

	/**
	 * @return 密码过期时限（以天计）
	 */
	public static String getPasswordExpireDate() {
		return getPara("1005");
	}
	
	/**
	 * @return 缺省计费规则
	 */
	public static String getDefaultFeeRuleId() {
		return getPara("1007");
	}

	public static void init() {
		log.debug("刷新系统参数");
		initSysParm();
		initSysDict();
	}

	private static void initSysDict() {
		sysDictMap.clear();
		List<SysDict> list = sysDictDao.findByParam(MapUtils.EMPTY_MAP);
		for (int i = 0; i < list.size(); i++) {
			SysDict sysDict = list.get(i);
			if(sysDictMap.get(sysDict.getId().getDictType()) == null){
				Map<String, String> map = new HashMap<String, String>();
				sysDictMap.put(sysDict.getId().getDictType(), map);
			}
			sysDictMap.get(sysDict.getId().getDictType()).put(sysDict.getId().getDictValue(), sysDict.getDictName());
		}
	}
	public static Map<String, String> getSysDict(String dictType) {
		if (sysDictMap.size() == 0) {
			initSysDict();
		}
		return sysDictMap.get(dictType);
	}
	
	
	public static String getSysDictName(String dictType, String value) {
		if (sysDictMap.size() == 0) {
			initSysDict();
		}
		return sysDictMap.get(dictType).get(value);
	}
	public static void setDictInReq(HttpServletRequest request, SysDictType dictType) {
		request.setAttribute(StringUtils.uncapitalize(dictType.getValue())+"s", getSysDict(dictType.getValue()));
	}
	public static void setDictInReq(HttpServletRequest request, SysDictType dictType, String name) {
		request.setAttribute(name, getSysDict(dictType.getValue()));
		
	}
	public static void setDictInReq(HttpServletRequest request, SysDictType[] dictTypes) {
		if (ArrayUtils.isEmpty(dictTypes)) {
			return;
		}
		for (int i = 0; i < dictTypes.length; i++) {
			SysDictType dictType = dictTypes[i];
			setDictInReq(request, dictType);
		}
	}
	public static void setAllDictInReq(HttpServletRequest request) {
		for(Map.Entry<String, SysDictType> entry : SysDictType.ALL.entrySet()){
			setDictInReq(request, entry.getValue());
		}
	}
	@SuppressWarnings("unchecked")
	private static void initSysParm() {
		for (Iterator i = sysParmDao.findAll().iterator(); i.hasNext();) {
			SysParm parm = (SysParm) i.next();
			String parmvalue = parm.getParvalue();
			if (parm.getParvalue() == null) {
				parmvalue = "";
			}
			paraMap.put(parm.getCode(), parmvalue);
		}
	}

	public static void refresh() {
		while (true) {
			try {
				ParaManager.init();
				Thread.sleep(sleepTime);
			} catch (InterruptedException e1) {
				log.debug(e1, e1);
			}
		}
	}
	public static Double getMaxPayMoney(){
		try {
			String maxPayMoney = getPara("0100");
			if (StringUtils.isNotEmpty(maxPayMoney)) {
				return Double.parseDouble(maxPayMoney);
			}
			return null;
		} catch (Exception e) {
			return null;
		}
		
	}

	public static void setContextPath(String path) {
		paraMap.put("contextPath", path);
	}
	public static String getContextPath() {
		return paraMap.get("contextPath");
	}
	public static boolean isProductMode() {
		String isProductMode = paraMap.get("0100");
		return Symbol.YES.equals(isProductMode);
	}
	private static boolean isCustServer = false;
	public static void setCustServer(boolean flag){
		isCustServer = flag;
	}
	public static boolean isCustServer(){
		return isCustServer;
	}
	
	public static String getSubPhone(){
		SysParm sysParm = sysParmDao.findById("0200");
		if (sysParm != null) {
			return sysParm.getParvalue();
		}
		return Symbol.NO;
	}
	public static String getRentLightPrice() {
		return getPara("1200");
	}
	public static String getLightPrice(String buildingNo){
		Map<String, String> rentTypes = getSysDict(SysDictType.RentType.getValue());
		if (rentTypes.get(buildingNo) != null) {
			return getRentLightPrice();
		}else {
			return getEcnLightPrice();
		}
	}
	public static String getServicePrice(Integer floor){
		if (floor == 1) {
			return getFirstServicePrice();
		}else {
			return getOtherFloorPrice();
		}
	}
	public static String getLiftFee(Integer floor){
		if (floor == null || floor < 1) {
			return "0";
		}
		if (floor == 1) {
			return "0";
		}else {
			return String.valueOf(100 + 20*(floor-2));
		}
	}
	public static String getEcnLightPrice() {
		return getPara("1201");
	}
	public static String getFirstServicePrice() {
		return getPara("1300");
	}
	public static String getOtherFloorPrice() {
		return getPara("1301");
	}
	public static String getCleanPrice() {
		return getPara("1400");
	}
	public static String getLiftBasePrice() {
		return getPara("1500");
	}
	public static String getLiftAddPrice() {
		return getPara("1501");
	}
	public static String getWaterPrice() {
		return getPara("1100");
	}
}
