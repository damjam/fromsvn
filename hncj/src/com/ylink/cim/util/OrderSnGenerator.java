package com.ylink.cim.util;

import com.ylink.cim.manage.domain.HouseInfo;

public class OrderSnGenerator {

	public static String getOrderSn(HouseInfo houseInfo) {
		String buildNo = houseInfo.getBuildingNo();
		String unitNo = houseInfo.getUnitNo();
		String position = houseInfo.getPosition();
		if (buildNo.length() == 1) {
			buildNo = "0" + buildNo;
		}
		if (position.length() == 3) {
			position = "0" + position;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(buildNo);
		sb.append(unitNo);
		sb.append(position);
		return sb.toString();
	}
}
