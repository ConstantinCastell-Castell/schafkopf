package net.langenmaier.schafkopf.services;

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
