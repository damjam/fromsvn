package flink.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 */
public class Css extends SimpleTagSupport {
	private static final long serialVersionUID = 1L;
	private String href;

	@Override
	public void doTag() throws JspException, IOException {
		String contextPath = ((HttpServletRequest) ((PageContext) this
				.getJspContext()).getRequest()).getContextPath();
		this.getJspContext()
				.getOut()
				.write("<link rel=\"stylesheet\" type=\"text/css\" href=\""
						+ contextPath + href + "\" />");
	}

	public void setHref(String href) {
		this.href = href;
	}
}
