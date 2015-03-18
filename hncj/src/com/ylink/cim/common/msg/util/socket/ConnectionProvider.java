package com.ylink.cim.common.msg.util.socket;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.ylink.cim.common.msg.SocketParams;

public class ConnectionProvider {
	// private static Object object_lock = new Object();
	private static Logger logger = Logger.getLogger(ConnectionProvider.class);
	private String ip;
	private int port;
	public static final String SERVER_IP = "SERVER_HOST_IP";
	public static final String SERVER_PORT = "SERVER_HOST_PORT";
	public static final String TIME_OUT_TIME = "SOCKET_TIMEOUT_TIME";
	public static final String MAX_SIZE = "30";
	public static final String MIN_SIZE = "10";
	/**
	 * Ĭ�ϵ����������
	 */
	private int max_size = 20;
	/**
	 * Ĭ�ϵ���С������
	 */
	private int min_size = 10;
	/**
	 * Socket connection������
	 */
	private ConnectionAdapter[] socketpool = null;

	/**
	 * ��������ʱ���ʼ�����ӳ�
	 * 
	 * @throws UnknownHostException
	 *             δ֪�������쳣
	 * @throws IOException
	 */

	private ConnectionProvider() {
		// ReadProperties readProperties = new
		// ReadProperties("d:\\project\\cim\\conf\\gess.properties");
		// ip = readProperties.getValByKey(SERVER_IP);
		// port = readProperties.getValByKey(SERVER_PORT);
		// SocketParams socketParams =
		// (SocketParams)SpringContext.getService("socketParams");
		SocketParams socketParams = new SocketParams("168.33.120.25", 13000, 5, "GBK");
		ip = socketParams.getHostName();
		port = socketParams.getPortNum();
		try {
			init(); // ��������ʱ���ʼ�����ӳ�
		} catch (Exception e) {
			logger.debug("��ʼ��socket���ӳ�ʧ��");
			e.printStackTrace();
		}

	}

	/**
	 * �ж��Ƿ��Ѿ��ػ�
	 * 
	 * @return boolean ����ػ�����ture,��֮����false
	 */
	public boolean isPooled() {
		if (socketpool != null) {
			return true;
		} else
			return false;
	}

	/**
	 * ����һ������
	 * 
	 * @return a Connection object.
	 */
	public ConnectionAdapter getConnection() {
		ConnectionAdapter s = null;
		for (int i = 0; i < socketpool.length; i++) {
			if (socketpool[i] != null) {
				// ����п��е����ӣ�����һ���������ӣ����û�У�����ѭ��
				if (socketpool[i].isFree()) {
					s = socketpool[i];
					socketpool[i].setBusy();
					return s;
				} else
					continue;
			} else { // �������Ϊ�գ�֤��������С��������������������
				try {
					s = socketpool[i] = new ConnectionAdapter(ip, port);
				} catch (Exception e) {
					// never throw
				}
			}
		}
		// ��������Ծ�Ϊ�յĻ����򳬹������������
		if (s == null) {
			try { // ������ͨ���ӣ��ɿͻ������йرգ��ͷ���Դ�����������ӳع���
				s = new ConnectionAdapter(ip, port, true);
			} catch (Exception e) { // ���쳣��Զ�����׳�

			}
		}
		return s;
	}

	/**
	 * ��ʼ�����ӳ�
	 * 
	 * @throws UnknownHostException
	 *             ����ip�Ҳ���
	 * @throws IOException
	 *             �˶˿ں�����server����
	 */
	public void init() throws UnknownHostException, IOException {
		socketpool = new ConnectionAdapter[max_size];
		for (int i = 0; i < min_size; i++) {
			socketpool[i] = new ConnectionAdapter(ip, port);
		}
	}

	/**
	 * �����������ӳ�
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void restart() throws UnknownHostException, IOException {
		destroy();
		init();
	}

	/**
	 * ע�������ӳ�
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
	 * ��̬���������ɴ����ӳ�ʵ�ֵĶ���
	 * 
	 * @param pro
	 *            Properties �����ӳ�����Ҫ�����в����ķ�װ
	 * @throws UnknownHostException
	 *             �����޷��ҵ�
	 * @throws IOException
	 *             ��������޷���������
	 * @return ConnectionProvider ���ظ���ConnectionProvider
	 */
	private static class ResourceHolder {
		public static ConnectionProvider resource = new ConnectionProvider();
	}

	public static ConnectionProvider getInstance() {
		return ResourceHolder.resource;
	}

}
