package com.dcw.app.rating.cache;

import com.dcw.app.rating.log.L;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/6
 */
public class BaseCache {

    /**
     * 数据库缓存
     */
    public String mCachePrefix = "cache_";
    private DBCache mDbCache;

    public BaseCache(int cacheSizeInBytes, int defaultCacheSize, int defaultCacheTime) {
        if (cacheSizeInBytes < defaultCacheSize) {
            L.w("DataCache#Specified cache size is too small: %d, use default: %d", cacheSizeInBytes, defaultCacheSize);
            cacheSizeInBytes = defaultCacheSize;
        }
        mDbCache = new DBCache(defaultCacheTime);
    }

    public DBCache getDbCache() {
        return mDbCache;
    }

    public void setCachePrefix(String prefix) {
        mCachePrefix = prefix;
    }

    public String getCacheKey(String key) {
        return mCachePrefix + key;
    }

    public void clearExpiredCache() {
        mDbCache.clearExpired();
    }
}
