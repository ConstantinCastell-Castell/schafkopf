package net.langenmaier.schafkopf.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.langenmaier.schafkopf.enums.Ranks;
import net.langenmaier.schafkopf.enums.Suits;

public class Deck {
	private List<Card> cards = new ArrayList<Card>();

	public Deck() {
		for (Suits s : Suits.values()) {
		    for (Ranks r : Ranks.values()) {
		         Card c = new Card(s,r);
		         cards.add(c);
		    }  
		}
		Collections.shuffle(cards);
	}
	
	public List<Card> deal() {
		List<Card> deal = new ArrayList<Card>();
		deal.addAll(cards.subList(0, 4));
		cards.subList(0, 4).clear();
		return deal;
	}
	
	public boolean isDealt() {
		return (cards.size() == 0);
	}
}
