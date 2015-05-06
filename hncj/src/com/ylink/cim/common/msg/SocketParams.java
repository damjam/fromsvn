package com.ylink.cim.common.msg;

public class SocketParams {

	private String hostName;
	private int portNum;
	private int timeout; // ³¬Ê±Ê±¼ä;
	private String charset;

	public SocketParams() {

	}

	public SocketParams(String hostName, int portNum, int timeout, String charset) {
		this.hostName = hostName;
		this.portNum = portNum;
		this.timeout = timeout;
		this.charset = charset;
	}

	public String getCharset() {
		return charset;
	}

	public String getHostName() {
		return hostName;
	}

	public int getPortNum() {
		return portNum;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setPortNum(int portNum) {
		this.portNum = portNum;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
