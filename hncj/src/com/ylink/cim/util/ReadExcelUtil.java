package com.ylink.cim.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import flink.etc.BizException;
import flink.etc.Symbol;

public class ReadExcelUtil {

	public static List<List<Map<String, Object>>> read(FileInputStream fis, String suffix, List<Map<String, String>> rules) throws Exception {
		int i=0,j=1,k =0;
		String sheetName = null;
		try {
			Workbook wb = null;
			if("xlsx".equals(suffix)){
				wb = new XSSFWorkbook(fis);
			}else {
				wb = WorkbookFactory.create(fis);
			}
			int sheetSize = wb.getNumberOfSheets();
			List<List<Map<String, Object>>> workBookData = new ArrayList<>();
			for (i = 0; i < sheetSize; i++) {
				List<Map<String, Object>> sheetData = new ArrayList<>();
				Sheet sheet = wb.getSheetAt(i);
				sheetName = sheet.getSheetName();
				int rowNum = sheet.getPhysicalNumberOfRows();
				Row firstRow = sheet.getRow(0);
				// 总列数
				int colNum = firstRow.getLastCellNum();
				for (j = 1; j < rowNum; j++) {//第一行为表头
					Row row = sheet.getRow(j);
					Map<String, Object> rowValues = new HashMap<>();
					for (k = 0; k < colNum; k++) {
						Map<String, String> rule = rules.get(k);
						String cellType = rule.get("cellType");
						String notNull = rule.get("notNull");
						String fieldName = rule.get("fieldName");
						Cell cell = row.getCell(k);
						Object cellValue = null;
						if (cell == null) {
							if (Symbol.YES.equals(notNull)) {
								throw new BizException("单元格不能为空");
							}else {
								continue;
							}
						}
						if ("String".equals(cellType)) {
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								cellValue = String.valueOf(cell.getNumericCellValue());
							}else {
								cellValue = cell.getStringCellValue();
							}
						} else if ("Double".equals(cellType)) {
							cellValue = cell.getNumericCellValue();
						} else if ("Integer".equals(cellType)) {
							cellValue = (int) cell.getNumericCellValue();
						} else if ("Date".equals(cellType)) {
							Double cellDateValue = cell.getNumericCellValue();
							Date date = DateUtil.getJavaDate(cellDateValue);
							cellValue = flink.util.DateUtil.getDateYYYYMMDD(date);
						} else if ("Time".equals(cellType)) {
							Double cellDateValue = cell.getNumericCellValue();
							Date date = DateUtil.getJavaDate(cellDateValue);
							cellValue = date;
						}else if ("DateTime".equals(cellType)) {
							Double cellDateValue = cell.getNumericCellValue();
							Date date = DateUtil.getJavaDate(cellDateValue);
							cellValue = date;
						}
						rowValues.put(fieldName, cellValue);
					}
					sheetData.add(rowValues);
				}
				workBookData.add(sheetData);
			}
			return workBookData;
		} catch (Exception e) {
			String msg = "工作簿" + sheetName + "第" + (j + 1) + "行" + "第" + (k + 1) +"列:"+e.getMessage();
			e.printStackTrace();
			throw new Exception(msg);
		}
	}
	
}
