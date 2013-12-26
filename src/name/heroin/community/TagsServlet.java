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
public class TagsServlet extends HttpServlet {
	private String tagsName = MenuName.ADMIN_TAGS.value();
	private String allTagsName = MenuName.ADMIN_ALL_TAGS.value();
	private String addTagName = MenuName.ADMIN_ADD_TAG.value();
	private String editTagName = MenuName.ADMIN_EDIT_TAG.value();
	
	private MenuModule menuModule = new MenuModule();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String servletPath = request.getServletPath().substring(1);
		
		Map<MenuItem, List<MenuItem>> menus = menuModule.getMenuItems();
		request.setAttribute(AttributeName.MENUS.value(), menus);
		
		MenuItem topLevelMenu = menuModule.getMenuByUrl(tagsName);
		request.setAttribute(AttributeName.TOP_LEVEL_MENU_ID.value(), topLevelMenu.getId());
		
		if(servletPath.equals(tagsName) || servletPath.equals(allTagsName)) {
			tags(request, response);
		}
		else if(servletPath.equals(addTagName)) {
			addTag(request, response);
		}
		else if(servletPath.equals(editTagName)) {
			editTag(request, response);
		}
	}
	
	private void tags(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(tagsName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(allTagsName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
        
        dispatcher.forward(request, response);
	}
	
	private void addTag(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(addTagName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(addTagName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
		request.setAttribute(AttributeName.BASE_URL.value(), Utils.getBaseUrl(request));
        
        dispatcher.forward(request, response);
	}
	
	private void editTag(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(editTagName));
		
		MenuItem subLevelMenu = menuModule.getMenuByUrl(editTagName);
		request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
        
        dispatcher.forward(request, response);
	}
}

