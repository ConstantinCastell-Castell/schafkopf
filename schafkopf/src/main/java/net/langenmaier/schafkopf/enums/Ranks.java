package net.langenmaier.schafkopf.enums;

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

public enum Ranks {
	ASS("ass", 11),
	ZEHN("zehn", 10),
	KOENIG("koenig", 4),
	OBER("ober", 3),
	UNTER("unter", 2),
	NEUN("neun", 0),
	ACHT("acht", 0),
	SIEBEN("sieben", 0);
	
	private String displayName;
	private int points;
	private Ranks(String displayName, int points) {
		this.displayName = displayName;
		this.points = points;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public int getPoints() {
		return points;
	}
}
