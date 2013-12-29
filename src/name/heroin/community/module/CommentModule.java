package name.heroin.community.module;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import name.heroin.community.model.Comment;
import name.heroin.community.model.User;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import org.hibernate.Session;

@Path("/comments/")
public class CommentModule {
	@POST
	@Produces("application/json")
	@Path("/add_comment")
	public Status addComment(@FormParam("userId") Integer userId, @FormParam("commentText") String commentText) {
		UserModule userModule = new UserModule();		
		User user = userModule.getById(userId);
		
		Comment comment = new Comment();
		comment.setCommentText(commentText);
		comment.setUser(user);
		comment.setIsVisible(true);

		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		session.save(comment);

		session.getTransaction().commit();
		session.close();

		Status status = new Status();
		status.setText("success");

		return status;
	}
}
