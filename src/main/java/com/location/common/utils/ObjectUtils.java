package com.location.common.utils;

public class ObjectUtils extends org.springframework.util.ObjectUtils {

    /**
     * @description true:不为空，false：为空
     * @param o
     * @return
     */
    public static boolean isNotEntity(Object o) {
        return !org.springframework.util.ObjectUtils.isEmpty(o);
    }

    /**
     * @description true:不为空，false：为空
     * @param o
     * @return
     */
    public static boolean isNotEntity(Object[] o) {
        return !org.springframework.util.ObjectUtils.isEmpty(o);
    }
}
