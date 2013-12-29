package name.heroin.community.module;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import name.heroin.community.model.MenuItem;
import name.heroin.community.model.Role;
import name.heroin.community.model.User;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
	
	@POST
	@Produces("application/json")
	@Path("/search")
	public List<User> search(@FormParam("term") String search) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.disjunction()
				.add(Restrictions.like("name", search + "%"))
				.add(Restrictions.like("email", search + "%"))
		);
		
		criteria.setMaxResults(10);
		List<User> users = criteria.list();		
		
		session.getTransaction().commit();
		session.close();
		
		if (users.isEmpty()) {
			return null;
		}
		return users;
	}
	
	public User getByEmail(String email) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		List<User> users = criteria.list();		
		
		session.getTransaction().commit();
		session.close();
		
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}
	
	public User getById(Integer id) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("id", id));
		List<User> users = criteria.list();		
		
		session.getTransaction().commit();
		session.close();
		
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}
}
