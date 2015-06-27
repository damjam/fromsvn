package flink.web.tag;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class FormatDate extends SimpleTagSupport {
	private static final long serialVersionUID = 1L;
	private String sourcePattern;
	private String targetPattern;
	private String value;

	public String getSourcePattern() {
		return sourcePattern;
	}

	public void setSourcePattern(String sourcePattern) {
		this.sourcePattern = sourcePattern;
	}

	public String getTargetPattern() {
		return targetPattern;
	}

	public void setTargetPattern(String targetPattern) {
		this.targetPattern = targetPattern;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(sourcePattern) || StringUtils.isEmpty(value)) {
			return;
		}
		try {
			if (StringUtils.isEmpty(targetPattern)) {
				targetPattern = "yyyy-MM-dd hh:mm:ss";
			}
			DateFormat df = new SimpleDateFormat(sourcePattern);
			Date d = df.parse(value);
			String html = new SimpleDateFormat(targetPattern).format(d);
			this.getJspContext().getOut().write(html);
		} catch (Exception e) {
			// do nothing.
		}
	}
}
