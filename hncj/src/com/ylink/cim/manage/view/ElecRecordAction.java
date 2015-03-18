package com.ylink.cim.manage.view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.ylink.cim.manage.dao.ElecRecordDao;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.domain.ElecRecord;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.service.ElecRecordService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class ElecRecordAction extends BaseDispatchAction {
	private ElecRecordDao elecRecordDao = (ElecRecordDao)getService("elecRecordDao");
	private HouseInfoDao houseInfoDao = (HouseInfoDao)getService("houseInfoDao");
	private ElecRecordService elecRecordService = (ElecRecordService)getService("elecRecordService");
	
	public ActionForward check(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			elecRecordService.checkRecord(id, getSessionUser(request));
			setResult(true, "账单生成成功", request);
		} catch (Exception e) {
			setResult(false, "生成账单失败", request);
			e.printStackTrace();
		}
		return list(mapping, form, request, response);
		
	}
	public ActionForward checkAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Integer num = elecRecordService.checkAllRecord(getSessionUser(request));
			if (num > 0) {
				setResult(true, "已生成"+num+"条账单", request);
			}else {
				setResult(true, "没有需要生成账单的记录", request);
			}
		} catch (Exception e) {
			setResult(false, "生成账单失败", request);
			e.printStackTrace();
		}
		return list(mapping, form, request, response);
		
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			elecRecordService.deleteRecord(id, getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, "删除失败", request);
			e.printStackTrace();
		}
		return list(mapping, form, request, response);
	}
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ElecRecordActionForm actionForm = (ElecRecordActionForm)form;
			ElecRecord elecRecord = new ElecRecord();
			BeanUtils.copyProperties(elecRecord, actionForm);
			elecRecordService.saveElecRecord(elecRecord, getSessionUser(request));
			setResult(true, "数据已保存", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			setResult(false, "保存失败"+e.getMessage(), request);
		}
		return forward("/pages/manage/meter/elec/elecRecordAdd.jsp");
	}
	public ActionForward doImport(ActionMapping mapping, ActionForm actionform, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ElecRecordActionForm form = (ElecRecordActionForm) actionform;
			String recordMonth = form.getRecordMonth();
			String preRecordDate = form.getPreRecordDate();
			String curRecordDate = form.getCurRecordDate();
			FormFile file = form.getFile();

			InputStream is = file.getInputStream();
			Workbook book = null;
			String fileName = file.getFileName();
			if (fileName.toLowerCase().endsWith(".xls")) {
				book = WorkbookFactory.create(is);
			} else if (fileName.toLowerCase().endsWith(".xlsx")) {
				book = new XSSFWorkbook(is);
			} else {
				throw new Exception("文件格式不正确");
			}
			// book = WorkbookFactory.create(is);
			// book = new XSSFWorkbook(is);
			IOUtils.closeQuietly(is);
			// 获得第一个工作表对象
			// XSSFSheet sheet = book.getSheetAt(0);
			int sheetCnt = book.getNumberOfSheets();
			List<ElecRecord> list = new ArrayList<ElecRecord>();
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
						houseSn = StringUtils.trimToEmpty(houseSn.replace("盛世浩苑", ""));
						HouseInfo houseInfo = houseInfoDao.findById(HouseInfo.class, houseSn);
						Assert.notNull(houseInfo, "房屋编号"+houseSn+"不存在");
						double prenumd = row.getCell(1).getNumericCellValue();
						Integer prenum = (int)prenumd;
						double curnumd = row.getCell(2).getNumericCellValue();
						Integer curnum = (int)curnumd;
						if (curnum < prenum) {
							throw new Exception("本期读数小于上期读数");
						}
						String remark = "";
						if (row.getCell(3) != null) {
							remark = row.getCell(3).getStringCellValue();
						}
						ElecRecord record = new ElecRecord();
						record.setHouseSn(houseSn);
						record.setPrenum(prenum);
						record.setCurnum(curnum);
						record.setNum(curnum-prenum);
						record.setRecordMonth(recordMonth);
						record.setPreRecordDate(preRecordDate);
						record.setCurRecordDate(curRecordDate);
						record.setRemark(remark);
						list.add(record);
					} catch (Exception e) {
						throw new Exception("第" + (i+1) +"个工作表中第"+ (j+1) +"行:" + e.getMessage());
					}
				}
			}
			elecRecordService.importDeposit(list, getSessionUser(request));
			StringBuilder message = new StringBuilder();
			message.append("操作完成，共导入").append(list.size()).append("条记录");
			setResult(true, message.toString(), request);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败:" + e.getMessage(), request);
		}
		return forward("/pages/manage/meter/elec/elecRecordImport.jsp");
	}
	public ActionForward getPreRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String houseSn = request.getParameter("houseSn");
			ElecRecord elecRecord = elecRecordService.getPreRecord(houseSn);
			JSONObject jsonObject = new JSONObject();
			Integer prenum = 0;
			String preRecordDate = "";
			if (elecRecord != null) {
				prenum = elecRecord.getCurnum();
				preRecordDate = elecRecord.getCurRecordDate();
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
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = getParaMap();
		ElecRecordActionForm actionForm = (ElecRecordActionForm)form;
		map.put("startCreateDate", actionForm.getStartCreateDate());
		map.put("endCreateDate", actionForm.getEndCreateDate());
		map.put("houseSn", actionForm.getHouseSn());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = elecRecordDao.findRecordPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return forward("/pages/manage/meter/elec/elecRecordList.jsp");
	}
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String preRecordDate = "";
		String curRecordDate = "";
		String beginDate = "";
		if(day > 26){
			preRecordDate = DateUtil.getMaxMonthDate(-1);
			curRecordDate = DateUtil.getCurrentDate();
			Date d1 = DateUtil.addMonths(DateUtil.getCurrent(), -1);
			beginDate = DateUtil.getDateYYYYMM(d1);
		}else {
			preRecordDate = DateUtil.getMaxMonthDate(-2);
			curRecordDate = DateUtil.getMaxMonthDate(-1);
			Date d1 = DateUtil.addMonths(DateUtil.getCurrent(), -2);
			beginDate = DateUtil.getDateYYYYMM(d1);
		}
		//DateUtil.addMonths(date, num);
		String recordMonth = beginDate+"-"+curRecordDate.substring(0, 6);
		ElecRecordActionForm actioinForm = (ElecRecordActionForm)form;
		actioinForm.setPreRecordDate(preRecordDate);
		actioinForm.setCurRecordDate(curRecordDate);
		actioinForm.setRecordMonth(recordMonth);
		return forward("/pages/manage/meter/elec/elecRecordAdd.jsp");
	}
	public ActionForward toImport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return forward("/pages/manage/meter/elec/elecRecordImport.jsp");
	}
}
