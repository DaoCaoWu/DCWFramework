package com.dcw.framework.state;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/7
 */
public interface IStateMachine {

    void initial(StateMachineConfig config);

    boolean isReady();

    boolean isState(State state);

    boolean isState(State[] state);

    State getState();

    int doEvent(String eventName, Object... args);

    int doEventForce(String eventName, Object... args);

    boolean canDoEvent(StateEvent event);

    boolean cannotDoEvent(StateEvent event);

    boolean isFinishedState();
}
