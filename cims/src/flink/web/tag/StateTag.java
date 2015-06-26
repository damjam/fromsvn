package flink.web.tag;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import flink.util.State;

/**
 *
 */
public class StateTag extends SimpleTagSupport {
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

	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(className) || StringUtils.isEmpty(value)) {
			return;
		}
		
		String packName = getPackage();
		if (StringUtils.isEmpty(packName)) {
			return;
		}
		
		try {
			Class stateClass = Class.forName(packName.concat(".").concat(className));
			if (stateClass == null) {
				return;
			}
			
			Field field = stateClass.getField("ALL");
			field.setAccessible(true);
			
			Map allState = (Map) field.get(stateClass);//ReflectionUtils.findField(stateClass, "ALL").get(stateClass);
			State state = (State) allState.get(value);
			
			if (allState.get(value) == null) {
				return;
			}
			
			if (StringUtils.isEmpty(property)) {
				property = "name";
			}
			
			String html = ObjectUtils.toString(PropertyUtils.getProperty(state, property));
			this.getJspContext().getOut().write(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return
	 * 
	 */
	private String getPackage() {
		HttpServletRequest request = (HttpServletRequest) ((PageContext) this.getJspContext()).getRequest();
		
		return (String) request.getSession().getServletContext().getInitParameter("statePackageName");
	}
}
