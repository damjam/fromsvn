package com.ylink.cim.manage.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.OwnerGrade;
import com.ylink.cim.common.type.SexType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.service.AccountService;
import com.ylink.cim.manage.service.OwnerInfoService;
import com.ylink.cim.util.ExcelReadUtil;
import com.ylink.cim.util.ExportExcelUtil;

import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.util.StringUtil;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class OwnerInfoAction extends BaseAction implements
		ModelDriven<OwnerInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private OwnerInfoDao ownerInfoDao;
	@Autowired
	private OwnerInfoService ownerInfoService;
	@Autowired
	private AccountService accountService;

	public String cancel() throws Exception {
		try {
			String id = request.getParameter("id");
			ownerInfoService.cancel(id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��", request);
		}
		return list();
	}

	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			ownerInfoService.delete(id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��", request);
		}
		return list();
	}

	public String doAdd() throws Exception {
		try {

			OwnerInfo ownerInfo = new OwnerInfo();
			BeanUtils.copyProperties(ownerInfo, model);
			ownerInfoService.add(ownerInfo, getSessionUser(request));
			setSucResult("��ӳɹ�", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			e.printStackTrace();
			return toAdd();
		} catch (Exception e) {
			setResult(false, "���ʧ��", request);
			e.printStackTrace();
			return toAdd();
		}
		return "toMain";
	}

	public String doImport() throws Exception {
		InputStream is = new FileInputStream(this.getFile());
		try {
			File file = this.getFile();
			FileInputStream fis = new FileInputStream(file);
			String suffix = fileFileName.substring(fileFileName.lastIndexOf(".")+1);//��չ��
			String importRule = StringUtil.class2Object(this.getModel().getClass().getName())+"ImportRule";
			List<Map<String, String>> houseInfoRule = (List<Map<String, String>>)SpringContext.getService(importRule);
			List<List<Map<String, Object>>> list = ExcelReadUtil.read(fis, suffix, houseInfoRule);
			Integer totalCnt = ownerInfoService.addFromExcel(list, getSessionUser(request));
			/*
			Workbook book = null;
			String fileName = model.getFileName();
			if (fileName.toLowerCase().endsWith(".xls")) {
				book = WorkbookFactory.create(is);
			} else if (fileName.toLowerCase().endsWith(".xlsx")) {
				book = new XSSFWorkbook(is);
			} else {
				throw new Exception("�ļ���ʽ����ȷ");
			}
			// book = WorkbookFactory.create(is);
			// book = new XSSFWorkbook(is);
			IOUtils.closeQuietly(is);
			// ��õ�һ�����������
			// XSSFSheet sheet = book.getSheetAt(0);
			int sheetCnt = book.getNumberOfSheets();
			List<OwnerInfo> list = new ArrayList<OwnerInfo>();
			for (int i = 0; i < sheetCnt; i++) {
				org.apache.poi.ss.usermodel.Sheet sheet = book.getSheetAt(i);
				// �õ���һ�е�һ�еĵ�Ԫ��
				int rownum = sheet.getPhysicalNumberOfRows(); // �õ�����
				for (int j = 1; j < rownum; j++) { // ѭ�����ж�д
					try {
						// ��0��ʼ
						// XSSFRow row = sheet.getRow(i);
						org.apache.poi.ss.usermodel.Row row = sheet.getRow(j);
						Assert.notNull(row, "������Ϊ��");
						String houseSn = "";
						houseSn = row.getCell(0).getStringCellValue();
						houseSn = StringUtils.trimToEmpty(houseSn.replace(
								"����̲", ""));
						if (StringUtils.isEmpty(houseSn)) {
							throw new Exception("���ݱ��Ϊ��");
						}
						if (houseInfoDao.findById(houseSn) != null) {
							throw new Exception("���ݱ��Ϊ" + houseSn + "�ķ�����Ϣ�Ѵ���");
						}
						Double area = row.getCell(1).getNumericCellValue();
						if (area == null || area <= 0) {
							throw new Exception("�������");
						}
						String ownerName = row.getCell(2).getStringCellValue();
						if (StringUtils.isEmpty(ownerName)) {
							throw new Exception("����Ϊ��");
						}
						String sexType = "";
						String sexTypeName = row.getCell(3)
								.getStringCellValue();
						if (SexType.SEX_M.getName().equals(sexTypeName)) {
							sexType = "M";
						} else if (SexType.SEX_F.getName().equals(sexTypeName)) {
							sexType = "F";
						}
						String mobile = row.getCell(4).getStringCellValue();
						if (StringUtils.isEmpty(mobile)) {
							throw new Exception("��ϵ�绰Ϊ��");
						}
						String idCard = row.getCell(5).getStringCellValue();

						OwnerInfo ownerInfo = new OwnerInfo();
						ownerInfo.setHouseSn(houseSn);
						ownerInfo.setGender(sexType);
						ownerInfo.setIdCard(idCard);
						ownerInfo.setOwnerName(ownerName);
						ownerInfo.setArea(area);
						ownerInfo.setMobile(mobile);
						ownerInfo.setGrade(OwnerGrade.NORMAL.getValue());
						list.add(ownerInfo);
					} catch (Exception e) {
						throw new Exception("��" + (i + 1) + "���������е�" + (j + 1)
								+ "��:" + e.getMessage());
					}
				}
			}
			ownerInfoService.importOwnerInfo(list, getSessionUser(request));
			*/
			String msg = LogUtils.r("������ɣ�������{?}����¼", totalCnt);
			setResult(true, msg, request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��:" + e.getMessage(), request);
		}finally {
			IOUtils.closeQuietly(is);
		}
		// return forward("/pages/manage/owner/ownerInfoImport.jsp");
		return "import";
	}

	public String export() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("ownerName", model.getOwnerName());
		map.put("houseSn", model.getHouseSn());
		Paginater paginater = ownerInfoDao.findPager(map, null);//���ٷ�ҳ
		List<List<List<Object>>> dataList = new ArrayList<>();
		Map<String, List<List<Object>>> dataMap = new HashMap<>();
		List<String> buildingList = new ArrayList<>();
		for (int i = 0, size = paginater.getList().size(); i < size; i++) {
			OwnerInfo ownerInfo = (OwnerInfo)paginater.getList().get(i);
			String houseSn = ownerInfo.getHouseSn();
			String buildingNo = houseSn.split("-")[0];
			//HouseInfo houseInfo = houseInfoDao.findById(ownerInfo.getHouseSn());
			List<List<Object>> tmpList = dataMap.get(buildingNo);//
			if (tmpList == null) {
				tmpList = new ArrayList<>();
				dataMap.put(buildingNo, tmpList);
			}
			List<Object> obj = new ArrayList<>();
			obj.add(ownerInfo.getHouseSn());
			//obj.add(houseInfo.getArea());
			obj.add(ownerInfo.getOwnerName());
			obj.add(ownerInfo.getGender());
			obj.add(ownerInfo.getMobile());
			obj.add(ownerInfo.getIdCard());
			obj.add(ownerInfo.getInstancyPerson());
			obj.add(ownerInfo.getInstancyTel());
			obj.add(ownerInfo.getCarNum());
			obj.add(ownerInfo.getRemark());
			tmpList.add(obj);
 		}
		for (Map.Entry<String, List<List<Object>>> entry : dataMap.entrySet()) {
			dataList.add(entry.getValue());
			buildingList.add(entry.getKey());
		}
		String branchName = "";
		if(!super.isHQ()){
			String branchNo = getSessionBranchNo(request);
			branchName = BranchType.valueOf(branchNo).getName();
		}
		String fileName = branchName+"ҵ����Ϣ-"+DateUtil.getCurrentDate()+".xlsx";
		String title = "";
		String exportRule = StringUtil.class2Object(this.getModel().getClass().getName())+"ExportRule";
		List<Map<String, String>> rules = (List<Map<String, String>>)SpringContext.getService(exportRule);
		String excelType = ParaManager.getExcelType(getSessionBranchNo(request));
		ExportExcelUtil exportExcelUtil = new ExportExcelUtil(fileName, title, buildingList.toArray(new String[buildingList.size()]), rules, dataList, excelType, response);
		exportExcelUtil.exportSheets();
		return null;
	}
	
	public String doUpdate() throws Exception {
		try {
			OwnerInfo ownerInfo = ownerInfoDao.findById(model.getId());
			OwnerInfo owner = (OwnerInfo) BeanUtils.cloneBean(ownerInfo);
			BeanUtils.copyProperties(ownerInfo, model);
			ownerInfo.setCreateDate(owner.getCreateDate());
			ownerInfo.setCreateUser(owner.getCreateUser());
			ownerInfoService.update(ownerInfo, getSessionUser(request));
			setResult(false, "�޸ĳɹ�", request);
		} catch (Exception e) {
			setResult(false, "�޸�ʧ��", request);
		}
		return toUpdate();
	}

	public void initSelect(HttpServletRequest request) throws Exception {
		SexType.setInReq(request);
		OwnerGrade.setInReq(request);
	}

	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("ownerName", model.getOwnerName());
		map.put("houseSn", model.getHouseSn());
		if (isHQ()) {//�ܲ�
			map.put("branchNo", model.getBranchNo());
		}else {//����
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = ownerInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		// return forward("/pages/manage/owner/ownerInfoList.jsp");
		return "list";
	}

	public String openAcct() throws Exception {
		try {
			String id = request.getParameter("id");
			accountService.add(id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��", request);
		}
		return list();
	}

	public String toAdd() throws Exception {
		initSelect(request);
		// return forward("/pages/manage/owner/ownerInfoAdd.jsp");
		return "add";
	}

	public String toImport() throws Exception {
		// return forward("/pages/manage/owner/ownerInfoImport.jsp");
		request.setAttribute("templateName", "ҵ����Ϣ����ģ��"+"."+ParaManager.getExcelType(getSessionBranchNo(request)));
		return "import";
	}

	public String toUpdate() throws Exception {
		initSelect(request);
		String id = request.getParameter("id");
		OwnerInfo ownerInfo = ownerInfoDao.findById(id);

		BeanUtils.copyProperties(model, ownerInfo);
		// return forward("/pages/manage/owner/ownerInfoUpdate.jsp");
		return "edit";
	}

	@Override
	public OwnerInfo getModel() {
		return model;
	}

	private OwnerInfo model = new OwnerInfo();
	
	private String fileFileName;
	private File file;

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
