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

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import net.langenmaier.schafkopf.Schafkopf;
import net.langenmaier.schafkopf.enums.GamePhase;
import net.langenmaier.schafkopf.enums.GameType;

public class Table {
	private static int currentId = 0;
	
	private List<Player> players = new ArrayList<Player>();
	private String name;
	private Player dealer;
	private Player owner;
	private Player currentPlayer;
	private Game game;
	private int id;
	private Deck deck;
	private GamePhase gamePhase = GamePhase.NO_GAME;
	private List<SimpleEntry<Player, Card>> centerCards = new ArrayList<SimpleEntry<Player, Card>>();
	private List<SimpleEntry<Player, Card>> lastCenterCards = new ArrayList<SimpleEntry<Player, Card>>();
	private List<SimpleEntry<Player, GameAnnouncement>> playerAnnouncements = new ArrayList<SimpleEntry<Player, GameAnnouncement>>();
	
	public List<SimpleEntry<Player, Card>> getCenterCards() {
		return centerCards;
	}
	
	public List<SimpleEntry<Player, Card>> getLastCenterCards() {
		return lastCenterCards;
	}

	public Table(String name, Player player) {
		this.name = name;
		this.dealer = player;
		this.owner = player;
		addPlayer(player);
		id = currentId++;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getNextPlayer(Player player) {
		if (players.lastIndexOf(player) == (players.size()-1)) {
			return players.get(0);
		} else {
			int i = players.lastIndexOf(player);
			return players.get(i+1);
		}
	}

	public void addPlayer(Player player) {
		if(!players.contains(player)) {
			players.add(player);
			player.setTable(this);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player getDealer() {
		return dealer;
	}

	public void setDealer(Player dealer) {
		this.dealer = dealer;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getId() {
		return id;
	}

	public void removePlayer(Player player) {
		players.remove(player);		
	}

	public void close() {
		owner = null;
		dealer = null;
		for (Player p : players) {
			if (p instanceof Bot) {
				Bot b = (Bot) p;
				b.requestExit();
			}
		}
		players.removeAll(players);
		game = null;
	}
	
	public void startDealing() {
		deck = new Deck();
		for (Player player : players) {
			player.takeCards(deck.deal());
		}
		currentPlayer = getNextPlayer(getDealer());
		gamePhase = GamePhase.DEALING;
		
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public GamePhase getGamePhase() {
		return gamePhase;
	}

	public Deck getDeck() {
		return deck;
	}

	private void next() {
		if (gamePhase == GamePhase.DEALING) {
			currentPlayer = getNextPlayer(currentPlayer);
			System.out.println("CURRENT: " + currentPlayer.getName());
			if (deck.isDealt()) {
				gamePhase = GamePhase.ANNOUNCEMENT;
			}
		} else if ((gamePhase == GamePhase.ANNOUNCEMENT)) {
			if (announcementWinner(playerAnnouncements) != null) {
				createGame();
				gamePhase = GamePhase.PLAYING;
				currentPlayer = getNextPlayer(dealer);
			} else {
				currentPlayer = getNextPlayer(currentPlayer);
			}
		} else if ((gamePhase == GamePhase.PLAYING)) {
			System.out.println("PLAYING");
			if (centerCards.size() == 4) {
				System.out.println("CLEARING");
				Player winner = getTrickWinner();
				winner.addTrick(centerCards);
				lastCenterCards = centerCards;
				centerCards = new ArrayList<SimpleEntry<Player, Card>>();
				currentPlayer = winner;
				if (winner.getHand().size() == 0) {
					gamePhase = GamePhase.PAYING;
				}
			} else {
				currentPlayer = getNextPlayer(currentPlayer);
			}
			System.out.println("NEXT PHASE COMPLETE");
		}

		synchronized (Schafkopf.class) {
    		Schafkopf.class.notifyAll();
		}
	}
	
	private Player getTrickWinner() {
		SimpleEntry<Player, Card> trickWinner = game.getTrickWinner(centerCards);
		return trickWinner.getKey();
	}

	private void createGame() {
		System.out.println("CREATING A GAME");
		
		SimpleEntry<Player, GameAnnouncement> winningAnnouncement = announcementWinner(playerAnnouncements);
		game = new NormalGame(winningAnnouncement.getKey(), winningAnnouncement.getValue());
	}

	private SimpleEntry<Player, GameAnnouncement> announcementWinner(List<SimpleEntry<Player, GameAnnouncement>> playerAnnouncement) {
		if (playerAnnouncement.size() == 4) {
			for(SimpleEntry<Player, GameAnnouncement> e : playerAnnouncement) {
				if (e.getValue().getType() == GameType.NORMAL_GAME) {
					return e;
				}
			}
		}
		return null;
	}

	public List<Player> getOtherPlayers(Player player) {
		List<Player> otherPlayers = new ArrayList<Player>();
		
		Player nextPlayer = getNextPlayer(player);
		while((nextPlayer != player) && (otherPlayers.size()<4)) {
			otherPlayers.add(nextPlayer);
			nextPlayer = getNextPlayer(nextPlayer);
		}
		
		return otherPlayers;
	}

	public boolean isPlayerActive(Player p) {
		return p == this.currentPlayer;
	}

	public Boolean playCard(Player player, int cardId) {
		if (player == currentPlayer && gamePhase==GamePhase.PLAYING) {
			Card card = null;
			for(Card c : player.getHand()) {
				if (c.hashCode() == cardId) {
					card = c;
				}
			}
			if (card == null) {
				System.out.println("CARD INVALID");
				return false;
			}
			
			playCard(player, card);
			System.out.println("PLAYED CARD");
			return true;
			
		}
		return false;
	}
	
	public void playCard(Player player, Card card) {
		centerCards.add(new SimpleEntry<Player, Card>(player, card));
		player.getHand().remove(card);
		
		next();
	}

	public void announce(Player player, GameAnnouncement announcement) {
		playerAnnouncements.add(new SimpleEntry<Player, GameAnnouncement>(player, announcement));
		next();
	}

	public List<Card> deal() {
		List<Card> cards = deck.deal();
		next();
		return cards;
	}




}
