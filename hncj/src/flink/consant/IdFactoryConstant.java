package flink.consant;

import java.util.HashMap;
import java.util.Map;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class IdFactoryConstant extends AbstractType {

	public static final Map<String, IdFactoryConstant> hsMap = new HashMap<String, IdFactoryConstant>();

	public static final IdFactoryConstant PRIVILEG_ERESOURCE_ID = new IdFactoryConstant("权限资源表主键",
			"privilege_resource_id");
	public static final IdFactoryConstant USER_INFO_ID = new IdFactoryConstant("用户基本信息表主键", "user_info_id");

	protected IdFactoryConstant(String name, String value) {
		super(name, value);
		hsMap.put(value, this);
	}

	public static IdFactoryConstant valueOf(String value) throws Exception {

		IdFactoryConstant idFactoryConstant = hsMap.get(value);
		if (null == idFactoryConstant) {
			ExceptionUtils.logException(IdFactoryConstant.class, "找不到主键");
		}

		return idFactoryConstant;
	}
}
