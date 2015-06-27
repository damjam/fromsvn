package flink.web.tag;

import flink.util.Paginater;

/**
 * image html navigator.
 *
 */
public class ImageHtmlNavigator implements HtmlNavigator {

	@Override
	public String getHtmlNavigation(String contextPath, Paginater p,
			int formIndex, boolean tidy) {
		StringBuffer html = new StringBuffer();
		html.append("<div class=\"pageNavigation\"><div id=\"pageimg\">");

		if (tidy) {
			html.append("<img src=\""
					+ contextPath
					+ "/images/refresh.gif\" onclick=\"javascript: Paginater.toPage("
					+ formIndex + "," + p.getCurrentPage()
					+ ")\" id=\"_refresh\" style=\"display:none\" />");

			if (p.getCurrentPage() != p.getFirstPage()) {
				html.append("<img src=\""
						+ contextPath
						+ "/images/page-first.gif\" onclick=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getFirstPage() + ")\" />");
				html.append("<img src=\""
						+ contextPath
						+ "/images/page-prev.gif\" onclick=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getPriorPage() + ")\" />");
			}

			if (p.getCurrentPage() != p.getLastPage()) {
				html.append("<img src=\""
						+ contextPath
						+ "/images/page-next.gif\" onclick=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getNextPage() + ")\" />");
				html.append("<img src=\""
						+ contextPath
						+ "/images/page-last.gif\" onclick=\"javascript: Paginater.toPage("
						+ p.getLastPage() + ")\" />");
			}
		} else {
			html.append("<img src=\""
					+ contextPath
					+ "/images/refresh.gif\" onclick=\"javascript: Paginater.toPage("
					+ formIndex + "," + p.getCurrentPage()
					+ ")\" id=\"_refresh\" />");
			html.append("&nbsp;&nbsp;");

			if (p.getCurrentPage() == p.getFirstPage()) {
				html.append("<img src=\"" + contextPath
						+ "/images/page-first-disabled.gif\" />");
				html.append("&nbsp;");
				html.append("<img src=\"" + contextPath
						+ "/images/page-prev-disabled.gif\" />");
				html.append("&nbsp;");
			} else {
				html.append("<img src=\""
						+ contextPath
						+ "/images/page-first.gif\" onclick=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getFirstPage() + ")\" />");
				html.append("&nbsp;");
				html.append("<img src=\""
						+ contextPath
						+ "/images/page-prev.gif\" onclick=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getPriorPage() + ")\" />");
				html.append("&nbsp;");
			}

			if (p.getCurrentPage() == p.getLastPage()) {
				html.append("<img src=\"" + contextPath
						+ "/images/page-next-disabled.gif\" />");
				html.append("&nbsp;");
				html.append("<img src=\"" + contextPath
						+ "/images/page-last-disabled.gif\" />");
				html.append("&nbsp;");
			} else {
				html.append("<img src=\""
						+ contextPath
						+ "/images/page-next.gif\" onclick=\"javascript: Paginater.toPage("
						+ p.getNextPage() + ")\" />");
				html.append("&nbsp;");
				html.append("<img src=\""
						+ contextPath
						+ "/images/page-last.gif\" onclick=\"javascript: Paginater.toPage("
						+ p.getLastPage() + ")\" />");
				html.append("&nbsp;");
			}
		}
		html.append("</div>");
		html.append("<div id=\"pagegoto\">绗�input type=\"text\" value=\""
				+ p.getCurrentPage()
				+ "\" id=\"goPageIndex\" size=\"3\" class=\"pageinputtext\" />椤�/div>");
		html.append("<div id=\"pagegotoimg\"><img src=\""
				+ contextPath
				+ "/images/gotoPage.gif\" onclick=\"Paginater.goPage()\" style=\"cursor: pointer;\" />");
		html.append("</div>");
		html.append("</div>");

		return html.toString();
	}

}
