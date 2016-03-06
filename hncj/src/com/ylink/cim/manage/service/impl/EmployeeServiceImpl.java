package com.ylink.cim.manage.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.state.EmployeeState;
import com.ylink.cim.common.type.TransferType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.EmpTransferDao;
import com.ylink.cim.manage.dao.EmployeeDao;
import com.ylink.cim.manage.domain.EmpTransfer;
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
	@Autowired
	private EmpTransferDao empTransferDao;
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
		Assert.notEquals(EmployeeState.QUIT.getValue(), state, "Ա������ְ�����ɽ�����ز���");
		employee.setState(toState);
		employee.setUpdateDate(DateUtil.getCurrent());
		employeeDao.update(employee);
	}

	@Override
	public void addVocation(Employee model, UserInfo userInfo) throws BizException {
		EmpTransfer empTransfer = model.getEmpTransfer();
		String transferDetail = "��"+empTransfer.getBeginDate()+"�ݼ���"+empTransfer.getEndDate();
		empTransferService.addEmpTransfer(empTransfer.getEmpId(), TransferType.LEAVE.getValue(), transferDetail, empTransfer.getBeginDate(), empTransfer.getReason(), userInfo);
		Employee employee = employeeDao.findById(empTransfer.getEmpId());
		employee.setState(EmployeeState.LEAVE.getValue());
		employee.setUpdateDate(DateUtil.getCurrent());
		employeeDao.update(employee);
	}

	@Override
	public void transfer(Employee model, UserInfo sessionUser) throws BizException{
		String id = model.getId();
		EmpTransfer empTransfer = model.getEmpTransfer();
		Employee employee = employeeDao.findById(id);
		String transPosition = empTransfer.getTransPosition();
		String transBranchNo = empTransfer.getTransBranchNo();
		if(TransferType.QUIT.getValue().equals(empTransfer.getTransferType())){
			employee.setState(EmployeeState.QUIT.getValue());
			employee.setUpdateDate(DateUtil.getCurrent());
			employeeDao.update(employee);
			empTransferService.addEmpTransfer(model.getId(), TransferType.QUIT.getValue(), "��"+empTransfer.getTransferDate()+"��ְ", empTransfer.getReason(), empTransfer.getTransferDate(), sessionUser);
			
		}else if(TransferType.TRANSFER.getValue().equals(empTransfer.getTransferType())){
			if (employee.getPosition().equals(transPosition) && employee.getBranchNo().equals(transBranchNo)) {
				throw new BizException("û��Ҫ�������Ϣ");
			}
			StringBuilder transferDetail = new StringBuilder();
			Map<String, String> positionMap = ParaManager.getAllPositions();
			if (!StringUtils.equals(model.getBranchNo(), employee.getBranchNo())) {
				transferDetail.append("��"+ParaManager.getBranches(true).get(employee.getBranchNo()));
				transferDetail.append(positionMap.get(employee.getPosition()));
				transferDetail.append("������"+ParaManager.getBranches(true).get(model.getBranchNo()));
				transferDetail.append(positionMap.get(model.getPosition()));
			}else {
				transferDetail.append("��λ��"+positionMap.get(employee.getPosition()));
				transferDetail.append("���Ϊ"+positionMap.get(model.getPosition()));
			}
			empTransferService.addEmpTransfer(model.getId(), TransferType.TRANSFER.getValue(), empTransfer.getReason(), transferDetail.toString(), empTransfer.getTransferDate(), sessionUser);
		}
		
	}

	@Override
	public void update(Employee model, UserInfo sessionUser) throws BizException {
		Employee employee = employeeDao.findByIdWithLock(model.getId());
		CopyPropertyUtil.copyPropertiesIgnoreNull(model, employee);
		employee.setUpdateDate(DateUtil.getCurrent());
		employeeDao.update(employee);
	}

	@Override
	public void delete(String id) throws BizException {
		employeeDao.deleteById(id);
		empTransferDao.deleteByEmpId(id);
	}
}
