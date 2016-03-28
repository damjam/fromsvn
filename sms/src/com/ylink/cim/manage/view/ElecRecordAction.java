package com.ylink.cim.manage.view;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

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
import com.ylink.cim.manage.dao.ElecRecordDao;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.domain.ElecRecord;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.service.ElecRecordService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class ElecRecordAction extends BaseAction implements
		ModelDriven<ElecRecord> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ElecRecordDao elecRecordDao;
	@Autowired
	private HouseInfoDao houseInfoDao;
	@Autowired
	private ElecRecordService elecRecordService;

	public String check() throws Exception {
		try {
			String id = request.getParameter("id");
			elecRecordService.checkRecord(id, getSessionUser(request));
			setResult(true, "�˵����ɳɹ�", request);
		} catch (Exception e) {
			setResult(false, "�����˵�ʧ��", request);
			e.printStackTrace();
		}
		return list();

	}

	public String checkAll() throws Exception {
		try {
			Integer num = elecRecordService
					.checkAllRecord(getSessionUser(request));
			if (num > 0) {
				setResult(true, "������" + num + "���˵�", request);
			} else {
				setResult(true, "û����Ҫ�����˵��ļ�¼", request);
			}
		} catch (Exception e) {
			setResult(false, "�����˵�ʧ��", request);
			e.printStackTrace();
		}
		return list();

	}

	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			elecRecordService.deleteRecord(id, getSessionUser(request));
			setResult(true, "�����ɹ�", request);
		} catch (Exception e) {
			setResult(false, "ɾ��ʧ��", request);
			e.printStackTrace();
		}
		return list();
	}

	public String doAdd() throws Exception {
		try {
			ElecRecord elecRecord = new ElecRecord();
			BeanUtils.copyProperties(elecRecord, model);
			elecRecordService.saveElecRecord(elecRecord,
					getSessionUser(request));
			setResult(true, "�����ѱ���", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			setResult(false, "����ʧ��" + e.getMessage(), request);
		}
		return "add";
	}

	public String doImport() throws Exception {
		InputStream is = new FileInputStream(model.getFile());
		try {
			String recordMonth = model.getRecordMonth();
			String preRecordDate = model.getPreRecordDate();
			String curRecordDate = model.getCurRecordDate();
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
			List<ElecRecord> list = new ArrayList<ElecRecord>();
			for (int i = 0; i < sheetCnt; i++) {
				org.apache.poi.ss.usermodel.Sheet sheet = book.getSheetAt(i);
				// �õ���һ�е�һ�еĵ�Ԫ��
				int rownum = sheet.getPhysicalNumberOfRows(); // �õ�����
				for (int j = 0; j < rownum; j++) { // ѭ�����ж�д
					try {
						// ��0��ʼ
						// XSSFRow row = sheet.getRow(i);
						org.apache.poi.ss.usermodel.Row row = sheet.getRow(j);
						Assert.notNull(row, "������Ϊ��");
						String houseSn = "";
						houseSn = row.getCell(0).getStringCellValue();
						if (StringUtils.isEmpty(houseSn)) {
							throw new Exception("���ݱ��Ϊ��");
						}
						houseSn = StringUtils.trimToEmpty(houseSn.replace(
								"ʢ����Է", ""));
						HouseInfo houseInfo = houseInfoDao.findById(
								HouseInfo.class, houseSn);
						Assert.notNull(houseInfo, "���ݱ��" + houseSn + "������");
						double prenumd = row.getCell(1).getNumericCellValue();
						Integer prenum = (int) prenumd;
						double curnumd = row.getCell(2).getNumericCellValue();
						Integer curnum = (int) curnumd;
						if (curnum < prenum) {
							throw new Exception("���ڶ���С�����ڶ���");
						}
						String remark = "";
						if (row.getCell(3) != null) {
							remark = row.getCell(3).getStringCellValue();
						}
						ElecRecord record = new ElecRecord();
						record.setHouseSn(houseSn);
						record.setPrenum(prenum);
						record.setCurnum(curnum);
						record.setNum(curnum - prenum);
						record.setRecordMonth(recordMonth);
						record.setPreRecordDate(preRecordDate);
						record.setCurRecordDate(curRecordDate);
						record.setRemark(remark);
						list.add(record);
					} catch (Exception e) {
						throw new Exception("��" + (i + 1) + "���������е�" + (j + 1)
								+ "��:" + e.getMessage());
					}
				}
			}
			elecRecordService.importDeposit(list, getSessionUser(request));
			StringBuilder message = new StringBuilder();
			message.append("������ɣ�������").append(list.size()).append("����¼");
			setResult(true, message.toString(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��:" + e.getMessage(), request);
		}finally {
			IOUtils.closeQuietly(is);
		}
		// return forward("/pages/manage/meter/elec/elecRecordImport.jsp");
		return "import";
	}

	public String getPreRecord() throws Exception {
		try {
			String houseSn = request.getParameter("houseSn");
			ElecRecord elecRecord = elecRecordService.getPreRecord(houseSn);
			JSONObject jsonObject = new JSONObject();
			Integer prenum = 0;
			String preRecordDate = "";
			if (elecRecord != null) {
				prenum = elecRecord.getCurnum();
				preRecordDate = elecRecord.getCurRecordDate();
			}
			jsonObject.put("prenum", prenum);
			jsonObject.put("preRecordDate", preRecordDate);
			respond(response, jsonObject.toString());
			return null;
		} catch (Exception e) {
			setResult(false, "ʧ��", request);
			e.printStackTrace();
		}
		return null;
	}

	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("startCreateDate", model.getStartCreateDate());
		map.put("endCreateDate", model.getEndCreateDate());
		map.put("houseSn", model.getHouseSn());
		if (isHQ()) {//�ܲ�
			map.put("branchNo", model.getBranchNo());
		}else {//����
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = elecRecordDao.findRecordPager(map,
				getPager(request));
		saveQueryResult(request, paginater);
		// return forward("/pages/manage/meter/elec/elecRecordList.jsp");
		return "list";
	}

	public String toAdd() throws Exception {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String preRecordDate = "";
		String curRecordDate = "";
		String beginDate = "";
		if (day > 26) {
			preRecordDate = DateUtil.getMaxMonthDate(-1);
			curRecordDate = DateUtil.getCurrentDate();
			Date d1 = DateUtil.addMonths(DateUtil.getCurrent(), -1);
			beginDate = DateUtil.getDateYYYYMM(d1);
		} else {
			preRecordDate = DateUtil.getMaxMonthDate(-2);
			curRecordDate = DateUtil.getMaxMonthDate(-1);
			Date d1 = DateUtil.addMonths(DateUtil.getCurrent(), -2);
			beginDate = DateUtil.getDateYYYYMM(d1);
		}
		// DateUtil.addMonths(date, num);
		String recordMonth = beginDate + "-" + curRecordDate.substring(0, 6);
		model.setPreRecordDate(preRecordDate);
		model.setCurRecordDate(curRecordDate);
		model.setRecordMonth(recordMonth);
		// return forward("/pages/manage/meter/elec/elecRecordAdd.jsp");
		return "add";
	}

	public String toImport() throws Exception {
		// return forward("/pages/manage/meter/elec/elecRecordImport.jsp");
		return "import";
	}

	@Override
	public ElecRecord getModel() {
		return model;
	}

	private ElecRecord model = new ElecRecord();
}
