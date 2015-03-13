package com.ylink.cim.common.msg.util.socket;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.ylink.cim.common.msg.SocketParams;

import flink.util.SpringContext;

public class ConnectionProvider {
	//private static Object object_lock = new Object();
	private static Logger logger = Logger.getLogger(ConnectionProvider.class);
	private String ip;
	private int port;
	public static final String SERVER_IP = "SERVER_HOST_IP";
	public static final String SERVER_PORT = "SERVER_HOST_PORT";
	public static final String TIME_OUT_TIME = "SOCKET_TIMEOUT_TIME";
	public static final String MAX_SIZE = "30";
	public static final String MIN_SIZE = "10";
	/**
	 * 默认的最大连接数
	 */
	private int max_size = 20;
	/**
	 * 默认的最小连接数
	 */
	private int min_size = 10;
	/**
	 * Socket connection池数组
	 */
	private ConnectionAdapter[] socketpool = null;

	/**
	 * 构造对象的时候初始化连接池
	 * 
	 * @throws UnknownHostException
	 *             未知的主机异常
	 * @throws IOException
	 */

	private ConnectionProvider() {
		//ReadProperties readProperties = new ReadProperties("d:\\project\\cim\\conf\\gess.properties");
		//ip = readProperties.getValByKey(SERVER_IP);
		//port = readProperties.getValByKey(SERVER_PORT);
		//SocketParams socketParams = (SocketParams)SpringContext.getService("socketParams");
		SocketParams socketParams = new SocketParams("168.33.120.25", 13000, 5, "GBK");
		ip = socketParams.getHostName();
		port = socketParams.getPortNum();
		try {
			init(); // 构造对象的时候初始化连接池
		} catch (Exception e) {
			logger.debug("初始化socket连接池失败");
			e.printStackTrace();
		}

	}

	/**
	 * 判断是否已经池化
	 * 
	 * @return boolean 如果池化返回ture,反之返回false
	 */
	public boolean isPooled() {
		if (socketpool != null) {
			return true;
		} else
			return false;
	}

	/**
	 *返回一个连接
	 * 
	 * @return a Connection object.
	 */
	public ConnectionAdapter getConnection() {
		ConnectionAdapter s = null;
		for (int i = 0; i < socketpool.length; i++) {
			if (socketpool[i] != null) {
				// 如果有空闲的连接，返回一个空闲连接，如果没有，继续循环
				if (socketpool[i].isFree()) {
					s = socketpool[i];
					socketpool[i].setBusy();
					return s;
				} else
					continue;
			} else { // 如果连接为空，证明超过最小连接数，重新生成连接
				try {
					s = socketpool[i] = new ConnectionAdapter(ip, port);
				} catch (Exception e) {
					// never throw
				}
			}
		}
		// 如果连接仍旧为空的话，则超过了最大连接数
		if (s == null) {
			try { // 生成普通连接，由客户端自行关闭，释放资源，不再由连接池管理
				s = new ConnectionAdapter(ip, port, true);
			} catch (Exception e) { // 此异常永远不会抛出
				
			}
		}
		return s;
	}

	/**
	 * 初始化连接池
	 * 
	 * @throws UnknownHostException
	 *             主机ip找不到
	 * @throws IOException
	 *             此端口号上无server监听
	 */
	public void init() throws UnknownHostException, IOException {
		socketpool = new ConnectionAdapter[max_size];
		for (int i = 0; i < min_size; i++) {
			socketpool[i] = new ConnectionAdapter(ip, port);
		}
	}

	/**
	 * 重新启动连接池
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void restart() throws UnknownHostException, IOException {
		destroy();
		init();
	}

	/**
	 * 注销此连接池
	 */
	public void destroy() {
		for (int i = 0; i < socketpool.length; i++) {
			if (socketpool[i] != null) {
				ConnectionAdapter adapter = (ConnectionAdapter) socketpool[i];
				adapter.destroy();
			}
		}
	}

	/**
	 * 静态方法，生成此连接池实现的对象
	 * 
	 * @param pro
	 *            Properties 此连接池所需要的所有参数的封装
	 * @throws UnknownHostException
	 *             主机无法找到
	 * @throws IOException
	 *             与服务器无法建立连接
	 * @return ConnectionProvider 返回父类ConnectionProvider
	 */
	private static class ResourceHolder {
		public static ConnectionProvider resource = new ConnectionProvider();
	}

	public static ConnectionProvider getInstance() {
		return ResourceHolder.resource;
	}

}
