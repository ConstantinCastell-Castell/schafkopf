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

import net.langenmaier.schafkopf.enums.GamePhase;

public class GameActions {
	private boolean canDeal = false;
	private boolean isOwner = false;
	private boolean canAddBot = false;
	private boolean canTakeMoreCards = false;
	private boolean canAnnounce = false;
	

	public GameActions(Player player, Table table) {
		if (player != null && table != null) {
			isOwner = (player == table.getOwner());
			canAddBot = ((player == table.getOwner()) && (table.getPlayers().size()<4));
			canDeal = ((player == table.getDealer()) && (table.getGamePhase() == GamePhase.NO_GAME));
			canTakeMoreCards = (table.isPlayerActive(player) && (player.getHand().size()<8) && (table.getGamePhase() == GamePhase.DEALING));
			canAnnounce = (table.isPlayerActive(player) && (table.getGamePhase() == GamePhase.ANNOUNCEMENT));
		}
	}
	
	public boolean canDeal() {
		return canDeal;
	}

	public boolean isOwner() {
		return isOwner;
	}
	public boolean canAddBot() {
		return canAddBot;
	}
	public boolean canTakeMoreCards() {
		return canTakeMoreCards;
	}
	
	public boolean canAnnounce() {
		return canAnnounce;
	}

}
