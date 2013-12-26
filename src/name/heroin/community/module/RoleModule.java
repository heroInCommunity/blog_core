package name.heroin.community.module;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import name.heroin.community.model.Permission;
import name.heroin.community.model.Role;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

@Path("/roles/")
public class RoleModule {
	public List<Role> getRoles() {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Role.class);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<Role> roles = criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		return roles;
	}
	
	public Role getRoleById(Integer id) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Role.class);
		criteria.add(Restrictions.eq("id", id));
		List<Role> roles = criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		return roles.get(0);
	}
	
	@POST
	@Produces("application/json")
	@Path("/add_role")
	public Status addRole(@FormParam("roleName") String roleName) {
		Role role = new Role();
		role.setName(roleName);
				
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		session.save(role);
		
		session.getTransaction().commit();
		session.close();
		
		Status status = new Status();
		status.setText("success");
		
		return status;
	}
}
