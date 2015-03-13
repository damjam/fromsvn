package flink.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextUtil {

	public static Object getBean(String name){
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("core-context.xml");
		return applicationContext.getBean(name);
	} 
	
}
