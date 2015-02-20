package net.langenmaier.schafkopf.models;

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

}
