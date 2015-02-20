package net.langenmaier.schafkopf.services;

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

import java.util.HashSet;
import java.util.Set;

import spark.Request;
import net.langenmaier.schafkopf.models.Player;

public class ConnectedPlayerService {
	private static Set<Player> players = new HashSet<Player>();
	
	public static void addPlayer(Player player) {
		if (!players.contains(player)) {
			players.add(player);
		}
	}
	
	public static Set<Player> getPlayers() {
		return players;
	}

	public static Player getCurrentPlayer(Request request) {
		if (request.session().attributes().contains("player")) {
			if (request.session().attribute("player") instanceof Player)
				return (Player)request.session().attribute("player");
		}
		return null;
	}


}
