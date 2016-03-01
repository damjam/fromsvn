package com.ylink.cim.manage.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.state.EmployeeState;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.TransferType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.EmployeeDao;
import com.ylink.cim.manage.domain.Employee;
import com.ylink.cim.manage.service.EmpTransferService;
import com.ylink.cim.manage.service.EmployeeService;
import com.ylink.cim.util.CopyPropertyUtil;

import flink.IdFactoryHelper;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
@Component("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private EmpTransferService empTransferService;
	@Override
	public void save(Employee model, UserInfo sessionUser) throws BizException{
		model.setCreateDate(DateUtil.getCurrent());
		model.setCreateUser(sessionUser.getUserName());
		model.setId(IdFactoryHelper.getId("EMPLOYEE_ID"));
		model.setState(EmployeeState.NORMAL.getValue());
		employeeDao.save(model);
	}

	@Override
	public void changeState(String id, String toState, UserInfo userInfo) throws BizException{
		Employee employee = employeeDao.findByIdWithLock(id);
		String state = employee.getState();
		Assert.notEquals(EmployeeState.LEAVE.getValue(), state, "员工已离职，不可进行相关操作");
		if (EmployeeState.QUIT.getValue().equals(state)) {
			empTransferService.addEmpTransfer(id, TransferType.QUIT.getValue(), "离职", userInfo);
		}
		employee.setState(state);
		employee.setUpdateDate(DateUtil.getCurrent());
		employeeDao.update(employee);
	}

	@Override
	public void addVocation(String empId, String transferDetail, UserInfo userInfo) throws BizException {
		empTransferService.addEmpTransfer(empId, TransferType.LEAVE.getValue(), transferDetail, userInfo);
		Employee employee = employeeDao.findByIdWithLock(empId);
		employee.setState(EmployeeState.LEAVE.getValue());
		employee.setUpdateDate(DateUtil.getCurrent());
		employeeDao.update(employee);
	}

	@Override
	public void transfer(Employee model, UserInfo sessionUser) throws BizException{
		String id = model.getId();
		Employee employee = employeeDao.findByIdWithLock(id);
		if (employee.getPosition().equals(model.getPosition()) && employee.getBranchNo().equals(model.getBranchNo())) {
			throw new BizException("没有要保存的信息");
		}
		StringBuilder transferDetail = new StringBuilder();
		if (!StringUtils.equals(model.getBranchNo(), employee.getBranchNo())) {
			transferDetail.append("由"+ParaManager.getBranches(true).get(employee.getBranchNo()));
			transferDetail.append(ParaManager.getSysDict(SysDictType.PositionType.getValue()).get(employee.getPosition()));
			transferDetail.append("调动到"+ParaManager.getBranches(true).get(model.getBranchNo()));
			transferDetail.append(ParaManager.getSysDict(SysDictType.PositionType.getValue()).get(model.getPosition()));
		}else {
			transferDetail.append("岗位由"+ParaManager.getSysDict(SysDictType.PositionType.getValue()).get(employee.getPosition()));
			transferDetail.append("变更为"+ParaManager.getSysDict(SysDictType.PositionType.getValue()).get(model.getPosition()));
		}
		empTransferService.addEmpTransfer(model.getId(), TransferType.TRANSFER.getValue(), transferDetail.toString(), sessionUser);
	}

	@Override
	public void update(Employee model, UserInfo sessionUser) throws BizException {
		Employee employee = employeeDao.findByIdWithLock(model.getId());
		CopyPropertyUtil.copyPropertiesIgnoreNull(model, employee);
		employee.setUpdateDate(DateUtil.getCurrent());
		employeeDao.update(employee);
	}
}
