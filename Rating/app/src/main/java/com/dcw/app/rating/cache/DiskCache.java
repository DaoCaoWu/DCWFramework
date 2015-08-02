package com.dcw.app.rating.cache;

import com.dcw.app.rating.db.bean.Cache;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/6
 */
public interface DiskCache<K, V> {

    V get(K key);

    boolean save(Cache cache);

    boolean remove(K key);

    void clear();
}
