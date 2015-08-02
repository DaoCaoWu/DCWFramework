package com.dcw.framework.state;

import java.util.ArrayList;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/8
 */
public class StateMachineTest {

    public static void main(String args[]) throws Exception {

        StateEventCallback eventCallback = new StateEventCallback() {
            @Override
            public String onEvent(StateEvent event) {
                System.out.println(event.getName() + "\t-->" + event.getFromStates()[0].getName() + "\t-->" + event.getToState().getName());
                if (event.getArgs() != null) {
                    System.out.println("Args:\t");
                    for (int i = 0; i < event.getArgs().length; i++) {
                        System.out.print(event.getArgs()[i]+"\t");
                    }
                }
                System.out.print("\n");
                return StateMachine.TRUE;
            }
        };

        StateMachine sm = new StateMachine();

        State redState = new State("Red");
        State greenState = new State("Green");
        State blueState = new State("Blue");
        redState.setOnEnterCallback(eventCallback);
        redState.setOnLevelCallback(eventCallback);

        StateEvent recoveryEvent = new StateEvent("recovery", redState, StateMachine.NONE);
        recoveryEvent.setOnBeforeCallback(eventCallback);
        recoveryEvent.setOnAfterCallback(eventCallback);


        StateMachineConfig config = new StateMachineConfig.Builder()
                .setInitialState(greenState)
                .setInitialArgs("arg1", 2, new ArrayList<String>())
                .addEvent(new StateEvent("changeColor1", greenState, redState, 1, 3, "3"))
                .addEvent(new StateEvent("changeColor2", greenState, redState, 1, 3, "3"))
                .addEvent(new StateEvent("changeColor3", StateMachine.WILDCARD, blueState, 1, 3, "3"))
                .addEvent(recoveryEvent)
                .setOnBeforeAnyEventCallback(eventCallback)
                .setOnAfterAnyEventCallback(eventCallback)
                .build();
        sm.initial(config);

        sm.doEvent("changeColor1");
        sm.doEvent(recoveryEvent.getName());
        sm.doEvent(StateMachine.START_UP);
        sm.doEvent("changeColor3");
        sm.doEvent(recoveryEvent.getName());
        sm.getCurrentState().getName();
    }
}
