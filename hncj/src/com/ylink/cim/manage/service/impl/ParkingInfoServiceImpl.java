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
import com.ylink.cim.manage.dao.ParkingInfoDao;
import com.ylink.cim.manage.domain.ParkingInfo;
import com.ylink.cim.manage.service.ParkingInfoService;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("parkingInfoService")
public class ParkingInfoServiceImpl implements ParkingInfoService {

	@Autowired
	private ParkingInfoDao parkingInfoDao;
	@Autowired
	private IdFactoryService idFactoryService;

	@Override
	public void saveOrUpdate(ParkingInfo parkingInfo, UserInfo userInfo)
			throws BizException {
		parkingInfo.setBranchNo(userInfo.getBranchNo());
		parkingInfoDao.saveOrUpdate(parkingInfo);
	}

	@Override
	public void save(ParkingInfo parkingInfo, UserInfo userInfo)
			throws BizException {
		String id = idFactoryService.generateId(Constants.PARKING_INFO_ID);
		parkingInfo.setId(id);
		parkingInfo.setBranchNo(userInfo.getBranchNo());
		parkingInfo.setCreateDate(DateUtil.getCurrent());
		parkingInfo.setCreateUser(userInfo.getUserName());
		parkingInfoDao.save(parkingInfo);
	}

	@Override
	public void update(ParkingInfo parkingInfo, UserInfo userInfo)
			throws BizException {
		parkingInfo.setBranchNo(userInfo.getBranchNo());
		parkingInfoDao.update(parkingInfo);
	}

	@Override
	public void delete(String id, UserInfo sessionUser) throws BizException {
		parkingInfoDao.deleteById(id);

	}

	@Override
	public Integer addFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws BizException{
		Integer totalCnt = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> rows = list.get(i);
			for (int j = 0; j < rows.size(); j++) {
				Map<String, Object> map = rows.get(j);
				ParkingInfo info = new ParkingInfo();
				String sn = MapUtils.getString(map, "sn");
				Map<String, Object> params = new HashMap<>();
				params.put("sn", sn);
				params.put("branchNo", sessionUser.getBranchNo());
				Assert.isNull(parkingInfoDao.findBy(params), "已存在编号为"+sn+"的车位信息");
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
				parkingInfoDao.save(info);
				totalCnt++;
			}
		}
		return totalCnt;
	}

}
