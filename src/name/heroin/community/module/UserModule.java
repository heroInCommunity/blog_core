package name.heroin.community.module;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import name.heroin.community.model.Role;
import name.heroin.community.model.User;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import org.hibernate.Session;

@Path("/users/")
public class UserModule {
	@POST
	@Produces("application/json")
	@Path("/add_user")
	public Status addUser(@FormParam("userName") String userName,
			@FormParam("userEmail") String userEmail,
			@FormParam("userRole") Integer userRole) {
		User user = new User();
		user.setName(userName);
		user.setEmail(userEmail);
		
		RoleModule roleModule = new RoleModule();
		Role role = roleModule.getRoleById(userRole);
		user.setRole(role);

		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		session.save(user);

		session.getTransaction().commit();
		session.close();

		Status status = new Status();
		status.setText("success");

		return status;
	}
}
