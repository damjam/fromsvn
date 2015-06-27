package flink.web.tag;

import flink.util.Paginater;

/**
 * classical html navigator.
 * 
 * <pre>
 * [首页][上一页][下一页][末页]
 * </pre>
 * 
 */
public class ClassicalHtmlNavigator implements HtmlNavigator {

	@Override
	public String getHtmlNavigation(String contextPath, Paginater p,
			int formIndex, boolean tidy) {
		StringBuffer html = new StringBuffer();
		html.append("<div class=\"pageNavigation\">");
		String refreshEvent = "Paginater.toPage(" + formIndex + ","
				+ p.getCurrentPage() + ")";
		String refreshId = " id=\"_refresh_" + formIndex + "\" ";

		if (tidy) {
			html.append("<a href=\"javascript: " + refreshEvent + "\""
					+ refreshId + " style=\"display:none\">[刷新]</a>");

			if (p.getCurrentPage() != p.getFirstPage()) {
				html.append("<a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getFirstPage() + ")\">[首页]</a>");
				html.append("<a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getPriorPage() + ")\">[上一页]</a>");
			}

			if (p.getCurrentPage() != p.getLastPage()) {
				html.append("<a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getNextPage() + ")\">[下一页]</a>");
				html.append("<a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getLastPage() + ")\">[末页]</a>");
			}
		} else {
			html.append("<a href=\"javascript: " + refreshEvent + "\""
					+ refreshId + ">[刷新]</a>");

			if (p.getCurrentPage() == p.getFirstPage()) {
				html.append("[首页][上一页]");
			} else {
				html.append("<a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getFirstPage() + ")\">[首页]</a>");
				html.append("<a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getPriorPage() + ")\">[上一页]</a>");
			}

			if (p.getCurrentPage() == p.getLastPage()) {
				html.append("[下一页][末页]");
			} else {
				html.append("<a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getNextPage() + ")\">[下一页]</a>");
				html.append("<a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getLastPage() + ")\">[末页]</a>");
			}
		}

		html.append("&nbsp;&nbsp;转至第<input type=\"text\" id=\"goPageIndex_"
				+ formIndex + "\" size=\"3\" class=\"pageinputtext\" />页");
		html.append("<img src=\"" + contextPath
				+ "/images/jumpto.bmp\" onclick=\"Paginater.goPage("
				+ formIndex + ")\" style=\"cursor: hand\" />");

		html.append("</div>");

		return html.toString();
	}

}
