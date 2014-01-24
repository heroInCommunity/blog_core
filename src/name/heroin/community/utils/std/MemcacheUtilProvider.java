package name.heroin.community.utils.std;

import name.heroin.community.utils.MemcacheServiceUtil;

public class MemcacheUtilProvider {
	public static MemcacheServiceUtil getMemcacheUtil() {
		return new MemcacheUtil();
	}
}
