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

	public String updateSignContract(String acctNo, String payChnlNo, HttpServletRequest request,
			HttpServletResponse response) throws BizException {
		SignContract signContract = signContractDao.getUniqueResult(SignContract.class, "investAcctNo", acctNo);
		//��ʱǩԼ��¼��״̬�Ǹ��Ĵ�ȷ��
		signContract.setState(SignState.UNCONFIRM_UPDATE.getValue());
		signContract.setPayChnlNo(payChnlNo);
		CustInfo custInfo = custInfoDao.findById(signContract.getCustId());
		String authId = getAuthId(signContract, custInfo.getIdCard());
		// ǩԼ��Ȩ�Ÿ���
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
	//16λ��Ȩ��
	private String getAuthId(SignContract contract, String idCard){
		StringBuilder builder = new StringBuilder();
		builder.append("C");//����ǰϵͳ
		builder.append(contract.getBranchNo());//4λ������
		builder.append(idCard.substring(idCard.length()-4, idCard.length()));//֤���ź�4λ
		builder.append(contract.getPayChnlType());//3λ�б�
		String payChnlNo = contract.getPayChnlNo();
		builder.append(payChnlNo.substring(payChnlNo.length()-4, payChnlNo.length()));//���п��ź�4λ
		return builder.toString();
	}

	/**
	 * ��Լ
	 * 
	 * @return
	 */
	public String cancelSuccess(String accreditId) throws BizException {
		SignContract signContract = signContractDao.getUniqueResult(SignContract.class, "accreditId", accreditId);
		String state = signContract.getState();
		String aipNo = signContract.getInvestAcctNo();
		//����
		String serialNo = cancelSignToCore(signContract.getInvestAcctNo(), signContract.getPayChnlType(), signContract.getPayChnlNo());
		if (SignState.CANCELING_ACCT.getValue().equals(state)) {
			// ����
			if (StringUtils.isNotEmpty(serialNo)) {
				//��Ͷ��������
				cancelAcctToCore(aipNo);
				
				signContract.setCancelTime(Calendar.getInstance().getTime());
				signContract.setState(SignState.CANCELED_ACCT.getValue());
				signContractDao.update(signContract);
				// ����Ƿ��п�ͨҵ��
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
		//��Լ������
		else if(SignState.CANCELING_SIGN.getValue().equals(state)){
			signContract.setState(SignState.UNSIGN.getValue());
			signContractDao.update(signContract);
		}
		return null;
	}

	/**
	 * ��Ͷ�˻�����,��ѯ�ͻ���Ͷ�˺�
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

	// ǩԼ�ɹ������
	public String signSuccess(Map<String, String> map) throws BizException {
		String accreditId = map.get("ACCREDIT_ID");
		SignContract contract = signContractDao.getUniqueResult(SignContract.class, "accreditId", accreditId);
		//������ȷ��
		if (SignState.UNCONFIRM_ADD.getValue().equals(contract.getState())) {
			String custId = contract.getCustId();
			CustInfo custInfo = custInfoDao.findById(custId);
			contract.setState(SignState.SIGNED.getValue());
			//��Ͷ���Ŀ�ͨҵ��
			String aipNo = openAcctToCore(custInfo);
			//��Ͷ����ǩԼ
			signToCore(aipNo, contract.getPayChnlType(), contract.getPayChnlNo());
			contract.setInvestAcctNo(aipNo);
			signContractDao.update(contract);
			serviceRecordDao.addServiceByContract(contract, accreditId.substring(1, 5));
			//��Ա��Ϣ
			if (CustType.TYPE_NORMAL.getValue().equals(custInfo.getCustType())) {
				//��ӻ�Ա��ɫ
				custInfoService.addMemberRole(custId);
				custInfo.setCustType(CustType.TYPE_MEMBER.getValue());
			}
			return aipNo;
		} else {
			//���Ĵ�ȷ��
			contract.setState(SignState.SIGNED.getValue());
			signContractDao.update(contract);
			signToCore(contract.getInvestAcctNo(), contract.getPayChnlType(), contract.getPayChnlNo());
			return contract.getInvestAcctNo();
		}
	}


	private String openAcctToCore(CustInfo custInfo) throws BizException {
		// ����
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

	private String cancelAcctToCore(String aipNo) throws BizException {
		// ����
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put(MsgField.h_exch_code.getFieldCode(), "A0017");

		Map<String, MsgField> bodyMap = MsgUtil.getResBody(msgMap, MsgA0017.A0017REQ, MsgA0017.A0017RES);
		String serialNo = bodyMap.get(MsgField.serial_no.getFieldCode()).getValue();
		return serialNo;
	}

	
	// ǩԼ
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
	//��Ͷ���Ľ�Լ
	private String cancelSignToCore(String aipNo, String payChnlType, String payChnlNo) throws BizException {
		Map<String, String> msgMap = new HashMap<String, String>();
		
		msgMap.put(MsgField.aip_no.getFieldCode(), aipNo);
		msgMap.put(MsgField.account_no.getFieldCode(), payChnlNo);
		//��Լ(A0013)
		msgMap.put(MsgField.h_exch_code.getFieldCode(), "A0013");
		Map<String, MsgField> bodyMap = MsgUtil.getResBody(msgMap, MsgA0013.A0013REQ, MsgA0013.A0013RES);
		String serialNo = bodyMap.get(MsgField.serial_no.getFieldCode()).getValue();
		return serialNo;
	}

	public String modifyInvestAcct(Map<String, String> msgMap) throws BizException {
		msgMap.put(MsgField.h_exch_code.getFieldCode(), "A0014");
		// String msg = MsgUtil.buildReqMsg(msgMap, MsgA0014.A0014REQ);
		// String resMsg = SocketUtil.sendRec(msg);
		Map<String, MsgField> bodyMap = MsgUtil.getResBody(msgMap, MsgA0014.A0014REQ, MsgA0014.A0014RES);
		String serialNo = bodyMap.get(MsgField.serial_no.getFieldCode()).getValue();
		return serialNo;
	}

	/**
	 * �鶨Ͷ�˺��µĿ��
	 */
	public Map<String, MsgField> queryInvestAcctStock(Map<String, String> msgMap) throws BizException {
		msgMap.put(MsgField.h_exch_code.getFieldCode(), "A2044");
		Map<String, MsgField> bodyMap = MsgUtil.getResBody(msgMap, MsgA2044.A2044REQ, MsgA2044.A2044RES);
		return bodyMap;
	}
	
	public boolean checkSign(String accreditId) throws BizException {
		SignContract contract = signContractDao.getUniqueResult(SignContract.class, "accreditId", accreditId);
		String signState = contract.getState();
		if (SignState.SIGNED.getValue().equals(signState)) {
			return true;
		}else {
			if (SignState.UNCONFIRM_ADD.getValue().equals(signState)) {
				//ǩԼʧ�ܣ�ֱ��ɾ��
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

	public boolean checkCancel(String aipNo) throws BizException {
		SignContract contract = signContractDao.getUniqueResult(SignContract.class, "investAcctNo", aipNo);
		String signState = contract.getState();
		//������
		if (SignState.CANCELED_ACCT.getValue().equals(signState)) {
			return true;
		} 
		//�ѽ�Լ
		else if(SignState.UNSIGN.getValue().equals(signState)){
			return true;
		} else {
			//��Լʧ�ܣ��ָ�״̬Ϊ��ǩԼ
			contract.setState(SignState.SIGNED.getValue());
			signContractDao.update(contract);
			return false;
		}
	}
	
	public void cancelSign(HttpServletRequest request, HttpServletResponse response, String acctNo) throws BizException{
		try {
			SignContract contract = signContractDao.getUniqueResult(SignContract.class, "investAcctNo", acctNo);
			//���������˻���Լ��
			contract.setState(SignState.CANCELING_SIGN.getValue());
			signContractDao.update(contract);
			CcbSignUtil.cancelSign(request, response, contract.getAccreditId());
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
	}
	public void cancelAcct(HttpServletRequest request, HttpServletResponse response, String acctNo) throws BizException {
		try {
			SignContract contract = signContractDao.getUniqueResult(SignContract.class, "investAcctNo", acctNo);
			//�ѽ�Լ
			if (SignState.UNSIGN.getValue().equals(contract.getState())) {
				//ֱ����������
				cancelAcctToCore(contract.getInvestAcctNo());
				return;
			}
			contract.setState(SignState.CANCELING_ACCT.getValue());
			signContractDao.update(contract);
			if (PayChnlType.BANK_105.getValue().equals(contract.getPayChnlType())) {
				CcbSignUtil.cancelSign(request, response, contract.getAccreditId());
			}else {
				//�����б�
			}
			
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
	}



}
