package com.ylink.cim.busioper.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.busioper.dao.NoticeMsgDao;
import com.ylink.cim.busioper.dao.NoticeMsgRecordDao;
import com.ylink.cim.busioper.domain.NoticeMsg;
import com.ylink.cim.busioper.domain.NoticeMsgRecord;
import com.ylink.cim.busioper.service.NoticeMngService;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.CustType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;
import com.ylink.cim.common.util.ParaManager;

import flink.etc.Symbol;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class NoticeMsgAction extends BaseDispatchAction {

	private NoticeMsgDao noticeMsgDao = (NoticeMsgDao)getService("noticeMsgDao");
	private NoticeMngService noticeMngService = (NoticeMngService)getService("noticeMngService");
	private NoticeMsgRecordDao noticeMsgRecordDao = (NoticeMsgRecordDao)getService("noticeMsgRecordDao");
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			NoticeMsgActionForm actionForm = (NoticeMsgActionForm)form;
			NoticeMsg noticeMsg = new NoticeMsg();
			BeanUtils.copyProperties(noticeMsg, actionForm);
			Map<String, Object> map = getParaMap();
			map.put("startCreateDate", actionForm.getStartCreateDate());
			map.put("endCreateDate", actionForm.getEndCreateDate());
			map.put("subject", actionForm.getSubject());
			map.put("branchNo", getSessionUser(request).getBranchNo());
			Paginater paginater = noticeMsgDao.findPaginater(map, getPager(request));
			saveQueryResult(request, paginater);
			String msg = LogUtils.r("消息提醒管理查询成功");
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		}catch(Exception e){
			String msg = LogUtils.r("消息提醒管理查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
			throw new Exception(e);
		}
		return forward("/pages/busioper/notice/noticeMsgList.jsp");
	}
	
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
//		ParaManager.setDictInReq(request, SysDictType.BranchType, "branchTypes");
		return forward("/pages/busioper/notice/noticeMsgAdd.jsp");
	}
	
	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NoticeMsgActionForm actionForm = (NoticeMsgActionForm)form;
		NoticeMsg noticeMsg = new NoticeMsg();
		BeanUtils.copyProperties(noticeMsg, actionForm);
		if (StringUtils.isEmpty(actionForm.getBranchNo()) 
				&& !BranchType.HQ_0000.getValue().equals(getSessionUser(request).getBranchNo())) {
			noticeMsg.setBranchNo(getSessionUser(request).getBranchNo());
		}
//		noticeMsg.setBranchNo(getSessionUser(request).getBranchNo());
		noticeMngService.saveNoticeMsg(noticeMsg);
		setResult(true, "添加成功", request);
		setReturnUrl("/noticeMsgAction.do?action=list", request);
		String msg = LogUtils.r("消息提醒添加成功,添加内容为：{?}",FeildUtils.toString(noticeMsg));
		super.logSuccess(request, UserLogType.ADD.getValue(), msg);
		return success(mapping);
	}
	public ActionForward showNotice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String custId = getSessionCustId(request);
		Map<String, Object> map = getParaMap();
		map.put("custId", custId);
		map.put("read", Symbol.NO);
		List<NoticeMsgRecord> list = noticeMsgRecordDao.findByParams(map);
		saveQueryResult(request, list);
		request.setAttribute("msgCnt", list.size());
		String msg = LogUtils.r("消息提醒查看成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return forward("/pageHome.jsp");
	}
	public ActionForward hasRead(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String msgId = request.getParameter("msgId");
			NoticeMsgRecord record = noticeMsgRecordDao.findById(msgId);
			record.setRead(Symbol.YES);
			record.setReadTime(new Date());
			noticeMngService.updateMsgRecord(record);
			request.setAttribute("readOne", Symbol.YES);
			String msg = LogUtils.r("客户阅读消息提醒成功,所更新的内容为：{?}",FeildUtils.toString(record));
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return showNotice(mapping, form, request, response);

		} catch (Exception e) {
			e.printStackTrace();
//			respond(response, "error");
			String msg = LogUtils.r("客户阅读消息提醒失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
		}
		return showNotice(mapping, form, request, response);
	}
	
	private void initData(HttpServletRequest request) throws Exception{
		//当前机构支持的业务类型
		ParaManager.setDictInReq(request, SysDictType.valueOf("BusiType"+getSessionBranch(request).getValue().substring(2, 4)), "busiTypes");
		CustType.setInReq(request);
		//ParaManager.setDictInReq(request, SysDictType.BranchType, "branchTypes");
	}
}
