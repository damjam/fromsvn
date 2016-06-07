package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.manage.dao.CarInfoDao;
import com.ylink.cim.manage.domain.CarInfo;
import com.ylink.cim.manage.domain.ParkingInfo;
import com.ylink.cim.manage.service.CarInfoService;

import flink.IdFactoryHelper;
import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("carInfoService")
public class CarInfoServiceImpl implements CarInfoService {

	@Autowired
	private CarInfoDao carInfoDao;
	@Autowired
	private IdFactoryService idFactoryService;

	@Override
	public void saveOrUpdate(CarInfo carInfo, UserInfo userInfo) throws BizException {
		carInfo.setBranchNo(userInfo.getBranchNo());
		carInfoDao.saveOrUpdate(carInfo);
	}

	@Override
	public void save(CarInfo carInfo, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.CAR_INFO_ID);
		carInfo.setId(id);
		carInfo.setBranchNo(userInfo.getBranchNo());
		carInfo.setCreateDate(DateUtil.getCurrent());
		carInfo.setCreateUser(userInfo.getUserName());
		carInfoDao.save(carInfo);
	}

	@Override
	public void update(CarInfo carInfo, UserInfo userInfo) throws BizException {
		carInfo.setBranchNo(userInfo.getBranchNo());
		carInfoDao.update(carInfo);
	}

	@Override
	public void delete(String id, UserInfo sessionUser) throws BizException {
		carInfoDao.deleteById(id);

	}

	@Override
	public int addFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws BizException {
		Integer totalCnt = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> rows = list.get(i);
			for (int j = 0; j < rows.size(); j++) {
				Map<String, Object> map = rows.get(j);
				CarInfo info = new CarInfo();
				String carSn = MapUtils.getString(map, "carSn");
				Map<String, Object> params = new HashMap<>();
				params.put("carSn", carSn);
				Assert.isEmpty(carInfoDao.findList(params), "已存在牌号为"+carSn+"的车辆信息");
				try{
					BeanUtils.populate(info, map);
				}catch (Exception e){
					throw new BizException("程序出现异常:"+e.getMessage());
				}
				String remark = MapUtils.getString(map, "remark");
				info.setRemark(remark);
				info.setCreateDate(DateUtil.getCurrent());
				info.setCreateUser(sessionUser.getUserName());
				info.setBranchNo(sessionUser.getBranchNo());
				info.setId(IdFactoryHelper.getId(CarInfo.class));
				carInfoDao.save(info);
				totalCnt++;
			}
		}
		return totalCnt;
	}

}
