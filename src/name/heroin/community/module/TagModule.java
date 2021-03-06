package name.heroin.community.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import name.heroin.community.constants.Parameters;
import name.heroin.community.model.Tag;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
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
	@Path("/edit_tag")
	public Status editTag(@FormParam("id") Integer tagId, @FormParam("tagName") String tagName) {
		Status status = new Status();
		if (tagId == null) {
			status.setText("error");
			return status;
		}
		
		Tag tag = getById(tagId);
		if (tag != null) {
			tag.setName(tagName);

			SessionProvider sessionProvider = new SessionProviderHibernate();

			Session session = sessionProvider.getSession();
			session.beginTransaction();

			session.update(tag);

			session.getTransaction().commit();
			session.close();

			status.setText("success");
		}
		else {
			status.setText("error");
		}

		return status;
	}

	@GET
	@Produces("application/json")
	@Path("/get_tags")
	public Map<String, Object> getTagsTableData(
			@DefaultValue(Parameters.Constants.S_SEARCH) @QueryParam("sSearch") String search,
			@DefaultValue(Parameters.Constants.S_ECHO) @QueryParam("sEcho") Integer echo,
			@DefaultValue(Parameters.Constants.I_DISPLAY_START) @QueryParam("iDisplayStart") Integer start,
			@DefaultValue(Parameters.Constants.I_DISPLAY_LENGTH) @QueryParam("iDisplayLength") Integer length) {
		Map<String, Object> result = new HashMap<String, Object>();
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Tag.class);

		if (search.length() >= Integer.parseInt(Parameters.Constants.MIN_LENGTH_TO_SEARCH)) {
			criteria.add(Restrictions.like("name", search + "%"));
		}
		
		criteria.setFirstResult(start);
		criteria.setMaxResults(length);
		List<Tag> tags = criteria.list();

		Number tagsCount = (Number) session.createCriteria(Tag.class)
				.setProjection(Projections.rowCount()).uniqueResult();

		session.getTransaction().commit();
		session.close();

		result.put("sEcho", echo);
		result.put("iTotalRecords", tagsCount);
		result.put("iTotalDisplayRecords", tagsCount);

		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		for (Tag tag : tags) {
			ArrayList<String> row = new ArrayList<String>();
			row.add("<input type='checkbox' class='ids' value='" + tag.getId() + "' />");
			row.add("<a href='edit_tag?id=" + tag.getId() + "'>" + tag.getName() + "</a>");
			data.add(row);
		}

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

	public Tag getById(int tagId) {
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Tag.class);
		criteria.add(Restrictions.eq("id", tagId));

		List<Tag> tags = criteria.list();

		session.getTransaction().commit();
		session.close();

		if (tags.isEmpty()) {
			return null;
		}
		return tags.get(0);
	}
}
