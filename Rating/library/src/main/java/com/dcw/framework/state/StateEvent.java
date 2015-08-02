package com.dcw.framework.state;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/7
 */
public class StateEvent {

    private String mName;

    private State[] mFromStates;

    private State mToState;

    private Object[] mArgs;

    private StateEventCallback mOnBeforeCallback;

    private StateEventCallback mOnAfterCallback;

    private EventTransition mCoreTransition;

    private EventTransition mCancelTransition;

    public StateEvent(String name, String from, State to, Object... args) {
        this(name, to, args);
        String[] stateNames = from.split(",");
        State[] states = new State[stateNames.length];
        for (int i = 0; i < stateNames.length; i++) {
            states[i] = new State(stateNames[i]);
        }
        mFromStates = states;
    }

    public StateEvent(String name, State to, Object... args) {
        mName = name;
        mFromStates = new State[1];
        mFromStates[0] = StateMachine.WILDCARD;
        mToState = to;
        mArgs = args;
    }

    public StateEvent(String name, State from, State to, Object... args) {
        mName = name;
        mFromStates = new State[1];
        mFromStates[0] = from;
        mToState = to;
        mArgs = args;
    }

    public StateEvent(String name, State[] froms, State to, Object... args) {
        mName = name;
        mFromStates = froms;
        mToState = to;
        mArgs = args;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public State[] getFromStates() {
        return mFromStates;
    }

    public void setFromStates(State[] fromStates) {
        this.mFromStates = fromStates;
    }

    public State getToState() {
        return mToState;
    }

    public void setToState(State toState) {
        this.mToState = toState;
    }

    public Object[] getArgs() {
        return mArgs;
    }

    public void setArgs(Object[] args) {
        this.mArgs = args;
    }

    public StateEventCallback getOnBeforeCallback() {
        return mOnBeforeCallback;
    }

    public void setOnBeforeCallback(StateEventCallback onBeforeCallback) {
        this.mOnBeforeCallback = onBeforeCallback;
    }

    public StateEventCallback getOnAfterCallback() {
        return mOnAfterCallback;
    }

    public void setOnAfterCallback(StateEventCallback onAfterCallback) {
        this.mOnAfterCallback = onAfterCallback;
    }

    protected EventTransition getCoreTransition() {
        return mCoreTransition;
    }

    protected void setCoreTransition(EventTransition coreTransition) {
        this.mCoreTransition = coreTransition;
    }

    protected EventTransition getCancelTransition() {
        return mCancelTransition;
    }

    protected void setCancelTransition(EventTransition cancelTransition) {
        this.mCancelTransition = cancelTransition;
    }

    public void cancal() {
        if (mCancelTransition != null) {
            mCancelTransition.execute();
        }
    }
}
