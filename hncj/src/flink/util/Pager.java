package flink.util;

public class Pager {
	private long pageNumber;

	private int pageSize;

	public Pager(long pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
