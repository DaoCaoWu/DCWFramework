package cn.ninegame.library.ui.framework;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.dcw.app.di.component.ActivityComponent;
import com.dcw.app.di.module.ActivityModule;
import com.dcw.app.di.component.DaggerActivityComponent;
import com.dcw.app.app.App;
import com.dcw.app.di.component.AppComponent;
import com.dcw.framework.pac.ui.BaseActivity;

public class BaseActivityWrapper extends BaseActivity {

    ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(this, true);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(AppCompatActivity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }


    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }
}
