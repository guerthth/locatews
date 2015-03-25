package amtc.gue.ws;

import java.util.logging.Logger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(wsdlLocation = "WEB-INF/wsdl/HelloWorldService.wsdl")
public class HelloWorld {
	
	private static Logger log = Logger.getLogger(HelloWorld.class.getName());
	
	@WebMethod
	public String sayHello(@WebParam(name = "person") Person person){
		
		log.info("call revceived. With person: " + person);
		
		// only return the greeting if the sent Person object is not null
		if(person != null){
			return "Hello " + person.getFirstName() + " " + person.getLastName();
		} else {
			return "Hello John Doe!";
		}
		
	}
}
