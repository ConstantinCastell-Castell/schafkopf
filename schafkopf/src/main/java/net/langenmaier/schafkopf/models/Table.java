package net.langenmaier.schafkopf.models;

import java.util.ArrayList;
import java.util.List;

import net.langenmaier.schafkopf.services.ConnectedPlayerService;

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
	private List<Card> centerCards = new ArrayList<Card>();
	
	public List<Card> getCenterCards() {
		return centerCards;
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

	public void next() {
		
		currentPlayer = getNextPlayer(currentPlayer);
		System.out.println("CURRENT: " + currentPlayer.getName());
		//TODO
		//implement gamephase change
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
		System.out.println("playing CARD");
		if (player == currentPlayer) {
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
			
			centerCards.add(card);
			player.getHand().remove(card);
			
			next();
			
			return true;
			
		}
		return false;
	}


}
