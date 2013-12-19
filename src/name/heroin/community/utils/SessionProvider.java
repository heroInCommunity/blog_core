package name.heroin.community.utils;

import org.hibernate.Session;

public interface SessionProvider {
	public Session getSession();
}
