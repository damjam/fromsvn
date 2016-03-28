package flink.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

public class Msg extends SimpleTagSupport {
	private static final long serialVersionUID = 1L;
	private String styleClass;
	private String style;

	public void setStyle(String style) {
		this.style = style;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	@Override
	public void doTag() throws JspException, IOException {
		HttpServletRequest request = (HttpServletRequest) ((PageContext) this
				.getJspContext()).getRequest();
		String msg = (String) request.getAttribute("msg");

		if (StringUtils.isEmpty(msg)) {
			return;
		}

		String classHtml = StringUtils.isEmpty(styleClass) ? " class=\"msg\" "
				: " class=\"".concat(styleClass).concat("\"");
		String styleHtml = StringUtils.isEmpty(style) ? "" : " style=\""
				.concat(style).concat("\" ");
		String html = "<div".concat(classHtml).concat(styleHtml).concat(">")
				.concat(msg).concat("</div>");

		this.getJspContext().getOut().write(html);
	}
}
