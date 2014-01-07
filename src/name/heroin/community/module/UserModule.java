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
import name.heroin.community.model.Role;
import name.heroin.community.model.User;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Path("/users/")
public class UserModule {
	@GET
	@Produces("application/json")
	@Path("/get_users")
	public Map<String, Object> getUsersTableData(
			@DefaultValue(Parameters.Constants.S_SEARCH) @QueryParam("sSearch") String search,
			@DefaultValue(Parameters.Constants.S_ECHO) @QueryParam("sEcho") Integer echo,
			@DefaultValue(Parameters.Constants.I_DISPLAY_START) @QueryParam("iDisplayStart") Integer start,
			@DefaultValue(Parameters.Constants.I_DISPLAY_LENGTH) @QueryParam("iDisplayLength") Integer length) {
		Map<String, Object> result = new HashMap<String, Object>();
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(User.class);

		if (search.length() >= Integer.parseInt(Parameters.Constants.MIN_LENGTH_TO_SEARCH)) {
			criteria.add(Restrictions.like("name", search + "%"));
		}
		
		criteria.setFirstResult(start);
		criteria.setMaxResults(length);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<User> users = criteria.list();

		Number usersCount = (Number) session.createCriteria(User.class)
				.setProjection(Projections.rowCount()).uniqueResult();

		session.getTransaction().commit();
		session.close();

		result.put("sEcho", echo);
		result.put("iTotalRecords", usersCount);
		result.put("iTotalDisplayRecords", usersCount);

		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		for (User user : users) {
			ArrayList<String> row = new ArrayList<String>();
			row.add("<input type='checkbox' class='ids' value='" + user.getId()
					+ "' />");
			row.add("<a href='edit_user?id=" + user.getId() + "'>" + user.getName() + "</a>");
			row.add(user.getEmail());
			row.add("<a href='edit_role?id=" + user.getRole().getId() + "'>" + user.getRole().getName() + "</a>");
			data.add(row);
		}

		result.put("aaData", data);

		return result;
	}
	
	@POST
	@Produces("application/json")
	@Path("/add_user")
	public Status addUser(@FormParam("userName") String userName,
			@FormParam("userEmail") String userEmail,
			@FormParam("userRole") Integer userRole) {
		User user = new User();
		user.setName(userName);
		user.setEmail(userEmail);
		
		RoleModule roleModule = new RoleModule();
		Role role = roleModule.getRoleById(userRole);
		user.setRole(role);

		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		session.save(user);

		session.getTransaction().commit();
		session.close();

		Status status = new Status();
		status.setText("success");

		return status;
	}
	
	@POST
	@Produces("application/json")
	@Path("/edit_user")
	public Status editUser(@FormParam("id") Integer userId, @FormParam("userName") String userName,
			@FormParam("userEmail") String userEmail, @FormParam("userRole") Integer userRole) {
		Status status = new Status();
		if (userId == null) {
			status.setText("error");
			return status;
		}
		
		User user = getById(userId);
		
		if (user != null) {
			user.setName(userName);
			user.setEmail(userEmail);
			
			RoleModule roleModule = new RoleModule();
			Role role = roleModule.getRoleById(userRole);
			user.setRole(role);
	
			SessionProvider sessionProvider = new SessionProviderHibernate();
	
			Session session = sessionProvider.getSession();
			session.beginTransaction();
	
			session.update(user);
	
			session.getTransaction().commit();
			session.close();
	
			status.setText("success");
		}
		else {
			status.setText("error");
		}

		return status;
	}
	
	@POST
	@Produces("application/json")
	@Path("/search")
	public List<User> search(@FormParam("term") String search) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.disjunction()
				.add(Restrictions.like("name", search + "%"))
				.add(Restrictions.like("email", search + "%"))
		);
		
		criteria.setMaxResults(10);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<User> users = criteria.list();		
		
		session.getTransaction().commit();
		session.close();
		
		if (users.isEmpty()) {
			return null;
		}
		return users;
	}
	
	public User getByEmail(String email) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		List<User> users = criteria.list();		
		
		session.getTransaction().commit();
		session.close();
		
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}
	
	public User getById(Integer id) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("id", id));
		List<User> users = criteria.list();		
		
		session.getTransaction().commit();
		session.close();
		
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}
}
