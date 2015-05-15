package flink.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.WebUtils;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.type.BranchType;

import flink.consant.Constants;

public class DispatchBranchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().toLowerCase();
		String contextPath = req.getContextPath();
		String tag = "";
		if (url.indexOf(contextPath + "/gz") != -1) {
			tag = "gz";
		} else if (url.indexOf(contextPath + "/dg") != -1) {
			tag = "dg";
		} else if (url.indexOf(contextPath + "/sz") != -1) {
			tag = "sz";
		}
		UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(req, Constants.SESSION_USER);
		if (userInfo != null) {
			String sessionTag = (String) WebUtils.getSessionAttribute(req, Constants.BRANCH_TAG);
			if (!StringUtils.isEmpty(sessionTag) && !StringUtils.equals(tag, sessionTag)) {
				req.setAttribute("tip", "����ǰ��¼�Ļ���Ϊ" + BranchType.getByTag(sessionTag).getName()
						+ ",�������Ҫ��¼�������������˳�ϵͳ�����µ�¼");
			}
			req.getRequestDispatcher("/main.jsp").forward(req, resp);
			return;
		}
		// WebUtils.setSessionAttribute(req, Constants.BRANCH_TYPE, branchType);
		WebUtils.setSessionAttribute(req, Constants.BRANCH_TAG, tag);
		WebUtils.setSessionAttribute(req, Constants.BRANCH_TYPE, BranchType.getByTag(tag));
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

}
