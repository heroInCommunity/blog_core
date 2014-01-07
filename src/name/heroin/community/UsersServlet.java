package name.heroin.community;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import name.heroin.community.constants.AttributeName;
import name.heroin.community.constants.MenuName;
import name.heroin.community.model.User;
import name.heroin.community.module.RoleModule;
import name.heroin.community.module.UserModule;
import name.heroin.community.utils.std.Utils;

public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String usersName = MenuName.ADMIN_USERS.value();
	private String allUsersName = MenuName.ADMIN_ALL_USERS.value();
	private String addUserName = MenuName.ADMIN_ADD_USER.value();
	private String editUserName = MenuName.ADMIN_EDIT_USER.value();
	
	private RoleModule roleModule = new RoleModule();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String servletPath = request.getServletPath().substring(1);
		
		if (servletPath.equals(usersName) || servletPath.equals(allUsersName)) {
			users(request, response);
		}
		else if (servletPath.equals(addUserName)) {
			addUser(request, response);
		}
		else if (servletPath.equals(editUserName)) {
			editUser(request, response);
		}
	}
	
	private void users(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(usersName));
        
        dispatcher.forward(request, response);
	}
	
	private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(addUserName));
		
		request.setAttribute(AttributeName.LIST_OF_ROLES.value(), roleModule.getRoles());
        
        dispatcher.forward(request, response);
	}
	
	private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
		
		if (request.getParameter("id") != null && Utils.checkNumber(request.getParameter("id"))) {
			int userId = Integer.parseInt(request.getParameter("id"));
			UserModule userModule = new UserModule();
			User user = userModule.getById(userId);
			
			if (user != null) {
				RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(editUserName));
				
				request.setAttribute(AttributeName.LIST_OF_ROLES.value(), roleModule.getRoles());
				request.setAttribute(AttributeName.USER.value(), user);
				
		        dispatcher.forward(request, response);
			}
		}
	}
}

