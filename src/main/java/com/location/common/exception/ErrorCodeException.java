package com.location.common.exception;

import com.location.common.enums.ResultCodeEnum;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 错误编码异常
 * Created by bawy on 2018/7/6 9:44.
 */
public class ErrorCodeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -3693189556505272571L;

    /**
     * 错误码
     * */
    private String code;

    /**
     * 错误信息
     */
    private String message;



    public ErrorCodeException() {
        code = ResultCodeEnum.UNDEFINE_ERROR.getErrorCode();
        message = ResultCodeEnum.UNDEFINE_ERROR.getErrorMsg();
    }


    public ErrorCodeException(ResultCodeEnum resultEnum, Object... args) {
        this.code = resultEnum.getErrorCode();
        String tempMsg = resultEnum.getErrorMsg();
        if (args != null){
            tempMsg = MessageFormat.format(tempMsg, args);
        }
        this.message = tempMsg;
    }

    public String getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
