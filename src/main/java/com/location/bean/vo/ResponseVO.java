package com.location.bean.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangcl on 2019/8/22.
 * 返回页面封装对象
 */
@ApiModel("页面返回统一封装对象")

//新加
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ResponseVO<T> {

    public ResponseVO(String errCode, String errMessage, T data) {
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.data = data;
    }

    //新加
    public ResponseVO() {
    }

    @ApiModelProperty("错误编码（成功：0000，失败：非0000）")
    private String errCode;

    @ApiModelProperty("错误信息")
    private String errMessage;

    @ApiModelProperty("实际返回对象")
    private T data;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseVO{" +
                "errCode='" + errCode + '\'' +
                ", errMessage='" + errMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
