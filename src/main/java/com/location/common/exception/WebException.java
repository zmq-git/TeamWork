package com.location.common.exception;

import com.location.common.enums.WebExceptionEnum;

/**
 * Created by wangcl on 2019/8/22.
 * Web异常
 */
public class WebException extends RuntimeException {

    public WebException(String errCode, String errMessage) {
        super(errMessage);
        this.code = errCode;
    }

    public WebException(WebExceptionEnum webExceptionEnum) {
        super(webExceptionEnum.getErrMessage());
        this.code = webExceptionEnum.getErrCode();
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
