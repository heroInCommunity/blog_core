package name.heroin.community;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import name.heroin.community.model.MenuItem;
import name.heroin.community.module.MenuModule;

@SuppressWarnings("serial")
public class PermissionsServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {		
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/admin/permissions.jsp");
        
        MenuModule menuModule = new MenuModule();
		Map<MenuItem, List<MenuItem>> menus = menuModule.getMenuItems();
		request.setAttribute("menus", menus);
		
		MenuItem topLevelMenu = menuModule.getMenuByUrl("admin/permissions");
		request.setAttribute("topLevelMenuId", topLevelMenu.getId());
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl("admin/all_permissions");
		request.setAttribute("subLevelMenuId", subLevelMenu.getId());
        
        dispatcher.forward(request, response);
	}
}

