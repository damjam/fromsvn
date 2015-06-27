package flink.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import flink.util.Paginater;

/**
 * 
 *
 */
public class Paginate extends SimpleTagSupport {
	private int formIndex;
	private boolean showSummary = true;
	private boolean tidy;
	private String navigateStyle;
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public void setNavigateStyle(String navigateStyle) {
		this.navigateStyle = navigateStyle;
	}

	public void setFormIndex(int formIndex) {
		this.formIndex = formIndex;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if (!isExistPaginater()) {
			return;
		}

		StringBuffer html = new StringBuffer();

		appendPageHeader(html);
		appendPageNavigationHtml(html);
		appendPageTail(html);

		this.getJspContext().getOut().write(html.toString());
	}

	/**
	 * check paginater is exist.
	 */
	private boolean isExistPaginater() {
		HttpServletRequest request = (HttpServletRequest) ((PageContext) this
				.getJspContext()).getRequest();

		String key = StringUtils.isEmpty(name) ? Paginater.PAGINATER : name;
		return (Paginater) request.getAttribute(key) != null;
	}

	/**
	 * page html tail.
	 * 
	 * @param html
	 */
	private void appendPageTail(StringBuffer html) {
		html.append("</div></div>");
		html.append("<div class=\"clear:both;\"></div>");
	}

	/**
	 * page html header.
	 * 
	 * @param html
	 */
	private void appendPageHeader(StringBuffer html) {
		html.append("<div class=\"paginaterLayer\"><div class=\"pageinner\">");
	}

	/**
	 * page navigation.
	 * 
	 * @param html
	 */
	private void appendPageNavigationHtml(StringBuffer html) {
		HttpServletRequest request = (HttpServletRequest) ((PageContext) this
				.getJspContext()).getRequest();
		String key = StringUtils.isEmpty(name) ? Paginater.PAGINATER : name;
		Paginater p = (Paginater) request.getAttribute(key);

		if (p == null) {
			return;
		}

		// 是否显示总结信息.
		String showSingleSummary = null;
		showSingleSummary = request.getSession().getServletContext()
				.getInitParameter("showSingleSummary");

		if (StringUtils.isNotEmpty(showSingleSummary)) {
			if (Boolean.parseBoolean(showSingleSummary) && showSummary) {
				appendPageSummaryHtml(html, p);
			}
		} else {
			if (showSummary) {
				appendPageSummaryHtml(html, p);
			}
		}

		if (StringUtils.isEmpty(navigateStyle)) {
			navigateStyle = request.getSession().getServletContext()
					.getInitParameter("htmlNavigator");
		}

		HtmlNavigator navigator = HtmlNavigatorFactory
				.getInstance(navigateStyle);
		html.append(navigator.getHtmlNavigation(request.getContextPath(), p,
				formIndex, tidy));
	}

	/**
	 * navigation summary.
	 * 
	 * @param p
	 * @return
	 */
	private void appendPageSummaryHtml(StringBuffer html, Paginater p) {
		html.append("<div class=\"pageSummary\">");

		if (tidy) {
			html.append("第" + p.getCurrentPage() + "/" + p.getMaxPage()
					+ "页&nbsp;");
			html.append("共" + p.getMaxRowCount() + "笔&nbsp;");
		} else {
			html.append("第" + p.getCurrentPage() + "页&nbsp;");
			html.append("共" + p.getMaxPage() + "页&nbsp;");
			html.append("共" + p.getMaxRowCount() + "笔");
		}
		html.append("</div>");
	}

	public void setShowSummary(boolean showSummary) {
		this.showSummary = showSummary;
	}

	public void setTidy(boolean tidy) {
		this.tidy = tidy;
	}
}
