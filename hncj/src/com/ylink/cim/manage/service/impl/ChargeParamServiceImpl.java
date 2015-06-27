package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.common.type.ChargeType;
import com.ylink.cim.common.type.ChargeWay;
import com.ylink.cim.manage.dao.ChargeItemDao;
import com.ylink.cim.manage.dao.ChargeParamDao;
import com.ylink.cim.manage.dao.ChargeParamItemDao;
import com.ylink.cim.manage.domain.ChargeItem;
import com.ylink.cim.manage.domain.ChargeParam;
import com.ylink.cim.manage.domain.ChargeParamItem;
import com.ylink.cim.manage.domain.ChargeParamItemId;
import com.ylink.cim.manage.service.ChargeParamService;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("chargeParamService")
public class ChargeParamServiceImpl implements ChargeParamService {
	@Autowired
	private ChargeParamDao chargeParamDao;
	@Autowired
	private ChargeItemDao chargeItemDao;
	@Autowired
	private ChargeParamItemDao chargeParamItemDao;
	@Autowired
	private IdFactoryService idFactoryService;

	@Override
	public void deleteItem(String id, UserInfo sessionUser) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemId", id);
		List<ChargeParamItem> list = chargeParamItemDao.findBy(params);
		if (list.size() > 0) {
			throw new BizException("�Ʒ�������ʹ�ã��޷�ɾ��");
		}
		chargeItemDao.deleteById(id);
	}

	@Override
	public void deleteParam(String id, UserInfo sessionUser)
			throws BizException {
		chargeParamDao.deleteById(id);
		chargeParamItemDao.deleteByParamId(id);
	}

	@Override
	public void deleteParamItem(String id, UserInfo sessionUser)
			throws BizException {
		chargeParamItemDao.deleteById(id);

	}

	@Override
	public void saveOrUpdateItem(ChargeItem chargeItem, UserInfo userInfo)
			throws BizException {
		String id = chargeItem.getId();
		if (StringUtils.isEmpty(chargeItem.getId())) {
			id = idFactoryService.generateId(Constants.CHARGE_ITEM_ID);
		}
		chargeItem.setId(id);
		chargeItem.setBranchNo(userInfo.getBranchNo());
		chargeItem.setCreateDate(DateUtil.getCurrent());
		chargeItem.setUpdateUser(userInfo.getUserName());
		StringBuilder ruleDesc = new StringBuilder();
		if (ChargeWay.UNIT.getValue().equals(chargeItem.getWay())) {
			ruleDesc.append("����:" + chargeItem.getUnitPrice() + "Ԫ");
			if (ChargeType.COMMON_SERVICE.getValue().equals(
					chargeItem.getItem())) {
				ruleDesc.append("ÿƽ��");
			}
		} else if (ChargeWay.SEG.getValue().equals(chargeItem.getWay())) {
			String segRule = chargeItem.getSegRule();
			String[] rules = segRule.split(";");
			for (int i = 0; i < rules.length; i++) {
				String rule = rules[i];
				String a = rule.substring(0, rule.indexOf(":"));
				String b = rule.substring(rule.indexOf(":") + 1, rule.length());
				ruleDesc.append(a + "��" + b + "Ԫÿ��");
				if (i != rules.length - 1) {
					ruleDesc.append(",");
				}
			}
		} else if (ChargeWay.STEP.getValue().equals(chargeItem.getWay())) {
			ruleDesc.append(chargeItem.getBaseFloor() + "����"
					+ chargeItem.getBasePrice() + "Ԫÿ��,");
			ruleDesc.append("ÿ����һ�����" + chargeItem.getStepPrice() + "Ԫ,");
			ruleDesc.append(chargeItem.getCapPrice() + "Ԫ�ⶥ");
		}
		chargeItem.setRuleDesc(ruleDesc.toString());
		chargeItemDao.saveOrUpdate(chargeItem);
	}

	@Override
	public void saveParam(ChargeParam chargeParam, UserInfo userInfo)
			throws BizException {
		String id = idFactoryService.generateId(Constants.CHARGE_PARAM_ID);
		chargeParam.setId(id);
		chargeParam.setBranchNo(userInfo.getBranchNo());
		chargeParam.setCreateDate(DateUtil.getCurrent());
		chargeParam.setCreateUser(userInfo.getUserName());
		chargeItemDao.save(chargeParam);
	}

	@Override
	public void saveParamItem(String id, String[] itemIds, UserInfo userInfo)
			throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", itemIds);
		List<Long> cnts = chargeItemDao.findItemNum(params);
		for (int i = 0; i < cnts.size(); i++) {
			if (cnts.get(i) > 1) {
				throw new BizException("ͬһ�Ʒ�����ֻ��ѡ��һ���Ʒ���");
			}
		}
		chargeParamItemDao.deleteByParamId(id);
		for (int i = 0; i < itemIds.length; i++) {
			ChargeParamItem cpi = new ChargeParamItem();
			ChargeParamItemId cpii = new ChargeParamItemId();
			cpii.setItemId(itemIds[i]);
			cpii.setParamId(id);
			cpi.setId(cpii);
			chargeParamItemDao.save(cpi);
		}
	}

	@Override
	public void updateParam(ChargeParam chargeParam, UserInfo userInfo)
			throws BizException {
		chargeParamDao.update(chargeParam);
	}

}
