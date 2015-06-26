package com.ylink.cim.cust.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.RoleInfoDao;
import com.ylink.cim.admin.dao.UserInfoDao;
import com.ylink.cim.admin.dao.UserRoleDao;
import com.ylink.cim.admin.domain.RoleInfo;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserRole;
import com.ylink.cim.admin.domain.UserRoleId;
import com.ylink.cim.common.msg.handle.MsgA0010;
import com.ylink.cim.common.msg.handle.MsgA0011;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.state.CustState;
import com.ylink.cim.common.state.SignState;
import com.ylink.cim.common.type.BusiType;
import com.ylink.cim.common.type.CustFromType;
import com.ylink.cim.common.type.CustType;
import com.ylink.cim.common.type.UserType;
import com.ylink.cim.common.util.SocketUtil;
import com.ylink.cim.cust.dao.CustInfoDao;
import com.ylink.cim.cust.domain.CustInfo;
import com.ylink.cim.cust.service.CustInfoService;
import com.ylink.cim.invest.dao.SignContractDao;
import com.ylink.cim.invest.domain.SignContract;
import com.ylink.cim.invest.service.SignContractService;

import flink.etc.BizException;
import flink.util.DateUtil;
@Component("custInfoService")
public class CustInfoServiceImpl implements CustInfoService {

	@Autowired
	private CustInfoDao custInfoDao;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private RoleInfoDao roleInfoDao;
	@Autowired
	private SignContractDao signContractDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private SignContractService signContractService;
	public CustInfoDao getCustInfoDao() {
		return custInfoDao;
	}

	public void setCustInfoDao(CustInfoDao custInfoDao) {
		this.custInfoDao = custInfoDao;
	}

	public CustInfo getCustInfoById(String custId) {
		return custInfoDao.findById(custId);
	}

	public CustInfo getCustInfoByUserId(String userId) {
		return (CustInfo)custInfoDao.getUniqueResult(CustInfo.class, "userId", userId);
	}

	public String addCustBasicInfo(CustInfo custInfo) throws BizException{
		custInfo.setCreateTime(new Date());
		custInfoDao.save(custInfo);
		UserInfo userInfo = new UserInfo();
		userInfo.setLoginId(custInfo.getMobile());
		userInfo.setLoginPwd(custInfo.getLoginPwd());
		userInfo.setUserType(UserType.CUSTOM.getValue());
		userInfo.setCreateTime(custInfo.getCreateTime());
		userInfo.setUpdateTime(custInfo.getCreateTime());
		userInfo.setUserName(custInfo.getMobile());
		userInfoDao.save(userInfo);
		custInfo.setUserId(userInfo.getUserId());
		custInfo.setFromType(CustFromType.TYPE_0.getValue());
		custInfoDao.update(custInfo);
		UserRole userRole = new UserRole();
		UserRoleId id = new UserRoleId();
		id.setUserId(userInfo.getUserId());
		//if (CustType.TYPE_NORMAL.getValue().equals(custInfo.getCustType())) {
			RoleInfo roleInfo = (RoleInfo)roleInfoDao.getUniqueResult(RoleInfo.class, "roleName", CustType.TYPE_NORMAL.getName());
			id.setRoleId(roleInfo.getRoleId());
			userRole.setId(id);
		//}
		roleInfoDao.save(userRole);
		return custInfo.getId();
	}

	public void updateSignResult(String custId, boolean result) {
		SignContract signContract = (SignContract)signContractDao.getUniqueResult(SignContract.class, "custId", custId);
		
		if (result) {
			signContractDao.lock(signContract, LockMode.UPGRADE);
			signContract.setState(SignState.SIGNED.getValue());
			signContractDao.update(signContract);
		} else {
			signContractDao.delete(signContract);
		}
		//TODO
		
		//访问定投核心,完成签约
	}

	public String addMemberInfo(CustInfo custInfo, String branchNo, HttpServletRequest request, HttpServletResponse response) throws BizException {
		CustInfo cust = custInfoDao.findByIdWithLock(custInfo.getId());
		cust.setCardType(custInfo.getCardType());
		cust.setIdCard(custInfo.getIdCard());
		cust.setBirthday(custInfo.getBirthday());
		cust.setSex(custInfo.getSex());
		cust.setName(custInfo.getName());
		//签约成功前，客户类型仍为普通客户
		//cust.setCustType(CustType.TYPE_MEMBER.getValue());
		cust.setAddr(custInfo.getAddr());
		cust.setAreaCode(custInfo.getAreaCode());
		cust.setState(CustState.NORMAL.getValue());
		custInfoDao.update(cust);
		//保存签约信息
		SignContract contract = new SignContract();
		contract.setPayChnlNo(custInfo.getPayChnlNo());
		contract.setPayChnlType(custInfo.getPayChnlType());
		contract.setCustId(custInfo.getId());
		contract.setBranchNo(branchNo);
		String accreditId = signContractService.addSignContract(contract, request, response);
		return accreditId;
	}
	
	
	public void addMemberRole(String custId) throws BizException {
		CustInfo custInfo = custInfoDao.findById(custId);
		RoleInfo normalCustRole = (RoleInfo)roleInfoDao.getUniqueResult(RoleInfo.class, "roleName", CustType.TYPE_NORMAL.getName());
		UserRoleId userRoleId = new UserRoleId();
		userRoleId.setRoleId(normalCustRole.getRoleId());
		userRoleId.setUserId(custInfo.getUserId());
		UserRole userRole = userInfoDao.findById(UserRole.class, userRoleId);
		if (userRole != null) {
			userRoleDao.delete(userRole);
		}
		RoleInfo memberCustRole = (RoleInfo)roleInfoDao.getUniqueResult(RoleInfo.class, "roleName", CustType.TYPE_MEMBER.getName());
		UserRoleId newUserRoleId = new UserRoleId();
		newUserRoleId.setRoleId(memberCustRole.getRoleId());
		newUserRoleId.setUserId(custInfo.getUserId());
		UserRole newUserRole = new UserRole();
		newUserRole.setId(newUserRoleId);
		roleInfoDao.save(newUserRole);
	}
	
	/**
	 * 发报文到渠道开户
	 * @param map
	 * @return
	 * @throws BizException
	 */
	public Map<String, MsgField> addCustInfo(CustInfo custInfo)throws BizException{
		
		Map<String, String> map =new HashMap<String, String>();
		map.put(MsgField.h_exch_code.getFieldCode(), "A0010");
		map.put(MsgField.h_bank_no.getFieldCode(), custInfo.getBranchNo());
		map.put(MsgField.acct_no.getFieldCode()	, custInfo.getId());
		map.put(MsgField.cust_name.getFieldCode()	, custInfo.getName());
		map.put(MsgField.cert_type.getFieldCode()	, custInfo.getCardType());
		map.put(MsgField.cert_num.getFieldCode()	, custInfo.getIdCard());
		map.put(MsgField.area_code.getFieldCode()	, custInfo.getAreaCode());
		map.put(MsgField.mobile_phone.getFieldCode()	, custInfo.getMobile());
		map.put(MsgField.tel.getFieldCode()	,"");
		map.put(MsgField.fax.getFieldCode()	,"");
		map.put(MsgField.addr.getFieldCode()	, custInfo.getAddr());
		map.put(MsgField.zipcode.getFieldCode()	, "");
		map.put(MsgField.email.getFieldCode()	, custInfo.getEmail());
		//开户
		String msg = MsgUtil.buildReqMsg(map, MsgA0010.A0010REQ);
		String resMsg = SocketUtil.sendRec(msg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA0010.A0010RES);
//		String	aipNo=bodyMap.get(MsgField.aip_no.getFieldCode()).getValue();
		return bodyMap;
	}
	
	/**
	 * 发报文到渠道签约
	 * @return
	 * @throws BizException
	 */
	
	public Map<String, MsgField> addSignContract(SignContract signContract,String bankno)throws BizException{
		//签约
		Map<String, String> msgSignMap=new HashMap<String,String>();
		msgSignMap.put(MsgField.h_exch_code.getFieldCode(), "A0011");
		msgSignMap.put(MsgField.h_bank_no.getFieldCode(), bankno);
		msgSignMap.put(MsgField.aip_no.getFieldCode(), signContract.getInvestAcctNo());
		msgSignMap.put(MsgField.account_no.getFieldCode(), signContract.getPayChnlNo());
		
		String msgSign = MsgUtil.buildReqMsg(msgSignMap, MsgA0011.A0011REQ);
		String resSign=SocketUtil.sendRec(msgSign);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resSign, MsgA0011.A0011RES);
		return bodyMap;
	}
	
	/**
	 * 保存签约信息，此时并未与银行签约，签约成功后更改签约记录状态为已签约
	 * @param custInfo
	 * @param branchNo
	 * @return
	 * @throws BizException
	 */
	@Deprecated
	private void addSignContract(CustInfo custInfo, String branchNo) throws BizException {
		SignContract signContract = new SignContract();
		signContract.setCreateTime(new Date());
		signContract.setCustId(custInfo.getId());
		signContract.setExtend(custInfo.getExtend());
		signContract.setPayChnlNo(custInfo.getPayChnlNo());
		signContract.setBusiType(custInfo.getBusiType());
		signContract.setPayChnlType(custInfo.getPayChnlType());
		signContract.setState(SignState.UNCONFIRM_ADD.getValue());
		if (StringUtils.isEmpty(signContract.getBusiType())) {
			signContract.setBusiType(BusiType.GOLD.getValue());
		}
		signContract.setCreateTime(DateUtil.getCurrent());
		signContractDao.save(signContract);
	}
	
	public void updateCustInfo(CustInfo custInfo) throws BizException {
		CustInfo cust = custInfoDao.findByIdWithLock(custInfo.getId());
		custInfo.setEmail(custInfo.getEmail());
		if (!custInfo.getMobile().equals(cust.getMobile())) {
			cust.setMobile(custInfo.getMobile());
			UserInfo userInfo = userInfoDao.findByIdWithLock(cust.getUserId());
			userInfo.setLoginId(custInfo.getMobile());
			userInfoDao.update(userInfo);
		}
		if ("A".equals(custInfo.getSubsEmail())) {
			cust.setvMailStr(custInfo.getvMailStr());
			cust.setvSendTime(custInfo.getvSendTime());
		}
		cust.setEmail(custInfo.getEmail());
		cust.setAddr(custInfo.getAddr());
		cust.setBirthday(custInfo.getBirthday());
		cust.setSubsEmail(custInfo.getSubsEmail());
		cust.setSubsPhone(custInfo.getSubsPhone());
		custInfoDao.update(cust);
	}

	public void resetCustPwd(String custId, String loginPwd) throws BizException {
		CustInfo custInfo = custInfoDao.findById(custId);
		String userId = custInfo.getUserId();
		UserInfo userInfo = userInfoDao.findById(userId);
		userInfo.setLoginPwd(loginPwd);
		//密码重置后错误次数及时间清空
		userInfo.setErrorTime(null);
		userInfo.setPwdErrorTimes(null);
		userInfoDao.update(userInfo);
	}
	
}
