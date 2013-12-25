package name.heroin.community.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import name.heroin.community.module.MenuModule;
import name.heroin.community.utils.SessionProvider;
import name.heroin.community.utils.std.SessionProviderHibernate;

import org.hibernate.Session;

class HibernateTest {

	public static void main(String[] args) {
		Post post = new Post();
		post.setTitle("Hi there!");
		post.setBody("I love your site.");
		post.setTimestamp(new Date());
		
		Comment comBur = new Comment();
		comBur.setName("buran");
		post.getComments().add(comBur);
		
		Tag tagBe = new Tag();
		tagBe.setName("beeeeee");
		post.getTags().add(tagBe);
		
		MenuItem topLevelMenu = new MenuItem();
		topLevelMenu.setName("Permissions");
		topLevelMenu.setDepth(0);
		topLevelMenu.setMenuOrder(1);
		topLevelMenu.setParentId(0);
		topLevelMenu.setUrl("admin/permissions");
		
		MenuItem subLevelMenuAdd = new MenuItem();
		subLevelMenuAdd.setName("Add permission");
		subLevelMenuAdd.setDepth(1);
		subLevelMenuAdd.setMenuOrder(2);
		subLevelMenuAdd.setUrl("admin/add_permission");
		
		MenuItem subLevelMenuAll = new MenuItem();
		subLevelMenuAll.setName("View permissions");
		subLevelMenuAll.setDepth(1);
		subLevelMenuAll.setMenuOrder(1);
		subLevelMenuAll.setUrl("admin/all_permission");
		
		SessionProvider sessionProvider = new SessionProviderHibernate();
		Session session = sessionProvider.getSession();
		session.beginTransaction();
		
		session.save(post);
		session.save(comBur);
		session.save(tagBe);
		
		session.save(topLevelMenu);
		subLevelMenuAdd.setParentId(topLevelMenu.getId());
		subLevelMenuAll.setParentId(topLevelMenu.getId());
		
		session.save(subLevelMenuAll);
		session.save(subLevelMenuAdd);
		
		session.getTransaction().commit();
		session.close();
		
		MenuModule menuModule = new MenuModule();
		Map<MenuItem, List<MenuItem>> menus = menuModule.getMenuItems();
		for (MenuItem menuItem : menus.keySet()) {
			System.out.println(menuItem.getName());
			for (MenuItem mItem : menus.get(menuItem)) {
				System.out.println(mItem.getName());
			}
		}
	}

}
