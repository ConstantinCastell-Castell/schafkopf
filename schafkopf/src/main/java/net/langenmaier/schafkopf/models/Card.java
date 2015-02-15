package net.langenmaier.schafkopf.models;

import net.langenmaier.schafkopf.enums.Ranks;
import net.langenmaier.schafkopf.enums.Suits;

public class Card implements Cloneable {
	private Suits suit;
	private Ranks rank;
	
	public Card(Suits suit, Ranks rank) {
		this.rank = rank;
		this.suit = suit;
	}

	public Ranks getRank() {
		return rank;
	}

	public Suits getSuit() {
		return suit;
	}
	
	public String getName() {
		return suit.name() + " - " + rank.name();
	}
	
	public String getRankName() {
		return rank.getDisplayName();
	}
	
	public String getIcon() {
		return "/images/" + suit.getDisplayName() + ".svg";
	}
	
	public int getId() {
		return this.hashCode();
	}
	
	public Card(Card c) {
		this.suit = c.getSuit();
		this.rank = c.getRank();
	}

}
