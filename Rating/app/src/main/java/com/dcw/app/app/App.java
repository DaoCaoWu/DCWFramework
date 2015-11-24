package com.dcw.app.app;

import android.app.Application;

import com.dcw.app.di.component.AppComponent;
import com.dcw.app.di.component.DaggerAppComponent;
import com.dcw.app.di.component.DaggerImageComponent;
import com.dcw.app.di.component.DaggerNetworkComponent;
import com.dcw.app.di.component.ImageComponent;
import com.dcw.app.di.component.NetworkComponent;
import com.dcw.app.di.module.AppModule;
import com.dcw.app.di.module.ImageModule;
import com.dcw.app.di.module.NetworkModule;

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
    private AppModule mAppModule;
    private NetworkComponent mNetworkComponent;
    private ImageComponent mImageComponent;

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
        mAppModule = new AppModule(this);
        mAppComponent = DaggerAppComponent.builder()
                .appModule(mAppModule)
                .build();
        mNetworkComponent = DaggerNetworkComponent.builder()
                .appModule(mAppModule)
                .networkModule(new NetworkModule())
                .build();
        mImageComponent = DaggerImageComponent.builder()
                .appModule(mAppModule)
                .imageModule(new ImageModule())
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

    public AppModule getAppModule() {
        return new AppModule(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

    public ImageComponent getImageComponent() {
        return mImageComponent;
    }
}
