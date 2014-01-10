package name.heroin.community;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import name.heroin.community.constants.MenuName;
import name.heroin.community.utils.std.Utils;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String adminIndex = MenuName.ADMIN_INDEX.value();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(adminIndex));
        dispatcher.forward(request, response);
	}
}

