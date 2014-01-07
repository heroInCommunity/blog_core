package name.heroin.community.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import name.heroin.community.constants.Parameters;
import name.heroin.community.model.Permission;
import name.heroin.community.model.Tag;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;
import name.heroin.community.utils.std.Status;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/permissions/")
public class PermissionModule {
	@GET
	@Produces("application/json")
	@Path("/get_permissions")
	public Map<String, Object> getPermissionsTableData(
			@DefaultValue(Parameters.Constants.S_SEARCH) @QueryParam("sSearch") String search,
			@DefaultValue(Parameters.Constants.S_ECHO) @QueryParam("sEcho") Integer echo,
			@DefaultValue(Parameters.Constants.I_DISPLAY_START) @QueryParam("iDisplayStart") Integer start,
			@DefaultValue(Parameters.Constants.I_DISPLAY_LENGTH) @QueryParam("iDisplayLength") Integer length) {
		Map<String, Object> result = new HashMap<String, Object>();
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Permission.class);

		if (search.length() >= Integer
				.parseInt(Parameters.Constants.MIN_LENGTH_TO_SEARCH)) {
			criteria.add(Restrictions.like("name", search + "%"));
		}

		criteria.setFirstResult(start);
		criteria.setMaxResults(length);
		List<Permission> permissions = criteria.list();

		Number permissionsCount = (Number) session
				.createCriteria(Permission.class)
				.setProjection(Projections.rowCount()).uniqueResult();

		session.getTransaction().commit();
		session.close();

		result.put("sEcho", echo);
		result.put("iTotalRecords", permissionsCount);
		result.put("iTotalDisplayRecords", permissionsCount);

		List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		for (Permission permission : permissions) {
			ArrayList<String> row = new ArrayList<String>();
			row.add("<input type='checkbox' class='ids' value='" + permission.getId() + "' />");
			row.add("<a href='edit_permission?id=" + permission.getId() + "'>" + permission.getName() + "</a>");
			data.add(row);
		}

		result.put("aaData", data);

		return result;
	}

	@POST
	@Produces("application/json")
	@Path("/search")
	public List<Permission> search(@FormParam("permissionName") String search) {
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Permission.class);
		criteria.add(Restrictions.like("name", search + "%"));

		criteria.setMaxResults(10);
		List<Permission> permissions = criteria.list();

		session.getTransaction().commit();
		session.close();

		if (permissions.isEmpty()) {
			return null;
		}
		return permissions;
	}

	@POST
	@Produces("application/json")
	@Path("/add_permission")
	public Status addPermission(
			@FormParam("permissionName") String permissionName) {
		Permission permission = new Permission();
		permission.setName(permissionName);

		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		session.save(permission);

		session.getTransaction().commit();
		session.close();

		Status status = new Status();
		status.setText("success");

		return status;
	}

	@POST
	@Produces("application/json")
	@Path("/edit_permission")
	public Status editPermission(@FormParam("id") Integer permissionId,
			@FormParam("permissionName") String permissionName) {
		Status status = new Status();
		if (permissionId == null) {
			status.setText("error");
			return status;
		}

		Permission permission = getById(permissionId);

		if (permission != null) {
			permission.setName(permissionName);

			SessionProvider sessionProvider = new SessionProviderHibernate();

			Session session = sessionProvider.getSession();
			session.beginTransaction();

			session.update(permission);

			session.getTransaction().commit();
			session.close();

			status.setText("success");
		}
		else {
			status.setText("error");
		}

		return status;
	}

	public Permission getById(int permissionId) {
		SessionProvider sessionProvider = new SessionProviderHibernate();

		Session session = sessionProvider.getSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Permission.class);
		criteria.add(Restrictions.eq("id", permissionId));

		List<Permission> permissions = criteria.list();

		session.getTransaction().commit();
		session.close();

		if (permissions.isEmpty()) {
			return null;
		}
		return permissions.get(0);
	}
}
