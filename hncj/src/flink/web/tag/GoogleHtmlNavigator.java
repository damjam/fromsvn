package flink.web.tag;

import java.util.Iterator;
import java.util.List;

import flink.util.Paginater;

/**
 * google html navigator.
 * 
 * 
 */
public class GoogleHtmlNavigator implements HtmlNavigator {
	private static final int BATCH_SIZE = 10;

	private StringBuffer getBatchPages(Paginater p, int formIndex) {
		List batchPages = p.getBatchPages(BATCH_SIZE);
		StringBuffer pages = new StringBuffer();

		for (Iterator i = batchPages.iterator(); i.hasNext();) {
			String page = (String) i.next();

			if (p.getCurrentPage() == Integer.parseInt(page)) {
				pages.append(" " + page + " ");
			} else {
				pages.append(" <a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + page + ")\">" + page + "</a> ");
			}
		}

		return pages;
	}

	@Override
	public String getHtmlNavigation(String contextPath, Paginater p,
			int formIndex, boolean tidy) {
		StringBuffer html = new StringBuffer();
		html.append("<div class=\"pageNavigation\">");

		if (tidy) {
			html.append("<a href=\"javascript: Paginater.toPage(" + formIndex
					+ "," + p.getCurrentPage()
					+ ")\" id=\"_refresh\" style=\"display:none\">[刷新]</a>");
			html.append(getBatchPages(p, formIndex));
		} else {
			html.append("<a href=\"javascript: Paginater.toPage(" + formIndex
					+ "," + p.getCurrentPage()
					+ ")\" id=\"_refresh\">[刷新]</a>");

			if (p.getCurrentPage() != p.getFirstPage()) {
				html.append("<a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getPriorPage() + ")\">[上一页]</a>");
			}

			html.append(getBatchPages(p, formIndex));

			if (p.getCurrentPage() != p.getLastPage()) {
				html.append("<a href=\"javascript: Paginater.toPage("
						+ formIndex + "," + p.getNextPage() + ")\">[下一页]</a>");
			}
		}

		html.append("</div>");

		return html.toString();
	}

}
