package com.ylink.cim.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
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

public class ExcelReadUtil {

	public static List<List<Object[]>> read(FileInputStream fis, String suffix, List<Map<String, String>> rules) throws Exception {
		try {
			List<List<Object[]>> list = null;
			Workbook wb = null;
			if("xlsx".equals(suffix)){
				wb = new XSSFWorkbook(fis);
				list = readExcel(wb, rules);
			}else {
				wb = WorkbookFactory.create(fis);
				list = readExcel(wb, rules);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	
	private static List<List<Object[]>> readExcel(Workbook wb, List<Map<String, String>> rules) throws BizException{
		int sheetSize = wb.getNumberOfSheets();
		List<List<Object[]>> workBookData = new ArrayList<>();
		for (int i = 0; i < sheetSize; i++) {
			List<Object[]> sheetData = new ArrayList<>();
			Sheet sheet = wb.getSheetAt(i);
			String sheetName = sheet.getSheetName();
			int rowNum = sheet.getLastRowNum();
			Row firstRow = sheet.getRow(0);
			// 总列数
			int colNum = firstRow.getLastCellNum();
			for (int j = 1; j < rowNum; j++) {//第一行为表头
				Row row = sheet.getRow(j);
				Object[] rowValues = new Object[colNum];
				for (int k = 0; k < colNum; k++) {
					Map<String, String> rule = rules.get(k);
					String cellType = rule.get("cellType");
					String notNull = rule.get("notNull");
					Cell cell = row.getCell(k);
					
					Object cellValue = null;
					if (cell == null) {
						if (Symbol.YES.equals(notNull)) {
							throw new BizException("工作簿" + sheetName + "第" + (j + 1) + "行" + "第" + (k + 1) + "列为空");
						}else {
							continue;
						}
					}
					
					if ("String".equals(cellType)) {
						cellValue = cell.getStringCellValue();

					} else if ("Double".equals(cellType)) {
						cellValue = cell.getNumericCellValue();

					} else if ("Integer".equals(cellType)) {
						cellValue = (int) cell.getNumericCellValue();
					} else if ("Date".equals(cellType)) {
						Double cellDateValue = cell.getNumericCellValue();
						Date date = DateUtil.getJavaDate(cellDateValue);
						cellValue = flink.util.DateUtil.getDate(date);
					}
					rowValues[k] = cellValue;
				}
				sheetData.add(rowValues);
			}
			workBookData.add(sheetData);
		}
		return workBookData;
	}
}
