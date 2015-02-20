package net.langenmaier.schafkopf.models;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	protected List<Card> hand = new ArrayList<Card>();
	protected List<List<Card>> tricks = new ArrayList<List<Card>>();
	private transient Table table;
	private boolean isActivePlayer = false;
	
	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void takeCards(List<Card> deal) {
		hand.addAll(deal);
	}
	
	public List<Card> getHand() {
		return hand;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
	public Table getTable() {
		return table;
	}
	
	public boolean isActivePlayer() {
		return (this == table.getCurrentPlayer());
	}
	
	public int cardsLeft() {
		return hand.size();
	}

	public Boolean setShuffeledHand(int[] cardIds) {
		List<Card> copyHand = new ArrayList<Card>();
		for(Card c : hand) {
			copyHand.add(c);
		}
		List<Card> newHand = new ArrayList<Card>();
		
		for(int id : cardIds) {
			for(Card c : copyHand) {
				if (c.hashCode() == id) {
					newHand.add(c);
					copyHand.remove(c);
					break;
				}
			}
		}
		if (newHand.size() == hand.size()) {
			hand = newHand;
			return true;
		}
		
		return false;
	}

	public void addTrick(List<Card> centerCards) {
		tricks.add(centerCards);
	}

}
