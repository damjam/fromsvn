package flink.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * web 资源.
 * 
 * 
 */
public abstract class WebResource {

	/**
	 * 第一个参数名称.
	 *
	 * @return
	 */
	public static final String PARAM_NAME = "action";

	/**
	 * 由请求参数获取link.
	 * 
	 * @param request
	 * @return
	 */
	public static String getLink(HttpServletRequest request) {
		String url = request.getRequestURI().substring(request.getContextPath().length());
		String param = request.getParameter(WebResource.PARAM_NAME);

		if (StringUtils.isNotEmpty(param)) {
			url = getLink(url, param);
		}

		return url;
	}

	/**
	 * 将url和param拼接.
	 *
	 * @return
	 */
	public static String getLink(String url, String param) {
		if (StringUtils.isEmpty(url)) {
			return null;
		}

		StringBuffer key = new StringBuffer();
		key.append(url);

		if (StringUtils.isNotEmpty(param)) {
			key.append("?");

			key.append(PARAM_NAME);
			key.append("=");
			key.append(param);
		}

		return key.toString();
	}

	/**
	 * 将资源列表分发到map中.
	 *
	 * @param resources
	 * @return (key: url和param拼接的链接, value:WebResource)
	 */
	public static Map<String, WebResource> map(List<WebResource> resources) {
		Map<String,WebResource> map = new HashMap<String,WebResource>();

		for (WebResource res : resources) {
			map.put(res.getLink(), res);
		}

		return map;
	}

	/**
	 * 将url和param拼接.
	 *
	 * @return
	 */
	public String getLink() {
		return getLink(getUrl(), getParamValue());
	}

	/**
	 * 获取参数值.
	 *
	 * @return
	 */
	public abstract String getParamValue();

	/**
	 * 获取url地址.
	 *
	 * @return
	 */
	public abstract String getUrl();

}
