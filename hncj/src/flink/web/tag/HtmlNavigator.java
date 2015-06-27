package flink.web.tag;

import flink.util.Paginater;

public interface HtmlNavigator {
	String getHtmlNavigation(String contextPath, Paginater p, int formIndex,
			boolean tidy);
}
