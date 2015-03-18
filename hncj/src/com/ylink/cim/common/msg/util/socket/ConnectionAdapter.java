package com.ylink.cim.common.msg.util.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionAdapter extends Socket {
	/**
	 * 连接状态
	 */
	private boolean status = true;
	/**
	 * 连接池以外的连接
	 */
	private boolean extra = false;
	/**
	 * 默认的构造函数
	 */
	public ConnectionAdapter() {
		super();
	}

	public ConnectionAdapter(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
	}
	public ConnectionAdapter(String host, int port, boolean extra) throws UnknownHostException, IOException {
		super(host, port);
		extra = true;
	}
	public void destroy() {
		release();
		try {
			super.close();
		} catch (Exception e) {
			
		}
	}
	public boolean isExtra() {
		return extra;
	}
	/**
	 * 判断此连接是否空闲
	 * 
	 * @return boolean 空闲返回ture,否则false
	 */
	public boolean isFree() {
		return status;
	}

	/**
	 * 当客户端关闭连接的时候状态设置为true(空闲）
	 */
	public void release() {
		status = true;
		//额外生成的，将其关闭
		if (extra) {
			try {
				super.close();
			} catch (Exception e) {
				
			}
		}
	}

	/**
	 * 当使用此连接的时候设置状态为false（忙碌）
	 */
	public void setBusy() {
		this.status = false;
	}
	

}