package com.dcw.app.biz.test;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dcw.app.R;
import com.dcw.app.biz.MainActivity;
import com.dcw.app.biz.toolbar.ToolbarController;
import com.dcw.app.biz.toolbar.ToolbarModel;
import cn.ninegame.framework.adapter.BaseFragmentWrapper;
import com.dcw.app.ui.lib.StateView;
import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;

/**
 * Created by adao12 on 2015/7/21.
 */
@InjectLayout(R.layout.fragment_state_view)
public class StateViewFragment extends BaseFragmentWrapper implements View.OnClickListener {

    StateView.ContentState mState;

    @InjectView(R.id.content)
    private StateView mNGStateView;

    @InjectView(value = R.id.btn_change_state, listeners = View.OnClickListener.class)
    private Button mChangeState;

    private int index = 0;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        mToolbarController = new ToolbarController(findViewById(R.id.toolbar), new ToolbarModel(this.getClass().getSimpleName()));
        mState = mNGStateView.getState();
        mNGStateView.setOnEmptyViewBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMsg("empty view button click!");
            }
        });
        setState(StateView.ContentState.ERROR_NETWORK);
    }

    @Override
    public void initListeners() {

    }

    public void setState(StateView.ContentState state) {
        mNGStateView.setState(state);
        mState = state;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_state:
                index++;
                if (index >= StateView.ContentState.values().length) {
                    index = 0;
                }
                setState(StateView.ContentState.values()[index]);
                break;
            default:

                break;
        }
    }

    private void toastMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
