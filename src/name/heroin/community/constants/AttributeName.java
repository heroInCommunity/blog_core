package name.heroin.community.constants;

public enum AttributeName {
	MENUS("menus"),
	SUB_LEVEL_MENU_ID("subLevelMenuId"),
	TOP_LEVEL_MENU_ID("topLevelMenuId");
	
	private final String value;
	AttributeName(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
