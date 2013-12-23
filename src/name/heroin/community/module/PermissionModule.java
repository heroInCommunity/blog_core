package name.heroin.community.module;

import org.hibernate.Session;

import name.heroin.community.model.Permission;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;

public class PermissionModule {
	public void addPermission(Permission permission) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		session.persist(permission);
		
		session.getTransaction().commit();
		session.close();
	}
}
