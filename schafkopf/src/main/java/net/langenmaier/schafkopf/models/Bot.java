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

public class Bot extends Player implements Runnable {
	private static int botCounter = 0;
	private int botId;

	public Bot() {
		super("Bot " + botCounter);
		botId = botCounter++;
	}

	@Override
	public void run() {
		while (true) {
			if (canPlay()) {
				play();
			}
			try {
				Table table = getTable();
				synchronized(table) {
					table.wait(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void play() {
		System.out.println("BOT " + botId +" PLAYING");
		if (getTable().getGamePhase() == GamePhase.DEALING) {
			if (getHand().size()<8) {
				takeCards(getTable().deal());
			}
		} else if (getTable().getGamePhase() == GamePhase.ANNOUNCEMENT) {
			getTable().announce(this, new GameAnnouncement());
		} else if (getTable().getGamePhase() == GamePhase.PLAYING) {
			getTable().playCard(this, hand.get(0));
		}
	}

	private boolean canPlay() {
		if (getTable() != null) {
			if (getTable().getCurrentPlayer() == this) {
				return true;
			}
		}

		return false;
	}

}
