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
	
	public String getBankAcctInfo() {
		return bankAcctInfo;
	}

	public void setBankAcctInfo(String bankAcctInfo) {
		this.bankAcctInfo = bankAcctInfo;
	}

	public String getAccreditId() {
		return accreditId;
	}

	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}
	
	public String getDebt() {
		return debt;
	}


	public void setDebt(String debt) {
		this.debt = debt;
	}

	
	public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public String getGoldTypeName() {
		return goldTypeName;
	}


	public void setGoldTypeName(String goldTypeName) {
		this.goldTypeName = goldTypeName;
	}


	public String getCityCode() {
		return cityCode;
	}

	
	public String getTakeBankName() {
		return takeBankName;
	}


	public void setTakeBankName(String takeBankName) {
		this.takeBankName = takeBankName;
	}


	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getTakeBank() {
		return takeBank;
	}

	public void setTakeBank(String takeBank) {
		this.takeBank = takeBank;
	}

	public String getTakeDate() {
		return takeDate;
	}

	public void setTakeDate(String takeDate) {
		this.takeDate = takeDate;
	}

	public String getGoldType() {
		return goldType;
	}

	public void setGoldType(String goldType) {
		this.goldType = goldType;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/*public String getUpdateSymbol() {
		return updateSymbol;
	}

	public void setUpdateSymbol(String updateSymbol) {
		this.updateSymbol = updateSymbol;
	}*/

	public String getPayChnlAlias() {
		return payChnlAlias;
	}

	public void setPayChnlAlias(String payChnlAlias) {
		this.payChnlAlias = payChnlAlias;
	}

	public String[] getInvestDays() {
		return investDays;
	}

	public void setInvestDays(String[] investDays) {
		this.investDays = investDays;
	}

	public String getPlanState() {
		return planState;
	}

	public void setPlanState(String planState) {
		this.planState = planState;
	}

	public String getAipAmount() {
		return aipAmount;
	}

	public void setAipAmount(String aipAmount) {
		this.aipAmount = aipAmount;
	}

	public String getAipMode() {
		return aipMode;
	}

	public void setAipMode(String aipMode) {
		this.aipMode = aipMode;
	}

	public String getAipType() {
		return aipType;
	}

	public void setAipType(String aipType) {
		this.aipType = aipType;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getPayChnlTypeName() {
		return payChnlTypeName;
	}

	public void setPayChnlTypeName(String payChnlTypeName) {
		this.payChnlTypeName = payChnlTypeName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getPayChnlType() {
		return payChnlType;
	}

	public void setPayChnlType(String payChnlType) {
		this.payChnlType = payChnlType;
	}

	public String getPayChnlNo() {
		return payChnlNo;
	}

	public void setPayChnlNo(String payChnlNo) {
		this.payChnlNo = payChnlNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getSignContractId() {
		return signContractId;
	}

	public void setSignContractId(String signContractId) {
		this.signContractId = signContractId;
	}

	public String getFirstAipDate() {
		return firstAipDate;
	}

	public void setFirstAipDate(String firstAipDate) {
		this.firstAipDate = firstAipDate;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getStartDate() {
		if (StringUtils.isBlank(startDate)) {
			startDate = DateUtil.getDateYYYYMMDD(DateUtil.addMonths(DateUtil.getCurrent(), -3));
		}
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		if (StringUtils.isBlank(endDate)) {
			endDate = DateUtil.getCurrentDate();
		}
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRedeemType() {
		return redeemType;
	}

	public void setRedeemType(String redeemType) {
		this.redeemType = redeemType;
	}

	public String getAipWeight() {
		return aipWeight;
	}

	public void setAipWeight(String aipWeight) {
		this.aipWeight = aipWeight;
	}

	public String getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}

	public String getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAipPeriod() {
		return aipPeriod;
	}

	public void setAipPeriod(String aipPeriod) {
		this.aipPeriod = aipPeriod;
	}

	
}
