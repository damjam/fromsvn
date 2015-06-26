package com.ylink.cim.appserver.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.ylink.cim.common.util.ParaManager;

import flink.util.SpringContext;

/**
 * ��̨����servlet.
 * 
 * 
 */
public class AppServerServlet extends HttpServlet {
	Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	
	public void destroy() {
		
	}

	
	public void init() throws ServletException {
		//����ֻ�ڹ��������
		if (ParaManager.isCustServer()) {
			//return;
		}
		try {
			Thread t = new Thread() {
				
				public void run() {
					IAppServer appServer = (IAppServer)SpringContext.getService("timerAppServer");
					try {
						appServer.doProcess();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
