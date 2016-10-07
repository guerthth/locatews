package amtc.gue.ws.service;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/")
public class RestApplication extends ResourceConfig {
	public RestApplication() {
		// Register resources and providers using package-scanning.
		packages("amtc.gue.ws.service");
		// Enable Tracing support.
		property(ServerProperties.TRACING, "ALL");
	}
}
