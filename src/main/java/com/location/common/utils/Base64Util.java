package com.location.common.utils;

import sun.misc.BASE64Decoder;


public class Base64Util {

    /**
     * 编码
     *
     * @param str
     * @return String
     */
    public static String encode(String str) {
        if (str == null) return null;
        return (new sun.misc.BASE64Encoder()).encode( str.getBytes() );
    }

    /**
     * 解码
     *
     * @param str
     * @return string
     */
    public static String getFromBASE64(String str) {
        if (str == null) return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(str);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
}
