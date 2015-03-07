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

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

public class Player {
	private String name;
	protected List<Card> hand = new ArrayList<Card>();
	protected transient List<List<SimpleEntry<Player, Card>>> tricks = new ArrayList<List<SimpleEntry<Player, Card>>>();
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

	public void addTrick(List<SimpleEntry<Player, Card>> centerCards) {
		tricks.add(centerCards);
	}

}
