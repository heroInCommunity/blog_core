package name.heroin.community.filters;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import name.heroin.community.constants.MenuName;
import name.heroin.community.model.User;
import name.heroin.community.utils.std.Utils;

public class AdminFilter implements javax.servlet.Filter {
	public FilterConfig filterConfig;
	private String adminLogin = MenuName.ADMIN_LOGIN.value();

	public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain)
			throws java.io.IOException, javax.servlet.ServletException {
		if (( (HttpServletRequest) request).getServletPath().contains(adminLogin)) {
			chain.doFilter(request, response);
		}
		else {
			HttpSession session = ( (HttpServletRequest) request).getSession(true);
			
			if (( (User) session.getAttribute("user")) == null) {
				( (HttpServletResponse) response).sendRedirect(Utils.permissionToUri(adminLogin));
			}
			chain.doFilter(request, response);
		}
	}

	public void init(final FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
	}
}