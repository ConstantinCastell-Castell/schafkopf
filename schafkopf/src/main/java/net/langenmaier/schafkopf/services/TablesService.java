package net.langenmaier.schafkopf.services;

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

import java.util.HashSet;
import java.util.Set;

import net.langenmaier.schafkopf.models.Player;
import net.langenmaier.schafkopf.models.Table;

public class TablesService {
	private static Set<Table> tables = new HashSet<Table>();
	
	public static void addTable(Table table) {
		if (!tables.contains(table)) {
			tables.add(table);
		}
	}
	
	public static Set<Table> getTables() {
		//FIXME
		addTable(new Table("Table No", new Player("p1")));
		return tables;
	}
	
	public static Table getTableById(int id) {
		for(Table t : tables) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}
	
	public static void closeTable(Table table) {
		if (tables.contains(table)) {
			table.close();
			tables.remove(table);
		} else {
			System.out.println("WARNING: no such open table found!");
		}
	}

	public static boolean hasTable(Table table) {
		return tables.contains(table);
	}
}
