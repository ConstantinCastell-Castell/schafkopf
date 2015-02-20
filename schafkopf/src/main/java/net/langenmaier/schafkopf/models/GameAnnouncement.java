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

import net.langenmaier.schafkopf.enums.GameOption;
import net.langenmaier.schafkopf.enums.GameType;
import net.langenmaier.schafkopf.enums.Suits;

public class GameAnnouncement {
	private GameType type;
	private Suits suit;
	private GameOption option;
	
	public GameAnnouncement(GameType type, Suits suit) {
		this.type = type;
		this.suit = suit;
		this.option = GameOption.NO_OPTION;
	}
	
	public GameAnnouncement() {
		this.type = GameType.NO_GAME;
		this.option = GameOption.NO_OPTION;
	}

	public GameType getType() {
		return type;
	}

	public Suits getSuit() {
		return suit;
	}

	public GameOption getOption() {
		return option;
	}
	
	
}
