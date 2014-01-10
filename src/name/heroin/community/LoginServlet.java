package name.heroin.community;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import name.heroin.community.constants.MenuName;
import name.heroin.community.model.User;
import name.heroin.community.module.UserModule;
import name.heroin.community.utils.std.PasswordHash;
import name.heroin.community.utils.std.Utils;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String adminLogin = MenuName.ADMIN_LOGIN.value();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(adminLogin));
        dispatcher.forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String email = request.getParameter("email");
		
		UserModule userModule = new UserModule();
		User user = userModule.getByEmail(email);
		if (user != null) {
			String password = request.getParameter("password");
			try {
				if (PasswordHash.validatePassword(password, user.getPassword())) {
					HttpSession session = request.getSession(true);
					session.setAttribute("user", user);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// do nothing? or log it?
			}
		}
		
	}
}
