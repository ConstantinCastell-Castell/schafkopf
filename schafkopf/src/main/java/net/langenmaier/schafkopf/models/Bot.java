package net.langenmaier.schafkopf.models;

import net.langenmaier.schafkopf.Schafkopf;

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
				System.out.println("WAINTING TO PLAY");
				Table table = getTable();
				synchronized(table) {
					table.wait(10000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void play() {
		System.out.println("BOT " +botId +" PLAYING");
		if (getTable().getGamePhase() == GamePhase.DEALING) {
			if (getHand().size()<8) {
				System.out.println("TAKING CARDS");
				takeCards(getTable().getDeck().deal());
				getTable().next();
			}
		}
		synchronized (Schafkopf.class) {
    		Schafkopf.class.notifyAll();;
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
