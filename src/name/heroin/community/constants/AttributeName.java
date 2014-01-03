package name.heroin.community.constants;

public enum AttributeName {
	MENUS("menus"),
	SUB_LEVEL_MENU_ID("subLevelMenuId"),
	TOP_LEVEL_MENU_ID("topLevelMenuId"),
	LIST_OF_ROLES("listOfRoles"),
	BASE_URL("baseUrl"),
	TAG("tag"),
	POST("post"),
	COMMENT("comment"),
	ROLE("role"),
	PERMISSION("permission"),
	USER("user");
	
	private final String value;
	AttributeName(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static boolean contains(String search) {
		for (AttributeName attributeName : AttributeName.values()) {
			if (attributeName.name().equals(search)) {
				return true;
			}
		}
		
		return false;
	}
}
