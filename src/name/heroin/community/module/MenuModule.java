package name.heroin.community.module;

import name.heroin.community.model.MenuItem;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class MenuModule {
	
	public Map<MenuItem, List<MenuItem>> getMenuItems() {
		Map<MenuItem, List<MenuItem>> menus = new HashMap<MenuItem, List<MenuItem>>();
		
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteriaTop = session.createCriteria(MenuItem.class);
		criteriaTop.add(Restrictions.eq("depth", 0));
		List<MenuItem> topLevelMenus = criteriaTop.list();
		
		for (MenuItem menuItem : topLevelMenus) {
			Criteria criteriaSub = session.createCriteria(MenuItem.class);
			criteriaSub.add(Restrictions.eq("depth", 1));
			menus.put(menuItem, criteriaSub.list());
		}
		
		session.getTransaction().commit();
		session.close();
		
		return menus;
	}
	
	public MenuItem getMenuByUrl(String url) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(MenuItem.class);
		criteria.add(Restrictions.eq("url", url));
		List<MenuItem> menu = criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		return menu.get(0);
	}
	
	public MenuItem getMenuById(String id) {
		SessionProvider sessionProvider = new SessionProviderHibernate();
		
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(MenuItem.class);
		criteria.add(Restrictions.eq("id", id));
		List<MenuItem> menu = criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		return menu.get(0);
	}

}
