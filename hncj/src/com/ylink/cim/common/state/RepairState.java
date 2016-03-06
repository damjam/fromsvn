package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class RepairState extends AbstractState {
	public static Map<String, RepairState> ALL = new LinkedHashMap<String, RepairState>();

	public static final RepairState UNDER_DEAL = new RepairState("������", "00");
	public static final RepairState DEALING = new RepairState("������", "01");
	public static final RepairState DEALED = new RepairState("�Ѵ���", "02");//�����˵�������ǰ�����˿��
	public static final RepairState TRANSFERED = new RepairState("��ת��", "03");//
	public static final RepairState CANCELED = new RepairState("�ѳ���", "04");//
	
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("repairStates", RepairState.ALL.values());
	}

	public static RepairState valueOf(String value) throws Exception {

		RepairState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(RepairState.class, "״̬����");
		}
		return state;
	}

	protected RepairState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
