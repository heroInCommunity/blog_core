package name.heroin.community.module;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import name.heroin.community.model.Post;
import name.heroin.community.model.Tag;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import org.hibernate.Session;

@Path("/posts/")
public class PostModule {
	@POST
	@Produces("application/json")
	@Path("/add_post")
	public Status addPost(@FormParam("title") String title, @FormParam("body") String body, @FormParam("tags[]") List<Integer> tagIds) {
		TagModule tagModule = new TagModule();
		List<Tag> tags = tagModule.getByIds(tagIds);
		
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
}
