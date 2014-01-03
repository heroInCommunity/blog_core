package name.heroin.community;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import name.heroin.community.constants.MenuName;
import name.heroin.community.utils.std.Utils;

public class PermissionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String permissionsName = MenuName.ADMIN_PERMISSIONS.value();
	private String allPermissionsName = MenuName.ADMIN_ALL_PERMISSIONS.value();
	private String addPermissionName = MenuName.ADMIN_ADD_PERMISSION.value();
	private String editPermissionName = MenuName.ADMIN_EDIT_PERMISSION.value();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String servletPath = request.getServletPath().substring(1);
		
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
        
        dispatcher.forward(request, response);
	}
	
	private void addPermission(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(addPermissionName));
        
        dispatcher.forward(request, response);
	}
	
	private void editPermission(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();		
        RequestDispatcher dispatcher = context.getRequestDispatcher(Utils.permissionToUri(editPermissionName));
        
        dispatcher.forward(request, response);
	}
}

