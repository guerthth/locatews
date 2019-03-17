package amtc.gue.ws.tournament;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.UnauthorizedException;

import amtc.gue.ws.Constants;
import amtc.gue.ws.base.Service;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.tournament.delegate.persist.PlayerPersistenceDelegator;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.response.PlayerServiceResponse;
import amtc.gue.ws.tournament.util.mapper.TournamentServiceEntityMapper;

@Api(name = "tournament", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID,
		Constants.API_EXPLORER_CLIENT_ID }, description = "API for the Tournament backend application")
public class PlayerService extends Service {
	private static final Logger log = Logger.getLogger(PlayerService.class.getName());
	private static final String SCOPE = "tournament";
	private AbstractPersistenceDelegator playerDelegator;

	public PlayerService() {
		super();
		playerDelegator = (PlayerPersistenceDelegator) SpringContext.context.getBean("playerPersistenceDelegator");
	}

	public PlayerService(AbstractPersistenceDelegator userDelegator, AbstractPersistenceDelegator playerDelegator) {
		super(userDelegator);
		this.playerDelegator = playerDelegator;
	}

	@ApiMethod(name = "addPlayers", path = "player", httpMethod = HttpMethod.POST)
	public PlayerServiceResponse addPlayers(final User user, Players players) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		playerDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.ADD, players);
		IDelegatorOutput bdOutput = playerDelegator.delegate();
		PlayerServiceResponse response = TournamentServiceEntityMapper.mapBdOutputToPlayerServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "getPlayers", path = "player", httpMethod = HttpMethod.GET)
	public PlayerServiceResponse getPlayers(final User user) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		playerDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, null);
		IDelegatorOutput bdOutput = playerDelegator.delegate();
		PlayerServiceResponse response = TournamentServiceEntityMapper.mapBdOutputToPlayerServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "removePlayer", path = "player/{id}", httpMethod = HttpMethod.DELETE)
	public PlayerServiceResponse removePlayer(final User user, @Named("id") final String id)
			throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		Players playersToRemove = new Players();
		Player playerToRemove = new Player();
		playerToRemove.setId(id);
		List<Player> playerListToRemove = new ArrayList<>();
		playerListToRemove.add(playerToRemove);
		playersToRemove.setPlayers(playerListToRemove);
		playerDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.DELETE, playersToRemove);
		IDelegatorOutput bdOutput = playerDelegator.delegate();
		PlayerServiceResponse response = TournamentServiceEntityMapper.mapBdOutputToPlayerServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}
}
