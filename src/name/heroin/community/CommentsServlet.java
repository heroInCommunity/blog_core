package name.heroin.community;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import name.heroin.community.constants.MenuName;
import name.heroin.community.utils.std.Utils;

public class CommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String commentsName = MenuName.ADMIN_COMMENTS.value();
	private String allCommentsName = MenuName.ADMIN_ALL_COMMENTS.value();
	private String addCommentName = MenuName.ADMIN_ADD_COMMENT.value();
	private String editCommentName = MenuName.ADMIN_EDIT_COMMENT.value();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String servletPath = request.getServletPath().substring(1);
		
		if(servletPath.equals(commentsName) || servletPath.equals(allCommentsName)) {
			comments(request, response);
		}
		else if(servletPath.equals(addCommentName)) {
			addComment(request, response);
		}
		else if(servletPath.equals(editCommentName)) {
			editComment(request, response);
		}
	}
	
	private void comments(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(commentsName));
        
        dispatcher.forward(request, response);
	}
	
	private void addComment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(addCommentName));
        
        dispatcher.forward(request, response);
	}
	
	private void editComment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(editCommentName));
		        
        dispatcher.forward(request, response);
	}
}

