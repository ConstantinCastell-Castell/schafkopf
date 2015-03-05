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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
	


}
