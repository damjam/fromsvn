package flink.web.tag;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ylink.cim.admin.domain.Privilege;

import flink.consant.Constants;

/**
 * 
 *
 */
public class PrivilegeTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * privilge id.
	 */
	private String pid;
	private String style;

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().write("</span>");
		} catch (IOException e) {
		}
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpSession session = ((HttpServletRequest) this.pageContext
				.getRequest()).getSession();
		List privileges = (List) session.getAttribute(Constants.USER_PRIVILEGE);
		boolean hasPrivilege = false;

		if (privileges != null) {
			for (Iterator i = privileges.iterator(); i.hasNext();) {
				Privilege p = (Privilege) i.next();

				if (p == null || p.getLimitId() == null) {
					continue;
				}

				if (p.getLimitId().equals(pid)) {
					hasPrivilege = true;

					break;
				}
			}
		}

		String span = hasPrivilege ? "<span style=\"" + style + "\">"
				: "<span class=\"no-privilege\" style=\"display:none;" + style
						+ "\">";
		try {
			pageContext.getOut().write(span);
		} catch (IOException e) {
		}
		return EVAL_BODY_INCLUDE;
	}
}
