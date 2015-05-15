package flink.web;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;

public class Log4jServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		super.init();
		String file = getInitParameter("log4j");

		if (StringUtils.isEmpty(file)) {
			return;
		}

		try {
			Properties ps = new Properties();
			ps.load(getServletContext().getResourceAsStream(file));
			PropertyConfigurator.configure(ps);
		} catch (IOException e) {
			// do nothing.
		}
	}
}
