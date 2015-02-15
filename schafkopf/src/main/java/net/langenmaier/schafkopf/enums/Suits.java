package net.langenmaier.schafkopf.enums;

public enum Suits {
	EICHEL("eichel"),
	LAUB("laub"),
	HERZ("herz"),
	SCHELLEN("schellen");
	
	private String displayName;
	private Suits(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
