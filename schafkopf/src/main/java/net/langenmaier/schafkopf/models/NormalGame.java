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
import java.util.HashMap;
import java.util.List;

import net.langenmaier.schafkopf.enums.Ranks;
import net.langenmaier.schafkopf.enums.Suits;


public class NormalGame extends Game {

	public NormalGame(Player player, GameAnnouncement announcement) {
		super();
		this.player = player;
		this.announcement = announcement;
		
	}

	@Override
	protected void initializeCardOrder() {
		trumpCards = new ArrayList<Card>();
		trumpCards.add(new Card(Suits.EICHEL, Ranks.OBER));
		trumpCards.add(new Card(Suits.LAUB, Ranks.OBER));
		trumpCards.add(new Card(Suits.HERZ, Ranks.OBER));
		trumpCards.add(new Card(Suits.SCHELLEN, Ranks.OBER));
		
		trumpCards.add(new Card(Suits.EICHEL, Ranks.UNTER));
		trumpCards.add(new Card(Suits.LAUB, Ranks.UNTER));
		trumpCards.add(new Card(Suits.HERZ, Ranks.UNTER));
		trumpCards.add(new Card(Suits.SCHELLEN, Ranks.UNTER));
		
		trumpCards.add(new Card(Suits.HERZ, Ranks.ASS));
		trumpCards.add(new Card(Suits.HERZ, Ranks.ZEHN));
		trumpCards.add(new Card(Suits.HERZ, Ranks.KOENIG));
		trumpCards.add(new Card(Suits.HERZ, Ranks.NEUN));
		trumpCards.add(new Card(Suits.HERZ, Ranks.ACHT));
		trumpCards.add(new Card(Suits.HERZ, Ranks.SIEBEN));
		
		colorCards = new HashMap<Suits, List<Card>>();
		Suits[] suits = new Suits[]{Suits.EICHEL, Suits.LAUB, Suits.SCHELLEN};
		for(Suits s : suits) {
			List<Card> cards = new ArrayList<Card>();
			
			cards.add(new Card(s, Ranks.ASS));
			cards.add(new Card(s, Ranks.ZEHN));
			cards.add(new Card(s, Ranks.KOENIG));
			cards.add(new Card(s, Ranks.NEUN));
			cards.add(new Card(s, Ranks.ACHT));
			cards.add(new Card(s, Ranks.SIEBEN));
			
			colorCards.put(s, cards);
		}
		
	}
}
