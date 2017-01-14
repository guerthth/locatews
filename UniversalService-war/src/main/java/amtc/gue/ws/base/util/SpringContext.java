package amtc.gue.ws.base.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ApplicationContext class
 * 
 * @author Thomas
 *
 */
public class SpringContext {
	
	/** application context xml file name */
	private static final String SPRING_APPLICATION_CONTEXT_XML = 
			"spring-application-context.xml";
	
	/** ApplicationContext */
	public static final ApplicationContext context = 
			new ClassPathXmlApplicationContext(SPRING_APPLICATION_CONTEXT_XML);
}
