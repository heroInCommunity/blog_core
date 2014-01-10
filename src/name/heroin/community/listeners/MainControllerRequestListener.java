package name.heroin.community.listeners;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import name.heroin.community.constants.AttributeName;
import name.heroin.community.constants.MenuName;
import name.heroin.community.model.MenuItem;
import name.heroin.community.module.MenuModule;
import name.heroin.community.utils.std.Utils;


public class MainControllerRequestListener implements ServletRequestListener {
	
	private MenuModule menuModule = new MenuModule();
 
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {}
 
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
    	HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
    	
    	if (isServletUrl(request)) {
    		request.setAttribute(AttributeName.BASE_URL.value(), Utils.getBaseUrl(request));
    	}
    	
    	if (request.getServletPath().contains("admin/")) {
    		String[] urlParts = request.getServletPath().split("/");
    		String methodName = "admin/" + urlParts[2];
    		String className = getClassName(methodName);
    		
    		if (MenuName.contains(methodName) && MenuName.contains(className) ) {
    			Map<MenuItem, List<MenuItem>> menus = menuModule.getMenuItems();
    			request.setAttribute(AttributeName.MENUS.value(), menus);
    			
    			if (methodName.contains("index")) {
    				return;
    			}
    			
    			MenuItem topLevelMenu = menuModule.getMenuByUrl(className);
    			request.setAttribute(AttributeName.TOP_LEVEL_MENU_ID.value(), topLevelMenu.getId());
    			
    			if (!methodName.contains("edit")) {
    				MenuItem subLevelMenu = menuModule.getMenuByUrl(methodName);
        			request.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
    			}
    		}
    	}
    }
    
    private Boolean isServletUrl(HttpServletRequest servletRequest) {
    	if (!servletRequest.getServletPath().contains("js/") && !servletRequest.getServletPath().contains("css/") 
    		&& !servletRequest.getServletPath().contains("images/")) {
    		return true;
    	}
    	
    	return false;
    }
    
    private String getClassName(String methodName) {
    	String result;
    	
    	if (methodName.contains("all_")) {
    		result = methodName.replaceAll("all_", "");
    	}
    	else if (methodName.contains("add_")) {
    		result = methodName.replaceAll("add_", "") + "s";
    	}
    	else if (methodName.contains("edit_")) {
    		result = methodName.replaceAll("edit_", "") + "s";
    	}
    	else {
    		result = methodName;
    	}
    	
    	return result;
    }
     
}