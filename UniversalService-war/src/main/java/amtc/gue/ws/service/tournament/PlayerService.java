package amtc.gue.ws.service.tournament;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import amtc.gue.ws.service.Service;

@Path("/tournament")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerService extends Service {

	protected static final Logger log = Logger.getLogger(PlayerService.class
			.getName());
	// TODO: intitialize PlayerPersistenceDelegator
	
}
