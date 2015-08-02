package com.dcw.app.rating.log;

import android.util.Log;

import com.dcw.app.rating.BuildConfig;


public class L {

    private static final String LOG_FORMAT = "%1$s\n%2$s";
    private static volatile boolean writeDebugLogs = false;
    private static volatile boolean writeLogs = true;

    private L() {
    }

    /**
     * Enables logger (if {@link #disableLogging()} was called before)
     *
     * @deprecated Use {@link #writeLogs(boolean) writeLogs(true)} instead
     */
    @Deprecated
    public static void enableLogging() {
        writeLogs(true);
    }

    /**
     * Disables logger, no logs will be passed to LogCat, all log methods will do nothing
     *
     * @deprecated Use {@link #writeLogs(boolean) writeLogs(false)} instead
     */
    @Deprecated
    public static void disableLogging() {
        writeLogs(false);
    }

    /**
     * Enables/disables detail logging of app work.
     * Consider {@link L#disableLogging()} to disable
     *  logging completely (even error logs)<br />
     * Debug logs are disabled by default.
     */
    public static void writeDebugLogs(boolean writeDebugLogs) {
        L.writeDebugLogs = writeDebugLogs;
    }

    /** Enables/disables logging of app completely (even error logs). */
    public static void writeLogs(boolean writeLogs) {
        L.writeLogs = writeLogs;
    }

    public static void d(String message, Object... args) {
        if (writeDebugLogs) {
            log(Log.DEBUG, null, message, args);
        }
    }

    public static void d(Throwable ex) {
        if (writeDebugLogs) {
            log(Log.DEBUG, ex, null);
        }
    }

    public static void i(String message, Object... args) {
        log(Log.INFO, null, message, args);
    }

    public static void w(String message, Object... args) {
        log(Log.WARN, null, message, args);
    }

    public static void w(Throwable ex) {
        log(Log.WARN, ex, null);
    }

    public static void e(Throwable ex) {
        log(Log.ERROR, ex, null);
    }

    public static void e(String message, Object... args) {
        log(Log.ERROR, null, message, args);
    }

    public static void e(Throwable ex, String message, Object... args) {
        log(Log.ERROR, ex, message, args);
    }

    private static void log(int priority, Throwable ex, String message, Object... args) {
        if (!writeLogs) return;
        if (args.length > 0) {
            message = String.format(message, args);
        }

        String log;
        if (ex == null) {
            log = message;
        } else {
            String logMessage = message == null ? ex.getMessage() : message;
            String logBody = Log.getStackTraceString(ex);
            log = String.format(LOG_FORMAT, logMessage, logBody);
        }
        Log.println(priority, BuildConfig.APPLICATION_ID, log);
    }
}
