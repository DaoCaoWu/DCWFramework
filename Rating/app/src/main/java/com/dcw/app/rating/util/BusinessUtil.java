package com.dcw.app.rating.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/6
 */
public class BusinessUtil {

    /**
     * 检查是否有SD卡
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        File file = Environment.getExternalStorageDirectory();
        String externalState = Environment.getExternalStorageState();
        boolean fileCanWrite = file.canWrite();
        if (externalState.equals(Environment.MEDIA_MOUNTED) && fileCanWrite) {
            return true;
        } else {
//            StatUtil.addJsonStat(null, "save_ch_failed", "isSDCardMounted", "a", externalState + "_" + fileCanWrite);
            return false;
        }
    }

    private static File getExternalFilesDir(Context ctx, String typeString) {
        if (!isSDCardMounted()) {
            return null;
        }

        File baseDir = ctx.getExternalFilesDir(typeString);
        if (baseDir == null) {
            baseDir = new File("/sdcard/Android/data/" + ctx.getPackageName() + "/files/" + typeString);
            baseDir.mkdirs();
        }
        if (!baseDir.exists()) {
            return null;
        }
        return baseDir;
    }

    private static File getExternalCacheDir(Context ctx) {
        if (!isSDCardMounted()) {
            return null;
        }
        File baseDir = ctx.getExternalCacheDir();
        if (baseDir == null) {
            baseDir = new File("/sdcard/Android/data/" + ctx.getPackageName() + "/cache/");
            baseDir.mkdirs();
        }
        if (!baseDir.exists()) {
            return null;
        }
        return baseDir;
    }

    /**
     * 获取崩溃日志e的目录
     *
     * @param ctx Context
     * @return 目录File对象
     * *
     */
    public static File getCrashLogDir(Context ctx) {
        File baseDir = getExternalCacheDir(ctx);
        if (baseDir == null) {
            baseDir = ctx.getCacheDir();
        }
        File dir = new File(baseDir, "crash");
        dir.mkdirs();
        return dir;
    }

    /**
     * 获取jni崩溃日志的目录
     *
     * @param ctx Context
     * @return 目录File对象
     * *
     */
    public static File getJNICrashLogDir(Context ctx) {
        File baseDir = getExternalCacheDir(ctx);
        if (baseDir == null) {
            baseDir = ctx.getCacheDir();
        }
        File dir = new File(baseDir, "jnicrash");
        dir.mkdirs();
        return dir;
    }


    /**
     * 获取jni崩溃日志的目录
     *
     * @param ctx Context
     * @return 目录File对象
     * *
     */
    public static File getANRThreadTraceDir(Context ctx) {
        File baseDir = getExternalCacheDir(ctx);
        if (baseDir == null) {
            baseDir = ctx.getCacheDir();
        }
        File dir = new File(baseDir, "anrtrace");
        dir.mkdirs();
        return dir;
    }
}
