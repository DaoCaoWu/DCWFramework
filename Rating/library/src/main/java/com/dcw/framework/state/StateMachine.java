package com.dcw.framework.state;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/7
 */
public class StateMachine implements IStateMachine {

    public static final String TAG = StateMachine.class.getSimpleName();

    //the event transitioned successfully from one state to another
    public static final int SUCCEEDED = 1;
    //the event was successfull but no state transition was necessary
    public static final int NO_TRANSITION = 2;
    //the event was cancelled by the caller in a beforeEvent callback
    public static final int CANCELLED = 3;
    //the event is asynchronous and the caller is in control of when the transition occurs
    public static final int PENDING = 4;
    //the event was failure
    public static final int FAILURE = 5;

    //caller tried to fire an event that was innapropriate in the current state
    public static final String INVALID_TRANSITION_ERROR = "INVALID_TRANSITION_ERROR";
    //caller tried to fire an event while an async transition was still pending
    public static final String PENDING_TRANSITION_ERROR = "PENDING_TRANSITION_ERROR";
    //caller provided callback function threw an exception
    public static final String INVALID_CALLBACK_ERROR = "INVALID_CALLBACK_ERROR";

    //to mark the event
    public static final String ASYNC = "ASYNC";
    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";

    public static final State WILDCARD = new State("*");
    public static final State NONE = new State("none");
    public static final String START_UP = "START_UP";

    private State mInitialState;
    private State mCurrentState;
    private State mTerminalState;
    private Map<String, StateEvent> mEvents;
    private LinkedList<StateEvent> mWildcardStates;

    private StateEventCallback mOnBeforeAnyEventCallback;
    private StateEventCallback mOnAfterAnyEventCallback;

    private StateEventCallback mOnEnterAnyStateCallback;
    private StateEventCallback mOnLevelAnyStateCallback;
    private StateEventCallback mOnChangeStateCallback;

    private boolean mIsInTransition;

    public StateMachine() {

    }

    public StateMachine(StateMachineConfig config) {
        initial(config);
    }

    public State getCurrentState() {
        return mCurrentState;
    }

    public void setCurrentState(State currentState) {
        this.mCurrentState = currentState;
    }

    public StateEventCallback getOnBeforeAnyEventCallback() {
        return mOnBeforeAnyEventCallback;
    }

    public void setOnBeforeAnyEventCallback(StateEventCallback onBeforeAnyEventCallback) {
        this.mOnBeforeAnyEventCallback = onBeforeAnyEventCallback;
    }

    public StateEventCallback getOnAfterAnyEventCallback() {
        return mOnAfterAnyEventCallback;
    }

    public void setOnAfterAnyEventCallback(StateEventCallback onAfterAnyEventCallback) {
        this.mOnAfterAnyEventCallback = onAfterAnyEventCallback;
    }

    public StateEventCallback getOnLevelAnyStateCallback() {
        return mOnLevelAnyStateCallback;
    }

    public void setOnLevelAnyStateCallback(StateEventCallback onLevelAnyStateCallback) {
        this.mOnLevelAnyStateCallback = onLevelAnyStateCallback;
    }

    public StateEventCallback getOnEnterAnyStateCallback() {
        return mOnEnterAnyStateCallback;
    }

    public void setOnEnterAnyStateCallback(StateEventCallback onEnterAnyStateCallback) {
        this.mOnEnterAnyStateCallback = onEnterAnyStateCallback;
    }

    public StateEventCallback getOnChangeStateCallback() {
        return mOnChangeStateCallback;
    }

    public void setOnChangeStateCallback(StateEventCallback onChangeStateCallback) {
        this.mOnChangeStateCallback = onChangeStateCallback;
    }

    public StateEvent getEvent(String eventName) {
        if (mEvents == null) {
            return null;
        }
        return mEvents.get(eventName);
    }

    @Override
    public void initial(StateMachineConfig config) {
        mInitialState = config.getInitialState();
        mOnBeforeAnyEventCallback = config.getOnBeforeAnyEventCallback();
        mOnAfterAnyEventCallback = config.getOnAfterAnyEventCallback();
        mCurrentState = NONE;
        mIsInTransition = false;

        //collect all events
        StateEvent initialEvent = new StateEvent(START_UP, NONE, mInitialState, config.getInitialArgs());
        if (mInitialState != null) {
            addEvent(initialEvent);
        }
        if (config.getEvents() != null && config.getEvents().size() != 0) {
            for (StateEvent event : config.getEvents()) {
                addEvent(event);
            }
        }
        //enter the state that caller defined
        if (mInitialState != null) {
            doEvent(initialEvent.getName(), config.getInitialArgs());
        }
    }

    private void addEvent(StateEvent event) {
        if (event == null) {
            return;
        }
        if (mEvents == null) {
            mEvents = new HashMap<String, StateEvent>();
        }
        if (mWildcardStates == null) {
            mWildcardStates = new LinkedList<StateEvent>();
        }

        //allow "wildcard" transition if "from" is not specified
        if (event.getFromStates() == null) {
            event.setFromStates(new State[]{WILDCARD});
        } else {
            State[] states = event.getFromStates();
            int length = states.length;
            for (int i = 0; i < length; i++) {
                if (states[i] == null) {
                    states[i] = WILDCARD;
                }
            }
        }
        // save the "to state" according to weather the "from state"' value is "*" or not.
        State[] states = event.getFromStates();
        int length = states.length;
        for (int i = 0; i < length; i++) {
            if (WILDCARD.equals(states[i].getName())) {
                mWildcardStates.add(event);
            }
        }
        mEvents.put(event.getName(), event);
    }

    @Override
    public boolean isReady() {
        return mCurrentState != NONE;
    }

    @Override
    public boolean isState(State state) {
        return mCurrentState.equals(state);
    }

    @Override
    public boolean isState(State[] states) {
        if (states != null) {
            for (int i = 0; i < states.length; i++) {
                if (mCurrentState.equals(states[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public State getState() {
        return mCurrentState;
    }

    @Override
    public int doEventForce(String eventName, Object... args) {
        if (eventName  == null || eventName.length() == 0) {
            throw new StateException("StateMachine#doEvent() - invalid param event" + eventName);
        }

        StateEvent event = getEvent(eventName);
        if (event == null) {
            throw new StateException("StateMachine#doEvent() - invalid param event" + eventName);
        }
        if (args != null && args.length != 0) {
            event.setArgs(args);
        }
        if (event.getToState() == null) {
            event.setToState(mCurrentState);
        }
        if (mIsInTransition) {
            mIsInTransition = false;
        }
        beforeEvent(event);
        if (mCurrentState.equals(event.getToState())) {
            afterEvent(event);
            return NO_TRANSITION;
        }
        mCurrentState = event.getToState();
        enterState(event);
        changeState(event);
        afterEvent(event);
        return SUCCEEDED;
    }

    private boolean containsWildcard(StateEvent event) {
        State[] states = event.getFromStates();
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null && WILDCARD.equals(states[i].getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int doEvent(String eventName, Object... args) {
        if (eventName  == null || eventName.length() == 0) {
            throw new StateException("StateMachine#doEvent() - invalid param event" + eventName);
        }

        StateEvent temp = getEvent(eventName);
        if (temp == null) {
            throw new StateException("StateMachine#doEvent() - invalid param event" + eventName);
        }
        if (args != null && args.length != 0) {
            temp.setArgs(args);
        }
        if (temp.getToState() == null) {
            temp.setToState(mCurrentState);
        }
        final StateEvent event = temp;

        if (mIsInTransition) {
            onError(event, PENDING_TRANSITION_ERROR, "event " + event.getName() + " inappropriate because previous transition did not complete");
            return FAILURE;
        }

        if (cannotDoEvent(event)) {
            onError(event, INVALID_TRANSITION_ERROR, "event " + event.getName() + " inappropriate in current state " + mCurrentState.getName());
            return FAILURE;
        }

        if (FALSE.equals(beforeEvent(event))) {
            return CANCELLED;
        }

        if (mCurrentState == event.getToState() || mCurrentState.getName().equals(event.getToState().getName())) {
            return NO_TRANSITION;
        }

        event.setCoreTransition(new EventTransition() {
            @Override
            public int execute() {
                mIsInTransition = false;
                mCurrentState = event.getToState();
                enterState(event);
                changeState(event);
                leaveState(mCurrentState, event);
                return SUCCEEDED;
            }
        });

        event.setCancelTransition(new EventTransition() {
            @Override
            public int execute() {
                event.setCoreTransition(null);
                afterEvent(event);
                return CANCELLED;
            }
        });

        mIsInTransition = true;

        String isLeft = leaveState(mCurrentState, event);
        if (FALSE.equals(isLeft)) {
            event.setCoreTransition(null);
            event.setCancelTransition(null);
            mIsInTransition = false;
            return CANCELLED;
        } else if (ASYNC.equals(isLeft.toUpperCase())) {
            return PENDING;
        } else {
            //need to check in case user manually called transition()
            //but forgot to return StateMachine.ASYNC
            if (event.getCoreTransition() != null) {
                event.getCoreTransition().execute();
            } else {
                mIsInTransition = false;
            }
        }
        return PENDING;
    }

    @Override
    public boolean canDoEvent(StateEvent event) {
        if (event == null || mEvents == null) {
            return false;
        }
        StateEvent value = mEvents.get(event.getName());
        if (value != null) {
            State[] states = value.getFromStates();
            if (states != null) {
                for (int i = 0; i < states.length; i++) {
                    if (!mIsInTransition && mCurrentState.equals(states[i]) || WILDCARD.equals(states[i])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean cannotDoEvent(StateEvent event) {
        return !canDoEvent(event);
    }

    @Override
    public boolean isFinishedState() {
        return isState(mTerminalState);
    }

    private String doCallback(StateEventCallback callback, StateEvent event) {
        if(callback != null) {
            return callback.onEvent(event);
        }
        return TRUE;
    }

    private String beforeAnyEvent(StateEvent event) {
        return doCallback(mOnBeforeAnyEventCallback, event);
    }

    private String afterAnyEvent(StateEvent event) {
        return doCallback(mOnAfterAnyEventCallback, event);
    }

    private String enterAnyState(StateEvent event) {
        return doCallback(mOnEnterAnyStateCallback, event);
    }

    private String levelAnyState(StateEvent event) {
        return doCallback(mOnLevelAnyStateCallback, event);
    }

    private String changeState(StateEvent event) {
        return doCallback(mOnChangeStateCallback, event);
    }

    private String beforeThisEvent(StateEvent event) {
        return doCallback(event.getOnBeforeCallback(), event);
    }

    private String afterThisEvent(StateEvent event) {
        return doCallback(event.getOnAfterCallback(), event);
    }

    private String enterThisState(State state, StateEvent event) {
        return doCallback(state.getOnEnterCallback(), event);
    }

    private String levelThisState(State state, StateEvent event) {
        return doCallback(state.getOnLevelCallback(), event);
    }

    private String beforeEvent(StateEvent event) {
        if (FALSE.equals(beforeThisEvent(event)) || FALSE.equals(beforeAnyEvent(event))) {
            return FALSE;
        }
        return TRUE;
    }

    private void afterEvent(StateEvent event) {
        afterThisEvent(event);
        afterAnyEvent(event);
    }

    private String leaveState(State state, StateEvent event) {
        String specific = levelThisState(state, event);
        String general = levelAnyState(event);
        if (FALSE.equals(specific) || FALSE.equals(general)) {
            return FALSE;
        } else if (ASYNC.equals(specific.toUpperCase()) || ASYNC.equals(general.toUpperCase())) {
            return ASYNC;
        }
        return TRUE;
    }

    private void enterState(StateEvent event) {
        enterThisState(event.getToState(), event);
        enterAnyState(event);
    }

    private void onError(StateEvent event, String error, String message) {
        System.err.println(TAG + "#" + String.format("ERROR: error %s, event %s, to %s \n %s", error, event.getName(), event.getToState().getName(), message));
    }
}
