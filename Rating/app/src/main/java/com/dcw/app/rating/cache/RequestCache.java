package com.dcw.app.rating.cache;

import android.support.v4.util.LruCache;

import com.dcw.app.rating.db.bean.Cache;
import com.dcw.app.rating.log.L;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/6
 */
public class RequestCache extends BaseCache {

    private LruCache<String, Map<String, Cache>> mLruCache;

    /**
     * 默认缓存大小100k
     */
    public final static int DEFAULT_CACHE_SIZE = 64;

    /**
     * 延迟数据库删除缓存时间，使数据库缓存有效期大于内存缓存, 2天
     */
    public final static int DEFAULT_CACHE_TIME = 86400 * 2;

    public RequestCache(int cacheSizeInBytes) {
        super(cacheSizeInBytes, DEFAULT_CACHE_SIZE, DEFAULT_CACHE_TIME);
        setCachePrefix(RequestCache.class.getSimpleName());
        mLruCache = new LruCache<String, Map<String, Cache>>(cacheSizeInBytes);
    }

    public LruCache<String, Map<String, Cache>> getLruCache() {
        return mLruCache;
    }

    /**
     * 保存数据到LruCache，不保存到数据库
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param seconds 缓存时间，单位秒
     */
    public void put(String key, String value, int groupId, int seconds) {
        put(key, value, seconds, groupId, false);
    }

    /**
     * 保存数据到LruCache，根据saveData可以指定是否保存到数据库
     *
     * @param key      缓存key
     * @param value    缓存值
     * @param seconds  缓存时间，单位秒
     * @param saveData true则保存到数据库，否则不保存
     */
    public void put(String key, String value, int seconds, int groupId, boolean saveData) {
        long expireTime = System.currentTimeMillis() / 1000 + seconds;
//        putIntoLru(key, new Cache(groupId, value, expireTime, Cache.CACHE_FROM_MEMORY));
        putIntoLru(key, value, expireTime, groupId);

        if (saveData) {
            L.d("DataCache#Save to database: " + key);
            getDbCache().save(new Cache(groupId, key, value, expireTime));
        } else {
            L.d("DataCache#Do not save to database: " + key);
        }
    }

    /**
     * 通过key值获取缓存值
     * 1.当命中内存时，如果缓存失效则返回null
     * 2.当命中数据库时，不管缓存失效时间(保证数据库数据没那么快失效)
     *
     * @param key 缓存key
     * @return 缓存值
     */
    public Cache get(String key, int groupId) {
        return get(key, groupId, true);
    }

    public Cache get(String key, int groupId, boolean isCareExpire) {
        Cache entry = getFromLru(key, groupId);
        // 缓存获取
        if (entry != null) {
            if (isCareExpire && entry.getExpireTime() < System.currentTimeMillis() / 1000) {
                getLruCache().remove(key);
                L.d("DataCache#The Key(" + key + ") of value is expire.");
                return null;
            }
            L.d("DataCache#Hit Memory Key: " + key);
            entry.type = Cache.CACHE_FROM_MEMORY;
            return entry;
        }
        // 数据库获取
        entry = getDbCache().get(key);
        if (entry != null) {
            if (entry.getExpireTime() > System.currentTimeMillis() / 1000) {
                putIntoLru(key, entry.getValue(), entry.getExpireTime(), groupId);
            }
            L.d("DataCache#Hit Database Key: " + key);
            entry.type = Cache.CACHE_FROM_DATABASE;
            return entry;
        }
        L.d("DataCache#No Hit Cache Key: " + key);
        return null;
    }

    /**
     * 根据key值获取缓存值
     * 1.当命中缓存时，如果缓存失效则返回null
     * 2.当命中数据库时，如果缓存失效则返回null
     *
     * @param key 缓存key
     * @return 缓存值
     */
    public String getValue(String key, int groupId) {
        Cache entry = get(key, groupId);
        if (entry == null) {
            return null;
        }
        if (entry.type == Cache.CACHE_FROM_MEMORY) {
            return entry.getValue();
        } else {
            if (entry.getExpireTime() < System.currentTimeMillis() / 1000) {
                remove(key, groupId);
                return null;
            }
            return entry.getValue();
        }
    }

    /**
     * 删除内存和数据库缓存
     *
     * @param key
     * @return
     */
    public boolean remove(String key, int groupId) {
        Map<String, Cache> hashMap = mLruCache.get(String.valueOf(groupId));
        if (hashMap != null) {
            hashMap.remove(key);
        }
        getDbCache().remove(key);
        return true;
    }

    /**
     * 清楚所有缓存
     *
     * @return
     */
    public void clear() {
        getLruCache().evictAll();
        getDbCache().clear();
    }

    private void putIntoLru(String key, Cache cache) {
        Map<String, Cache> hashMap = getLruCache().get(String.valueOf(cache.getGroupId()));
        if (hashMap != null) {
            L.d("hashMap isn't empty, set value to LruCache: key %s, groupId %s", key, cache.getGroupId());
            hashMap.put(key, new Cache(cache.getValue(), cache.getExpireTime(), Cache.CACHE_FROM_MEMORY));

        } else {
            L.d("hashMap is empty, set value to LruCache: key %s, groupId %s", key, cache.getGroupId());
            hashMap = new HashMap<String, Cache>(0);
            hashMap.put(key, cache);
        }
        mLruCache.put(String.valueOf(cache.getGroupId()), hashMap);
    }

    private void putIntoLru(String key, String value, long expireTime, int groupId) {
        Map<String, Cache> hashMap = getLruCache().get(String.valueOf(groupId));
        if (hashMap != null) {
            L.d("hashMap isn't empty, set value to LruCache: key %s, groupId %s", key, groupId);
            hashMap.put(key, new Cache(value, expireTime, Cache.CACHE_FROM_MEMORY));

        } else {
            L.d("hashMap is empty, set value to LruCache: key %s, groupId %s", key, groupId);
            hashMap = new HashMap<String, Cache>(0);
            hashMap.put(key, new Cache(value, expireTime, Cache.CACHE_FROM_MEMORY));
        }
        mLruCache.put(String.valueOf(groupId), hashMap);
    }

    private Cache getFromLru(String key, int groupId) {
        Map<String, Cache> hashMap = mLruCache.get(String.valueOf(groupId));
        if (hashMap != null) {
            L.d("hashMap isn't empty, get value from LruCache: key %s groupId %s", key, groupId);
            return hashMap.get(key);

        } else {
            L.d("hashMap is empty, get value from LruCache: key %s groupId %s", key, groupId);
            return null;
        }
    }
}
