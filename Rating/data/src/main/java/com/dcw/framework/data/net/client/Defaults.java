package com.dcw.framework.data.net.client;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/10/20
 */
final class Defaults {
    static final int CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s
    static final int READ_TIMEOUT_MILLIS = 20 * 1000; // 20s
    static final int PLATFORM_GAME = 5;
    static int[] M9_SECRET_KEY = new int[]{'f', '0', '2', 'a', '1', '7', '0', 'b', 'c', '7', 'f', 'c', 'b', '7', '1', '3'};

    private Defaults() {
        // No instances.
    }
}
