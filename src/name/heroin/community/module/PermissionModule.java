package name.heroin.community.module;

import org.hibernate.Session;

import name.heroin.community.model.Permission;
import name.heroin.community.model.Role;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;

@Path("/permissions/")
public class PermissionModule {
	
	@POST
	@Produces("application/json")
	@Path("/add_permission")
	public Status addPermission(@FormParam("permissionName") String permissionName, @FormParam("userRole") Integer userRole) {
		Permission permission = new Permission();
		permission.setName(permissionName);
				
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		RoleModule roleModule = new RoleModule();
		Role role = roleModule.getRoleById(userRole);
		role.getPermissions().add(permission);
		
		session.save(permission);
		session.update(role);
		
		session.getTransaction().commit();
		session.close();
		
		Status status = new Status();
		status.setText("success");
		
		return status;
	}
}
