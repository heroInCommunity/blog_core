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
public class RolesServlet extends HttpServlet {
	private String rolesName = MenuName.ADMIN_ROLES.value();
	private String allRolesName = MenuName.ADMIN_ALL_ROLES.value();
	private String addRoleName = MenuName.ADMIN_ADD_ROLE.value();
	private String editRoleName = MenuName.ADMIN_EDIT_ROLE.value();
	
	private MenuModule menuModule = new MenuModule();
	private RoleModule roleModule = new RoleModule();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String servletPath = request.getServletPath().substring(1);
		
		Map<MenuItem, List<MenuItem>> menus = menuModule.getMenuItems();
		request.setAttribute(AttributeName.MENUS.value(), menus);
		
		MenuItem topLevelMenu = menuModule.getMenuByUrl(rolesName);
		request.setAttribute(AttributeName.TOP_LEVEL_MENU_ID.value(), topLevelMenu.getId());
		
		if(servletPath.equals(rolesName) || servletPath.equals(allRolesName)) {
			roles(request, response);
		}
		else if(servletPath.equals(addRoleName)) {
			addRole(request, response);
		}
		else if(servletPath.equals(editRoleName)) {
			editRole(request, response);
		}
	}
	
	private void roles(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(rolesName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(allRolesName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
        
        dispatcher.forward(request, response);
	}
	
	private void addRole(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(addRoleName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(addRoleName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
		request.setAttribute(AttributeName.LIST_OF_ROLES.value(), roleModule.getRoles());
		request.setAttribute(AttributeName.BASE_URL.value(), Utils.getBaseUrl(request));
        
        dispatcher.forward(request, response);
	}
	
	private void editRole(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(editRoleName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(editRoleName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
        
        dispatcher.forward(request, response);
	}
}

