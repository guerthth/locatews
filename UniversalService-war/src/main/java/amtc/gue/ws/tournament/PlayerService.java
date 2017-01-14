package amtc.gue.ws.tournament;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import amtc.gue.ws.base.delegate.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.util.PersistenceTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.tournament.delegate.persist.PlayerPersistenceDelegator;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.response.PlayerServiceResponse;
import amtc.gue.ws.tournament.util.TournamentServiceEntityMapper;

@Path("/players")
@Api(value = "players")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerService {

	protected static final Logger log = Logger.getLogger(PlayerService.class
			.getName());
	private AbstractPersistenceDelegator playerDelegator;

	public PlayerService() {
		this.playerDelegator = (PlayerPersistenceDelegator) SpringContext.context
				.getBean("playerPersistenceDelegator");
	}

	public PlayerService(AbstractPersistenceDelegator delegator) {
		this.playerDelegator = delegator;
	}

	@RolesAllowed("TOURNAMENT")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public PlayerServiceResponse addPlayers(Players players) {
		playerDelegator.buildAndInitializePersistenceDelegator(
				PersistenceTypeEnum.ADD, players);
		IDelegatorOutput bdOutput = playerDelegator.delegate();
		return TournamentServiceEntityMapper
				.mapBdOutputToPlayerServiceResponse(bdOutput);
	}

	@RolesAllowed("TOURNAMENT")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns all players", notes = "Returns all players", response = PlayerServiceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful retrieval of players", response = PlayerServiceResponse.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	public PlayerServiceResponse getPlayers() {
		playerDelegator.buildAndInitializePersistenceDelegator(
				PersistenceTypeEnum.READ, null);
		IDelegatorOutput bdOutput = playerDelegator.delegate();
		return TournamentServiceEntityMapper
				.mapBdOutputToPlayerServiceResponse(bdOutput);
	}

	@RolesAllowed("TOURNAMENT")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public PlayerServiceResponse removePlayer(@PathParam("id") String id) {
		Players playersToRemove = new Players();
		Player playerToRemove = new Player();
		playerToRemove.setId(id);
		List<Player> playerListToRemove = new ArrayList<>();
		playerListToRemove.add(playerToRemove);
		playersToRemove.setPlayers(playerListToRemove);
		playerDelegator.buildAndInitializePersistenceDelegator(
				PersistenceTypeEnum.DELETE, playersToRemove);
		IDelegatorOutput bdOutput = playerDelegator.delegate();
		return TournamentServiceEntityMapper
				.mapBdOutputToPlayerServiceResponse(bdOutput);
	}
}
