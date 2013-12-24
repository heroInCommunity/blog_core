package name.heroin.community.module;

import org.hibernate.Session;

import name.heroin.community.model.Permission;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

@Path("/permissions/")
public class PermissionModule {
	
	@POST
	@Produces("application/json")
	@Path("/add_permission")
	public Boolean addPermission(Permission permission) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		session.persist(permission);
		
		session.getTransaction().commit();
		session.close();
		System.out.println("fewf");
		return true;
	}
}
