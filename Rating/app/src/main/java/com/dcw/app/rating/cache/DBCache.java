package com.dcw.app.rating.cache;

import com.dcw.app.rating.app.RatingApplication;
import com.dcw.app.rating.db.bean.Cache;
import com.dcw.app.rating.db.dao.CacheDao;

import java.util.List;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/6
 */
public class DBCache implements DiskCache<String, Cache> {

    private int mCacheTime;
    private CacheDao mCacheDAO;

    public DBCache(int cacheTime) {
        mCacheTime = cacheTime;
        mCacheDAO = RatingApplication.getInstance().getDaoSession().getCacheDao();
    }

    @Override
    public Cache get(String key) {
        return null;
    }

    @Override
    public boolean save(Cache cache) {
        try {
            mCacheDAO.insertOrReplace(cache);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remove(String key) {
        try {
            mCacheDAO.delete(new Cache(key));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void clear() {
        mCacheDAO.deleteAll();
    }

    public void clearExpired() {
        List<Cache> caches = mCacheDAO.queryBuilder()
                .where(CacheDao.Properties.ExpireTime.lt(System.currentTimeMillis() / 1000 - mCacheTime))
                .list();
        mCacheDAO.deleteInTx(caches);
    }
}
