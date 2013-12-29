package name.heroin.community.module;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import name.heroin.community.model.Tag;
import name.heroin.community.model.User;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@Path("/tags/")
public class TagModule {
	@POST
	@Produces("application/json")
	@Path("/add_tag")
	public Status addTag(@FormParam("tagName") String tagName) {
		Tag tag = new Tag();
		tag.setName(tagName);
				
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		session.save(tag);
		
		session.getTransaction().commit();
		session.close();
		
		Status status = new Status();
		status.setText("success");
		
		return status;
	}
	
	@POST
	@Produces("application/json")
	@Path("/search")
	public List<Tag> search(@FormParam("tagName") String search) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Tag.class);
		criteria.add(Restrictions.like("name", search + "%"));
		
		criteria.setMaxResults(10);
		List<Tag> tags = criteria.list();		
		
		session.getTransaction().commit();
		session.close();
		
		if (tags.isEmpty()) {
			return null;
		}
		return tags;
	}
}
