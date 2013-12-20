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

@SuppressWarnings("serial")
public class PermissionsServlet extends HttpServlet {
	private String permissionsName = MenuName.ADMIN_PERMISSIONS.value();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(permissionsName));
        
        MenuModule menuModule = new MenuModule();
		Map<MenuItem, List<MenuItem>> menus = menuModule.getMenuItems();
		request.setAttribute(AttributeName.MENUS.value(), menus);
		
		MenuItem topLevelMenu = menuModule.getMenuByUrl(permissionsName);
		request.setAttribute(AttributeName.TOP_LEVEL_MENU_ID.value(), topLevelMenu.getId());
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(permissionsName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
        
        dispatcher.forward(request, response);
	}
}

