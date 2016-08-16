package flink.util;

import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * poi工具类.
 * 
 * 
 */
public abstract class PoiUtils {
	private static final Logger logger = Logger.getLogger(PoiUtils.class);

	/**
	 * 创建cell, 设置utf16编码.
	 * 
	 * @param row
	 * @param index
	 * @param value
	 * @return
	 */
	private static HSSFCell createCell(HSSFRow row, int index) {
		HSSFCell cell = row.createCell(index);

		return cell;
	}

	/**
	 * 创建cell, 并设置值.
	 * 
	 * @param row
	 * @param index
	 * @param value
	 * @return
	 */
	public static HSSFCell createCell(HSSFRow row, int index, Date value) {
		HSSFCell cell = createCell(row, index);

		if (value != null) {
			cell.setCellValue(value);
		}

		return cell;
	}

	/**
	 * 创建cell, 并设置值.
	 * 
	 * @param row
	 * @param index
	 * @param value
	 * @return
	 */
	public static HSSFCell createCell(HSSFRow row, int index, Date value,
			HSSFCellStyle style) {
		HSSFCell cell = createCell(row, index);

		if (value != null) {
			cell.setCellValue(value);

			if (style != null) {
				cell.setCellStyle(style);
			}
		}

		return cell;
	}

	/**
	 * 创建cell, 并设置值.
	 * 
	 * @param row
	 * @param index
	 * @param value
	 * @return
	 */
	public static HSSFCell createCell(HSSFRow row, int index, Double value) {
		return createCell(row, index, value, null);
	}

	/**
	 * 创建cell, 并设置值.
	 * 
	 * @param row
	 * @param index
	 * @param value
	 * @return
	 */
	public static HSSFCell createCell(HSSFRow row, int index, Double value,
			HSSFCellStyle style) {
		HSSFCell cell = createCell(row, index);

		if (value != null) {
			cell.setCellValue(value.doubleValue());

			if (style != null) {
				cell.setCellStyle(style);
			}
		}

		return cell;
	}

	/**
	 * 创建cell, 并设置值.
	 * 
	 * @param row
	 * @param index
	 * @param value
	 * @return
	 */
	public static HSSFCell createCell(HSSFRow row, int index, Long value) {
		HSSFCell cell = createCell(row, index);

		if (value != null) {
			cell.setCellValue(value.longValue());
		}

		return cell;
	}

	/**
	 * 创建cell, 并设置值.
	 * 
	 * @param row
	 * @param index
	 * @param value
	 * @return
	 */
	public static HSSFCell createCell(HSSFRow row, int index, String value) {
		HSSFCell cell = createCell(row, index);
		cell.setCellValue(value);

		return cell;
	}

	/**
	 * 创建cell, 并设置值.
	 * 
	 * @param row
	 * @param index
	 * @param value
	 * @return
	 */
	public static void createCell(HSSFRow row, int startIndex, String[] values) {
		for (int i = 0; i < values.length; i++) {
			HSSFCell cell = createCell(row, startIndex + i);
			cell.setCellValue(values[i]);
		}
	}

	/**
	 * 创建sheet.
	 * 
	 * @param workBook
	 * @param sheetName
	 * @return
	 */
	public static HSSFSheet createSheet(HSSFWorkbook workBook, String sheetName) {
		HSSFSheet sheet = workBook.createSheet();
		workBook.setSheetName(workBook.getNumberOfSheets() - 1, sheetName);
		return sheet;
	}

	public static HSSFCellStyle getAmountStyle(HSSFWorkbook workBook) {
		HSSFCellStyle style = workBook.createCellStyle();
		style.setDataFormat(workBook.createDataFormat().getFormat("#,##0.00"));

		return style;
	}

	/**
	 * 合并元素.
	 * 
	 * @param rowFrom
	 * @param colFrom
	 * @param rowTo
	 * @param colTo
	 */
	public static void mergeCell(HSSFSheet sheet, int rowFrom, int colFrom,
			int rowTo, int colTo) {
		sheet.addMergedRegion(new CellRangeAddress(rowFrom, colFrom, rowTo,
				colTo));
	}

	public static void respondExcel(HttpServletResponse response,
			HSSFWorkbook workBook, String fileName) {
		OutputStream output = null;

		try {
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			response.setContentType("application/vnd.ms-excel");

			output = response.getOutputStream();
			workBook.write(output);
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}
}
