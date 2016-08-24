package com.ylink.cim.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
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

import flink.util.DateUtil;

/**
 * 导出Excel公共方法
 * 
 * @version 1.0
 * 
 * @author wangcp
 * 
 */
public class ExportExcelUtil {

	private String fileName;
	// 显示的导出表的标题
	private String title;
	
	// 导出表的列名
	private List<Map<String, String>> rowRules = new ArrayList<Map<String,String>>();
	
	private String sheetName;
	
	private String[] sheetNames;

	private List<List<Object>> dataList = new ArrayList<List<Object>>();
	
	private List<List<List<Object>>> sheetList = new ArrayList<List<List<Object>>>();
	
	HttpServletResponse response;
	
	private String excelType;
	

	// 构造方法，传入要导出的数据
	public ExportExcelUtil(String fileName, String title, String sheetName, List<Map<String, String>> rowRules, List<List<Object>> dataList,String excelType,
			HttpServletResponse response) {
		this.dataList = dataList;
		this.rowRules = rowRules;
		this.title = title;
		this.sheetName = sheetName;
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
	 * 导出数据
	 */
	public void exportSheet() throws Exception {
		try {
			Workbook workbook = null;
			if("xls".equals(excelType)){
				workbook = new HSSFWorkbook(); // 创建工作簿对象
			}else {
				workbook = new XSSFWorkbook(); // 创建工作簿对象
			}
			
			createSheet(workbook, sheetName, dataList);
			if (workbook != null) {
				try {
					fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
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
				workbook = new HSSFWorkbook(); // 创建工作簿对象
			}else {
				workbook = new XSSFWorkbook(); // 创建工作簿对象
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
	 * 列头单元格样式
	 */
	public CellStyle getColumnTopStyle(Workbook workbook) {

		// 设置字体
		Font font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		CellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * 列数据信息单元格样式
	 */
	public CellStyle getStyle(Workbook workbook) {
		// 设置字体
		Font font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		//font.setFontName("Courier New");
		// 设置样式;
		CellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(CellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(CellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(CellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(CellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		return style;

	}
	
	private void createSheet(Workbook workbook, String sheetName, List<List<Object>> dataList){
		Sheet sheet = workbook.createSheet(sheetName); // 创建工作表
		
		// 产生表格标题行
		/*HSSFRow rowm = sheet.createRow(0);
		HSSFCell cellTiltle = rowm.createCell(0);

		

		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
		cellTiltle.setCellStyle(columnTopStyle);
		cellTiltle.setCellValue(title);*/
		// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
		CellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
		CellStyle style = this.getStyle(workbook); // 单元格样式对象
		// 定义所需列数
		int columnNum = rowRules.size();
		Row rowRowName = sheet.createRow(0); // 在索引2的位置创建行(最顶端的行开始的第二行)
		sheet.createFreezePane(0, 1, 0, 1);
		// 将列头设置到sheet的单元格中
		for (int n = 0; n < columnNum; n++) {
			Cell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
			cellRowName.setCellType(Cell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
			Map<String, String> rule = rowRules.get(n);
			cellRowName.setCellValue(rule.get("fieldName")); // 设置列头单元格的值
			cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
		}

		// 将查询出的数据设置到sheet对应的单元格中
		for (int i = 0; i < dataList.size(); i++) {

			List<Object> obj = dataList.get(i);// 遍历每个对象
			Row row = sheet.createRow(i + 1);// 创建所需的行数
			for (int j = 0; j < obj.size(); j++) {
				Cell cell = null; // 设置单元格的数据类型
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
					}else if("DateTime".equals(cellType)){
						cell = row.createCell(j, Cell.CELL_TYPE_NUMERIC);
						//style.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd HH:mm:ss"));
						if (obj.get(j) != null) {
							//cell.setCellValue((Date)obj.get(j));
							cell.setCellValue(DateUtil.getPrettyDateTime((Date)obj.get(j)));
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
				}else {
					//单元格默认居中
					style.setAlignment(CellStyle.ALIGN_CENTER);
				}
				String dataFormat = rowRule.get("dataFormat");
				if("0.00".equals(dataFormat)){
					style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
				}
				cell.setCellStyle(style); // 设置单元格样式
			}
		}
		// 让列宽随着导出的列长自动适应
		
		for (int colNum = 0; colNum < columnNum; colNum++) {
			int columnWidth = sheet.getColumnWidth(colNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				Row currentRow;
				// 当前行未被使用过
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
			sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
			/*if (colNum == 0) {
				sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
			} else {
				sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
			}*/
		}
	}
}