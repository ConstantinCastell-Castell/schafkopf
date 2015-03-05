package net.langenmaier.schafkopf.models;

/*
 * #%L
 * schafkopf
 * %%
 * Copyright (C) 2015 langenmaier.net
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;

/**
 * This a serializable class for informing a client about the current game
 * 
 * @author slangenmaier
 *
 */
public class GameState {
	private Player player;
	private List<OtherPlayerState> otherPlayersState = new ArrayList<OtherPlayerState>();
	private GameActions gameActions = null;
	
	//TODO center cards
	
	public GameState(Table table, Player player) {
		this.player = player;
		if (table != null) {
			this.gameActions = new GameActions(player, table);
		
			for(Player p : table.getOtherPlayers(player)) {
				otherPlayersState.add(new OtherPlayerState(p.cardsLeft(), table.isPlayerActive(p)));
			}
		}
	}

}
