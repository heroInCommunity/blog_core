package name.heroin.community.model;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

class HibernateTest {

	public static void main(String[] args) {
		Post post = new Post();
		post.setTitle("Hi there!");
		post.setBody("I love your site.");
		post.setTimestamp(new Date());
		
		Comment comBur = new Comment();
		comBur.setName("buran");
		post.getComments().add(comBur);
		
		Tag tagBe = new Tag();
		tagBe.setName("beeeeee");
		post.getTags().add(tagBe);
		
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.persist(post);
		
		session.getTransaction().commit();
		session.close();
	}

}
