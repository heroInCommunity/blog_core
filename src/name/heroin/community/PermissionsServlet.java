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
public class PermissionsServlet extends HttpServlet {
	private String permissionsName = MenuName.ADMIN_PERMISSIONS.value();
	private String allPermissionsName = MenuName.ADMIN_ALL_PERMISSIONS.value();
	private String addPermissionName = MenuName.ADMIN_ADD_PERMISSION.value();
	private String editPermissionName = MenuName.ADMIN_EDIT_PERMISSION.value();
	
	private MenuModule menuModule = new MenuModule();
	private RoleModule roleModule = new RoleModule();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String servletPath = request.getServletPath().substring(1);
		
		Map<MenuItem, List<MenuItem>> menus = menuModule.getMenuItems();
		request.setAttribute(AttributeName.MENUS.value(), menus);
		
		MenuItem topLevelMenu = menuModule.getMenuByUrl(permissionsName);
		request.setAttribute(AttributeName.TOP_LEVEL_MENU_ID.value(), topLevelMenu.getId());
		
		if(servletPath.equals(permissionsName) || servletPath.equals(allPermissionsName)) {
			permissions(request, response);
		}
		else if(servletPath.equals(addPermissionName)) {
			addPermission(request, response);
		}
		else if(servletPath.equals(editPermissionName)) {
			editPermission(request, response);
		}
	}
	
	private void permissions(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(permissionsName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(allPermissionsName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
        
        dispatcher.forward(request, response);
	}
	
	private void addPermission(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(addPermissionName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(addPermissionName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
		request.setAttribute(AttributeName.LIST_OF_ROLES.value(), roleModule.getRoles());
		request.setAttribute(AttributeName.BASE_URL.value(), Utils.getBaseUrl(request));
        
        dispatcher.forward(request, response);
	}
	
	private void editPermission(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(editPermissionName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(editPermissionName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
        
        dispatcher.forward(request, response);
	}
}

