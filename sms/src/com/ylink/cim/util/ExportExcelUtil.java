package com.ylink.cim.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

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
	private String[] rowName;
	
	private String sheetName;
	
	private String[] sheetNames;

	private List<Object[]> dataList = new ArrayList<Object[]>();
	
	private List<List<Object[]>> sheetList = new ArrayList<List<Object[]>>();
	
	HttpServletResponse response;

	// ���췽��������Ҫ����������
	public ExportExcelUtil(String fileName, String title, String sheetName, String[] rowName, List<Object[]> dataList,
			HttpServletResponse response) {
		this.dataList = dataList;
		this.rowName = rowName;
		this.title = title;
		this.response = response;
		this.fileName = fileName;
	}
	public ExportExcelUtil(String fileName, String title, String[] sheetNames, String[] rowName, List<List<Object[]>> sheetList,
			HttpServletResponse response) {
		this.sheetList = sheetList;
		this.rowName = rowName;
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
			HSSFWorkbook workbook = new HSSFWorkbook(); // ��������������
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
			HSSFWorkbook workbook = new HSSFWorkbook(); // ��������������
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
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

		// ��������
		HSSFFont font = workbook.createFont();
		// ���������С
		font.setFontHeightInPoints((short) 11);
		// ����Ӵ�
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// ������������
		font.setFontName("Courier New");
		// ������ʽ;
		HSSFCellStyle style = workbook.createCellStyle();
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
	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// ��������
		HSSFFont font = workbook.createFont();
		// ���������С
		// font.setFontHeightInPoints((short)10);
		// ����Ӵ�
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// ������������
		font.setFontName("Courier New");
		// ������ʽ;
		HSSFCellStyle style = workbook.createCellStyle();
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
	
	private void createSheet(HSSFWorkbook workbook, String sheetName, List<Object[]> dataList){
		HSSFSheet sheet = workbook.createSheet(sheetName); // ����������

		// ������������
		HSSFRow rowm = sheet.createRow(0);
		HSSFCell cellTiltle = rowm.createCell(0);

		// sheet��ʽ���塾getColumnTopStyle()/getStyle()��Ϊ�Զ��巽�� - ������ - ����չ��
		HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// ��ȡ��ͷ��ʽ����
		HSSFCellStyle style = this.getStyle(workbook); // ��Ԫ����ʽ����

		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
		cellTiltle.setCellStyle(columnTopStyle);
		cellTiltle.setCellValue(title);

		// ������������
		int columnNum = rowName.length;
		HSSFRow rowRowName = sheet.createRow(2); // ������2��λ�ô�����(��˵��п�ʼ�ĵڶ���)
		
		// ����ͷ���õ�sheet�ĵ�Ԫ����
		for (int n = 0; n < columnNum; n++) {
			HSSFCell cellRowName = rowRowName.createCell(n); // ������ͷ��Ӧ�����ĵ�Ԫ��
			cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // ������ͷ��Ԫ�����������
			HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
			cellRowName.setCellValue(text); // ������ͷ��Ԫ���ֵ
			cellRowName.setCellStyle(columnTopStyle); // ������ͷ��Ԫ����ʽ
		}

		// ����ѯ�����������õ�sheet��Ӧ�ĵ�Ԫ����
		for (int i = 0; i < dataList.size(); i++) {

			Object[] obj = dataList.get(i);// ����ÿ������
			HSSFRow row = sheet.createRow(i + 3);// �������������

			for (int j = 0; j < obj.length; j++) {
				HSSFCell cell = null; // ���õ�Ԫ�����������
				if (j == 0) {
					cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(i + 1);
				} else {
					cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
					if (!"".equals(obj[j]) && obj[j] != null) {
						cell.setCellValue(obj[j].toString()); // ���õ�Ԫ���ֵ
					}
				}
				cell.setCellStyle(style); // ���õ�Ԫ����ʽ
			}
		}
		// ���п����ŵ������г��Զ���Ӧ
		for (int colNum = 0; colNum < columnNum; colNum++) {
			int columnWidth = sheet.getColumnWidth(colNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				HSSFRow currentRow;
				// ��ǰ��δ��ʹ�ù�
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}
				if (currentRow.getCell(colNum) != null) {
					HSSFCell currentCell = currentRow.getCell(colNum);
					if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
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