package name.heroin.community.constants;

public enum MenuName {
	ADMIN_ADD_PERMISSION("admin/add_permission"),
	ADMIN_EDIT_PERMISSION("admin/edit_permission"),
	ADMIN_PERMISSIONS("admin/permissions"),
	ADMIN_ALL_PERMISSIONS("admin/all_permissions"),
	
	ADMIN_TAGS("admin/tags"),
	ADMIN_ALL_TAGS("admin/all_tags"),
	ADMIN_ADD_TAG("admin/add_tag"),
	ADMIN_EDIT_TAG("admin/edit_tag"),
	
	ADMIN_COMMENTS("admin/comments"),
	ADMIN_ALL_COMMENTS("admin/all_comments"),
	ADMIN_ADD_COMMENT("admin/add_comment"),
	ADMIN_EDIT_COMMENT("admin/edit_comment"),
	
	ADMIN_ROLES("admin/roles"),
	ADMIN_ALL_ROLES("admin/all_roles"),
	ADMIN_ADD_ROLE("admin/add_role"),
	ADMIN_EDIT_ROLE("admin/edit_role"),
	
	ADMIN_USERS("admin/users"),
	ADMIN_ALL_USERS("admin/all_users"),
	ADMIN_ADD_USER("admin/add_user"),
	ADMIN_EDIT_USER("admin/edit_user"),
	
	ADMIN_POSTS("admin/posts"),
	ADMIN_ALL_POSTS("admin/all_posts"),
	ADMIN_ADD_POST("admin/add_post"),
	ADMIN_EDIT_POST("admin/edit_post"),
	
	ADMIN_INDEX("admin/index"),
	ADMIN_LOGIN("admin/login");	

	private final String value;
	MenuName(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static boolean contains(String search) {
		for (MenuName menuName : MenuName.values()) {
			if (menuName.value.equals(search)) {
				return true;
			}
		}
		
		return false;
	}
}
