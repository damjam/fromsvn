package com.ylink.cim.invest.view;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import flink.util.DateUtil;

public class InvestInfoActionForm extends ActionForm {

	
	private static final long serialVersionUID = 1L;

	private String signContractId;
	private String custId;
	private String payChnlType;
	private String payChnlNo;//֧���˺�/����
	private Date createTime;
	private String extend;
	private Date cancelTime;
	private String state;
	private String busiCode;
	private String acctNo;//��Ͷ�˺�
	private String payChnlTypeName;
	private String planNo;//��Ͷ�ƻ���
	
	private String aipMode;//��Ͷ��ʽ
	
	private String aipType;//��Ͷ���
	
	private String aipAmount;//��Ͷ���
	
	private String planState;
	
	private String[] investDays;
	
	private String payChnlAlias;
	
	private String firstAipDate;//�״�Ͷ������
	
	private String minAmount;//��ѯ���ˣ���ʼ���
	private String maxAmount;//��ѯ���ˣ��������
	private String startDate;//��ѯʱ��ο�ʼ����
	private String endDate;//��ѯʱ��ν�������
	private String aipWeight;//��Ͷ����
	private String redeemType;//�������
	//private String updateSymbol;
	
	private String amount;
	
	private String balance;
	
	private String debt;
	
	private String goldType;
	
	private String goldTypeName;
	
	private String takeDate;
	
	private String takeBank;
	
	private String takeBankName;
	
	private String isDelay;//�Ƿ�����
	
	private String isEffect;//�Ƿ�������Ч
	
	private String orderNo;//��Ͷ�ƻ�������ˮ��
	
	private String aipPeriod;//��Ͷ����
	
	private String cityCode;
	
	private String cityName;
	
	private String accreditId;
	
	private String bankAcctInfo;
	
	public String getAccreditId() {
		return accreditId;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public String getAipAmount() {
		return aipAmount;
	}

	public String getAipMode() {
		return aipMode;
	}
	
	public String getAipPeriod() {
		return aipPeriod;
	}


	public String getAipType() {
		return aipType;
	}

	
	public String getAipWeight() {
		return aipWeight;
	}


	public String getAmount() {
		return amount;
	}


	public String getBalance() {
		return balance;
	}


	public String getBankAcctInfo() {
		return bankAcctInfo;
	}


	public String getBusiCode() {
		return busiCode;
	}

	
	public Date getCancelTime() {
		return cancelTime;
	}


	public String getCityCode() {
		return cityCode;
	}


	public String getCityName() {
		return cityName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCustId() {
		return custId;
	}

	public String getDebt() {
		return debt;
	}

	public String getEndDate() {
		if (StringUtils.isBlank(endDate)) {
			endDate = DateUtil.getCurrentDate();
		}
		return endDate;
	}

	public String getExtend() {
		return extend;
	}

	public String getFirstAipDate() {
		return firstAipDate;
	}

	public String getGoldType() {
		return goldType;
	}

	public String getGoldTypeName() {
		return goldTypeName;
	}

	public String[] getInvestDays() {
		return investDays;
	}

	public String getIsDelay() {
		return isDelay;
	}

	/*public String getUpdateSymbol() {
		return updateSymbol;
	}

	public void setUpdateSymbol(String updateSymbol) {
		this.updateSymbol = updateSymbol;
	}*/

	public String getIsEffect() {
		return isEffect;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getPayChnlAlias() {
		return payChnlAlias;
	}

	public String getPayChnlNo() {
		return payChnlNo;
	}

	public String getPayChnlType() {
		return payChnlType;
	}

	public String getPayChnlTypeName() {
		return payChnlTypeName;
	}

	public String getPlanNo() {
		return planNo;
	}

	public String getPlanState() {
		return planState;
	}

	public String getRedeemType() {
		return redeemType;
	}

	public String getSignContractId() {
		return signContractId;
	}

	public String getStartDate() {
		if (StringUtils.isBlank(startDate)) {
			startDate = DateUtil.getDateYYYYMMDD(DateUtil.addMonths(DateUtil.getCurrent(), -3));
		}
		return startDate;
	}

	public String getState() {
		return state;
	}

	public String getTakeBank() {
		return takeBank;
	}

	public String getTakeBankName() {
		return takeBankName;
	}

	public String getTakeDate() {
		return takeDate;
	}

	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public void setAipAmount(String aipAmount) {
		this.aipAmount = aipAmount;
	}

	public void setAipMode(String aipMode) {
		this.aipMode = aipMode;
	}

	public void setAipPeriod(String aipPeriod) {
		this.aipPeriod = aipPeriod;
	}

	public void setAipType(String aipType) {
		this.aipType = aipType;
	}

	public void setAipWeight(String aipWeight) {
		this.aipWeight = aipWeight;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public void setBankAcctInfo(String bankAcctInfo) {
		this.bankAcctInfo = bankAcctInfo;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public void setDebt(String debt) {
		this.debt = debt;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public void setFirstAipDate(String firstAipDate) {
		this.firstAipDate = firstAipDate;
	}

	public void setGoldType(String goldType) {
		this.goldType = goldType;
	}

	public void setGoldTypeName(String goldTypeName) {
		this.goldTypeName = goldTypeName;
	}

	public void setInvestDays(String[] investDays) {
		this.investDays = investDays;
	}

	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}

	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setPayChnlAlias(String payChnlAlias) {
		this.payChnlAlias = payChnlAlias;
	}

	public void setPayChnlNo(String payChnlNo) {
		this.payChnlNo = payChnlNo;
	}

	public void setPayChnlType(String payChnlType) {
		this.payChnlType = payChnlType;
	}

	public void setPayChnlTypeName(String payChnlTypeName) {
		this.payChnlTypeName = payChnlTypeName;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public void setPlanState(String planState) {
		this.planState = planState;
	}

	public void setRedeemType(String redeemType) {
		this.redeemType = redeemType;
	}

	public void setSignContractId(String signContractId) {
		this.signContractId = signContractId;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTakeBank(String takeBank) {
		this.takeBank = takeBank;
	}

	public void setTakeBankName(String takeBankName) {
		this.takeBankName = takeBankName;
	}

	public void setTakeDate(String takeDate) {
		this.takeDate = takeDate;
	}

	
}
