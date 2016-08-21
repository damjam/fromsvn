package flink.web.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import flink.util.Type;

/**
 *
 */
public class TypeTag extends SimpleTagSupport {
	private static final long serialVersionUID = 1L;
	private String className;
	private String value;
	private String property;

	public void setProperty(String property) {
		this.property = property;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(className) || StringUtils.isEmpty(value)) {
			return;
		}

		String packName = getPackage();
		if (StringUtils.isEmpty(packName)) {
			return;
		}

		try {
			Class typeClass = Class.forName(packName.concat(".").concat(
					className));
			if (typeClass == null) {
				return;
			}

			Map allType = (Map) ReflectionUtils.findField(typeClass, "ALL")
					.get(typeClass);
			Type type = (Type) allType.get(value);

			if (allType.get(value) == null) {
				return;
			}

			if (StringUtils.isEmpty(property)) {
				property = "name";
			}

			String html = ObjectUtils.toString(PropertyUtils.getProperty(type,
					property));
			this.getJspContext().getOut().write(html);
		} catch (Exception e) {
			// do nothing.
		}
	}

	private String getPackage() {
		HttpServletRequest request = (HttpServletRequest) ((PageContext) this
				.getJspContext()).getRequest();

		return request.getSession().getServletContext()
				.getInitParameter("typePackageName");
	}
}
