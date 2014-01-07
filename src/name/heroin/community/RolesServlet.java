package name.heroin.community;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import name.heroin.community.constants.AttributeName;
import name.heroin.community.constants.MenuName;
import name.heroin.community.model.Role;
import name.heroin.community.module.RoleModule;
import name.heroin.community.utils.std.Utils;

public class RolesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String rolesName = MenuName.ADMIN_ROLES.value();
	private String allRolesName = MenuName.ADMIN_ALL_ROLES.value();
	private String addRoleName = MenuName.ADMIN_ADD_ROLE.value();
	private String editRoleName = MenuName.ADMIN_EDIT_ROLE.value();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String servletPath = request.getServletPath().substring(1);
		
		if (servletPath.equals(rolesName) || servletPath.equals(allRolesName)) {
			roles(request, response);
		}
		else if (servletPath.equals(addRoleName)) {
			addRole(request, response);
		}
		else if (servletPath.equals(editRoleName)) {
			editRole(request, response);
		}
	}
	
	private void roles(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(rolesName));
		        
        dispatcher.forward(request, response);
	}
	
	private void addRole(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(addRoleName));
        
        dispatcher.forward(request, response);
	}
	
	private void editRole(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
		
		if (request.getParameter("id") != null && Utils.checkNumber(request.getParameter("id"))) {
			int roleId = Integer.parseInt(request.getParameter("id"));
			RoleModule roleModule = new RoleModule();
			Role role = roleModule.getById(roleId);
			
			if (role != null) {
				RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(editRoleName));
				request.setAttribute(AttributeName.ROLE.value(), role);
		        dispatcher.forward(request, response);
			}
		}
	}
}

