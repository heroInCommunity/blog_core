package name.heroin.community.utils.std;

import javax.servlet.http.HttpServletRequest;

import name.heroin.community.constants.MenuName;

public class Utils {
	public static boolean checkNumber(String numberStr) {
		try {
			Integer.parseInt(numberStr);
		}
		catch(NumberFormatException exc) {
			return false;
		}
		
		return true;
	}
	
	public static String permissionToUri(String permission) {
		return "/" + permission + ".jsp";
	}
	
	public static String getModuleName(String urlClassName) {
		String result = urlClassName.substring(0,1).toUpperCase() + urlClassName.substring(1, urlClassName.length() - 2);
		
		return "name.heroin.community.module." + result + "Module";
	}
	
	public static String getModelName(String urlClassName) {
		String result = urlClassName.substring(0,1).toUpperCase() + urlClassName.substring(1, urlClassName.length() - 2);
		
		return "name.heroin.community.module." + result;
	}
	
	public static String getBaseUrl(HttpServletRequest request) {		
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
	}
	
	public static String getApiClassName(HttpServletRequest request) {
		String[] apiUrlParts = request.getRequestURI().substring(5).split("/");
		
		return apiUrlParts[0];
	}
	
	public static String getApiMethodName(HttpServletRequest request) {
		String[] apiUrlParts = request.getRequestURI().substring(5).split("/");
		
		return apiUrlParts[1];
	}
	
	public static String[] getApiParameters(HttpServletRequest request) {
		String[] apiUrlParts = request.getRequestURI().substring(5).split("/");
		String[] result = new String[apiUrlParts.length - 2];
		
		System.arraycopy(apiUrlParts, 2, result, 0, apiUrlParts.length - 2);
		
		return result;
	}
	
	public static boolean isClassAndMethodExists(HttpServletRequest request) {
		return MenuName.contains("admin/" + getApiMethodName(request))
				&& MenuName.contains("admin/" + getApiClassName(request));
	}
}
