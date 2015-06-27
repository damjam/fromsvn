package flink.web.tag;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 显示表格, 如果表格无数据, 则显示提示信息.
 * 
 *
 */
public class ShowDataGridTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * 表格数据在request 中的名称.
	 */
	private String name;
	private String msg;
	private String styleId;
	private String styleClass;
	private boolean showTopbar;

	public void setShowTopbar(boolean showTopbar) {
		this.showTopbar = showTopbar;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().write("</div>");
		} catch (IOException e) {
		}
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		Collection data = (Collection) request.getAttribute(name);
		boolean hasData = CollectionUtils.isNotEmpty(data);

		if (StringUtils.isEmpty(msg)) {
			msg = "没有您需要的数据";
		}

		StringBuffer divSb = new StringBuffer();

		if (showTopbar) {
			divSb.append("<div class=\"separator-bar\"></div>");
		}

		if (hasData) {
			divSb.append("<div ");

			if (StringUtils.isNotEmpty(styleId)) {
				divSb.append("id=\"" + styleId + "\" ");
			}

			divSb.append("class=\"has-data");

			if (StringUtils.isNotEmpty(styleClass)) {
				divSb.append(" " + styleClass + "\"");
			} else {
				divSb.append("\"");
			}

			divSb.append(">");
		} else {
			divSb.append("<div class=\"no-data " + styleClass + " \">");
			divSb.append(msg);
		}

		try {
			pageContext.getOut().write(divSb.toString());
		} catch (IOException e) {

		}

		return hasData ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}
}
