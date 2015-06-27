package flink.etc;

import flink.util.Type;

/**
 * “µŒÒ“Ï≥£
 */
public class NoRollbackBizException extends BizException {
	private static final long serialVersionUID = 1;

	public NoRollbackBizException(Throwable cause) {
		super(cause);
	}

	public NoRollbackBizException(String msg) {
		super(msg);
	}

	public NoRollbackBizException(String code, String msg) {
		super(code, msg);
	}

	public NoRollbackBizException(Type type, String msg) {
		super(type, msg);
	}

	protected NoRollbackBizException() {
	}

}
