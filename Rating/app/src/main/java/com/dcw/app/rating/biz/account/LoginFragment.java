package com.dcw.app.rating.biz.account;

import android.widget.Toast;

import com.dcw.app.R;
import com.dcw.app.rating.app.App;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.toolbar.ToolbarController;
import com.dcw.app.rating.biz.toolbar.ToolbarModel;
import com.dcw.app.rating.di.component.DaggerUIComponent;
import com.dcw.app.rating.di.component.UIComponent;
import com.dcw.app.rating.di.module.ActivityModule;
import com.dcw.app.rating.di.module.UIModule;
import com.dcw.app.ui.framework.BaseActivityWrapper;
import com.dcw.app.ui.framework.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;

import javax.inject.Inject;

@InjectLayout(R.layout.fragment_login)
public class LoginFragment extends BaseFragmentWrapper {

    UIComponent mUIComponent;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {
        mUIComponent = DaggerUIComponent.builder().appComponent(((App)getActivity().getApplication()).getAppComponent()).uIModule(new UIModule()).build();
    }

    @Override
    public void initUI() {
        new LoginController((LoginView) findViewById(R.id.root_view), new UserModel());
        mToolbarController = new ToolbarController(findViewById(R.id.toolbar), new ToolbarModel(this.getClass().getSimpleName(), false));
        getUIComponent().toastManager().showToast("kwkwkwkww");
//        Toast.makeText(getFragmentComponent().fragment().getActivity(), "dkfjsdjflsjl", Toast.LENGTH_LONG).show();
    }

    @Override
    public void initListeners() {

    }

    UIComponent getUIComponent() {
        return mUIComponent;
    }
}
