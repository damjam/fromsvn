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
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.SexType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.ContactDao;
import com.ylink.cim.manage.dao.MerchantInfoDao;
import com.ylink.cim.manage.domain.Contact;
import com.ylink.cim.manage.service.ContactService;
import com.ylink.cim.util.CopyPropertyUtil;
import com.ylink.cim.util.ExportExcelUtil;
import com.ylink.cim.util.ReadExcelUtil;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.util.StringUtil;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class ContactAction extends BaseAction implements ModelDriven<Contact> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private ContactService contactService;
	@Autowired
	private MerchantInfoDao merchantInfoDao;

	public String toEdit() throws Exception {
		SexType.setInReq(request);

		String id = model.getId();
		if (!StringUtils.isEmpty(id)) {
			Contact contact = (Contact) contactDao.findById(id);
			BeanUtils.copyProperties(model, contact);
		}
		return "edit";
	}

	public String doEdit() throws Exception {
		try {
			String id = model.getId();
			if (StringUtils.isEmpty(id)) {
				Map<String, Object> params = getParaMap();
				params.put("mobile", model.getMobile());
				Assert.isEmpty(contactDao.findList(params), LogUtils.r("手机号码{?}已存在", model.getMobile()));
				contactService.save(model, getSessionUser(request));
			}else {
				Contact contact = contactDao.findById(id);
				Map<String, Object> params = getParaMap();
				params.put("mobile", model.getMobile());
				params.put("excludeId", model.getId());
				Assert.isEmpty(contactDao.findList(params), LogUtils.r("手机号码{?}已存在", model.getMobile()));
				
				CopyPropertyUtil.copyPropertiesIgnoreNull(model, contact);
				contactService.update(contact);
			}
			setSucResult("操作成功", request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toEdit();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败" + e.getMessage(), request);
			return toEdit();
		}
		return "toMain";
	}

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		map.put("contactName", model.getContactName());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		map.put("mobile", model.getMobile());
		map.put("remark", model.getRemark());
		Paginater paginater = contactDao.findPager(map, getPager(request));
		Map<String, Object> params = getParaMap();
		params.put("branchNo", getSessionBranchNo(request));
		/*List<MerchantInfo> list = merchantInfoDao.findList(params);
		BoUtils.addProperty(paginater.getList(), "merchantNo", "merchantName",
				list, "id", "mrname");*/
		saveQueryResult(request, paginater);
		return "list";
	}

	public String del() throws Exception {
		String id = model.getId();
		try {
			contactService.delete(id);
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, "操作失败" + e.getMessage(), request);
		}
		return list();
	}

	public String export() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("contactName", model.getContactName());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		map.put("mobile", model.getMobile());
		map.put("remark", model.getRemark());
		Paginater paginater = contactDao.findPager(map, null);
		List<Contact> list = paginater.getList();
		String branchNo = super.getSessionBranchNo(request);
		List<List<Object>> dataList = new ArrayList<>();
		for (int i = 0, size = list.size(); i < size; i++) {
			Contact info = (Contact)list.get(i);
			List<Object> obj = new ArrayList<>();
			obj.add(info.getContactName());
			obj.add(info.getSex());
			obj.add(info.getIndustry());
			obj.add(info.getPosition());
			obj.add(info.getWorkUnit());
			obj.add(info.getMobile());
			obj.add(info.getRemark());
			dataList.add(obj);
 		}
		
		String branchName = BranchType.valueOf(branchNo).getName();
		String fileName = branchName+"联系人-"+DateUtil.getCurrentDate()+"."+ParaManager.getExcelType(getSessionBranchNo(request));
		String title = "";
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ExportRule");
		String excelType = ParaManager.getExcelType(branchName);
		//ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, buildingList.toArray(new String[buildingList.size()]), rules, dataList, excelType, response);
		ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, "联系人信息", rules, dataList, excelType, response);
		exportExcelUtil.exportSheet();
		return null;
	}
	public String toImport() throws Exception {
		request.setAttribute("templateName", "联系人信息导入模板."+ParaManager.getExcelType(getSessionBranchNo(request)));
		return "import";
	}
	public String doImport() throws Exception {
		try{
			File file = this.getFile();
			FileInputStream fis = new FileInputStream(file);
			String suffix = fileFileName.substring(fileFileName.lastIndexOf(".")+1);//扩展名
			List<Map<String, String>> houseInfoRule = (List<Map<String, String>>)SpringContext.getService(StringUtil.class2Object(this.getModel().getClass().getName())+"ImportRule");
			List<List<Map<String, Object>>> list = ReadExcelUtil.read(fis, suffix, houseInfoRule);
			int cnt = contactService.addFromExcel(list, getSessionUser(request));
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
	public Contact getModel() {
		return model;
	}

	private Contact model = new Contact();

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
