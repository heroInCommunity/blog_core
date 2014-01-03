package name.heroin.community.module;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import name.heroin.community.constants.Parameters;
import name.heroin.community.model.Post;
import name.heroin.community.model.SlimPost;
import name.heroin.community.model.Tag;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Path("/posts/")
public class PostModule {
	@GET
	@Produces("application/json")
	@Path("/get_posts")
	public Map<String, Object> getPostsTableData(
			@DefaultValue(Parameters.Constants.S_SEARCH) @QueryParam("sSearch") String search,
			@DefaultValue(Parameters.Constants.S_ECHO) @QueryParam("sEcho") Integer echo,
			@DefaultValue(Parameters.Constants.I_DISPLAY_START) @QueryParam("iDisplayStart") Integer start,
			@DefaultValue(Parameters.Constants.I_DISPLAY_LENGTH) @QueryParam("iDisplayLength") Integer length) {
		Map<String, Object> result = new HashMap<String, Object>();
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Post.class);

		if (search.length() >= Integer.parseInt(Parameters.Constants.MIN_LENGTH_TO_SEARCH)) {
			criteria.add(Restrictions.like("title", search + "%"));
		}
		
		criteria.setFirstResult(start);
		criteria.setMaxResults(length);
		List<Post> posts = criteria.list();

		Number postsCount = (Number) session.createCriteria(Post.class)
				.setProjection(Projections.rowCount()).uniqueResult();

		session.getTransaction().commit();
		session.close();

		result.put("sEcho", echo);
		result.put("iTotalRecords", postsCount);
		result.put("iTotalDisplayRecords", postsCount);

		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		for (Post post : posts) {
			ArrayList<String> row = new ArrayList<String>();
			row.add("<input type='checkbox' class='ids' value='" + post.getId()
					+ "' />");
			row.add(post.getTitle());
			row.add(getDisplayDate(post.getTimestamp()));
			row.add(getStringTags(post));
			data.add(row);
		}

		result.put("aaData", data);

		return result;
	}
	
	private String getDisplayDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.YEAR);
	}
	
	private String getStringTags(Post post) {
		StringBuffer stringBuffer = new StringBuffer();
		
		int iter = 0;
		for(Tag tag : post.getTags()) {
			if (iter != 0) {
				stringBuffer.append(", ");
			}
			
			stringBuffer.append(tag.getName());
			iter++;
		}
		
		return stringBuffer.toString();
	}
	
	@POST
	@Produces("application/json")
	@Path("/add_post")
	public Status addPost(@FormParam("title") String title, @FormParam("body") String body, @FormParam("tags[]") List<Integer> tagIds) {
		Set<Tag> tags = new HashSet<Tag>();
		for(Integer id : tagIds) {
			Tag tag = new Tag();
			tag.setId(id);
			tags.add(tag);
		}
		
		Post post = new Post();
		post.setTitle(title);
		post.setBody(body);
		post.setTags(tags);
		post.setTimestamp(new Date());
				
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		session.save(post);
		
		session.getTransaction().commit();
		session.close();
		
		Status status = new Status();
		status.setText("success");
		
		return status;
	}
	
	@POST
	@Produces("application/json")
	@Path("/search")
	public List<SlimPost> search(@FormParam("term") String search) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		List<SlimPost> posts = (List<SlimPost>) session.getNamedQuery("getIdNameMap").setString("search", search + "%").list();		
		
		session.getTransaction().commit();
		session.close();
		
		return posts;
	}
	
	public Post getById(Integer id) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Post.class);
		criteria.add(Restrictions.eq("id", id));
		List<Post> posts = criteria.list();		
		
		session.getTransaction().commit();
		session.close();
		
		if (posts.isEmpty()) {
			return null;
		}
		return posts.get(0);
	}
}
