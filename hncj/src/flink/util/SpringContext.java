package flink.util;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * SpringContext
 * 
 * 
 */
public class SpringContext {

	private static SpringContext instance = new SpringContext();

	public static SpringContext getInstance() {
		return instance;
	}

	public ApplicationContext initContext(ServletContext sc) {
		context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sc);
		return context;
	}

	private ApplicationContext context;

	public ApplicationContext getContext() {
		return context;
	}

	public ApplicationContext initContext(String classPathFile) {
		context = new ClassPathXmlApplicationContext(classPathFile);
		return context;
	}

	public ApplicationContext initContext(String[] classPathFiles) {
		context = new ClassPathXmlApplicationContext(classPathFiles);
		return context;
	}

	public static Object getService(String serviceName) {
		ApplicationContext context = instance.getContext();
		return context.getBean(serviceName);
	}

	public static DefaultListableBeanFactory getBeanFactory() {
		ApplicationContext context = instance.getContext();

		return (DefaultListableBeanFactory) ((AbstractApplicationContext) context)
				.getBeanFactory();
	}

	public static Object getSingleton(String beanName) {
		return getBeanFactory().getSingleton(beanName);
	}
}
