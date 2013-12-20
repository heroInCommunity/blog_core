package name.heroin.community.constants;

public enum MenuName {
	ADMIN_ADD_PERMISSION("admin/add_permission"),
	ADMIN_EDIT_PERMISSION("admin/edit_permission"),
	ADMIN_PERMISSIONS("admin/permissions"),
	ADMIN_ALL_PERMISSIONS("admin/all_permissions");

	private final String value;
	MenuName(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
