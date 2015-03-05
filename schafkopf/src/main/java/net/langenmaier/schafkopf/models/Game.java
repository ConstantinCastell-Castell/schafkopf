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
import java.util.List;
import java.util.Map;

import net.langenmaier.schafkopf.enums.Suits;

public abstract class Game {
	Player player;
	GameAnnouncement announcement;
	
	//Cards are in order
	List<Card> trumpCards;
	Map<Suits,List<Card>> colorCards;
	
	protected Game() {
		initializeCardOrder();
	}
	
	protected abstract void initializeCardOrder();

	public SimpleEntry<Player, Card> getTrickWinner(List<SimpleEntry<Player, Card>> centerCards) {
		SimpleEntry<Player, Card> trickWinner =null;
		
		for (SimpleEntry<Player, Card> playedCard : centerCards) {
			if (trickWinner == null) {
				trickWinner = playedCard;
			} else {
				trickWinner = higherCard(trickWinner, playedCard);
			}
		}
		
		return trickWinner;
	}

	private SimpleEntry<Player, Card> higherCard(
			SimpleEntry<Player, Card> trickWinner,
			SimpleEntry<Player, Card> playedCard) {
		if (trumpCards.contains(trickWinner.getValue())) {
			System.out.println("TRUMP");
			if (trumpCards.contains(playedCard.getValue())) {
				for (Card c : trumpCards) {
					// The cards are in the order of strength
					// so the first one found is stronger
					if (trickWinner.getValue().equals(c)) {
						return trickWinner;
					}
					if (playedCard.getValue().equals(c)) {
						return playedCard;
					}
				}
			}
			return trickWinner;
		} else {
			if (trumpCards.contains(playedCard.getValue())) {
				// a trump is always stronger
				return playedCard;
			} else {
				if (trickWinner.getValue().getSuit().equals(playedCard.getValue().getSuit())) {
					Suits suit = trickWinner.getValue().getSuit();
					for (Card c : colorCards.get(suit)) {
						// The cards are in the order of strength
						// so the first one found is stronger
						if (trickWinner.getValue().equals(c)) {
							return trickWinner;
						}
						if (playedCard.getValue().equals(c)) {
							return playedCard;
						}
					}
				} else {
					return trickWinner;
				}
			}
			return trickWinner;
		}
		
		
	}

}
