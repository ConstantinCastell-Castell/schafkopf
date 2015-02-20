package net.langenmaier.schafkopf.models;

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
