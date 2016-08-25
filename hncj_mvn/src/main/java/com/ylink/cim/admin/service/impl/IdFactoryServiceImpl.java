package com.ylink.cim.admin.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.IdFactoryDao;
import com.ylink.cim.admin.domain.IdFactory;
import com.ylink.cim.admin.service.IdFactoryService;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("idFactoryService")
public class IdFactoryServiceImpl implements IdFactoryService {
	@Autowired
	private IdFactoryDao idFactoryDao;

	@Override
	public void deleteIdFactory(String seqIdName) throws BizException {
		this.idFactoryDao.deleteIdFactory(seqIdName);
	}

	@Override
	public String generateId(String seqIdName) throws BizException {

		IdFactory idFactory = this.idFactoryDao.getIdFactory(seqIdName);
		if (null == idFactory) {
			//ExceptionUtils.logBizException(IdFactoryDaoImpl.class, "不存在对的id: " + seqIdName);
			idFactory = new IdFactory();
			idFactory.setSeqIdName(seqIdName);
			idFactory.setInitValue("1000");
			idFactory.setDateLength("8");
			idFactoryDao.save(idFactory);
		}

		if (StringUtils.isEmpty(idFactory.getInitValue())) {
			idFactory.setInitValue("1000");
			// ExceptionUtils.logBizException(IdFactoryHibernateDaoImpl.class,
			// "未设置初始值");
		}

		if (StringUtils.isEmpty(idFactory.getCurrentValue())) {
			idFactory.setCurrentValue(idFactory.getInitValue());
		}

		if (StringUtils.isEmpty(idFactory.getStepValue())) {
			idFactory.setStepValue("1");
		}

		if (StringUtils.isEmpty(idFactory.getDirection())) {
			idFactory.setDirection(Constants.LEFT);
			// ExceptionUtils.logBizException(IdFactoryHibernateDaoImpl.class,
			// "未设置填充方向");
		}
		/*
		 * if(StringUtils.isEmpty(idFactory.getFillValue())){
		 * idFactory.setFillValue(""); }
		 */

		int dateLength = Integer.parseInt(idFactory.getDateLength());
		String currentDate = DateUtil.getCurrentDateTime();

		String dateVal = currentDate.substring(0, dateLength);

		String currentValue = idFactory.getCurrentValue();
		long curVal = Long.parseLong(currentValue);

		String stepValue = idFactory.getStepValue();
		int stepVal = Integer.parseInt(stepValue);

		curVal = curVal + stepVal;
		idFactory.setCurrentValue(String.valueOf(curVal));

		// int maxLength=Integer.parseInt(idFactory.getMaxLength());
		int maxLength = 8;
		String retVal = "";
		
		if (currentValue.length() < maxLength) {
			if (Constants.LEFT.equals(idFactory.getDirection())) {
				retVal = dateVal + curVal;
				retVal = StringUtils.leftPad(retVal, maxLength, idFactory.getFillValue());
			} else if (Constants.RIGHT.equals(idFactory.getDirection())) {
				retVal = dateVal + curVal;
				retVal = StringUtils.rightPad(retVal, maxLength, idFactory.getFillValue());
			} else {
				if (Constants.CENTER.equals(idFactory.getDirection())) {
					retVal = dateVal
							+ StringUtils.leftPad(String.valueOf(curVal), maxLength - dateLength,
									idFactory.getFillValue());
				}
			}
		}
		if (StringUtils.isNotEmpty(idFactory.getPrefix())) {
			retVal = idFactory.getPrefix() + retVal;
		}
		this.idFactoryDao.update(idFactory);
		return retVal;
	}

	@Override
	public void saveIdFactory(IdFactory idFactory) throws BizException {
		this.idFactoryDao.saveIdFactory(idFactory);

	}

	public void setIdFactoryDao(IdFactoryDao idFactoryDao) {
		this.idFactoryDao = idFactoryDao;
	}

}
