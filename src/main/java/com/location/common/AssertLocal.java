package com.location.common;

import com.location.common.enums.ResultCodeEnum;
import com.location.common.exception.ErrorCodeException;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * CSOP项目校验类
 * Created by bawy on 2018/7/6 10:37.
 */
public class AssertLocal extends Assert {

    /**
     * 校验表达式是否为真（异常编码为参数类型）
     * @author bawy
     * @date 2018/7/6 13:11
     * @param expression 表达式
     * @param message 提示信息
     */
    public static void isTrue(boolean expression, String message) {
        isTrue(expression, ResultCodeEnum.PARAM_ERROR, message);
    }

    /**
     * 校验表达式是否为真
     * @author bawy
     * @date 2018/7/6 13:14
     * @param expression 表达式
     * @param resultCodeEnum 异常编码枚举
     * @param message 提示信息
     */
    public static void isTrue(boolean expression, ResultCodeEnum resultCodeEnum, Object... message) {
        if (!expression) {
            throw new ErrorCodeException(resultCodeEnum, message);
        }
    }

    /**
     * 校验对象不能为空（异常编码为参数类型）
     * @author bawy
     * @date 2018/7/6 11:16
     * @param object 被校验对象
     * @param message 提示信息
     */
    public static void notNull(Object object, String message) {
        notNull(object, ResultCodeEnum.PARAM_ERROR, message);
    }

    /**
     * 校验对象不能为空（异常编码为参数类型）
     * @author majade
     * @date 2018/7/6 11:16
     * @param object 被校验对象
     * @param message 提示信息
     */
    public static void isNotNull(Object object, String message) {
        notNull(object,message);
    }
    /**
     * 校验对象不能为空（异常编码为自定义类型）
     * @author bawy
     * @date 2018/7/6 11:18
     * @param object 被校验对象
     * @param resultCodeEnum 异常编码枚举
     * @param messages 提示信息数组
     */
    public static void notNull(Object object, ResultCodeEnum resultCodeEnum, Object... messages) {
        if (object == null) {
            throw new ErrorCodeException(resultCodeEnum, messages);
        }
    }

    /**
     * 校验对象必须为空,若不未空，则抛出异常。
     * @author bawy
     * @date 2018/7/6 13:17
     * @param object 被校验对象
     * @param resultCodeEnum 异常编码枚举
     * @param messages 提示信息数组
     */
    public static void isNull(Object object, ResultCodeEnum resultCodeEnum, Object... messages) {
        if (object != null) {
            throw new ErrorCodeException(resultCodeEnum, messages);
        }
    }


    /**
     * 校验对象不未空，则顺利通过，若反之，则抛出异常
     * @author bawy
     * @date 2018/7/6 13:17
     * @param object 被校验对象
     * @param resultCodeEnum 异常编码枚举
     * @param messages 提示信息数组
     */
    public static void isNotNull(Object object, ResultCodeEnum resultCodeEnum, Object... messages) {
        if (object == null) {
            throw new ErrorCodeException(resultCodeEnum, messages);
        }
    }

    /**
     * 校验字符串不为空且长度大于0（异常编码为参数类型）
     * @author bawy
     * @date 2018/7/6 13:26
     * @param text 字符串
     * @param message 提示信息
     */
    public static void hasLength(String text, String message){
        hasLength(text, ResultCodeEnum.PARAM_ERROR, message);
    }

    /**
     * 校验字符串不为空且长度大于0
     * @author bawy
     * @date 2018/7/6 13:25
     * @param text 字符串
     * @param resultCodeEnum 异常编码枚举
     * @param messages 提示信息数组
     */
    public static void hasLength(String text, ResultCodeEnum resultCodeEnum, Object... messages) {
        if (text==null || text.length()==0) {
            throw new ErrorCodeException(resultCodeEnum, messages);
        }
    }

    /**
     * 校验数组不能为空
     * @param array 数组
     * @param resultCodeEnum 错误编码枚举
     * @param messages 提示信息数组
     */
    public static void notEmpty(Object[] array, ResultCodeEnum resultCodeEnum, Object... messages) {
        if (ObjectUtils.isEmpty(array)) {
            throw new ErrorCodeException(resultCodeEnum, messages);
        }
    }

}
