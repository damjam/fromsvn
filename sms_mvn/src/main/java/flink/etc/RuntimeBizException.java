package flink.etc;

import flink.util.NameValue;
import flink.util.State;
import flink.util.Type;

/**
 * “µŒÒ“Ï≥£
 */
public class RuntimeBizException extends RuntimeException {
	private static final long serialVersionUID = 1;
	private String code;
	private Type type;
	private State state;

	protected RuntimeBizException() {
	}

	public RuntimeBizException(String msg) {
		super(msg);
	}

	public RuntimeBizException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public RuntimeBizException(Throwable cause) {
		super(cause);
	}

	public RuntimeBizException(Type type) {
		super(type.getName());
		this.type = type;
	}

	public RuntimeBizException(Type type, String msg) {
		super(msg);
		this.type = type;
	}

	public String getCode() {
		return this.code;
	}

	public NameValue getState() {
		return state;
	}

	public Type getType() {
		return type;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
