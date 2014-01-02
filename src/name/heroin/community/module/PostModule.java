package name.heroin.community.module;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import name.heroin.community.model.Post;
import name.heroin.community.model.SlimPost;
import name.heroin.community.model.Tag;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@Path("/posts/")
public class PostModule {
	@POST
	@Produces("application/json")
	@Path("/add_post")
	public Status addPost(@FormParam("title") String title, @FormParam("body") String body, @FormParam("tags[]") List<Integer> tagIds) {
		List<Tag> tags = new ArrayList<Tag>();
		for(Integer id : tagIds) {
			Tag tag = new Tag();
			tag.setId(id);
			tags.add(tag);
		}
		
		Post post = new Post();
		post.setTitle(title);
		post.setBody(body);
		post.setTags(tags);
				
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
