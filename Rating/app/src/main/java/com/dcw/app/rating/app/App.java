package com.dcw.app.rating.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.dcw.app.rating.cache.BaseCache;
import com.dcw.app.rating.cache.DataCache;
import com.dcw.app.rating.db.dao.DaoMaster;
import com.dcw.app.rating.db.dao.DaoSession;
import com.dcw.app.rating.di.component.AppComponent;
import com.dcw.app.rating.di.component.DaggerAppComponent;
import com.dcw.app.rating.di.module.AppModule;
import com.dcw.app.rating.error.ErrorReporter;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/5
 */
public class App extends Application {

//    private static App sInstance;
//    private DaoSession daoSession;
//    private ErrorReporter mErrorReporter;
//    private DataCache mDataCache;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
//        setupDatabase();
//        sInstance = this;
//        mErrorReporter = new ErrorReporter(this);
//        mDataCache = new DataCache(1024 * 100);
//        mDataCache.clearExpiredCache();

    }

    private void initializeInjector() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

//    private void setupDatabase() {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "rating-db", null);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(db);
//        daoSession = daoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
//
//    public ErrorReporter getErrorReporter() {
//        return mErrorReporter;
//    }
//
//    public BaseCache getDataCache() {
//        return mDataCache;
//    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
