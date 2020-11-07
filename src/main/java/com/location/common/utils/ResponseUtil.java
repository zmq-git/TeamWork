package com.location.common.utils;

import com.location.common.exception.ErrorCodeException;
import com.location.bean.vo.ResponseVO;
import com.location.common.constants.ErrorMessageConst;
import com.location.common.enums.ErrorCodeEnum;
import com.location.common.exception.WebException;

/**
 * Created by wangcl on 2019/8/22.
 * 封装返回页面对象工具类
 */
public class ResponseUtil {

    // 成功返回对象
    public static <T> ResponseVO<T> wrap(T result) {
        return new ResponseVO(ErrorCodeEnum.SUCCESS.getErrorCode(), ErrorMessageConst.SUCCESS, result);
    }

    // 返回异常对象
    public static <T> ResponseVO<T> wrapException(WebException webException, T... result) {
        return new ResponseVO(webException.getCode(), webException.getMessage(), result);
    }

    public static <T> ResponseVO<T> wrapErrorCodeException(ErrorCodeException ErrorCodeException, T... result) {
        return new ResponseVO(ErrorCodeException.getCode(), ErrorCodeException.getMessage(), result);
    }
}
