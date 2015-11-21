package com.dcw.app.biz.account;

import com.dcw.app.R;
import com.dcw.app.app.App;
import com.dcw.app.biz.MainActivity;
import com.dcw.app.biz.toolbar.ToolbarController;
import com.dcw.app.biz.toolbar.ToolbarModel;
import com.dcw.app.di.component.DaggerUIComponent;
import com.dcw.app.di.component.UIComponent;
import com.dcw.app.di.module.UIModule;

import cn.ninegame.library.ui.framework.BaseFragmentWrapper;
import cn.ninegame.library.ui.framework.ToastManager;
import com.dcw.framework.view.annotation.InjectLayout;

import javax.inject.Inject;

@InjectLayout(R.layout.fragment_login)
public class LoginFragment extends BaseFragmentWrapper {

    UIComponent mUIComponent;
    @Inject
    ToastManager mToastManager;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {
        mUIComponent = DaggerUIComponent.builder().appComponent(((App)getActivity().getApplication()).getAppComponent()).uIModule(new UIModule()).build();
        mUIComponent.inject(this);
    }

    @Override
    public void initUI() {
        new LoginController((LoginView) findViewById(R.id.root_view), new UserModel());
        mToolbarController = new ToolbarController(findViewById(R.id.toolbar), new ToolbarModel(this.getClass().getSimpleName(), false));
        mToastManager.showToast("kwkwkwkww");
//        Toast.makeText(getFragmentComponent().fragment().getActivity(), "dkfjsdjflsjl", Toast.LENGTH_LONG).show();
    }

    @Override
    public void initListeners() {

    }

    UIComponent getUIComponent() {
        return mUIComponent;
    }
}
