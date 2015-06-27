package flink.etc;

import flink.util.NameValue;
import flink.util.State;
import flink.util.Type;

/**
 * “µŒÒ“Ï≥£
 */
public class BizException extends Exception {
	private static final long serialVersionUID = 1;
	private String code;
	private Type type;
	private State state;

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String msg) {
		super(msg);
	}

	public BizException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public BizException(Type type, String msg) {
		super(msg);
		this.type = type;
	}

	public NameValue getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getCode() {
		return this.code;
	}

	protected BizException() {
	}

}
