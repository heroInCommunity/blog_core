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
import name.heroin.community.model.Comment;
import name.heroin.community.model.SlimPost;
import name.heroin.community.model.User;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Path("/comments/")
public class CommentModule {
	@POST
	@Produces("application/json")
	@Path("/add_comment")
	public Status addComment(@FormParam("userId") Integer userId, @FormParam("postId") Integer postId, @FormParam("commentText") String commentText) {
		UserModule userModule = new UserModule();		
		User user = userModule.getById(userId);
		
		Comment comment = new Comment();
		comment.setCommentText(commentText);
		comment.setUser(user);
		
		SlimPost slimPost = new SlimPost();
		slimPost.setId(postId);
		
		comment.setPost(slimPost);
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

	@GET
	@Produces("application/json")
	@Path("/get_comments")
	public Map<String, Object> getCommentsTableData(
			@DefaultValue(Parameters.Constants.S_SEARCH) @QueryParam("sSearch") String search,
			@DefaultValue(Parameters.Constants.S_ECHO) @QueryParam("sEcho") Integer echo,
			@DefaultValue(Parameters.Constants.I_DISPLAY_START) @QueryParam("iDisplayStart") Integer start,
			@DefaultValue(Parameters.Constants.I_DISPLAY_LENGTH) @QueryParam("iDisplayLength") Integer length) {
		Map<String, Object> result = new HashMap<String, Object>();
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Comment.class);

		if (search.length() >= Integer.parseInt(Parameters.Constants.MIN_LENGTH_TO_SEARCH)) {
			criteria.add(Restrictions.like("commentText", search + "%"));
		}
		
		criteria.setFirstResult(start);
		criteria.setMaxResults(length);
		List<Comment> comments = criteria.list();

		Number commentsCount = (Number) session.createCriteria(Comment.class)
				.setProjection(Projections.rowCount()).uniqueResult();

		session.getTransaction().commit();
		session.close();

		result.put("sEcho", echo);
		result.put("iTotalRecords", commentsCount);
		result.put("iTotalDisplayRecords", commentsCount);
		
		//setPosts(comments);

		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		for (Comment comment : comments) {
			ArrayList<String> row = new ArrayList<String>();
			row.add("<input type='checkbox' class='ids' value='" + comment.getId()
					+ "' />");
			row.add(comment.getUser().getName() + " : " + comment.getUser().getEmail());
			row.add(comment.getPost().getTitle());
			row.add(comment.getCommentText());
			row.add(comment.getIsVisible() ? "Visible" : "Invisible");
			data.add(row);
		}

		result.put("aaData", data);

		return result;
	}
	
	public List<Comment> setPosts(List<Comment> comments) {
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		for(Comment comment : comments) {
//			Criteria criteria = session.createCriteria(Comment.class);
//			criteria.add(Restrictions.eq("id", comment.getId()));
//			//criteria.setProjection(Projections.property("post"));
//			List<Comment> coms = criteria.list();
//			comment.setPost(coms.get(0).getPost());
			List<SlimPost> posts = (List<SlimPost>) session.getNamedQuery("getPostForComment").setString("id", new Integer(comment.getId()).toString()).list();
			comment.setPost(posts.get(0));
		}

		session.getTransaction().commit();
		session.close();
		
		return comments;
	}
}
