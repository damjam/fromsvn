package com.ylink.cim.invest.service;

import java.util.List;
import java.util.Map;

import com.ylink.cim.common.msg.handle.MsgField;

import flink.etc.BizException;

public interface GoldAcctService {

	String addRedeemCash(Map<String, String> map) throws BizException;

	String addRedeemGold(Map<String, String> map) throws BizException;
	
	//��ѯ�������
	List<Map<String, String>> findStoreBranch(Map<String, String> map) throws BizException;
	//��ѯ���
	Map<String, String> findGoldStock(Map<String, String> map, String[] investAcctNos) throws BizException;
	//����ղ�ѯ
	List<Map<String, MsgField>> findTakeDate(Map<String, String> map, String[] takeDate) throws BizException;
	//������в�ѯ
	Map<String, String> findStoreCity() throws BizException;
	//��ѯ���Ʒ��
	List<Map<String, MsgField>> findGoldVariety(Map<String, String> map) throws BizException;
	
}
