package com.dcw.app.rating.ui.framework;

import android.os.Bundle;

import com.dcw.app.rating.biz.welcome.WelcomeFragment;
import com.dcw.framework.pac.basic.BaseController;
import com.dcw.framework.pac.basic.FrameworkMessage;
import com.dcw.framework.pac.basic.IResultListener;
import com.dcw.framework.pac.basic.RegisterMessages;

/**
 * Created by adao12 on 2015/5/19.
 */
@RegisterMessages(FrameworkMessage.LAUNCHER_CONTROLLER_INVOKE)
public class LauncherController extends BaseController {

    @Override
    public void handleMessage(String messageId, Bundle messageData, IResultListener listener) {
        if (messageId == FrameworkMessage.LAUNCHER_CONTROLLER_INVOKE) {
            startFragment(WelcomeFragment.class);
        }
    }
}
