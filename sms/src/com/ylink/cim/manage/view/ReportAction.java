package com.ylink.cim.manage.view;

import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.java.swing.plaf.motif.resources.motif;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.InoutType;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.common.util.ComInfo;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.AccountDetailDao;
import com.ylink.cim.manage.dao.AccountJournalDao;
import com.ylink.cim.manage.dao.AdrentBillDao;
import com.ylink.cim.manage.dao.DepositBillDao;
import com.ylink.cim.manage.dao.GeneralBillDao;
import com.ylink.cim.manage.dao.OrderDetailDao;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.GeneralBill;
import com.ylink.cim.manage.domain.OrderDetail;
import com.ylink.cim.manage.domain.OrderRecord;

import flink.etc.Assert;
import flink.util.AmountUtils;
import flink.util.DateUtil;
import flink.util.SpringContext;
import flink.web.BaseAction;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * 
 * 
 * @date 2013-11-27
 */
@Scope("prototype")
@Component
public class ReportAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private static final ComInfo comInfo = (ComInfo) getService("comInfo");
	@Autowired
	private GeneralBillDao generalBillDao = (GeneralBillDao) getService("generalBillDao");
	@Autowired
	private DepositBillDao depositBillDao;
	@Autowired
	private AccountDetailDao accountDetailDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountJournalDao accountJournalDao;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Autowired
	private OrderRecordDao orderRecordDao;
	public String waterBillDetail() throws Exception {
		String buildingNo = request.getParameter("buildingNo");
		String startCreateDate = request.getParameter("startCreateDate");
		String endCreateDate = request.getParameter("endCreateDate");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startCreateDate", startCreateDate);
		map.put("endCreateDate", DateUtil.addDays(endCreateDate, 1, "yyyyMMdd"));
		map.put("buildingNo", buildingNo);
		map.put("today", DateUtil.getCurrentDate());
		map.put("branchNo", getSessionBranchNo(request));
		generateReportWithConn("waterBillDetail.jasper", map, request, response);
		return null;
	}

	public String orderRecord() throws Exception {
		String id = request.getParameter("id");
		OrderRecord record = orderRecordDao.findById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createDate", DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", record.getCreateDate()));
		map.put("orderDate", record.getOrderDate());
		map.put("clientName", record.getClientName());
		map.put("contact", record.getContact());
		map.put("clientTel", record.getClientTel());//客户电话
		map.put("address", record.getAddress());//送货地址
		map.put("orderDate", record.getOrderDate());//订货日期
		map.put("orderId", record.getId());//订货日期
		map.put("comName", comInfo.getName());
		map.put("comTel", comInfo.getTel());
		map.put("orderId", id);
		map.put("amountStr", MoneyUtil.getFormatStr2(record.getPayAmt()));
		map.put("remark", record.getRemark());
		generateReportWithConn("orderRecord.jasper", map, request, response);
		return null;
	}

	public String orderDetail() throws Exception {
		String id = request.getParameter("id");
		OrderDetail orderDetail = orderDetailDao.findById(id);
		OrderRecord record = orderRecordDao.findById(orderDetail.getOrderId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("carModel", orderDetail.getCarModel());
		map.put("productName", orderDetail.getProductName());
		map.put("num", orderDetail.getNum());
		map.put("material", orderDetail.getMaterial());
		map.put("price", MoneyUtil.getFormatStr(orderDetail.getPrice()));
		map.put("amount", orderDetail.getAmount());
		map.put("color", orderDetail.getColor());
		map.put("amount", MoneyUtil.getFormatStr(orderDetail.getAmount()));
		map.put("createDate", DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", record.getCreateDate()));
		map.put("detailSn", orderDetail.getId());//明细单号
		
		map.put("clientName", record.getClientName());
		map.put("clientTel", record.getClientTel());//客户电话
		map.put("address", record.getAddress());//送货地址
		map.put("orderDate", record.getOrderDate());//订货日期
		
		map.put("comName", comInfo.getName());
		map.put("comTel", comInfo.getTel());
		generateReportWithConn("orderRecord.jasper", map, request, response);
		return null;
	}

	public String tradeReport() throws Exception {
		String tradeDate = request.getParameter("tradeDate");
		Map<String, Object> map = getParaMap();
		map.put("startCreateDate", tradeDate);
		map.put("endCreateDate", tradeDate);
		map.put("branchNo", getSessionBranchNo(request));
		Map<String, Object> sumInfo = accountJournalDao.findSumInfo(map);
		Long inCnt = (Long) sumInfo.get("inCnt");
		Double inAmtDouble = (Double) sumInfo.get("inAmt");
		String inAmt = MoneyUtil.getFormatStr(inAmtDouble);
		Long outCnt = (Long) sumInfo.get("outCnt");
		Double outAmtDouble = (Double) sumInfo.get("outAmt");
		String outAmt = MoneyUtil.getFormatStr(outAmtDouble);
		Map<String, Object> params = getParaMap();
		params.put("branchNo", getSessionBranchNo(request));
		params.put("tradeDate", tradeDate);
		params.put("beginDate", DateUtil.string2Date(tradeDate, "yyyyMMdd"));
		params.put("endDate", DateUtil.getNextDate(tradeDate, "yyyyMMdd"));
		params.put("inCnt", inCnt);
		params.put("inAmt", inAmt);
		params.put("outCnt", outCnt);
		params.put("outAmt", outAmt);
		params.put("netAmt",
				MoneyUtil.getFormatStr((Double) sumInfo.get("netAmt")));
		generateReportWithConn("tradeReport.jasper", params, request, response);
		return null;
	}

	public String gather() throws Exception {
		String gatherWay = request.getParameter("gatherWay");
		String gatherPeriod = request.getParameter("gatherPeriod");
		Map<String, Object> params = getParaMap();
		params.put("gatherWay", gatherWay);
		params.put("gatherPeriod", gatherPeriod);
		params.put("branchNo", getSessionBranchNo(request));
		List<Map<String, Object>> sumList = accountJournalDao
				.findGatherInfo(params);
		List<Map<String, Object>> inList = new LinkedList<Map<String, Object>>();
		List<Map<String, Object>> outList = new LinkedList<Map<String, Object>>();
		Map<String, Object> sumInfo = new HashMap<String, Object>();
		Long totalCnt = 0L;
		Double totalAmt = 0d;
		Long inCnt = 0L;
		Double inAmt = 0d;
		Long outCnt = 0L;
		Double outAmt = 0d;
		for (int i = 0; i < sumList.size(); i++) {
			Map<String, Object> map = sumList.get(i);
			map.put("tradeTypeName",
					TradeType.valueOf((String) map.get("tradeType")).getName());
			String inoutType = (String) map.get("inoutType");
			Long cnt = (Long) map.get("cnt");
			Double sumAmt = (Double) map.get("sumAmt");
			if (cnt == null) {
				cnt = 0L;
			}
			if (sumAmt == null) {
				sumAmt = 0d;
			}
			if (InoutType.TYPE_IN.getValue().equals(inoutType)) {
				inCnt += cnt;
				inAmt += sumAmt;
				inList.add(map);
			} else {
				outCnt += cnt;
				outAmt += sumAmt;
				outList.add(map);
			}
			totalCnt += cnt;
			totalAmt += sumAmt;
		}
		sumInfo.put("inCnt", inCnt);
		sumInfo.put("inAmt", MoneyUtil.getFormatStr2(inAmt));
		sumInfo.put("outCnt", outCnt);
		sumInfo.put("outAmt", MoneyUtil.getFormatStr2(outAmt));
		sumInfo.put("totalCnt", totalCnt);
		sumInfo.put("netAmt",
				MoneyUtil.getFormatStr2(AmountUtils.add(inAmt, -outAmt)));
		sumInfo.putAll(request.getParameterMap());
		sumInfo.put("today", DateUtil.getCurrentDate());
		String gatherTitle = "";
		String pattern = "";
		if ("D".equals(gatherWay)) {
			gatherTitle = "汇总日期:";
			pattern = "yyyyMMdd";
		} else if ("M".equals(gatherWay)) {
			gatherTitle = "汇总月份:";
			pattern = "yyyyMM";
		} else if ("Y".equals(gatherWay)) {
			gatherTitle = "汇总年份:";
			pattern = "yyyy";
		}
		Date beginDate = DateUtil.string2Date(gatherPeriod, pattern);
		Date endDate = DateUtil.getNextDate(gatherPeriod, pattern);
		sumInfo.put("gatherTitle", gatherTitle);
		sumInfo.put("gatherPeriod", gatherPeriod);
		sumInfo.put("beginDate", beginDate);
		sumInfo.put("endDate", endDate);
		generateReportWithConn("gatherBill.jasper", sumInfo, request, response);
		return null;
	}

	/**
	 * 通用收据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String generalBill() throws Exception {
		String id = request.getParameter("id");
		GeneralBill bill = generalBillDao.findById(GeneralBill.class, id);
		Assert.isTrue(
				canPrint(bill.getBranchNo(), getSessionBranchNo(request)),
				"单号错误，无法打印");
		Map<String, Object> map = PropertyUtils.describe(bill);
		map.putAll(request.getParameterMap());
		map.put("content", TradeType.valueOf(bill.getTradeType()).getName());
		map.put("today", DateUtil.getCurrentDate());
		map.put("num", bill.getNum());
		map.put("chargeUser", getSessionUser(request).getUserName());
		map.put("billSn", bill.getId());
		map.put("amount", MoneyUtil.getFormatStr2(bill.getPaidAmt()));
		map.put("price", MoneyUtil.getFormatStr2(bill.getUnitPrice()));
		map.put("chineseAmount", " " + MoneyUtil.numToRMBStr(bill.getPaidAmt()));
		// HouseInfo houseInfo = waterBillDao.findById(HouseInfo.class,
		// bill.getHouseSn());
		// map.put("houseDesc", houseInfo.getHouseDesc());
		map.put("comName", comInfo.getName());
		generateReportWithConn("generalBill.jasper", map, request, response);
		return null;
	}


	private void generateReportWithData(String jasperFileName,
			Map<String, Object> parameters, List<?> data,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");
		path = path.endsWith(File.separator) ? path : path + File.separator;
		String fullPath = path + "jasper" + File.separator + jasperFileName;
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(fullPath);
		JasperPrint jasperPrint = null;
		if (data != null) {
			JRMapCollectionDataSource ds = new JRMapCollectionDataSource(data);
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					parameters, ds);
		} else {
			jasperPrint = JasperFillManager
					.fillReport(jasperReport, parameters);
		}
		if (jasperPrint != null) {
			FileBufferedOutputStream fbos = new FileBufferedOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fbos);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "pdf文件");
			try {
				exporter.exportReport();
				fbos.close();
				response.setContentType("application/pdf");
				response.setContentLength(fbos.size());
				ServletOutputStream ouputStream = response.getOutputStream();
				try {
					fbos.writeData(ouputStream);
					fbos.dispose();
					ouputStream.flush();
				} finally {
					if (ouputStream != null)
						ouputStream.close();
				}
			} finally {
				IOUtils.closeQuietly(fbos);
				if (fbos != null) {
					fbos.close();
					fbos.dispose();
				}
			}
		}
	}

	private void generateReportWithConn(String jasperFileName,
			Map<String, Object> parameters, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");
		path = path.endsWith(File.separator) ? path : path + File.separator;
		String fullPath = path + "jasper" + File.separator + jasperFileName;
		System.out.println(fullPath);
		DataSource p = (DataSource) SpringContext.getService("dataSource");
		Connection conn = p.getConnection();
		response.reset();
		response.setContentType("application/pdf");
		ServletOutputStream ouputStream = null;
		try {
			ouputStream = response.getOutputStream();
			byte[] bytepdf = JasperRunManager.runReportToPdf(fullPath,
					parameters, conn);
			response.setContentLength(bytepdf.length);
			ouputStream.write(bytepdf, 0, bytepdf.length);
		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			try {
				ouputStream.flush();
				ouputStream.close();
				conn.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}

	private static boolean canPrint(String billBranchNo, String sessionBranchNo) {
		if (BranchType.HQ_0000.getValue().equals(sessionBranchNo)) {
			return true;
		}
		return StringUtils.equals(billBranchNo, sessionBranchNo);
	}

}
