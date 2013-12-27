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
    	HttpServletRequest servletRequest = (HttpServletRequest) servletRequestEvent.getServletRequest();
    	
    	if (servletRequest.getServletPath().contains("admin")) {
    		String[] urlParts = servletRequest.getServletPath().split("/");
    		String methodName = "admin/" + urlParts[2];
    		String className = getClassName(methodName);
    		
    		if (MenuName.contains(methodName) && MenuName.contains(className) ) {
    			Map<MenuItem, List<MenuItem>> menus = menuModule.getMenuItems();
    			servletRequest.setAttribute(AttributeName.MENUS.value(), menus);
    			
    			MenuItem topLevelMenu = menuModule.getMenuByUrl(className);
    			servletRequest.setAttribute(AttributeName.TOP_LEVEL_MENU_ID.value(), topLevelMenu.getId());
    			
    			MenuItem subLevelMenu = menuModule.getMenuByUrl(methodName);
    			servletRequest.setAttribute(AttributeName.SUB_LEVEL_MENU_ID.value(), subLevelMenu.getId());
    			
    			servletRequest.setAttribute(AttributeName.BASE_URL.value(), Utils.getBaseUrl(servletRequest));
    		}
    	}
    }
    
    private String getClassName(String methodName) {
    	String result;
    	
    	if (methodName.contains("all_")) {
    		result = methodName.replaceAll("all_", "") + "s";
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