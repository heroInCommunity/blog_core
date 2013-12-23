package name.heroin.community.module;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import name.heroin.community.model.MenuItem;
import name.heroin.community.model.Role;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;

public class RoleModule {
	public List<Role> getRoles() {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Role.class);
		List<Role> roles = criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		return roles;
	}
}
