package flink.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.ylink.cim.common.util.ParaManager;

import flink.util.SpringContext;

public class SystemContextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public void init() throws ServletException {
		super.init();
		
		SpringContext.getInstance().initContext(getServletContext());
		
		ParaManager.init();
		
	}


	
}
