package com.ylink.cim.manage.view;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.SexType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.EmpTransferDao;
import com.ylink.cim.manage.dao.EmployeeDao;
import com.ylink.cim.manage.domain.EmpTransfer;
import com.ylink.cim.manage.domain.Employee;
import com.ylink.cim.manage.service.EmployeeService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Scope("prototype")
@Component
public class EmployeeAction extends BaseAction implements ModelDriven<Employee> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private EmpTransferDao empTransferDao;
	
	@Override
	public Employee getModel() {
		return model;
	}
	private Employee model = new Employee();

	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("startCreateDate", model.getStartCreateDate());
		map.put("endCreateDate", model.getEndCreateDate());
		map.put("state", model.getState());
		map.put("tel", model.getTel());
		map.put("branchNo", model.getBranchNo());
		map.put("userInfo", getSessionUser(request));
		Paginater paginater = employeeDao.findPager(map, getPager(request));
		for(int i=0; i<paginater.getList().size(); i++){
			
		}
		saveQueryResult(request, paginater);
		initSelect();
		return "list";
	}
	public String toAdd() throws Exception {
		initSelect();
		return "add";
	}
	public String toEdit() throws Exception {
		Employee employee = employeeDao.findById(model.getId());
		BeanUtils.copyProperties(model, employee);
		initSelect();
		return "edit";
	}
	public String doAdd() throws Exception {
		try {
			employeeService.save(model, getSessionUser(request));
			setSucResult("数据已保存", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败" + e.getMessage(), request);
			return toEdit();
		}
		return "toMain";
	}
	public String doEdit() throws Exception {
		try {
			employeeService.update(model, getSessionUser(request));
			setSucResult("数据已保存", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "保存失败" + e.getMessage(), request);
			return toEdit();
		}
		return "toMain";
	}
	public String detail() throws Exception {
		try {
			String id = model.getId();
			Employee employee = employeeDao.findById(id);
			request.setAttribute("branchName", ParaManager.getBranches(true).get(employee.getBranchNo()));
			request.setAttribute("positionName", ParaManager.getAllPositions().get(employee.getPosition()));
			BeanUtils.copyProperties(model, employee);
			List<EmpTransfer> list = empTransferDao.findByEmpId(model.getId());
			request.setAttribute("transferSize", list.size());
			saveQueryResult(request, list, "transfers");
		} catch (Exception e) {
			setFailResult("系统错误", request);
			e.printStackTrace();
			return "toMain";
		}
		return "detail";
	}
	
	public String delete() throws Exception {
		try {
			String id = model.getId();
			employeeDao.deleteById(id);
			setSucResult("操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return "toMain";
	}
	public String changeState() throws Exception {
		try {
			String id = model.getId();
			String state = model.getState();
			employeeService.changeState(id, state, getSessionUser(request));
			setSucResult("操作成功", request);
		} catch (Exception e) {
			setFailResult("操作失败", request);
			e.printStackTrace();
		}
		return "toMain";
	}
	
	public String toTransfer() throws Exception {
		initSelect();
		return "transfer";
	}
	public String doTransfer() throws Exception {
		try{
			employeeService.transfer(model, getSessionUser(request));
			return "transfer";
		}catch(BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
		}catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
		}
		return toTransfer();
	}
	public String showTransfer() throws Exception {
		List<EmpTransfer> list = empTransferDao.findByEmpId(model.getId());
		saveQueryResult(request, list);
		return "transferList";
	}
	private void initSelect() {
		request.setAttribute("branches", ParaManager.getBranches(true));
		request.setAttribute("positionTypes", ParaManager.getSysDict(SysDictType.BranchPostType.getValue()));
		SexType.setInReq(request);
	}
	public String toAddVocation() throws Exception {
		
		return "vocation";
	}
	public String addVocation() throws Exception {
		try {
			EmpTransfer empTransfer = model.getEmpTransfer();
			if (empTransfer.getEndDate().compareTo(empTransfer.getBeginDate()) <= 0) {
				throw new BizException("结束日期必须在开始日期之后");
			}
			employeeService.addVocation(model, getSessionUser(request));
			setSucResult("操作成功", request);
		} catch(BizException e){
			setResult(false, e.getMessage(), request);
			e.printStackTrace();
			return toAddVocation();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toAddVocation();
		}
		return "toMain";
	}
	public String export() throws Exception {
		
		return null;
	}
	public String loadPosts() throws Exception {
		JSONObject object = new JSONObject();
		try {
			String branchNo = request.getParameter("branchNo");
			JSONArray array = new JSONArray();
			Map<String, String> positionMap = null;
			if ("0000".equals(branchNo)) {
				positionMap = ParaManager.getSysDict(SysDictType.CenterPostType.getValue());
			}else {
				positionMap = ParaManager.getSysDict(SysDictType.BranchPostType.getValue());
			}
			for (Map.Entry<String, String> entry : positionMap.entrySet()) {
				JSONObject position = new JSONObject();
				position.put("key", entry.getKey());
				position.put("value", entry.getValue());
				array.add(position);
			}
			object.put("positions", array);
			object.put("status", "1");
		} catch (Exception e) {
			object.put("status", "0");
		}
		respond(response, object.toString());
		return null;
	}
}
