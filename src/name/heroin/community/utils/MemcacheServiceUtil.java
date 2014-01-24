package name.heroin.community.utils;

public interface MemcacheServiceUtil {
	public Object getValue(String key);
	public void putValue(String key, Object object);
}
