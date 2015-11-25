package com.dcw.framework.container.tools.endecode;

import android.util.Base64;

public class EndecodeUtil {

    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    protected static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private final static int[] COMMON_M8_KEY = {238, 185, 233, 179, 129, 142, 151, 167};
    
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        // 由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        return value;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        // 由高位到低位
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static String byteToHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            char c0 = hexDigits[(b & 0xf0) >> 4];
            char c1 = hexDigits[b & 0xf];
            hexString.append(c0);
            hexString.append(c1).append(separator);
        }

        return hexString.toString().toUpperCase();
    }

    public static byte[] base64Decode(byte[] base64Array) {
        return Base64.decode(base64Array, Base64.DEFAULT);
    }
    
    public static byte[] base64Decode(String str) {
        return Base64.decode(str, Base64.DEFAULT);
    }
    
    public static byte[] base64Decode(String str, int flag) {
        return Base64.decode(str, flag);
    }
    
    public static byte[] base64Decode(byte[] data, int flag) {
        return Base64.decode(data, flag);
    }
    
    public static String base64Encode2String(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    
    public static String base64Encode2String(byte[] bytes, int flag) {
        return Base64.encodeToString(bytes, flag);
    }
    
    public static byte[] base64Encode(byte[] bytes) {
        return Base64.encode(bytes, Base64.DEFAULT);
    }
    
    public static byte[] base64Encode(byte[] bytes, int flag) {
        return Base64.encode(bytes, flag);
    }

    /**
     * 自定义M8加密算法
     * @param srcArray byte[] 待解密的字节数组
     * @param maskArray int[] 密钥)
     * @return byte[] 加密后的字节数组
     */
    private static byte[] m8Encode(byte[] srcArray, final int[] maskArray) {

        if (srcArray == null || maskArray==null || maskArray.length != 8)
            return null;

        byte mask = 0;
        byte a = 0;
        byte b = 0;

        int srcLen = srcArray.length;
        byte[] desBuffer = null;

        try{
            desBuffer = new byte[srcLen + 2];
        }catch(Exception e){
            return null;
        }

        for (int i = 0; i < srcLen; i++)
        {
            a = srcArray[i];
            b = (byte) (a ^ maskArray[i % 8]);
            desBuffer[i] = (byte)b;
            mask = (byte) (mask ^ a);
        }

        desBuffer[srcLen] = (byte)(mask ^ maskArray[0]);
        desBuffer[srcLen + 1] = (byte)(mask ^ maskArray[1]);

        return desBuffer;
    }
    

    /**
     * 判断一个字符是否为ascii码
     * 
     * @param c
     * @return
     */
    public static boolean isAscii(char c) {
        return c / 0x80 == 0;
    }

    /**
     * first m8 encrypt the string as "utf-8", then base64 encode the result by m8 encrypted.
     * NOTE: m8 encrypt use the key: COMMON_M8_KEY, and base64 encode use the flag Base64.NO_WRAP.
     * @param str
     * @return
     */
    public static String m8AndBase64EncodeString(String str) {
        if (null == str || 0 == str.length()) {
            return "";
        }
        
        byte[] data = null;
        
        try {
            data = str.getBytes("utf-8");
        } catch ( Throwable e ) {
            e.printStackTrace();
        }
        
        data = m8Encode(data, COMMON_M8_KEY);
        return base64Encode2String(data, Base64.NO_WRAP);
    }

}
