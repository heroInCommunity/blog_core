package name.heroin.community;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.servlet.http.*;

import name.heroin.community.constants.MenuName;

@SuppressWarnings("serial")
public class AjaxApiServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/plain");
		
		if(Utils.isClassAndMethodExists(request)) {
			String className = Utils.getApiClassName(request);
			try {
				Class clazz = Class.forName(Utils.getModuleName(className));
				//Constructor<?> ctor = clazz.getConstructor();
				Method method = clazz.getDeclaredMethod(Utils.getApiMethodName(request), Class.forName(Utils.getModelName(className)));
				//method.invoke(obj, args)
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

