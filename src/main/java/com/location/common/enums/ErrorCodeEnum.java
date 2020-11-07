package com.location.common.enums;

/**
 * Created by wangcl on 2019/8/22.
 * ErrorCode枚举
 */
public enum ErrorCodeEnum {

    SUCCESS("0000");

    ErrorCodeEnum(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorCode;

    public String getErrorCode() {
        return this.errorCode;
    }

}
