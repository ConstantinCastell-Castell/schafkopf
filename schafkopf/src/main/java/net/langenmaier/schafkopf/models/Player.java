package net.langenmaier.schafkopf.models;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private List<Card> hand = new ArrayList<Card>();
	private Table table;
	
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

}
