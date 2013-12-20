package name.heroin.community.constants;

public enum MenuName {
	ADMIN_PERMISSIONS("/admin/permissions");

	private final String value;
	MenuName(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
