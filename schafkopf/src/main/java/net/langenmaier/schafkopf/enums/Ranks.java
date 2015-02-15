package net.langenmaier.schafkopf.enums;

public enum Ranks {
	ASS("ass"),
	ZEHN("zehn"),
	KOENIG("koenig"),
	OBER("ober"),
	UNTER("unter"),
	NEUN("neun"),
	ACHT("acht"),
	SIEBEN("sieben");
	
	private String displayName;
	private Ranks(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
