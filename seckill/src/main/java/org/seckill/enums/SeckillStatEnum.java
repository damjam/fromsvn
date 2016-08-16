package org.seckill.enums;

public enum SeckillStatEnum {

	SUCCESS(1, "秒杀成功"),
	END(0,"秒杀结束"),
	REPEAT(-1,"重复秒杀"),
	DATA_REWIRTE(-3,"数据篡改");
	
	private int state;
	
	private String stateInfo;

	private SeckillStatEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public static SeckillStatEnum stateOf(int index) {
		for(SeckillStatEnum state : values()){
			return state;
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	
}
