package name.heroin.community;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import name.heroin.community.constants.AttributeName;
import name.heroin.community.model.Post;
import name.heroin.community.model.SlimPost;
import name.heroin.community.module.PostModule;
import name.heroin.community.utils.std.Utils;

public class MainFrontendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PostModule postModule = new PostModule();
		Post latestPost = postModule.getLatestPost();
		
		List<SlimPost> posts = postModule.getPostTitles("");
		
		if(latestPost != null && posts != null) {
			ServletContext context = getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri("main"));
        	request.setAttribute(AttributeName.LATEST_POST.value(), latestPost);
        	request.setAttribute(AttributeName.POST_TITLES.value(), posts);
            dispatcher.forward(request, response);
		}
	}
}
	
