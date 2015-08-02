package com.dcw.framework.state;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/8
 */
public class StateException extends RuntimeException {

    private static final long serialVersionUID = 6162710183389028792L;

    public StateException() {
        super();
    }

    public StateException(String detailMessage) {
        super(detailMessage);
    }

}
