package com.ylink.cim.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ����Excel��������
 * 
 * @version 1.0
 * 
 * @author wangcp
 * 
 */
public class ExportExcelUtil {

	private String fileName;
	// ��ʾ�ĵ�����ı���
	private String title;
	
	// �����������
	private List<Map<String, String>> rowRules = new ArrayList<Map<String,String>>();
	
	private String sheetName;
	
	private String[] sheetNames;

	private List<List<Object>> dataList = new ArrayList<List<Object>>();
	
	private List<List<List<Object>>> sheetList = new ArrayList<List<List<Object>>>();
	
	HttpServletResponse response;
	
	private String excelType;
	

	// ���췽��������Ҫ����������
	public ExportExcelUtil(String fileName, String title, String sheetName, List<Map<String, String>> rowRules, List<List<Object>> dataList,String excelType,
			HttpServletResponse response) {
		this.dataList = dataList;
		this.rowRules = rowRules;
		this.title = title;
		this.response = response;
		this.fileName = fileName;
		this.excelType = excelType;
	}
	public ExportExcelUtil(String fileName, String title, String[] sheetNames, List<Map<String, String>> rowRules, List<List<List<Object>>> sheetList,String excelType,
			HttpServletResponse response) {
		this.sheetList = sheetList;
		this.rowRules = rowRules;
		this.title = title;
		this.response = response;
		this.fileName = fileName;
		this.sheetNames = sheetNames;
	}
	/*
	 * ��������
	 */
	public void exportSheet() throws Exception {
		try {
			Workbook workbook = null;
			if("xls".equals(excelType)){
				workbook = new HSSFWorkbook(); // ��������������
			}else {
				workbook = new XSSFWorkbook(); // ��������������
			}
			
			createSheet(workbook, sheetName, dataList);
			if (workbook != null) {
				try {
					String headStr = "attachment; filename=\"" + fileName + "\"";
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition", headStr);
					OutputStream out = response.getOutputStream();
					workbook.write(out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void exportSheets() throws Exception {
		try {
			Workbook workbook = null;
			if("xls".equals(excelType)){
				workbook = new HSSFWorkbook(); // ��������������
			}else {
				workbook = new XSSFWorkbook(); // ��������������
			}
			for(int i=0; i<sheetNames.length; i++) {
				createSheet(workbook, sheetNames[i], sheetList.get(i));
			}
			if (workbook != null) {
				try {
					fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
					String headStr = "attachment; filename=\"" + fileName + "\"";
					response.setCharacterEncoding("utf-8");
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition", headStr);
					OutputStream out = response.getOutputStream();
					workbook.write(out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * ��ͷ��Ԫ����ʽ
	 */
	public CellStyle getColumnTopStyle(Workbook workbook) {

		// ��������
		Font font = workbook.createFont();
		// ���������С
		font.setFontHeightInPoints((short) 11);
		// ����Ӵ�
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// ������������
		font.setFontName("Courier New");
		// ������ʽ;
		CellStyle style = workbook.createCellStyle();
		// ���õױ߿�;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// ���õױ߿���ɫ;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// ������߿�;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// ������߿���ɫ;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// �����ұ߿�;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// �����ұ߿���ɫ;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// ���ö��߿�;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// ���ö��߿���ɫ;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// ����ʽ��Ӧ�����õ�����;
		style.setFont(font);
		// �����Զ�����;
		style.setWrapText(false);
		// ����ˮƽ�������ʽΪ���ж���;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// ���ô�ֱ�������ʽΪ���ж���;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * ��������Ϣ��Ԫ����ʽ
	 */
	public CellStyle getStyle(Workbook workbook) {
		// ��������
		Font font = workbook.createFont();
		// ���������С
		// font.setFontHeightInPoints((short)10);
		// ����Ӵ�
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// ������������
		//font.setFontName("Courier New");
		// ������ʽ;
		CellStyle style = workbook.createCellStyle();
		// ���õױ߿�;
		style.setBorderBottom(CellStyle.BORDER_THIN);
		// ���õױ߿���ɫ;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// ������߿�;
		style.setBorderLeft(CellStyle.BORDER_THIN);
		// ������߿���ɫ;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// �����ұ߿�;
		style.setBorderRight(CellStyle.BORDER_THIN);
		// �����ұ߿���ɫ;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// ���ö��߿�;
		style.setBorderTop(CellStyle.BORDER_THIN);
		// ���ö��߿���ɫ;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// ����ʽ��Ӧ�����õ�����;
		style.setFont(font);
		// �����Զ�����;
		style.setWrapText(false);
		// ����ˮƽ�������ʽΪ���ж���;
		style.setAlignment(CellStyle.ALIGN_CENTER);
		// ���ô�ֱ�������ʽΪ���ж���;
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		return style;

	}
	
	private void createSheet(Workbook workbook, String sheetName, List<List<Object>> dataList){
		Sheet sheet = workbook.createSheet(sheetName); // ����������
		
		// ������������
		/*HSSFRow rowm = sheet.createRow(0);
		HSSFCell cellTiltle = rowm.createCell(0);

		

		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
		cellTiltle.setCellStyle(columnTopStyle);
		cellTiltle.setCellValue(title);*/
		// sheet��ʽ���塾getColumnTopStyle()/getStyle()��Ϊ�Զ��巽�� - ������ - ����չ��
		CellStyle columnTopStyle = this.getColumnTopStyle(workbook);// ��ȡ��ͷ��ʽ����
		CellStyle style = this.getStyle(workbook); // ��Ԫ����ʽ����
		// ������������
		int columnNum = rowRules.size();
		Row rowRowName = sheet.createRow(0); // ������2��λ�ô�����(��˵��п�ʼ�ĵڶ���)
		sheet.createFreezePane(0, 1, 0, 1);
		// ����ͷ���õ�sheet�ĵ�Ԫ����
		for (int n = 0; n < columnNum; n++) {
			Cell cellRowName = rowRowName.createCell(n); // ������ͷ��Ӧ�����ĵ�Ԫ��
			cellRowName.setCellType(Cell.CELL_TYPE_STRING); // ������ͷ��Ԫ�����������
			Map<String, String> rule = rowRules.get(n);
			cellRowName.setCellValue(rule.get("fieldName")); // ������ͷ��Ԫ���ֵ
			cellRowName.setCellStyle(columnTopStyle); // ������ͷ��Ԫ����ʽ
		}

		// ����ѯ�����������õ�sheet��Ӧ�ĵ�Ԫ����
		for (int i = 0; i < dataList.size(); i++) {

			List<Object> obj = dataList.get(i);// ����ÿ������
			Row row = sheet.createRow(i + 1);// �������������
			for (int j = 0; j < obj.size(); j++) {
				Cell cell = null; // ���õ�Ԫ�����������
				Map<String, String> rowRule = rowRules.get(j);
				/*if (j == 0) {
					cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(i + 1);
				} else {*/
					String cellType = rowRule.get("cellType");
					if("String".equals(cellType)){
						cell = row.createCell(j, Cell.CELL_TYPE_STRING);
						if (obj.get(j) != null) {
							cell.setCellValue(obj.get(j).toString());
						}
					}else if("Date".equals(cellType)){
						cell = row.createCell(j, Cell.CELL_TYPE_STRING);
						if (obj.get(j) != null) {
							cell.setCellValue(obj.get(j).toString());
						}else {
							cell.setCellValue("");
						}
					}else if ("Double".equals(cellType)) {
						cell = row.createCell(j, Cell.CELL_TYPE_NUMERIC);
						if (obj.get(j) != null) {
							cell.setCellValue((Double)obj.get(j));
						}
					}else if ("Integer".equals(cellType)) {
						cell = row.createCell(j, Cell.CELL_TYPE_NUMERIC);
						if (obj.get(j) != null) {
							cell.setCellValue((Integer)obj.get(j));
						}
					}
					
				//}
				String textAlign = rowRule.get("textAlign");
				if("left".equals(textAlign)){
					style.setAlignment(CellStyle.ALIGN_LEFT);
				}else if ("right".equals(textAlign)) {
					style.setAlignment(CellStyle.ALIGN_RIGHT);
				}
				cell.setCellStyle(style); // ���õ�Ԫ����ʽ
			}
		}
		// ���п����ŵ������г��Զ���Ӧ
		
		for (int colNum = 0; colNum < columnNum; colNum++) {
			int columnWidth = sheet.getColumnWidth(colNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				Row currentRow;
				// ��ǰ��δ��ʹ�ù�
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}
				if (currentRow.getCell(colNum) != null) {
					Cell currentCell = currentRow.getCell(colNum);
					if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
						int length = currentCell.getStringCellValue().getBytes().length;
						if (columnWidth < length) {
							columnWidth = length;
						}
					}
				}
			}
			if (colNum == 0) {
				sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
			} else {
				sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
			}
		}
	}
}