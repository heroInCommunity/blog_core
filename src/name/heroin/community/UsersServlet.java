package name.heroin.community;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import name.heroin.community.constants.AttributeName;
import name.heroin.community.constants.MenuName;
import name.heroin.community.model.MenuItem;
import name.heroin.community.module.MenuModule;
import name.heroin.community.module.RoleModule;

@SuppressWarnings("serial")
public class UsersServlet extends HttpServlet {
	private String usersName = MenuName.ADMIN_USERS.value();
	private String allUsersName = MenuName.ADMIN_ALL_USERS.value();
	private String addUserName = MenuName.ADMIN_ADD_USER.value();
	private String editUserName = MenuName.ADMIN_EDIT_USER.value();
	
	private MenuModule menuModule = new MenuModule();
	private RoleModule roleModule = new RoleModule();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String servletPath = request.getServletPath().substring(1);
		
		Map<MenuItem, List<MenuItem>> menus = menuModule.getMenuItems();
		request.setAttribute(AttributeName.MENUS.value(), menus);
		
		MenuItem topLevelMenu = menuModule.getMenuByUrl(usersName);
		request.setAttribute(AttributeName.TOP_LEVEL_MENU_ID.value(), topLevelMenu.getId());
		
		if(servletPath.equals(usersName) || servletPath.equals(allUsersName)) {
			users(request, response);
		}
		else if(servletPath.equals(addUserName)) {
			addUser(request, response);
		}
		else if(servletPath.equals(editUserName)) {
			editUser(request, response);
		}
	}
	
	private void users(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(usersName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(allUsersName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
        
        dispatcher.forward(request, response);
	}
	
	private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(addUserName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(addUserName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
		request.setAttribute(AttributeName.LIST_OF_ROLES.value(), roleModule.getRoles());
		request.setAttribute(AttributeName.BASE_URL.value(), Utils.getBaseUrl(request));
        
        dispatcher.forward(request, response);
	}
	
	private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(editUserName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(editUserName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
        
        dispatcher.forward(request, response);
	}
}

