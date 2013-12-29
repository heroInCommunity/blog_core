package name.heroin.community;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import name.heroin.community.constants.MenuName;
import name.heroin.community.utils.std.Utils;

public class PostsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String postsName = MenuName.ADMIN_POSTS.value();
	private String allPostsName = MenuName.ADMIN_ALL_POSTS.value();
	private String addPostName = MenuName.ADMIN_ADD_POST.value();
	private String editPostName = MenuName.ADMIN_EDIT_POST.value();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String servletPath = request.getServletPath().substring(1);
		
		if(servletPath.equals(postsName) || servletPath.equals(allPostsName)) {
			posts(request, response);
		}
		else if(servletPath.equals(addPostName)) {
			addPost(request, response);
		}
		else if(servletPath.equals(editPostName)) {
			editPost(request, response);
		}
	}
	
	private void posts(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(postsName));
        
        dispatcher.forward(request, response);
	}
	
	private void addPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(addPostName));
        
        dispatcher.forward(request, response);
	}
	
	private void editPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(editPostName));
		        
        dispatcher.forward(request, response);
	}
}

