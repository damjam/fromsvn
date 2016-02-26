package flink;

import com.ylink.cim.admin.service.IdFactoryService;

import flink.consant.IdFactoryConstant;
import flink.util.SpringContext;

public class IdFactoryHelper {

	private static final IdFactoryHelper idFactoryHelper = new IdFactoryHelper();

	public IdFactoryHelper getInstance() {
		return idFactoryHelper;
	}

	public static synchronized String getId(String seqIdName) {
		try {
			IdFactoryService idFactoryService = (IdFactoryService) SpringContext.getService("idFactoryService");
			return idFactoryService.generateId(seqIdName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
