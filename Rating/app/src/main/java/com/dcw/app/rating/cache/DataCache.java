package com.dcw.app.rating.cache;

import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.dcw.app.rating.db.bean.Cache;
import com.dcw.app.rating.log.L;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/6
 */
public class DataCache extends BaseCache {

    private LruCache<String, Cache> mLruCache;

    /**
     * 默认缓存大小100k
     */
    public final static int DEFAULT_CACHE_SIZE = 1024 * 100;

    /**
     * 延迟数据库删除缓存时间，使数据库缓存有效期大于内存缓存, 1天
     */
    public final static int DEFAULT_CACHE_TIME = 7 * 86400;

    public DataCache(int cacheSizeInBytes) {
        super(cacheSizeInBytes, DEFAULT_CACHE_SIZE, DEFAULT_CACHE_TIME);
        setCachePrefix(RequestCache.class.getSimpleName());
        mLruCache = new LruCache<String, Cache>(cacheSizeInBytes) {
            @Override
            protected int sizeOf(String key, Cache value) {
                return value.getValue().length() * 2;    // in java, char encoding is UTF16_BE, always 2 bytes
            }
        };
    }

    public LruCache<String, Cache> getLruCache() {
        return mLruCache;
    }

    /**
     * 保存数据到LruCache，不保存到数据库
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param seconds 缓存时间，单位秒
     */
    public void put(String key, String value, int seconds) {
        put(key, value, seconds, false);
    }

    /**
     * 保存数据到LruCache，根据saveData可以指定是否保存到数据库
     *
     * @param key      缓存key
     * @param value    缓存值
     * @param seconds  缓存时间，单位秒
     * @param saveData true则保存到数据库，否则不保存
     */
    public void put(String key, String value, int seconds, boolean saveData) {
        long expireTime = System.currentTimeMillis() / 1000 + seconds;
        getLruCache().put(key, new Cache(value, expireTime, Cache.CACHE_FROM_MEMORY));

        if (saveData) {
            L.d("DataCache#Save to database: " + key);
            getDbCache().save(new Cache(key, value, expireTime));
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
    public Cache get(String key) {
        return get(key, true);
    }

    public Cache get(String key, boolean isCareExpire) {
        Cache entry = getLruCache().get(key);
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
     * @param key 缓存key
     * @return 缓存值
     */
    public String getValue(String key) {
        Cache entry = get(key);
        if (entry == null) {
            return null;
        }
        if (entry.type == Cache.CACHE_FROM_MEMORY) {
            return entry.getValue();
        } else {
            if (entry.getExpireTime() < System.currentTimeMillis() / 1000) {
                remove(key);
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
    public boolean remove(String key) {
        if (TextUtils.isEmpty(key)) {
            getLruCache().remove(key);
            getDbCache().remove(key);
            return true;
        }
        return false;
    }
}
