package com.dcw.framework.state;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/7
 */
public class StateMachineConfig {

    private State mInitialState;

    private Object[] mInitialArgs;

    private List<StateEvent> mEvents;

    private StateEventCallback mOnBeforeAnyEventCallback;

    private StateEventCallback mOnAfterAnyEventCallback;

    public State getInitialState() {
        return mInitialState;
    }

    public Object[] getInitialArgs() {
        return mInitialArgs;
    }

    public List<StateEvent> getEvents() {
        return mEvents;
    }

    public StateEventCallback getOnBeforeAnyEventCallback() {
        return mOnBeforeAnyEventCallback;
    }

    public StateEventCallback getOnAfterAnyEventCallback() {
        return mOnAfterAnyEventCallback;
    }

    public static class Builder {

        private StateMachineConfig mConfig;

        public Builder() {
            mConfig = new StateMachineConfig();
        }

        public Builder setInitialState(State initialState) {
            mConfig.mInitialState = initialState;
            return this;
        }

        public Builder setInitialArgs(Object... args) {
            mConfig.mInitialArgs = args;
            return this;
        }

        public Builder setEvents(List<StateEvent> events) {
            mConfig.mEvents = events;
            return this;
        }

        public Builder addEvent(StateEvent event) {
            if (mConfig.mEvents == null) {
                mConfig.mEvents = new ArrayList<StateEvent>();
            }
            mConfig.mEvents.add(event);
            return this;
        }

        public Builder setOnBeforeAnyEventCallback(StateEventCallback onBeforeAnyEventCallback) {
            mConfig.mOnBeforeAnyEventCallback = onBeforeAnyEventCallback;
            return this;
        }

        public Builder setOnAfterAnyEventCallback(StateEventCallback onAfterAnyEventCallback) {
            mConfig.mOnAfterAnyEventCallback = onAfterAnyEventCallback;
            return this;
        }

        public StateMachineConfig build() {
            return mConfig;
        }
    }
}
