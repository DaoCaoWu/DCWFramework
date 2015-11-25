package com.dcw.framework.container.tools.endecode;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class Md5Utils {

    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        String str = null;
        MessageDigest messageDigest = null;
        if (file != null) {
            try {
                messageDigest = MessageDigest.getInstance("MD5");
                fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        messageDigest.update(bArr, 0, read);
                    } else {
                        str = EndecodeUtil.byteToHexString(messageDigest.digest(), "");
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e2) {
                            }
                        }
                        return str;
                    }
                }
            } catch (FileNotFoundException e3) {
                safeClose(fileInputStream);
                fileInputStream = null;
            } catch (IOException e4) {
                safeClose(fileInputStream);
                fileInputStream = null;
            } catch (Throwable th) {
                safeClose(fileInputStream);
                fileInputStream = null;
            }
        }

        return str;
    }

    private static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMD5(String str) {
        if (str == null) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes();
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bytes);
            return EndecodeUtil.byteToHexString(instance.digest(), "");
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] getMD5(InputStream inputStream) {
        byte[] bArr = null;
        if (inputStream != null) {
            MessageDigest instance;
            try {
                instance = MessageDigest.getInstance("MD5");
                if (instance != null) {
                    byte[] bArr2 = new byte[8192];
                    while (true) {
                        int read = inputStream.read(bArr2);
                        if (read != -1) {
                            instance.update(bArr2, 0, read);
                        } else {
                            bArr = instance.digest();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return bArr;
    }

    public static byte[] getMD5(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (Exception e) {
            return null;
        }
    }
}