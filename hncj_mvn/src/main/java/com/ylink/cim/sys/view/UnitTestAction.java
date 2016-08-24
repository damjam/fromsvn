package com.ylink.cim.sys.view;

import flink.web.BaseAction;

public class UnitTestAction extends BaseAction {

	public String testService() throws Exception {
		try {
			// BatchTradeService batchTradeService =
			// (BatchTradeService)SpringContext.getService("batchTradeService");
			// batchTradeService.autoAccept(153583L);
			// IManualMatchService manualTradeService =
			// (IManualMatchService)getService("manualMatchService");
			// Map map = new HashMap();
			// manualTradeService.findAccountWithdrawBillAll(map, new Pager(1,
			// 10));
			// TradeService tradeService =
			// (TradeService)SpringContext.getService("batchTradeService");
			// ChannelTradeBatchDao chnlBatchDao =
			// (ChannelTradeBatchDao)SpringContext.getService("channelTradeBatchDao");
			// ChannelTradeBatch batch =
			// chnlBatchDao.findById(ChannelTradeBatch.class, 5458238L);
			//
			// //tradeService.getExpectSendTime(DBTime.getDBTime(), batch);
			// System.out.println(ParaManager.getChnlConfig("030101").getChnlName());
			// setResult(true, "执行成功", request);
			// ChannelConfigDao channelConfigDao =
			// (ChannelConfigDao)SpringContext.getService("channelConfigDao");
			// List list = channelConfigDao.findAll();
			// System.out.println(list);
			// BatchTradeService batchTradeService =
			// (BatchTradeService)SpringContext.getService("batchTradeService");
			// batchTradeService.autoAccept(1111156144L);
		} catch (Exception e) {
			setResult(false, "出现异常", request);
			e.printStackTrace();
		}
		// "/pages/common/unitTest.jsp";
		return "test";
	}

}
