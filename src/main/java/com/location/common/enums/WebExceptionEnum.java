package com.location.common.enums;

/**
 * Created by wangcl on 2019/8/22.
 * WEB异常枚举
 */
public enum WebExceptionEnum {

    NO_LOGIN("1001", "用户未登录"),
    PERMISSION_DENIED("2001", "权限不足"),
    OTHER_EXCEPTION("9999", "其他异常");

    WebExceptionEnum(String errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    private String errCode;

    private String errMessage;

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
