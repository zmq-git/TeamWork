package com.location.common.exception;

import com.location.bean.vo.ResponseVO;
import com.location.common.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangcl on 2019/8/22.
 * WEB异常全局处理
 */
@ControllerAdvice(basePackages = "com.location.Controller")
public class WebExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVO handleException(Exception e) {

        if (e instanceof WebException) {
            return ResponseUtil.wrapException((WebException) e);
        } else {
            return ResponseUtil.wrapException(new WebException("9999", "异常信息： " + e.getMessage()));
        }
    }

}
