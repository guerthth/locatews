package amtc.gue.ws.tournament.util.mapper.jpa;

import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.jpa.GAEJPAPlayerEntity;
import amtc.gue.ws.tournament.util.mapper.TournamentServiceEntityMapper;

/**
 * Class responsible for mapping of JPA Entities relevant for the
 * TournamentService
 * 
 * @author Thomas
 *
 */
public class TournamentServiceJPAEntityMapper extends TournamentServiceEntityMapper {

	@Override
	public GAEPlayerEntity mapPlayerToEntity(Player player, DelegatorTypeEnum type) {
		GAEPlayerEntity playerEntity = new GAEJPAPlayerEntity();
		if (player.getId() != null && type != DelegatorTypeEnum.ADD)
			playerEntity.setKey(player.getId());
		playerEntity.setDescription(player.getDescription());
		return playerEntity;
	}

}
