package flink.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 */
public class SymbolTrans extends SimpleTagSupport {
	private String symbol;
	
	@Override
	public void doTag() throws JspException, IOException {
		String s = null;
		
		if ("Y".equalsIgnoreCase(symbol) || "YES".equalsIgnoreCase(symbol) || "TRUE".equalsIgnoreCase(symbol)) {
			s = "是";
		}
		else if ("N".equalsIgnoreCase(symbol) || "NO".equalsIgnoreCase(symbol) || "FALSE".equalsIgnoreCase(symbol)) {
			s = "否";
		}
		else {
			s = symbol;
		}
		
		this.getJspContext().getOut().write(s);
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
