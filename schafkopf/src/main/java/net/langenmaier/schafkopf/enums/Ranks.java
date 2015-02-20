package net.langenmaier.schafkopf.enums;

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
