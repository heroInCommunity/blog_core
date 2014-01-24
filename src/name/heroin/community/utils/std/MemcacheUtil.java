package name.heroin.community.utils.std;

import java.util.Map;
import java.util.logging.Level;

import name.heroin.community.utils.MemcacheServiceUtil;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class MemcacheUtil implements MemcacheServiceUtil {
	
	/**
	 * Get value Object from sync Memcache
	 * @param key
	 * @return Object
	 */
	public Object getValue(String key) {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    return syncCache.get(key); // read from cache
	}
	
	/**
	 * Put value Object to sync Memcache
	 * @param key
	 * @param object
	 */
	public void putValue(String key, Object object) {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    syncCache.put(key, object);
	}
	
	/**
	 * Put Map to Memcache
	 * @param key
	 * @param values
	 */
	public void putValuesAll(Map<?,?> values) {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    syncCache.putAll(values);
	}
}
