package com.ylink.cim.manage.view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

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
import flink.web.BaseDispatchAction;

public class OwnerInfoAction extends BaseDispatchAction {
	private OwnerInfoDao OwnerInfoDao = (OwnerInfoDao)getService("ownerInfoDao");
	private OwnerInfoService ownerInfoService = (OwnerInfoService)getService("ownerInfoService");
	private AccountService accountService = (AccountService)getService("accountService");
	private HouseInfoDao houseInfoDao = (HouseInfoDao)getService("houseInfoDao");
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		return list(mapping, form, request, response);
	}
	private void clearForm(OwnerInfoActionForm actionForm) {
		actionForm.setOwnerName(null);
		actionForm.setHouseSn(null);
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			ownerInfoService.delete(id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��", request);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			OwnerInfoActionForm actionForm = (OwnerInfoActionForm)form;
			OwnerInfo ownerInfo = new OwnerInfo();
			BeanUtils.copyProperties(ownerInfo, actionForm);
			ownerInfoService.add(ownerInfo, getSessionUser(request));
			setResult(true, "��ӳɹ�", request);
			clearForm(actionForm);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			e.printStackTrace();
			return toAdd(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "���ʧ��", request);
			e.printStackTrace();
			return toAdd(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward doImport(ActionMapping mapping, ActionForm actionform, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			OwnerInfoActionForm form = (OwnerInfoActionForm) actionform;
			
			FormFile file = form.getFile();

			InputStream is = file.getInputStream();
			Workbook book = null;
			String fileName = file.getFileName();
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
						houseSn = StringUtils.trimToEmpty(houseSn.replace("����̲", ""));
						if (StringUtils.isEmpty(houseSn)) {
							throw new Exception("���ݱ��Ϊ��");
						}
						if(houseInfoDao.findById(houseSn) != null){
							throw new Exception("���ݱ��Ϊ"+houseSn+"�ķ�����Ϣ�Ѵ���");
						}
						Double area = row.getCell(1).getNumericCellValue();
						if (area == null || area<= 0) {
							throw new Exception("�������");
						}
						String ownerName = row.getCell(2).getStringCellValue();
						if (StringUtils.isEmpty(ownerName)) {
							throw new Exception("����Ϊ��");
						}
						String sexType = "";
						String sexTypeName = row.getCell(3).getStringCellValue();
						if (SexType.SEX_M.getName().equals(sexTypeName)) {
							sexType = "M";
						}else if (SexType.SEX_F.getName().equals(sexTypeName)) {
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
						throw new Exception("��" + (i+1) +"���������е�"+ (j+1) +"��:" + e.getMessage());
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
		}
		return forward("/pages/manage/owner/ownerInfoImport.jsp");
	}
	public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			OwnerInfoActionForm actionForm = (OwnerInfoActionForm)form;
			OwnerInfo ownerInfo = OwnerInfoDao.findById(actionForm.getId());
			OwnerInfo owner = (OwnerInfo)BeanUtils.cloneBean(ownerInfo);
			BeanUtils.copyProperties(ownerInfo, actionForm);
			ownerInfo.setCreateDate(owner.getCreateDate());
			ownerInfo.setCreateUser(owner.getCreateUser());
			ownerInfoService.update(ownerInfo, getSessionUser(request));
			setResult(false, "�޸ĳɹ�", request);
		} catch (Exception e) {
			setResult(false, "�޸�ʧ��", request);
		}
		return toUpdate(mapping, form, request, response);
	}
	public void initSelect(HttpServletRequest request) throws Exception {
		SexType.setInReq(request);
		OwnerGrade.setInReq(request);
	}
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = getParaMap();
		OwnerInfoActionForm actionForm = (OwnerInfoActionForm)form;
		map.put("ownerName", actionForm.getOwnerName());
		map.put("houseSn", actionForm.getHouseSn());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = OwnerInfoDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return forward("/pages/manage/owner/ownerInfoList.jsp");
	}
	public ActionForward openAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			accountService.add(id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��", request);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initSelect(request);
		return forward("/pages/manage/owner/ownerInfoAdd.jsp");
	}
	public ActionForward toImport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return forward("/pages/manage/owner/ownerInfoImport.jsp");
	}
	public ActionForward toUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initSelect(request);
		String id = request.getParameter("id");
		OwnerInfo ownerInfo = OwnerInfoDao.findById(id);
		OwnerInfoActionForm actionForm = (OwnerInfoActionForm)form;
		BeanUtils.copyProperties(actionForm, ownerInfo);
		return forward("/pages/manage/owner/ownerInfoUpdate.jsp");
	}
}
