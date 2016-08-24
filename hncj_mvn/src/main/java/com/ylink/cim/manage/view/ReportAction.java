package com.ylink.cim.manage.view;

import java.io.File;
import java.io.OutputStream;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.IcCardType;
import com.ylink.cim.common.type.InoutType;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.common.util.ComInfo;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.AccountDetailDao;
import com.ylink.cim.manage.dao.AccountJournalDao;
import com.ylink.cim.manage.dao.AdrentBillDao;
import com.ylink.cim.manage.dao.DepositBillDao;
import com.ylink.cim.manage.dao.GeneralBillDao;
import com.ylink.cim.manage.dao.IcDepositDao;
import com.ylink.cim.manage.dao.WaterBillDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.AccountDetail;
import com.ylink.cim.manage.domain.AdrentBill;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.DecorateServiceBill;
import com.ylink.cim.manage.domain.DepositBill;
import com.ylink.cim.manage.domain.GeneralBill;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.IcDeposit;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.domain.ParkingBill;
import com.ylink.cim.manage.domain.WaterBill;

import flink.etc.Assert;
import flink.util.AmountUtils;
import flink.util.DateUtil;
import flink.util.SpringContext;
import flink.web.BaseAction;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
	private WaterBillDao waterBillDao = (WaterBillDao) getService("waterBillDao");
	@Autowired
	private GeneralBillDao generalBillDao = (GeneralBillDao) getService("generalBillDao");
	@Autowired
	private DepositBillDao depositBillDao;
	@Autowired
	private IcDepositDao icDepositDao;
	@Autowired
	private AccountDetailDao accountDetailDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountJournalDao accountJournalDao;
	@Autowired
	private AdrentBillDao adrentBillDao;

	public String waterBill() throws Exception {
		String waterBillId = request.getParameter("id");
		WaterBill bill = waterBillDao.findById(WaterBill.class, waterBillId);
		Assert.notNull(bill, "单号错误，无法打印");
		Assert.isTrue(
				canPrint(bill.getBranchNo(), getSessionBranchNo(request)),
				"单号错误，无法打印");
		Map<String, Object> map = PropertyUtils.describe(bill);
		map.putAll(request.getParameterMap());
		map.put("content", "水费");
		map.put("today", DateUtil.getCurrentDate());
		map.put("price", ParaManager.getWaterPrice());
		map.put("chargeUser", getSessionUser(request).getUserName());
		map.put("billSn", bill.getId());
		map.put("amount", MoneyUtil.getFormatStr2(bill.getAmount()));
		map.put("price", MoneyUtil.getFormatStr2(Double.parseDouble(ParaManager
				.getWaterPrice())));
		map.put("chineseAmount", " " + MoneyUtil.numToRMBStr(bill.getAmount()));
		HouseInfo houseInfo = waterBillDao.findById(HouseInfo.class,
				bill.getHouseSn());
		map.put("houseDesc", houseInfo.getHouseDesc());
		String startDate = DateUtil.getDate(
				DateUtil.getDateByYYYMMDD(bill.getPreRecordDate()),
				"yyyy-MM-dd");
		String endDate = DateUtil.getDate(
				DateUtil.getDateByYYYMMDD(bill.getCurRecordDate()),
				"yyyy-MM-dd");
		map.put("period", startDate + "--" + endDate);
		map.put("comName", comInfo.getName());
		generateReportWithConn("waterBill.jasper", map, request, response);
		return null;
	}

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

	public String parkingBill() throws Exception {
		String id = request.getParameter("id");
		ParkingBill parkingBill = waterBillDao.findById(ParkingBill.class, id);
		Assert.isTrue(
				canPrint(parkingBill.getBranchNo(), getSessionBranchNo(request)),
				"单号错误，无法打印");
		Map<String, Object> map = PropertyUtils.describe(parkingBill);
		map.putAll(request.getParameterMap());
		map.put("ownerName", parkingBill.getOwnerName());
		map.put("today", DateUtil.getCurrentDate());
		// map.put("price", ParaManager.getWaterPrice());
		map.put("chargeUser", getSessionUser(request).getUserName());
		map.put("billSn", parkingBill.getId());
		map.put("amount", MoneyUtil.getFormatStr2(parkingBill.getAmount()));
		map.put("price", MoneyUtil.getFormatStr2(Double.parseDouble(ParaManager
				.getWaterPrice())));
		map.put("chineseAmount",
				" " + MoneyUtil.numToRMBStr(parkingBill.getAmount()));
		map.put("comName", comInfo.getName());
		generateReportWithConn("parkingBill.jasper", map, request, response);
		return null;
	}

	public String decorateServiceBill() throws Exception {
		String id = request.getParameter("id");
		DecorateServiceBill bill = waterBillDao.findById(
				DecorateServiceBill.class, id);
		Assert.isTrue(
				canPrint(bill.getBranchNo(), getSessionBranchNo(request)),
				"单号错误，无法打印");
		Map<String, Object> map = PropertyUtils.describe(bill);
		map.putAll(request.getParameterMap());
		map.put("today", DateUtil.getCurrentDate());
		HouseInfo houseInfo = waterBillDao.findById(HouseInfo.class,
				bill.getHouseSn());
		map.put("area", MoneyUtil.getFormatStr2(houseInfo.getArea()) + "㎡");
		map.put("houseDesc", houseInfo.getHouseDesc());
		map.put("chargeUser", getSessionUser(request).getUserName());
		map.put("billSn", bill.getId());
		map.put("totalAmount", MoneyUtil.getFormatStr2(bill.getAmount()));
		map.put("cleanPrice", MoneyUtil.getFormatStr2(bill.getCleanPrice())
				+ "元/㎡");
		map.put("cleanAmount", MoneyUtil.getFormatStr2(bill.getCleanAmount()));
		map.put("liftFee", MoneyUtil.getFormatStr2(bill.getLiftFee()));
		map.put("liftFeeDesc", "二层收100元，二层以上每层加收20元");
		map.put("chineseAmount", " " + MoneyUtil.numToRMBStr(bill.getAmount()));
		map.put("sumTitle", "汇总金额");
		map.put("comName", comInfo.getName());
		generateReportWithConn("decorateServiceBill.jasper", map, request,
				response);
		return null;
	}

	public String depositBill() throws Exception {
		String id = request.getParameter("id");
		DepositBill bill = depositBillDao.findById(id);
		Assert.isTrue(
				canPrint(bill.getBranchNo(), getSessionBranchNo(request)),
				"单号错误，无法打印");
		Map<String, Object> map = PropertyUtils.describe(bill);
		map.putAll(request.getParameterMap());
		map.put("today", DateUtil.getCurrentDate());
		// map.put("price", ParaManager.getWaterPrice());
		if(StringUtils.isNotEmpty(bill.getHouseSn())){
			HouseInfo houseInfo = depositBillDao.findById(HouseInfo.class,
					bill.getHouseSn());
			map.put("houseDesc", houseInfo.getHouseDesc());
		}
		map.put("chargeUser", getSessionUser(request).getUserName());
		map.put("billSn", bill.getId());
		map.put("amount", MoneyUtil.getFormatStr2(bill.getAmount()));
		map.put("chineseAmount", " " + MoneyUtil.numToRMBStr(bill.getAmount()));
		map.put("content", "押金");
		map.put("comName", comInfo.getName());
		String templateName = "depositBill.jasper";
		if(StringUtils.isEmpty(bill.getHouseSn())) {
			templateName = "merchantDepositBill.jasper";
		}
		generateReportWithConn(templateName, map, request, response);
		
		return null;
	}

	// 业主充值
	public String depositDetailBill() throws Exception {
		String id = request.getParameter("id");
		AccountDetail detail = accountDetailDao.findById(id);
		String acctNo = detail.getAcctNo();
		Account account = accountDao.findById(acctNo);
		Assert.isTrue(
				canPrint(account.getBranchNo(), getSessionBranchNo(request)),
				"单号错误，无法打印");
		Map<String, Object> map = PropertyUtils.describe(detail);
		map.putAll(request.getParameterMap());
		map.put("today", DateUtil.getCurrentDate());
		// map.put("price", ParaManager.getWaterPrice());
		OwnerInfo ownerInfo = depositBillDao.findById(OwnerInfo.class,
				detail.getAcctNo());
		String houseSn = ownerInfo.getHouseSn();
		HouseInfo houseInfo = depositBillDao.findById(HouseInfo.class, houseSn);
		map.put("payerName", ownerInfo.getOwnerName());
		map.put("houseDesc", houseInfo.getHouseDesc());
		map.put("chargeUser", getSessionUser(request).getUserName());
		map.put("billSn", detail.getId());
		map.put("amount", MoneyUtil.getFormatStr2(detail.getAmount()));
		map.put("chineseAmount",
				" " + MoneyUtil.numToRMBStr(detail.getAmount()));
		map.put("content", "预存水费");
		map.put("remark",
				"本次充值后余额：" + MoneyUtil.getFormatStr2(detail.getBalance()) + "元");
		map.put("comName", comInfo.getName());
		generateReportWithConn("depositBill.jasper", map, request, response);
		return null;
	}

	public String commonServiceBill() throws Exception {
		String id = request.getParameter("id");
		CommonServiceBill bill = waterBillDao.findById(CommonServiceBill.class,
				id);
		Assert.isTrue(
				canPrint(bill.getBranchNo(), getSessionBranchNo(request)),
				"单号错误，无法打印");
		Map<String, Object> map = PropertyUtils.describe(bill);
		map.putAll(request.getParameterMap());
		map.put("today", DateUtil.getCurrentDate());
		// map.put("price", ParaManager.getWaterPrice());
		HouseInfo houseInfo = waterBillDao.findById(HouseInfo.class,
				bill.getHouseSn());
		map.put("houseDesc",
				houseInfo.getHouseSn() + "，面积:"
						+ MoneyUtil.getFormatStr2(bill.getArea()) + "㎡");
		map.put("billSn", bill.getId());
		map.put("totalAmount", MoneyUtil.getFormatStr2(bill.getTotalAmount()));
		map.put("servicePrice", MoneyUtil.getFormatStr2(bill.getServicePrice())
				+ "元/月.平米");
		map.put("serviceAmount",
				MoneyUtil.getFormatStr2(bill.getServiceAmount()));
		map.put("lightPrice", MoneyUtil.getFormatStr2(bill.getLightPrice())
				+ "元/月");
		map.put("lightAmount", MoneyUtil.getFormatStr2(bill.getLightAmount()));
		map.put("chineseAmount",
				" " + MoneyUtil.numToRMBStr(bill.getTotalAmount()));
		String startDate = DateUtil.getDate(DateUtil.getDateByYYYMMDD(bill
				.getStartDate()));
		String endDate = DateUtil.getDate(DateUtil.getDateByYYYMMDD(bill
				.getEndDate()));
		map.put("period", startDate + "—" + endDate);
		map.put("comName", comInfo.getName());
		generateReportWithConn("commonServiceBill.jasper", map, request,
				response);
		// generateReportWithData("commonServiceBill.jasper", map, null,
		// request, response);
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

	public String icDepositBill() throws Exception {
		String id = request.getParameter("id");
		IcDeposit bill = icDepositDao.findById(IcDeposit.class, id);
		Assert.isTrue(
				canPrint(bill.getBranchNo(), getSessionBranchNo(request)),
				"单号错误，无法打印");
		Map<String, Object> map = PropertyUtils.describe(bill);
		map.putAll(request.getParameterMap());
		map.put("cardType", IcCardType.valueOf(bill.getCardType()).getName());
		map.put("today", DateUtil.getCurrentDate());
		map.put("chargeUser", getSessionUser(request).getUserName());
		map.put("billSn", bill.getId());
		map.put("amount", MoneyUtil.getFormatStr2(bill.getAmount()));
		// map.put("price", MoneyUtil.getFormatStr2(bill.getUnitPrice()));
		map.put("chineseAmount", " " + MoneyUtil.numToRMBStr(bill.getAmount()));
		// HouseInfo houseInfo = waterBillDao.findById(HouseInfo.class,
		// bill.getHouseSn());
		// map.put("houseDesc", houseInfo.getHouseDesc());
		map.put("comName", comInfo.getName());
		generateReportWithConn("icDepositBill.jasper", map, request, response);
		return null;
	}

	public String adrentBill() throws Exception {
		String id = request.getParameter("id");
		AdrentBill bill = adrentBillDao.findById(AdrentBill.class, id);
		Assert.isTrue(
				canPrint(bill.getBranchNo(), getSessionBranchNo(request)),
				"单号错误，无法打印");
		Map<String, Object> map = PropertyUtils.describe(bill);
		map.putAll(request.getParameterMap());
		map.put("today", DateUtil.getCurrentDate());
		map.put("content", "广告位租赁费");
		map.put("chargeUser", getSessionUser(request).getUserName());
		map.put("billSn", bill.getId());
		map.put("amount", MoneyUtil.getFormatStr2(bill.getPaidAmt()));
		map.put("period", bill.getBeginDate() + "-" + bill.getEndDate());
		// map.put("price", MoneyUtil.getFormatStr2(bill.getUnitPrice()));
		map.put("chineseAmount", " " + MoneyUtil.numToRMBStr(bill.getPaidAmt()));
		// HouseInfo houseInfo = waterBillDao.findById(HouseInfo.class,
		// bill.getHouseSn());
		// map.put("houseDesc", houseInfo.getHouseDesc());
		map.put("comName", comInfo.getName());
		generateReportWithConn("adrentBill.jasper", map, request, response);
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
				.loadObjectFromFile(fullPath);
		JasperPrint jasperPrint = null;
		OutputStream outputStream = response.getOutputStream();
		if (data != null) {
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					parameters, dataSource);
		} else {
			jasperPrint = JasperFillManager
					.fillReport(jasperReport, parameters);
		}
		
		if (jasperPrint != null) {
			try {
				JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			} catch (JRException e) {
				e.printStackTrace();
			} finally {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (Exception e) {
					e.getMessage();
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
			JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromFile(fullPath);
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(jasperReport, parameters, conn);
			JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
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
