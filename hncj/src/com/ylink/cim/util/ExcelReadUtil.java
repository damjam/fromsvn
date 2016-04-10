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
			List<List<Object[]>> workBookData = new ArrayList<>();
			for (i = 0; i < sheetSize; i++) {
				List<Object[]> sheetData = new ArrayList<>();
				Sheet sheet = wb.getSheetAt(i);
				sheetName = sheet.getSheetName();
				int rowNum = sheet.getPhysicalNumberOfRows();
				Row firstRow = sheet.getRow(0);
				// ������
				int colNum = firstRow.getLastCellNum();
				for (j = 1; j < rowNum; j++) {//��һ��Ϊ��ͷ
					Row row = sheet.getRow(j);
					Object[] rowValues = new Object[colNum];
					for (k = 0; k < colNum; k++) {
						Map<String, String> rule = rules.get(k);
						String cellType = rule.get("cellType");
						String notNull = rule.get("notNull");
						Cell cell = row.getCell(k);
						
						Object cellValue = null;
						if (cell == null) {
							if (Symbol.YES.equals(notNull)) {
								throw new BizException("��Ԫ����Ϊ��");
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
							cellValue = flink.util.DateUtil.getDateYYYYMMDD(date);
						}
						rowValues[k] = cellValue;
					}
					sheetData.add(rowValues);
				}
				workBookData.add(sheetData);
			}
			return workBookData;
		} catch (Exception e) {
			String msg = "������" + sheetName + "��" + (j + 1) + "��" + "��" + (k + 1) +e.getMessage();
			e.printStackTrace();
			throw new Exception(msg);
		}
	}
	
}
