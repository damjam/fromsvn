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
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.dao.WaterRecordDao;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.WaterRecord;
import com.ylink.cim.manage.service.WaterRecordService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class WaterRecordAction extends BaseAction implements
		ModelDriven<WaterRecord> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private WaterRecordDao waterRecordDao;
	@Autowired
	private HouseInfoDao houseInfoDao;
	@Autowired
	private WaterRecordService waterRecordService;

	public String check() throws Exception {
		try {
			String id = request.getParameter("id");
			waterRecordService.checkRecord(id, getSessionUser(request));
			setResult(true, "账单生成成功", request);
		} catch (Exception e) {
			setResult(false, "生成账单失败", request);
			e.printStackTrace();
		}
		return list();

	}

	public String checkAll() throws Exception {
		try {
			Integer num = waterRecordService
					.checkAllRecord(getSessionUser(request));
			if (num > 0) {
				setResult(true, "已生成" + num + "条账单", request);
			} else {
				setResult(true, "没有需要生成账单的记录", request);
			}
		} catch (Exception e) {
			setResult(false, "生成账单失败", request);
			e.printStackTrace();
		}
		return list();

	}

	public String delete() throws Exception {
		try {
			String id = request.getParameter("id");
			waterRecordService.deleteRecord(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list();
	}

	public String doAdd() throws Exception {
		try {
			WaterRecord waterRecord = new WaterRecord();
			BeanUtils.copyProperties(waterRecord, model);
			waterRecordService.saveWaterRecord(waterRecord,
					getSessionUser(request));
			setResult(true, "数据已保存", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			setResult(false, "保存失败" + e.getMessage(), request);
		}
		// return forward("/pages/manage/meter/water/waterRecordAdd.jsp");
		return "add";
	}

	public String doImport() throws Exception {
		try {
			String recordMonth = model.getRecordMonth();
			String preRecordDate = model.getPreRecordDate();
			String curRecordDate = model.getCurRecordDate();
			String fileName = model.getFileName();
			if (!fileName.toLowerCase().endsWith(".xls") && !fileName.toLowerCase().endsWith(".xlsx")) {
				throw new Exception("文件格式不正确");
			}
			InputStream is = new FileInputStream(model.getFileName());
			Workbook book = null;
			if (fileName.toLowerCase().endsWith(".xls")) {
				book = WorkbookFactory.create(is);
			} else if (fileName.toLowerCase().endsWith(".xlsx")) {
				book = new XSSFWorkbook(is);
			}
			// book = WorkbookFactory.create(is);
			// book = new XSSFWorkbook(is);
			IOUtils.closeQuietly(is);
			// 获得第一个工作表对象
			// XSSFSheet sheet = book.getSheetAt(0);
			int sheetCnt = book.getNumberOfSheets();
			List<WaterRecord> list = new ArrayList<WaterRecord>();
			for (int i = 0; i < sheetCnt; i++) {
				org.apache.poi.ss.usermodel.Sheet sheet = book.getSheetAt(i);
				// 得到第一列第一行的单元格
				int rownum = sheet.getPhysicalNumberOfRows(); // 得到行数
				for (int j = 0; j < rownum; j++) { // 循环进行读写
					try {
						// 从0开始
						// XSSFRow row = sheet.getRow(i);
						org.apache.poi.ss.usermodel.Row row = sheet.getRow(j);
						Assert.notNull(row, "行数据为空");
						String houseSn = "";
						houseSn = row.getCell(0).getStringCellValue();
						if (StringUtils.isEmpty(houseSn)) {
							throw new Exception("房屋编号为空");
						}
						HouseInfo houseInfo = houseInfoDao.findById(
								HouseInfo.class, houseSn);
						Assert.notNull(houseInfo, "房屋编号" + houseSn + "不存在");
						double prenumd = row.getCell(1).getNumericCellValue();
						Long prenum = (long) prenumd;
						double curnumd = row.getCell(2).getNumericCellValue();
						Long curnum = (long) curnumd;
						if (curnum < prenum) {
							throw new Exception("本期读数小于上期读数");
						}
						String remark = "";
						if (row.getCell(3) != null) {
							remark = row.getCell(3).getStringCellValue();
						}
						WaterRecord record = new WaterRecord();
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
						throw new Exception("第" + (i + 1) + "个工作表中第" + (j + 1)
								+ "行:" + e.getMessage());
					}
				}
			}
			waterRecordService.importDeposit(list, getSessionUser(request));
			StringBuilder message = new StringBuilder();
			message.append("操作完成，共导入").append(list.size()).append("条记录");
			setResult(true, message.toString(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败:" + e.getMessage(), request);
		}
		// return forward("/pages/manage/meter/water/waterRecordImport.jsp");
		return "import";
	}

	public String getPreRecord() throws Exception {
		try {
			String houseSn = request.getParameter("houseSn");
			WaterRecord waterRecord = waterRecordService.getPreRecord(houseSn);
			JSONObject jsonObject = new JSONObject();
			Long prenum = 0L;
			String preRecordDate = "";
			if (waterRecord != null) {
				prenum = waterRecord.getCurnum();
				preRecordDate = waterRecord.getCurRecordDate();
			}
			jsonObject.put("prenum", prenum);
			jsonObject.put("preRecordDate", preRecordDate);
			respond(response, jsonObject.toString());
			return null;
		} catch (Exception e) {
			setResult(false, "失败", request);
			e.printStackTrace();
		}
		return null;
	}

	public String list() throws Exception {
		Map<String, Object> map = getParaMap();

		map.put("startCreateDate", model.getStartCreateDate());
		map.put("endCreateDate", model.getEndCreateDate());
		map.put("houseSn", model.getHouseSn());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = waterRecordDao.findWaterRecordPager(map,
				getPager(request));
		saveQueryResult(request, paginater);
		// return forward("/pages/manage/meter/water/waterRecordList.jsp");
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
		// return forward("/pages/manage/meter/water/waterRecordAdd.jsp");
		return "add";
	}

	public String toImport() throws Exception {

		// return forward("/pages/manage/meter/water/waterRecordImport.jsp");
		return "import";
	}

	@Override
	public WaterRecord getModel() {
		return model;
	}

	private WaterRecord model = new WaterRecord();
}
