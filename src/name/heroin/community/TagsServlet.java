package name.heroin.community;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import name.heroin.community.constants.MenuName;
import name.heroin.community.utils.std.Utils;

public class TagsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String tagsName = MenuName.ADMIN_TAGS.value();
	private String allTagsName = MenuName.ADMIN_ALL_TAGS.value();
	private String addTagName = MenuName.ADMIN_ADD_TAG.value();
	private String editTagName = MenuName.ADMIN_EDIT_TAG.value();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String servletPath = request.getServletPath().substring(1);
		
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
        
        dispatcher.forward(request, response);
	}
	
	private void addTag(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(addTagName));
        
        dispatcher.forward(request, response);
	}
	
	private void editTag(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(editTagName));
		        
        dispatcher.forward(request, response);
	}
}

