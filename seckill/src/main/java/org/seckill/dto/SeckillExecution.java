package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;

public class SeckillExecution {

	
	private Integer seckillId;
	
	private SeckillStatEnum seckillStatEnum;

	private SuccessKilled successKilled;
	
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
	
}
