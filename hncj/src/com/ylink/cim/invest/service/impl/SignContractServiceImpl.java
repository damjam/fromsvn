package com.ylink.cim.invest.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.common.msg.handle.MsgA0010;
import com.ylink.cim.common.msg.handle.MsgA0011;
import com.ylink.cim.common.msg.handle.MsgA0013;
import com.ylink.cim.common.msg.handle.MsgA0014;
import com.ylink.cim.common.msg.handle.MsgA0017;
import com.ylink.cim.common.msg.handle.MsgA2031;
import com.ylink.cim.common.msg.handle.MsgA2044;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.state.ServiceState;
import com.ylink.cim.common.state.SignState;
import com.ylink.cim.common.type.BusiType;
import com.ylink.cim.common.type.CustType;
import com.ylink.cim.common.type.InvestErrCodeType;
import com.ylink.cim.common.type.PayChnlType;
import com.ylink.cim.common.util.CcbSignUtil;
import com.ylink.cim.common.util.SocketUtil;
import com.ylink.cim.cust.dao.CustInfoDao;
import com.ylink.cim.cust.domain.CustInfo;
import com.ylink.cim.cust.service.CustInfoService;
import com.ylink.cim.invest.dao.ServiceRecordDao;
import com.ylink.cim.invest.dao.SignContractDao;
import com.ylink.cim.invest.domain.ServiceRecord;
import com.ylink.cim.invest.domain.SignContract;
import com.ylink.cim.invest.service.SignContractService;

import flink.etc.BizException;
import flink.util.DateUtil;

@Component("signContractService")
public class SignContractServiceImpl implements SignContractService {

	@Autowired
	private SignContractDao signContractDao;

	@Autowired
	private CustInfoDao custInfoDao;

	@Autowired
	private ServiceRecordDao serviceRecordDao;

	@Autowired
	private CustInfoService custInfoService;
	public String addSignContract(SignContract signContract, HttpServletRequest request, HttpServletResponse response) throws BizException {
		if (StringUtils.isEmpty(signContract.getBusiType())) {
			signContract.setBusiType(BusiType.GOLD.getValue());
		}
		signContract.setCreateTime(DateUtil.getCurrent());
		signContract.setState(SignState.UNCONFIRM_ADD.getValue());
		CustInfo custInfo = custInfoDao.findById(signContract.getCustId());
		String authId = getAuthId(signContract, custInfo.getIdCard());
		signContract.setAccreditId(authId);
		signContractDao.save(signContract);
		if (PayChnlType.BANK_105.getValue().equals(signContract.getPayChnlType())) {
			Map<String, String> map = new HashMap<String, String>();
			try {
				map.put("AUTHID", authId);
				map.put("PAYMENT", "1000");
				map.put("LIMIT", "1000");
				map.put("UName", custInfo.getName());
				map.put("IdType", custInfo.getCardType());
				map.put("IdNumber", custInfo.getIdCard());
				map.put("EPAYNO", signContract.getPayChnlNo());
				CcbSignUtil.sign(request, response, map);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException(e.getMessage());
			}
		}
		return authId;
	}

	public void cancelAcct(HttpServletRequest request, HttpServletResponse response, String acctNo) throws BizException {
		try {
			SignContract contract = signContractDao.getUniqueResult(SignContract.class, "investAcctNo", acctNo);
			//已解约
			if (SignState.UNSIGN.getValue().equals(contract.getState())) {
				//直接销户即可
				cancelAcctToCore(contract.getInvestAcctNo());
				return;
			}
			contract.setState(SignState.CANCELING_ACCT.getValue());
			signContractDao.update(contract);
			if (PayChnlType.BANK_105.getValue().equals(contract.getPayChnlType())) {
				CcbSignUtil.cancelSign(request, response, contract.getAccreditId());
			}else {
				//其他行别
			}
			
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
	}
	private String cancelAcctToCore(String aipNo) throws BizException {
		// 销户
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put(MsgField.h_exch_code.getFieldCode(), "A0017");

		Map<String, MsgField> bodyMap = MsgUtil.getResBody(msgMap, MsgA0017.A0017REQ, MsgA0017.A0017RES);
		String serialNo = bodyMap.get(MsgField.serial_no.getFieldCode()).getValue();
		return serialNo;
	}

	public void cancelSign(HttpServletRequest request, HttpServletResponse response, String acctNo) throws BizException{
		try {
			SignContract contract = signContractDao.getUniqueResult(SignContract.class, "investAcctNo", acctNo);
			//更改银行账户解约中
			contract.setState(SignState.CANCELING_SIGN.getValue());
			signContractDao.update(contract);
			CcbSignUtil.cancelSign(request, response, contract.getAccreditId());
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
	}

	//定投核心解约
	private String cancelSignToCore(String aipNo, String payChnlType, String payChnlNo) throws BizException {
		Map<String, String> msgMap = new HashMap<String, String>();
		
		msgMap.put(MsgField.aip_no.getFieldCode(), aipNo);
		msgMap.put(MsgField.account_no.getFieldCode(), payChnlNo);
		//解约(A0013)
		msgMap.put(MsgField.h_exch_code.getFieldCode(), "A0013");
		Map<String, MsgField> bodyMap = MsgUtil.getResBody(msgMap, MsgA0013.A0013REQ, MsgA0013.A0013RES);
		String serialNo = bodyMap.get(MsgField.serial_no.getFieldCode()).getValue();
		return serialNo;
	}

	/**
	 * 解约
	 * 
	 * @return
	 */
	public String cancelSuccess(String accreditId) throws BizException {
		SignContract signContract = signContractDao.getUniqueResult(SignContract.class, "accreditId", accreditId);
		String state = signContract.getState();
		String aipNo = signContract.getInvestAcctNo();
		//销户
		String serialNo = cancelSignToCore(signContract.getInvestAcctNo(), signContract.getPayChnlType(), signContract.getPayChnlNo());
		if (SignState.CANCELING_ACCT.getValue().equals(state)) {
			// 更新
			if (StringUtils.isNotEmpty(serialNo)) {
				//定投核心销户
				cancelAcctToCore(aipNo);
				
				signContract.setCancelTime(Calendar.getInstance().getTime());
				signContract.setState(SignState.CANCELED_ACCT.getValue());
				signContractDao.update(signContract);
				// 检查是否还有开通业务
				String serviceId = signContract.getServiceId();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("serviceId", serviceId);
				map.put("branchNo", signContract.getBranchNo());
				map.put("state", SignState.SIGNED.getValue());
				List<SignContract> list = signContractDao.findByParams(map);
				if (null == list || list.size() < 1) {
					ServiceRecord serviceRecord = serviceRecordDao.findById(serviceId);
					serviceRecord.setCancelTime(new Date());
					serviceRecord.setState(ServiceState.CANCELED.getValue());
					serviceRecordDao.update(serviceRecord);
				}
			}
			return serialNo;
		} 
		//解约不销户
		else if(SignState.CANCELING_SIGN.getValue().equals(state)){
			signContract.setState(SignState.UNSIGN.getValue());
			signContractDao.update(signContract);
		}
		return null;
	}


	public boolean checkCancel(String aipNo) throws BizException {
		SignContract contract = signContractDao.getUniqueResult(SignContract.class, "investAcctNo", aipNo);
		String signState = contract.getState();
		//已销户
		if (SignState.CANCELED_ACCT.getValue().equals(signState)) {
			return true;
		} 
		//已解约
		else if(SignState.UNSIGN.getValue().equals(signState)){
			return true;
		} else {
			//解约失败，恢复状态为已签约
			contract.setState(SignState.SIGNED.getValue());
			signContractDao.update(contract);
			return false;
		}
	}

	public boolean checkSign(String accreditId) throws BizException {
		SignContract contract = signContractDao.getUniqueResult(SignContract.class, "accreditId", accreditId);
		String signState = contract.getState();
		if (SignState.SIGNED.getValue().equals(signState)) {
			return true;
		}else {
			if (SignState.UNCONFIRM_ADD.getValue().equals(signState)) {
				//签约失败，直接删除
				signContractDao.delete(contract);
			} else if(SignState.UNCONFIRM_UPDATE.getValue().equals(signState)){
				contract.setState(SignState.UNSIGN.getValue());
				contract.setAccreditId(null);
				contract.setPayChnlNo(null);
				signContractDao.update(contract);
			}
			return false;
		}
	}

	
	//16位授权号
	private String getAuthId(SignContract contract, String idCard){
		StringBuilder builder = new StringBuilder();
		builder.append("C");//代表当前系统
		builder.append(contract.getBranchNo());//4位机构号
		builder.append(idCard.substring(idCard.length()-4, idCard.length()));//证件号后4位
		builder.append(contract.getPayChnlType());//3位行别
		String payChnlNo = contract.getPayChnlNo();
		builder.append(payChnlNo.substring(payChnlNo.length()-4, payChnlNo.length()));//银行卡号后4位
		return builder.toString();
	}
	public String modifyInvestAcct(Map<String, String> msgMap) throws BizException {
		msgMap.put(MsgField.h_exch_code.getFieldCode(), "A0014");
		// String msg = MsgUtil.buildReqMsg(msgMap, MsgA0014.A0014REQ);
		// String resMsg = SocketUtil.sendRec(msg);
		Map<String, MsgField> bodyMap = MsgUtil.getResBody(msgMap, MsgA0014.A0014REQ, MsgA0014.A0014RES);
		String serialNo = bodyMap.get(MsgField.serial_no.getFieldCode()).getValue();
		return serialNo;
	}

	private String openAcctToCore(CustInfo custInfo) throws BizException {
		// 开户
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put(MsgField.h_exch_code.getFieldCode(), "A0010");
		msgMap.put(MsgField.acct_no.getFieldCode(), custInfo.getId());
		msgMap.put(MsgField.cust_name.getFieldCode(), custInfo.getName());
		msgMap.put(MsgField.cert_type.getFieldCode(), custInfo.getCardType());
		msgMap.put(MsgField.cert_num.getFieldCode(), custInfo.getIdCard());
		msgMap.put(MsgField.area_code.getFieldCode(), custInfo.getAreaCode());
		msgMap.put(MsgField.mobile_phone.getFieldCode(), custInfo.getMobile());
		msgMap.put(MsgField.addr.getFieldCode(), custInfo.getAddr());
		msgMap.put(MsgField.email.getFieldCode(), custInfo.getEmail());

		Map<String, MsgField> bodyMap = MsgUtil.getResBody(msgMap, MsgA0010.A0010REQ, MsgA0010.A0010RES);
		String aipNo = bodyMap.get(MsgField.aip_no.getFieldCode()).getValue();
		return aipNo;
	}

	/**
	 * 定投账户管理,查询客户定投账号
	 */
	public List<Map<String, String>> queryInvestAcctList(Map<String, String> msgMap, String[] acctNos)
			throws BizException {
		List<Map<String, MsgField>> resList = new ArrayList<Map<String, MsgField>>();
		msgMap.put(MsgField.h_exch_code.getFieldCode(), "A2031");
		if (!StringUtils.isEmpty(msgMap.get(MsgField.aip_no.getFieldCode()))) {
			resList = MsgUtil.getResBodyList(msgMap, MsgA2031.A2031REQ, MsgA2031.A2031RES);
		} else {
			for (int i = 0; i < acctNos.length; i++) {
				msgMap.put(MsgField.aip_no.getFieldCode(), acctNos[i]);
				List<Map<String, MsgField>> bodyMapList = MsgUtil.getResBodyList(msgMap, MsgA2031.A2031REQ,
						MsgA2031.A2031RES);
				resList.addAll(bodyMapList);
			}
		}
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		for (int i = 0; i < resList.size(); i++) {
			Map<String, MsgField> resMap = resList.get(i);
			String aipNo = resMap.get(MsgField.aip_no.getFieldCode()).getValue();
			SignContract contract = signContractDao.getUniqueResult(SignContract.class, "investAcctNo", aipNo);
			Map<String, String> map = new HashMap<String, String>();
			map.put("aipNo", resMap.get(MsgField.aip_no.getFieldCode()).getValue());
			map.put("accountNo", resMap.get(MsgField.account_no.getFieldCode()).getValue());
			map.put("createTime", resMap.get(MsgField.o_date.getFieldCode()).getValue());
			map.put("acctState", resMap.get(MsgField.acct_stat.getFieldCode()).getValue());
			map.put("state", contract.getState());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 查定投账号下的库存
	 */
	public Map<String, MsgField> queryInvestAcctStock(Map<String, String> msgMap) throws BizException {
		msgMap.put(MsgField.h_exch_code.getFieldCode(), "A2044");
		Map<String, MsgField> bodyMap = MsgUtil.getResBody(msgMap, MsgA2044.A2044REQ, MsgA2044.A2044RES);
		return bodyMap;
	}

	// 签约成功后调用
	public String signSuccess(Map<String, String> map) throws BizException {
		String accreditId = map.get("ACCREDIT_ID");
		SignContract contract = signContractDao.getUniqueResult(SignContract.class, "accreditId", accreditId);
		//新增待确认
		if (SignState.UNCONFIRM_ADD.getValue().equals(contract.getState())) {
			String custId = contract.getCustId();
			CustInfo custInfo = custInfoDao.findById(custId);
			contract.setState(SignState.SIGNED.getValue());
			//定投核心开通业务
			String aipNo = openAcctToCore(custInfo);
			//定投核心签约
			signToCore(aipNo, contract.getPayChnlType(), contract.getPayChnlNo());
			contract.setInvestAcctNo(aipNo);
			signContractDao.update(contract);
			serviceRecordDao.addServiceByContract(contract, accreditId.substring(1, 5));
			//会员信息
			if (CustType.TYPE_NORMAL.getValue().equals(custInfo.getCustType())) {
				//添加会员角色
				custInfoService.addMemberRole(custId);
				custInfo.setCustType(CustType.TYPE_MEMBER.getValue());
			}
			return aipNo;
		} else {
			//更改待确认
			contract.setState(SignState.SIGNED.getValue());
			signContractDao.update(contract);
			signToCore(contract.getInvestAcctNo(), contract.getPayChnlType(), contract.getPayChnlNo());
			return contract.getInvestAcctNo();
		}
	}
	
	// 签约
	private String signToCore(String aipNo, String payChnlType, String payChnlNo) throws BizException {
		Map<String, String> msgSignMap = new HashMap<String, String>();
		msgSignMap.put(MsgField.h_exch_code.getFieldCode(), "A0011");
		msgSignMap.put(MsgField.aip_no.getFieldCode(), aipNo);
		msgSignMap.put(MsgField.account_no.getFieldCode(), payChnlNo);

		String msgSign = MsgUtil.buildReqMsg(msgSignMap, MsgA0011.A0011REQ);
		String resSign = SocketUtil.sendRec(msgSign);
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resSign);
		String resCode = headMap.get(MsgField.h_rsp_code.getFieldCode()).getValue();
		if (!InvestErrCodeType.DT0000.getValue().equals(resCode)) {
			throw new BizException(InvestErrCodeType.valueOf(resCode).getName());
		} else {
			Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resSign, MsgA0011.A0011RES);
			return bodyMap.get(MsgField.serial_no.getFieldCode()).getValue();
		}
	}
	public String updateSignContract(String acctNo, String payChnlNo, HttpServletRequest request,
			HttpServletResponse response) throws BizException {
		SignContract signContract = signContractDao.getUniqueResult(SignContract.class, "investAcctNo", acctNo);
		//此时签约记录的状态是更改待确认
		signContract.setState(SignState.UNCONFIRM_UPDATE.getValue());
		signContract.setPayChnlNo(payChnlNo);
		CustInfo custInfo = custInfoDao.findById(signContract.getCustId());
		String authId = getAuthId(signContract, custInfo.getIdCard());
		// 签约授权号更改
		signContract.setAccreditId(authId);
		signContractDao.update(signContract);
		if (PayChnlType.BANK_105.getValue().equals(signContract.getPayChnlType())) {
			Map<String, String> map = new HashMap<String, String>();
			try {
				map.put("AUTHID", authId);
				map.put("PAYMENT", "1000");
				map.put("LIMIT", "1000");
				//map.put("UName", custInfo.getName());
				map.put("UName", custInfo.getName());
				map.put("IdType", custInfo.getCardType());
				map.put("IdNumber", custInfo.getIdCard());
				map.put("EPAYNO", signContract.getPayChnlNo());
				CcbSignUtil.sign(request, response, map);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException(e.getMessage());
			}
		}
		return authId;
	}



}
