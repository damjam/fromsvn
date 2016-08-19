package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;

public class SeckillExecution {

	
	private Integer seckillId;
	
	private SeckillStatEnum seckillStatEnum;

	private SuccessKilled successKilled;
	
	private String state;
	
	private String stateInfo;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Integer seckillId) {
		this.seckillId = seckillId;
	}

	public SeckillStatEnum getSeckillStatEnum() {
		return seckillStatEnum;
	}

	public void setSeckillStatEnum(SeckillStatEnum seckillStatEnum) {
		this.seckillStatEnum = seckillStatEnum;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	public SeckillExecution(Integer seckillId, SeckillStatEnum seckillStatEnum) {
		super();
		this.seckillId = seckillId;
		this.seckillStatEnum = seckillStatEnum;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SeckillExecution(Integer seckillId, String state, String stateInfo) {
		super();
		this.seckillId = seckillId;
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	
	
}
