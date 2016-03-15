package com.ylink.cim.manage.view;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.type.OwnerGrade;
import com.ylink.cim.common.type.SexType;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.service.AccountService;
import com.ylink.cim.manage.service.OwnerInfoService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Paginater;
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
	private OwnerInfoDao OwnerInfoDao;
	@Autowired
	private OwnerInfoService ownerInfoService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private HouseInfoDao houseInfoDao;

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
		InputStream is = new FileInputStream(model.getFile());
		try {
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
			StringBuilder message = new StringBuilder();
			message.append("������ɣ�������").append(list.size()).append("����¼");
			setResult(true, message.toString(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��:" + e.getMessage(), request);
		}finally {
			IOUtils.closeQuietly(is);
		}
		// return forward("/pages/manage/owner/ownerInfoImport.jsp");
		return "import";
	}

	public String doUpdate() throws Exception {
		try {
			OwnerInfo ownerInfo = OwnerInfoDao.findById(model.getId());
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
		Paginater paginater = OwnerInfoDao.findPager(map, getPager(request));
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
		return "import";
	}

	public String toUpdate() throws Exception {
		initSelect(request);
		String id = request.getParameter("id");
		OwnerInfo ownerInfo = OwnerInfoDao.findById(id);

		BeanUtils.copyProperties(model, ownerInfo);
		// return forward("/pages/manage/owner/ownerInfoUpdate.jsp");
		return "update";
	}

	@Override
	public OwnerInfo getModel() {
		return model;
	}

	private OwnerInfo model = new OwnerInfo();
}
