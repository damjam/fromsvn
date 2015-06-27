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
public class ButtonHtmlNavigator implements HtmlNavigator {

	@Override
	public String getHtmlNavigation(String contextPath, Paginater p,
			int formIndex, boolean tidy) {
		String refreshEvent = "Paginater.toPage(" + formIndex + ","
				+ p.getCurrentPage() + ")";
		String refreshId = " id='_refresh_" + formIndex + "' ";

		StringBuffer html = new StringBuffer();
		html.append("<div class='pageNavigation'>");
		html.append("  <span class='pageSummary'>共" + p.getMaxRowCount()
				+ "条记录，共" + p.getMaxPage() + "页</span>");
		html.append("  <span>");
		html.append("    <input type='text' name='pageSize' id='pageSize_"
				+ formIndex + "' class='pageinputtext' value='"
				+ p.getPageSize() + "' onkeypress='Paginater.keyToPage("
				+ formIndex + ")'/>条记录／页");
		html.append("    当前第<input type='text' name='pageNumber' id='goPageIndex_"
				+ formIndex
				+ "' class='pageinputtext' value='"
				+ p.getCurrentPage()
				+ "' onkeypress='Paginater.keyToPage("
				+ formIndex + ")' />页");
		html.append("  </span>");

		html.append("<a href='javascript: " + refreshEvent + "'" + refreshId
				+ " style='display:none'>[刷新]</a>");

		if (p.getCurrentPage() == p.getFirstPage()) {
			html.append("首页&nbsp;上一页");
		} else {
			html.append("<input type='button' class='pagenavbtn' onclick='Paginater.toPage("
					+ formIndex + "," + p.getFirstPage() + ")' value='首页'/>");
			html.append("<input type='button' class='pagenavbtn' onclick='Paginater.toPage("
					+ formIndex + "," + p.getPriorPage() + ")' value='上一页'/>");
		}

		if (p.getCurrentPage() == p.getLastPage() || p.getLastPage() <= 1) {
			html.append("下一页&nbsp;末页");
		} else {
			html.append("<input type='button' class='pagenavbtn' onclick='Paginater.toPage("
					+ formIndex + "," + p.getNextPage() + ")' value='下一页'/>");
			html.append("<input type='button' class='pagenavbtn' onclick='Paginater.toPage("
					+ formIndex + "," + p.getLastPage() + ")' value='尾页'/>");
		}

		html.append("</div>");

		return html.toString();
	}

}
