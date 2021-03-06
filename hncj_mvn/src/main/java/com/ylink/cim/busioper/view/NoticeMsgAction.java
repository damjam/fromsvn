package com.ylink.cim.busioper.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.busioper.dao.NoticeMsgDao;
import com.ylink.cim.busioper.dao.NoticeMsgRecordDao;
import com.ylink.cim.busioper.domain.NoticeMsg;
import com.ylink.cim.busioper.domain.NoticeMsgRecord;
import com.ylink.cim.busioper.service.NoticeMngService;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FieldUtils;

import flink.etc.Symbol;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class NoticeMsgAction extends BaseAction implements
		ModelDriven<NoticeMsg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private NoticeMsgDao noticeMsgDao;
	@Autowired
	private NoticeMngService noticeMngService;
	@Autowired
	private NoticeMsgRecordDao noticeMsgRecordDao;

	public String doAdd() throws Exception {
		NoticeMsg noticeMsg = new NoticeMsg();
		BeanUtils.copyProperties(noticeMsg, model);
		if (StringUtils.isEmpty(model.getBranchNo())
				&& !BranchType.HQ_0000.getValue().equals(
						getSessionUser(request).getBranchNo())) {
			noticeMsg.setBranchNo(getSessionUser(request).getBranchNo());
		}
		// noticeMsg.setBranchNo(getSessionUser(request).getBranchNo());
		noticeMngService.saveNoticeMsg(noticeMsg);
		setResult(true, "添加成功", request);
		setReturnUrl("/noticeMsgAction.do?action=list", request);
		String msg = MsgUtils.r("消息提醒添加成功,添加内容为：{?}",
				FieldUtils.toString(noticeMsg));
		super.logSuccess(request, UserLogType.ADD.getValue(), msg);
		return SUCCESS;
	}

	public String hasRead() throws Exception {
		try {
			String msgId = request.getParameter("msgId");
			NoticeMsgRecord record = noticeMsgRecordDao.findById(msgId);
			record.setRead(Symbol.YES);
			record.setReadTime(new Date());
			noticeMngService.updateMsgRecord(record);
			request.setAttribute("readOne", Symbol.YES);
			String msg = MsgUtils.r("客户阅读消息提醒成功,所更新的内容为：{?}",
					FieldUtils.toString(record));
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return showNotice();

		} catch (Exception e) {
			e.printStackTrace();
			// respond(response, "error");
			String msg = MsgUtils.r("客户阅读消息提醒失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
		}
		return showNotice();
	}

	private void initData(HttpServletRequest request) throws Exception {
		// 当前机构支持的业务类型
		//CustType.setInReq(request);
		// ParaManager.setDictInReq(request, SysDictType.BranchType,
		// "branchTypes");
	}

	public String list() throws Exception {
		try {
			NoticeMsg noticeMsg = new NoticeMsg();
			BeanUtils.copyProperties(noticeMsg, model);
			Map<String, Object> map = getParaMap();
			map.put("startCreateDate", model.getStartCreateDate());
			map.put("endCreateDate", model.getEndCreateDate());
			map.put("subject", model.getSubject());
			map.put("branchNo", getSessionUser(request).getBranchNo());
			Paginater paginater = noticeMsgDao.findPaginater(map,
					getPager(request));
			saveQueryResult(request, paginater);
			String msg = MsgUtils.r("消息提醒管理查询成功");
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			String msg = MsgUtils.r("消息提醒管理查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
			throw new Exception(e);
		}
		// return forward("/pages/busioper/notice/noticeMsgList.jsp");
		return "list";
	}

	public String showNotice() throws Exception {
		String custId = getSessionCustId(request);
		Map<String, Object> map = getParaMap();
		map.put("custId", custId);
		map.put("read", Symbol.NO);
		List<NoticeMsgRecord> list = noticeMsgRecordDao.findByParams(map);
		saveQueryResult(request, list);
		request.setAttribute("msgCnt", list.size());
		String msg = MsgUtils.r("消息提醒查看成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		// return forward("/pageHome.jsp");
		return "home";
	}

	public String toAdd() throws Exception {
		initData(request);
		// ParaManager.setDictInReq(request, SysDictType.BranchType,
		// "branchTypes");
		// return forward("/pages/busioper/notice/noticeMsgAdd.jsp");
		return "add";
	}

	@Override
	public NoticeMsg getModel() {
		return model;
	}

	private NoticeMsg model = new NoticeMsg();
}
