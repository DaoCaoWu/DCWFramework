package com.dcw.framework.state;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/7
 */
public class State {

    private String mName;

    private StateEventCallback mOnEnterCallback;

    private StateEventCallback mOnLevelCallback;

    public State(String name) {
        mName = name;
    }

    public State(String name, StateEventCallback enterCallback, StateEventCallback levelCallback) {
        mName = name;
        mOnEnterCallback = enterCallback;
        mOnLevelCallback = levelCallback;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public StateEventCallback getOnEnterCallback() {
        return mOnEnterCallback;
    }

    public void setOnEnterCallback(StateEventCallback onEnterCallback) {
        this.mOnEnterCallback = onEnterCallback;
    }

    public StateEventCallback getOnLevelCallback() {
        return mOnLevelCallback;
    }

    public void setOnLevelCallback(StateEventCallback onLevelCallback) {
        this.mOnLevelCallback = onLevelCallback;
    }

    public boolean equals(State obj) {
        if (obj != null) {
            if (mName == null || mName.length() == 0) {
                return false;
            }
            if (mName.equals(obj.getName())) {
                return true;
            }
        }
        return false;
    }
}
