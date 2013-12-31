package name.heroin.community.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
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
	
	@GET
	@Produces("application/json")
	@Path("/get_tags")
	public Map<String, Object> getTags() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("sEcho", 1);
		result.put("iTotalRecords", 3);
		result.put("iTotalDisplayRecords", 3);
		
		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> first = new ArrayList<String>();
		first.add("<input type='checkbox' class='ids' value='1' />");
		first.add("First");
		
		ArrayList<String> second = new ArrayList<String>();
		second.add("<input type='checkbox' class='ids' value='1' />");
		second.add("Second");
		
		ArrayList<String> third = new ArrayList<String>();
		third.add("<input type='checkbox' class='ids' value='1' />");
		third.add("First");
		
		data.add(first);
		data.add(second);
		data.add(third);		
		
		result.put("aaData", data);
		
		return result;
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
	
	public List<Tag> getByIds(List<Integer> ids) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Tag.class);
		criteria.add(Restrictions.in("id", ids));
		List<Tag> tags = criteria.list();		
		
		session.getTransaction().commit();
		session.close();
		
		if (tags.isEmpty()) {
			return null;
		}
		return tags;
	}
}
