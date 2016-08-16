/**
 * 
 */
package flink.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 */
public class Paginater implements Serializable {
	private static final long serialVersionUID = -3993240616257317798L;
	public static final String PAGINATER = "_page";
	public static final String PAGE_NUMBER = "pageNumber";
	public static final int PAGE_SIZE = 20;
	public static final Paginater EMPTY = getEmptyPaginater();
	private long currentPage;

	private long maxPage;

	private long maxRowCount;

	private int pageSize = PAGE_SIZE;

	private int index;

	private Collection data;

	public Paginater(long maxRowCount, long currentPage) {
		this.maxRowCount = maxRowCount;
		this.countMaxPage();
		this.setCurrentPage(currentPage);
	}

	private static Paginater getEmptyPaginater() {
		Paginater p = new Paginater(0, 0);
		p.setData(Collections.EMPTY_LIST);

		return p;
	}

	public Paginater(long maxRowCount, String currentPage) {
		this.maxRowCount = maxRowCount;
		this.countMaxPage();
		this.setCurrentPage(currentPage);
	}

	@SuppressWarnings("unchecked")
	public Paginater(long maxRowCount, String currentPage, Object dataObj) {
		this.maxRowCount = maxRowCount;
		this.countMaxPage();
		this.setCurrentPage(currentPage);

		this.data = new ArrayList();
		this.data.add(dataObj);
	}

	public Paginater(long maxRowCount, long currentPage, int pageSize) {
		this.maxRowCount = maxRowCount;
		this.pageSize = pageSize;
		this.countMaxPage();
		this.setCurrentPage(currentPage);
	}

	@SuppressWarnings("unchecked")
	public Paginater(int pageSize, Object dataObj) {
		this.maxRowCount = 1;
		this.pageSize = pageSize;
		this.countMaxPage();
		this.setCurrentPage(1);

		this.data = new ArrayList();
		this.data.add(dataObj);
	}

	public Paginater(long maxRowCount, String currentPage, int pageSize) {
		this.maxRowCount = maxRowCount;
		this.pageSize = pageSize;
		this.countMaxPage();
		this.setCurrentPage(currentPage);
	}

	public long getIndex() {
		return ++index + this.getOffsetIndex();
	}

	public void setCurrentPage(long currentPage) {
		if (currentPage < 1) {
			this.currentPage = 1;
		} else if (currentPage > this.maxPage) {
			this.currentPage = this.maxPage;
		} else {
			this.currentPage = currentPage;
		}

		if (this.currentPage < 1) {
			this.currentPage = 1;
		}
	}

	public void setCurrentPage(String currentPage) {
		try {
			setCurrentPage(Integer.parseInt(currentPage));
		} catch (NumberFormatException ex) {
			setCurrentPage(1);
		}
	}

	public long getCurrentPage() {
		if (this.currentPage < 1) {
			this.currentPage = 1;
		}

		return this.currentPage;
	}

	public long getMaxPage() {
		return this.maxPage;
	}

	public long getMaxRowCount() {
		return this.maxRowCount;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public int getFirstPage() {
		return 1;
	}

	public long getPriorPage() {
		if (this.currentPage == 1) {
			return this.currentPage;
		}

		return (this.currentPage - 1);
	}

	public long getLastPage() {
		return this.maxPage;
	}

	public long getNextPage() {
		if (this.currentPage == this.maxPage) {
			return this.currentPage;
		}

		return (this.currentPage + 1);
	}

	public long getOffsetIndex() {
		return (this.getCurrentPage() - 1) * this.getPageSize();
	}

	private void countMaxPage() {
		if (this.maxRowCount % this.pageSize == 0) {
			this.maxPage = this.maxRowCount / this.pageSize;
		} else {
			this.maxPage = this.maxRowCount / this.pageSize + 1;
		}
	}

	public List getBatchPages(int batchSize) {
		return new BatchPage(this, batchSize).getBatchPages();
	}

	@SuppressWarnings("unchecked")
	public List getCurrentPageData(List list) {
		if (list.size() < 1) {
			return null;
		}

		List result = new ArrayList();
		long curRow = (currentPage - 1) * pageSize + 1;

		for (long i = curRow; i < curRow + pageSize; i++) {
			if (i > maxRowCount) {
				break;
			}

			result.add(list.get((int) i - 1));
		}

		return result;
	}

	public Collection getData() {
		return data;
	}

	public void setData(Collection data) {
		this.data = data;
	}

	public List getList() {
		return (List) data;
	}

	public Set getSet() {
		return (Set) data;
	}
}

class BatchPage {
	private Paginater p;
	private int batchSize;

	BatchPage(Paginater p, int batchSize) {
		this.p = p;
		this.batchSize = batchSize;
	}

	public List getBatchPages() {
		List list = new ArrayList();

		appendPriorPages(list);
		appendCurrentPage(list);
		appendNextPages(list);

		return list;
	}

	/**
	 * append next pages.
	 * 
	 * @param list
	 */
	private void appendNextPages(List list) {
		if (p.getCurrentPage() == p.getMaxPage()) {
			return;
		}

		int nextPageNum = this.batchSize - list.size();
		long remainPageNum = p.getMaxPage() - p.getCurrentPage();
		long appendPageNum = nextPageNum < remainPageNum ? nextPageNum
				: remainPageNum;

		for (long i = 0; i < appendPageNum; i++) {
			addOnePage(list, p.getCurrentPage() + (i + 1));
		}
	}

	/**
	 * append current page.
	 * 
	 * @param list
	 */
	private void appendCurrentPage(List list) {
		addOnePage(list, p.getCurrentPage());
	}

	/**
	 * append prior pages.
	 * 
	 * @param list
	 */
	private void appendPriorPages(List list) {
		if (p.getCurrentPage() == 1) {
			return;
		}

		long appendPageNum = 0;
		long currentPage = p.getCurrentPage();
		long remainPageNum = p.getMaxPage() - p.getCurrentPage();

		if (remainPageNum < this.batchSize / 2) {
			appendPageNum = this.batchSize - remainPageNum - 1;
			appendPageNum = appendPageNum < currentPage ? appendPageNum
					: (currentPage - 1);
		} else {
			long priorPageNum = this.batchSize / 2;
			appendPageNum = priorPageNum < currentPage ? priorPageNum
					: (currentPage - 1);
		}

		for (long i = 0; i < appendPageNum; i++) {
			addOnePage(list, currentPage - appendPageNum + i);
		}
	}

	/**
	 * add one page.
	 * 
	 * @param list
	 * @param page
	 */
	@SuppressWarnings("unchecked")
	private void addOnePage(List list, long page) {
		list.add(String.valueOf(page));
	}
}
