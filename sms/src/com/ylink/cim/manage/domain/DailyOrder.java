package com.ylink.cim.manage.domain;

public class DailyOrder extends BranchField {

	private String id;
	
	private String orderDate;
	
	private Integer paidCnt = 0;
	
	private Double paidAmt = 0d;
	
	private Integer unpaidCnt = 0;
	
	private Double unpaidAmt = 0d;
	
	private Integer footpadCnt = 0;
	
	private Double footpadAmt = 0d;
	
	private Integer seatpadCnt = 0;
	
	private Double seatpadAmt = 0d;
	
	private Integer wrapTrunkCnt = 0;
	
	private Double wrapTrunkAmt = 0d;
	
	private Integer flatTrunkCnt = 0;
	
	private Double flatTrunkAmt = 0d;
	
	private Integer silkFootpadCnt = 0;
	
	private Double silkFootpadAmt = 0d;

	private Integer totalCnt = 0;
	
	private Double totalAmt = 0d;
	
	private String beginDate;
	
	private String endDate;
	
	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(Integer totalCnt) {
		this.totalCnt = totalCnt;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getPaidCnt() {
		return paidCnt;
	}

	public void setPaidCnt(Integer paidCnt) {
		this.paidCnt = paidCnt;
	}

	public Double getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public Integer getUnpaidCnt() {
		return unpaidCnt;
	}

	public void setUnpaidCnt(Integer unpaidCnt) {
		this.unpaidCnt = unpaidCnt;
	}

	public Double getUnpaidAmt() {
		return unpaidAmt;
	}

	public void setUnpaidAmt(Double unpaidAmt) {
		this.unpaidAmt = unpaidAmt;
	}

	public Integer getFootpadCnt() {
		return footpadCnt;
	}

	public void setFootpadCnt(Integer footpadCnt) {
		this.footpadCnt = footpadCnt;
	}

	public Double getFootpadAmt() {
		if (footpadAmt == null) {
			footpadAmt = 0d;
		}
		return footpadAmt;
	}

	public void setFootpadAmt(Double footpadAmt) {
		this.footpadAmt = footpadAmt;
	}

	public Integer getSeatpadCnt() {
		return seatpadCnt;
	}

	public void setSeatpadCnt(Integer seatpadCnt) {
		this.seatpadCnt = seatpadCnt;
	}

	public Double getSeatpadAmt() {
		return seatpadAmt;
	}

	public void setSeatpadAmt(Double seatpadAmt) {
		this.seatpadAmt = seatpadAmt;
	}

	public Integer getWrapTrunkCnt() {
		return wrapTrunkCnt;
	}

	public void setWrapTrunkCnt(Integer wrapTrunkCnt) {
		this.wrapTrunkCnt = wrapTrunkCnt;
	}

	public Double getWrapTrunkAmt() {
		return wrapTrunkAmt;
	}

	public void setWrapTrunkAmt(Double wrapTrunkAmt) {
		this.wrapTrunkAmt = wrapTrunkAmt;
	}

	public Integer getFlatTrunkCnt() {
		return flatTrunkCnt;
	}

	public void setFlatTrunkCnt(Integer flatTrunkCnt) {
		this.flatTrunkCnt = flatTrunkCnt;
	}

	public Double getFlatTrunkAmt() {
		return flatTrunkAmt;
	}

	public void setFlatTrunkAmt(Double flatTrunkAmt) {
		this.flatTrunkAmt = flatTrunkAmt;
	}

	public Integer getSilkFootpadCnt() {
		return silkFootpadCnt;
	}

	public void setSilkFootpadCnt(Integer silkFootpadCnt) {
		this.silkFootpadCnt = silkFootpadCnt;
	}

	public Double getSilkFootpadAmt() {
		return silkFootpadAmt;
	}

	public void setSilkFootpadAmt(Double silkFootpadAmt) {
		this.silkFootpadAmt = silkFootpadAmt;
	}
	
	
	
}
