package name.heroin.community.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import name.heroin.community.constants.Parameters;
import name.heroin.community.model.Permission;
import name.heroin.community.model.Role;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

@Path("/roles/")
public class RoleModule {
	public List<Role> getRoles() {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Role.class);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<Role> roles = criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		return roles;
	}

	@GET
	@Produces("application/json")
	@Path("/get_roles")
	public Map<String, Object> getRolesTableData(
			@DefaultValue(Parameters.Constants.S_SEARCH) @QueryParam("sSearch") String search,
			@DefaultValue(Parameters.Constants.S_ECHO) @QueryParam("sEcho") Integer echo,
			@DefaultValue(Parameters.Constants.I_DISPLAY_START) @QueryParam("iDisplayStart") Integer start,
			@DefaultValue(Parameters.Constants.I_DISPLAY_LENGTH) @QueryParam("iDisplayLength") Integer length) {
		Map<String, Object> result = new HashMap<String, Object>();
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Role.class);

		if (search.length() >= Integer.parseInt(Parameters.Constants.MIN_LENGTH_TO_SEARCH)) {
			criteria.add(Restrictions.like("name", search + "%"));
		}
		
		criteria.setFirstResult(start);
		criteria.setMaxResults(length);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<Role> roles = criteria.list();

		Number rolesCount = (Number) session.createCriteria(Role.class)
				.setProjection(Projections.rowCount()).uniqueResult();

		session.getTransaction().commit();
		session.close();

		result.put("sEcho", echo);
		result.put("iTotalRecords", rolesCount);
		result.put("iTotalDisplayRecords", rolesCount);

		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		for (Role role : roles) {
			ArrayList<String> row = new ArrayList<String>();
			row.add("<input type='checkbox' class='ids' value='" + role.getId()
					+ "' />");
			row.add("<a href='edit_role?id=" + role.getId() + "'>" + role.getName() + "</a>");
			row.add(getStringPermissions(role));
			data.add(row);
		}

		result.put("aaData", data);

		return result;
	}
	
	private String getStringPermissions(Role role) {
		StringBuffer stringBuffer = new StringBuffer();
		
		int iter = 0;
		for (Permission permission : role.getPermissions()) {
			if (iter != 0) {
				stringBuffer.append(", ");
			}
			
			stringBuffer.append("<a href='edit_permission?id=" + permission.getId() + "'>" + permission.getName() + "</a>");
			iter++;
		}
		
		return stringBuffer.toString();
	}
	
	public Role getRoleById(Integer id) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Role.class);
		criteria.add(Restrictions.eq("id", id));
		List<Role> roles = criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		return roles.get(0);
	}
	
	@POST
	@Produces("application/json")
	@Path("/add_role")
	public Status addRole(@FormParam("roleName") String roleName, @FormParam("permissions[]") List<Integer> permissionIds) {
		Role role = new Role();
		role.setName(roleName);
		
		Set<Permission> permissions = new HashSet<Permission>();
		for (Integer id : permissionIds) {
			Permission permission = new Permission();
			permission.setId(id);
			permissions.add(permission);
		}
		role.setPermissions(permissions);
				
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		session.save(role);
		
		session.getTransaction().commit();
		session.close();
		
		Status status = new Status();
		status.setText("success");
		
		return status;
	}
	
	@POST
	@Produces("application/json")
	@Path("/edit_role")
	public Status editRole(@FormParam("id") Integer roleId, @FormParam("roleName") String roleName, @FormParam("permissions[]") List<Integer> permissionIds) {
		Status status = new Status();
		if (roleId == null) {
			status.setText("error");
			return status;
		}
		
		Role role = getById(roleId);
		if (role != null) {
			role.setName(roleName);
			
			Set<Permission> permissions = new HashSet<Permission>();
			for (Integer id : permissionIds) {
				Permission permission = new Permission();
				permission.setId(id);
				permissions.add(permission);
			}
			role.setPermissions(permissions);
					
			SessionProvider sessionProvider = new SessionProviderHibernate();
			
			Session session = sessionProvider.getSession();
			session.beginTransaction();
			
			session.update(role);
			
			session.getTransaction().commit();
			session.close();
			
			status.setText("success");
		}
		else {
			status.setText("error");
		}
		
		return status;
	}

	public Role getById(int roleId) {
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Role.class);
		criteria.add(Restrictions.eq("id", roleId));

		List<Role> roles = criteria.list();

		session.getTransaction().commit();
		session.close();

		if (roles.isEmpty()) {
			return null;
		}
		return roles.get(0);
	}
}
