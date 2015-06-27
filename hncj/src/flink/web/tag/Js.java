package flink.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class Js extends SimpleTagSupport {
	private static final long serialVersionUID = 1L;
	private String src;
	private String defer;

	@Override
	public void doTag() throws JspException, IOException {
		String contextPath = ((HttpServletRequest) ((PageContext) this
				.getJspContext()).getRequest()).getContextPath();
		String deferContent = "";

		if (StringUtils.isNotEmpty(defer)) {
			deferContent = "defer=\"" + defer + "\"";
		}

		this.getJspContext()
				.getOut()
				.write("<script type=\"text/javascript\" " + deferContent
						+ " src=\"" + contextPath + src + "\"></script>");
	}

	public void setDefer(String defer) {
		this.defer = defer;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}
