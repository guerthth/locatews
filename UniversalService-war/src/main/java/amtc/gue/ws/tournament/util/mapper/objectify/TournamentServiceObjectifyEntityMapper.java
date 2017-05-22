package amtc.gue.ws.tournament.util.mapper.objectify;

import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.objectify.GAEObjectifyPlayerEntity;
import amtc.gue.ws.tournament.util.mapper.TournamentServiceEntityMapper;

/**
 * Class responsible for mapping of Objectify Entities relevant for the
 * TournamentService
 * 
 * @author Thomas
 *
 */
public class TournamentServiceObjectifyEntityMapper extends TournamentServiceEntityMapper {

	@Override
	public GAEPlayerEntity mapPlayerToEntity(Player player, DelegatorTypeEnum type) {
		GAEPlayerEntity playerEntity = new GAEObjectifyPlayerEntity();
		if (player.getId() != null && type != DelegatorTypeEnum.ADD)
			playerEntity.setKey(player.getId());
		playerEntity.setDescription(player.getDescription());
		return playerEntity;
	}

}
