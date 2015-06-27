package flink.web.tag;

import org.apache.log4j.Logger;

public abstract class HtmlNavigatorFactory {
	private static final Logger logger = Logger
			.getLogger(HtmlNavigatorFactory.class);
	public static final String CLASSICAL = "classical";
	public static final String GOOGLE = "google";
	public static final String IMAGE = "image";

	public static HtmlNavigator getInstance(String style) {
		if (CLASSICAL.equalsIgnoreCase(style)) {
			return new ClassicalHtmlNavigator();
		} else if (GOOGLE.equalsIgnoreCase(style)) {
			return new GoogleHtmlNavigator();
		} else if (IMAGE.equalsIgnoreCase(style)) {
			return new ImageHtmlNavigator();
		} else {
			try {
				return (HtmlNavigator) Class.forName(style).newInstance();
			} catch (Exception e) {
				logger.error(e, e);
			}

			return new ClassicalHtmlNavigator();
		}
	}
}
