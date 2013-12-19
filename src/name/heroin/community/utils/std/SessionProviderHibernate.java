package name.heroin.community.utils.std;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import name.heroin.community.utils.SessionProvider;

public class SessionProviderHibernate implements SessionProvider {
	private Session session;
	
	public Session getSession() {
		if (session == null) {
			Configuration configuration = new Configuration();
			configuration.configure();
			ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
			ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();
			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			session = sessionFactory.openSession();
		}
		
		return session;
	}
}
