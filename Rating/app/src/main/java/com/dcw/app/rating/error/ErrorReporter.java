package com.dcw.app.rating.error;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.view.Display;
import android.view.WindowManager;

import com.dcw.app.rating.log.L;
import com.dcw.app.rating.util.BusinessUtil;
import com.dcw.app.rating.util.TaskExecutor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: neevek
 * Date: 12-7-8
 * Time: PM9:05
 * This class is for collecting crash log of the app and sending them to remote server
 */
public class ErrorReporter implements Thread.UncaughtExceptionHandler {
    //    private final static String ERR_LOG_TYPE = "9gameclient";
    private Context mContext;

    private final int JNICRASH = 0;
    private final int CRASH = 1;
    private final int ANRTRACE = 2;

    public ErrorReporter(Context ctx) {
        mContext = ctx;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void checkAndPostCrashLog() {
        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                postCrashLogAndDeleteStacktraceFiles(locateCrashLogFiles(BusinessUtil.getANRThreadTraceDir(mContext)), ANRTRACE);
                postCrashLogAndDeleteStacktraceFiles(locateCrashLogFiles(BusinessUtil.getCrashLogDir(mContext)), CRASH);
                postCrashLogAndDeleteStacktraceFiles(locateCrashLogFiles(BusinessUtil.getJNICrashLogDir(mContext)), JNICRASH);
            }

            private void postCrashLogAndDeleteStacktraceFiles(File[] files, int flag) {
                if (files != null && files.length > 0) {
                    boolean postResult = postCrashLog(files, flag);

                    deleteStackTraceFiles(files);
                    if (postResult && flag == 0) {
//                        BusinessStat.getInstance().addStat("jnibreakdown```", true);
                    }
                }
            }
        });
    }

    /*public void postInstantErrorLog(final String errMsg) {
        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                postErrorInfoToRemoteServer(errMsg);
            }
        });
    }*/

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        BusinessStat.getInstance().addStat("breakdown```", true);
        saveErrorReportAsFile(ex);
        ex.printStackTrace(System.err);
//        EmmaReport.generateCoverageReport(R.string.build);
        //wait for content provider to write breakdown log to db
        TaskExecutor.scheduleTask(1000, new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
    }

    private long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    private long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    private String collectDeviceInfo(boolean isJNICrash) {
        StringBuilder sb = new StringBuilder();
        sb.append("==== Device Info ====\n");
//        sb.append("UUID: ").append(BusinessUtil.getAppUUID(mContext)).append('\n');
        sb.append("Model: ").append(Build.MODEL).append('\n');
        sb.append("OS Version: ").append(Build.VERSION.RELEASE).append('\n');
        sb.append("SDK Version: ").append(Build.VERSION.SDK).append('\n');
        sb.append("Device Name: ").append(Build.DEVICE).append('\n');
        sb.append("Product Name: ").append(Build.PRODUCT).append('\n');
        sb.append("Display: ").append(Build.DISPLAY).append('\n');
        sb.append("Brand: ").append(Build.BRAND).append('\n');
        sb.append("-------------------------------\n");
        if (!isJNICrash) {
            sb.append("Proccess: ").append("fore").append('\n');
//            sb.append("Proccess: ").append(RatingApplication.getInstance().isMainProcess() ? "fore" : "back").append('\n');
        }
        sb.append("JNICrash: ").append(isJNICrash ? "Yes" : "No").append('\n');
        sb.append("Available ROM: ").append(getAvailableInternalMemorySize()).append('\n');
        sb.append("Total ROM: ").append(getTotalInternalMemorySize()).append('\n');
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ((ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(mi);
        sb.append("Available RAM: ").append(mi.availMem).append('\n');
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        sb.append("Screen Resolution: ").append(display.getWidth()).append('x').append(display.getHeight()).append('\n');
        return sb.toString();
    }

    private String collectPackageInfo() {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = null;

        try {
            pi = pm.getPackageInfo(mContext.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            L.w(e);
        }

        if (pi == null) {
            return "FATEL: PackageInfo is null, can't collect package info.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("==== Package Info ====\n");
        sb.append("Time: ").append(new Date());
        sb.append("Pkg Name: ").append(pi.packageName).append('\n');
        sb.append("Version Name: ").append(pi.versionName).append('\n');
        sb.append("Version Code: ").append(pi.versionCode).append('\n');

        if (Build.VERSION.SDK_INT >= 9) { // Android2.3
            sb.append("First Install Time: ").append(new Date(pi.firstInstallTime)).append('\n');
            sb.append("Last Update Time: ").append(new Date(pi.lastUpdateTime)).append('\n');

        } else { // Android 2.2
            File f = new File(pi.applicationInfo.sourceDir);
            sb.append("Last Update Time: ").append(new Date(f.lastModified())).append('\n');
        }

        return sb.toString();
    }

    private String collectStackTrace(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append("==== Stack Trace ====\n");
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        sb.append(result.toString());
        sb.append('\n');
        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        Throwable cause = e.getCause();

        if (cause != null) {
            cause.printStackTrace(printWriter);
            sb.append("==== Root Cause ====\n");
            sb.append(result.toString());
            sb.append('\n');
        }

        printWriter.close();
        return sb.toString();
    }

    private void saveErrorReportAsFile(Throwable e) {
        FileOutputStream fos = null;

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Crash Report (").append(new Date()).append(")\n\n");
            sb.append(collectPackageInfo()).append('\n');
            sb.append(collectDeviceInfo(false)).append('\n');
            sb.append(collectStackTrace(e)).append('\n');
            String fileName = System.currentTimeMillis() + ".stacktrace";
            File file = new File(BusinessUtil.getCrashLogDir(mContext), fileName);
            fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes("utf-8"));

        } catch (Exception ex) {
            // do nothing
            L.e(e);

        } finally {
            if (fos != null) {
                try {
                    fos.close();

                } catch (Exception ex) {
                    // do nothing
                    L.w(e);
                }
            }
        }
    }

    private boolean postCrashLog(File[] files, int flag) {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                // we want descending order, so the leading '-' sign
                return -f1.getName().compareTo(f2.getName());
            }
        });
        File file = files[0];
        String stackTrace = readFileAsString(files.length, file);
        if (flag == 0 || flag == 2) {
            StringBuilder sb = new StringBuilder();
            if (flag == 0) {
                sb.append("Crash Report (").append(new Date()).append(")\n\n");
            } else {
                sb.append("AnrTrace Report (").append(new Date()).append(")\n\n");
            }
            sb.append(collectPackageInfo()).append('\n');
            if (flag == 0) {
                sb.append(collectDeviceInfo(true)).append('\n');
            } else {
                sb.append(collectDeviceInfo(false)).append('\n');
            }
            sb.append(stackTrace);
            if (flag == 0) {
                String helpfulJavaCrashInfo = null;
                File f = new File(BusinessUtil.getJNICrashLogDir(mContext) + "/zHelpfulJavaCrashInfo.logcat");
                if (f.exists()) {
                    helpfulJavaCrashInfo = readLogcatFile(f);   //读取java log辅助文件
                }
                if (null != helpfulJavaCrashInfo) {
                    sb.append("==== Logcat ====\n");
                    sb.append(helpfulJavaCrashInfo);
                }
            }
            stackTrace = sb.toString();
        }
        return postErrorInfoToRemoteServer(stackTrace);
    }

    private void deleteStackTraceFiles(File[] files) {
        for (File f : files) {
            boolean deleted = f.delete();
            if (!deleted) {
                f.renameTo(new File(f.getAbsoluteFile() + ".deleted"));
            }
        }
    }

    private File[] locateCrashLogFiles(File dir) {
        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".stacktrace");
            }
        });
    }

    private boolean postErrorInfoToRemoteServer(String info) {
//        HttpPost httpPost = new HttpPost(OperationHelper.buildUrl(mContext, URIConfig.URL_ERROR_REPORT_LOG, ApiHost.HOST_TYPE_LOG));
//
//        try {
//            String msg = null;
//            try {
//                msg = java.net.URLEncoder.encode(info, "utf-8");
//            } catch(UnsupportedEncodingException e) {
//                msg = "postErrorInfoToRemoteServer UnsupportedEncodingException";
//            }
//            StringEntity formParameterEntity = new StringEntity("msg=" + msg);
//            formParameterEntity.setContentType("application/x-www-form-urlencoded");
//            httpPost.setEntity(formParameterEntity);
//            HttpResponse response = HttpClientWrapper.getHttpClient().execute(httpPost);
//
//            if (response.getStatusLine().getStatusCode() == 200) {
//                // success ...
//                return true;
//            }
//
//        } catch (Exception e) {
//            L.w(e);
//        }
//
//        return false;
        return true;
    }

    private String readFileAsString(int fileCount, File file) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            sb.append("==== File Info ====\n");
            sb.append("Name: ").append(file.getAbsolutePath()).append('\n');
            sb.append("Size: ").append(file.length()).append('\n');
            sb.append("File count: ").append(fileCount).append('\n');
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

        } catch (Exception e) {
            // do nothing
            sb.append("read file " + file.getAbsolutePath() + " cause exception\n");
            sb.append(e.toString());
            L.w(e);
        } finally {
            if (br != null) {
                try {
                    br.close();

                } catch (IOException e) {
                    // do nothing
                    L.w(e);
                }
            }
        }

        return sb.toString();
    }


    private String readLogcatFile(File file) {

        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (Exception e) {
            // do nothing
            sb.append("read  file " + file.getAbsolutePath() + " cause exception\n");
            sb.append(e.toString());
            L.w(e);
        } finally {
            if (br != null) {
                try {
                    br.close();

                } catch (IOException e) {
                    // do nothing
                    L.w(e);
                }
            }
        }

        boolean deleted = file.delete();
        if (!deleted) {
            file.renameTo(new File(file.getAbsoluteFile() + ".deleted"));
        }

        return sb.toString();

    }


}
