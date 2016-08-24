package com.ylink.cim.manage.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.AccountChangeType;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.AccountDetailDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.service.AccountService;
import com.ylink.cim.util.ExportExcelUtil;
import com.ylink.cim.util.ReadExcelUtil;

import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.util.StringUtil;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class AccountAction extends BaseAction implements ModelDriven<Account> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private AccountDetailDao accountDetailDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountService accountService;

	public String toDeposit() throws Exception {
		YesNoType.setInReq(request);

		String acctNo = request.getParameter("id");
		Account account = (Account) accountDao.findById(acctNo);
		BeanUtils.copyProperties(model, account);
		return "deposit";
	}

	public String deposit() throws Exception {
		try {
			Double balance = accountService.deposit(model.getId(),
					model.getAmount(), getSessionUser(request));
			String tip = "操作成功";
			if (balance < model.getAmount()) {
				tip += "，已扣除待缴水费"
						+ MoneyUtil.getFormatStr2(model.getAmount() - balance)
						+ "元";
			}
			tip += "，当前账户余额" + MoneyUtil.getFormatStr2(balance) + "元";
			setResult(true, tip, request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toDeposit();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败" + e.getMessage(), request);
			return toDeposit();
		}
		return list();
	}

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();

		map.put("houseSn", model.getHouseSn());
		map.put("state", model.getState());
		map.put("ownerName", model.getOwnerName());
		map.put("year", model.getYear());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = accountDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return "list";
	}

	public String toWithdraw() throws Exception {

		String acctNo = request.getParameter("id");
		Account account = (Account) accountDao.findById(acctNo);
		BeanUtils.copyProperties(model, account);
		// return forward("/pages/manage/account/withdraw.jsp");
		return "withdraw";
	}

	public String withdraw() throws Exception {
		try {

			accountService.withdraw(model.getId(), model.getAmount(),
					getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toWithdraw();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toWithdraw();
		}
		return list();
	}

	public String detail() throws Exception {
		AccountChangeType.setInReq(request);
		Map<String, Object> params = getParaMap();
		String acctNo = model.getAcctNo();
		Account account = accountDao.findById(acctNo);
		if (!BranchType.HQ_0000.getValue().equals(getSessionBranchNo(request))
				&& !account.getBranchNo().equals(getSessionBranchNo(request))) {
			setResult(false, "你无权查看该业主账户", request);
			list();
		}
		params.put("acctNo", model.getAcctNo());
		params.put("type", model.getType());
		params.put("startCreateDate", model.getStartCreateDate());
		params.put("endCreateDate", model.getEndCreateDate());
		Paginater paginater = accountDetailDao.findPager(params,
				getPager(request));
		saveQueryResult(request, paginater);
		// return forward("/pages/manage/account/accountDetailList.jsp");
		return "detail_list";
	}

	public String export() throws Exception {
		Map<String, Object> map = getParaMap();
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = accountDao.findPager(map, null);
		List<Account> list = paginater.getList();
		String branchNo = super.getSessionBranchNo(request);
		List<List<Object>> dataList = new ArrayList<>();
		for (int i = 0, size = list.size(); i < size; i++) {
			Account info = (Account)list.get(i);
			List<Object> obj = new ArrayList<>();
			obj.add(info.getHouseSn());
			obj.add(info.getOwnerName());
			obj.add(info.getBalance());//余额
			obj.add(info.getLastChangeDate());//最后变动时间
			if (StringUtils.isNotBlank(info.getLastTradeType())) {
				obj.add(TradeType.valueOf(info.getLastTradeType()).getName());//最后交易类型
			}else {
				obj.add("");
			}
			obj.add(info.getLastTradeAmt());//最后交易金额
			//obj.add(AccountState.valueOf(info.getState()).getName());//状态
			dataList.add(obj);
 		}
		
		String branchName = BranchType.valueOf(branchNo).getName();
		String fileName = branchName+"业主账户-"+DateUtil.getCurrentDate()+"."+ParaManager.getExcelType(getSessionBranchNo(request));
		String title = "";
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ExportRule");
		String excelType = ParaManager.getExcelType(branchName);
		//ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, buildingList.toArray(new String[buildingList.size()]), rules, dataList, excelType, response);
		ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, "账户信息", rules, dataList, excelType, response);
		exportExcelUtil.exportSheet();
		return null;
	}
	public String toImport() throws Exception {
		request.setAttribute("templateName", "业主账户信息导入模板."+ParaManager.getExcelType(getSessionBranchNo(request)));
		return "import";
	}
	public String toOpen() throws Exception {
		return "openAcct";
	}
	public String openAcct() throws Exception {
		try{
			accountService.openAcct(model.getHouseSn(), model.getAmount(), getSessionUser(request));
			setSucResult(request);
		}catch(BizException e){
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toOpen();
		}catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败", request);
			return toOpen();
		}
		return "toMain";
	}
	public String doImport() throws Exception {
		try{
			File file = this.getFile();
			FileInputStream fis = new FileInputStream(file);
			String suffix = fileFileName.substring(fileFileName.lastIndexOf(".")+1);//扩展名
			List<Map<String, String>> houseInfoRule = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ImportRule");
			List<List<Map<String, Object>>> list = ReadExcelUtil.read(fis, suffix, houseInfoRule);
			int cnt = accountService.addFromExcel(list, getSessionUser(request));
			setSucResult(MsgUtils.r("共导入{?}条记录", cnt), request);
		}catch (Exception e){
			e.printStackTrace();
			if (e instanceof BizException) {
				setResult(false, e.getMessage(), request);
			}else {
				setResult(false, "操作失败", request);
			}
			return toImport();
		}
		return "toMain";
	}
	
	@Override
	public Account getModel() {
		return model;
	}

	private Account model = new Account();

	private File file;
	private String fileFileName;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
}
