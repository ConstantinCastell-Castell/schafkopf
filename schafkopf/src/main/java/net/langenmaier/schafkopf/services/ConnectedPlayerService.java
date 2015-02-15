package net.langenmaier.schafkopf.services;

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
