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
